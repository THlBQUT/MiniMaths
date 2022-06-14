package fr.ensisa.minimaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Settings extends AppCompatActivity {

    private GoogleSignInClient client;
    private GoogleSignInAccount account;
    private int RC_CONNEXION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SharedPreferences preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().build();
        client = GoogleSignIn.getClient(this,gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
    }

    /*public static class SettingsFragment extends PreferenceFragmentCompat {
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            setPreferencesFromResource(R.xml.root_preferences, rootKey);
        }
    }*/
    public void goToHome(View v){

    }

    public void goToRanking(View v){
//
    }

    public void backButton(View v){
        onBackPressed();
    }

    public void googleConnect(View v){
        Intent connexionIntent = client.getSignInIntent();
        startActivityForResult(connexionIntent, RC_CONNEXION);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == RC_CONNEXION){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            connectToService(task);
        }
    }

    public void connectToService(Task<GoogleSignInAccount> completedTask){
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            FirebaseDatabase database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference();

            reference.get().addOnCompleteListener(task1 ->{
                if(task1.getResult().getValue() == null){
                    DatabaseReference child = reference.child("email");
                    child.setValue(account.getEmail());

                    child = reference.child("name");
                    child.setValue(account.getDisplayName());
                }
                else {
                    Log.i("googleAuth","Connected As" + task1.getResult());
                }
            });
            Toast.makeText(getApplicationContext(),"Welcome " + account.getDisplayName(), Toast.LENGTH_LONG).show();
        } catch (ApiException e) {
            Toast.makeText(getApplicationContext(),"Connexion error", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }

    }
}
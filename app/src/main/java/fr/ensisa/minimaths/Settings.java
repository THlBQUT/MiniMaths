package fr.ensisa.minimaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
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

import de.hdodenhof.circleimageview.CircleImageView;
import fr.ensisa.minimaths.Credits;

public class Settings extends AppCompatActivity {

    private GoogleSignInClient client;
    private GoogleSignInAccount account;
    private int RC_CONNEXION;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Switch musique, vibration;
    private String pseudo;
    private EditText editPseudo;
    private Vibrator vibrator;
    private CircleImageView img = findViewById(R.id.imgAccount);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        musique = findViewById(R.id.settings_button_musique);
        musique.setChecked(preferences.getBoolean("SHARED_PREF_MAIN_MUSIQUE", true));
        musique.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("SHARED_PREF_MAIN_MUSIQUE", isChecked);
                editor.apply();
                if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        });
        account = GoogleSignIn.getLastSignedInAccount(this);
        if (account != null){
            editPseudo.setText(account.getDisplayName());
        }

        vibration = findViewById(R.id.settings_button_vibration);
        vibration.setChecked(preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true));
        vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                editor.putBoolean("SHARED_PREF_MAIN_VIBRATION", isChecked);
                editor.apply();
                if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
                    vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
            }
        });

        pseudo = preferences.getString("SHARED_PREF_MAIN_PSEUDO", "");
        editPseudo = findViewById(R.id.settings_edittext_pseudo);
        editPseudo.setText(pseudo);
        editPseudo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                String nom = editPseudo.getText().toString();
                editor.putString("SHARED_PREF_MAIN_PSEUDO", editPseudo.getText().toString());
                editor.apply();
            }
        });

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().requestProfile().build();
        client = GoogleSignIn.getClient(this,gso);
        account = GoogleSignIn.getLastSignedInAccount(this);
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void goToRanking(View v){
        Intent ranking = new Intent(this, Ranking.class);
        startActivity(ranking);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void showCredits(View v) {
        Intent activityCredits = new Intent(this, Credits.class);
        startActivity(activityCredits);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
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
            DatabaseReference reference = database.getReference("user/" + account.getId());

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
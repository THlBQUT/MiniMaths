package fr.ensisa.minimaths;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.Iterator;

public class Ranking extends AppCompatActivity {

    private ListView rankLayout;
    private Vibrator vibrator;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private long userCount = -1;
    private ArrayList<String> userListID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        editor = preferences.edit();
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        this.rankLayout = this.findViewById(R.id.listRank);
    }

    public void createRank(View v) {
        database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("user/");
        DatabaseReference user = database.getReference("user");
        user.get().addOnCompleteListener(task -> userCount = task.getResult().getChildrenCount());
        if(userCount != -1) Log.e("NB OF USER ", String.valueOf(userCount));
        user.get().addOnCompleteListener(task -> {
            Iterable<DataSnapshot> datasnapshot = task.getResult().getChildren();
            while ( datasnapshot.iterator().hasNext() ){
                userListID.add(datasnapshot.iterator().next().getKey());
            }
        });
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
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
}
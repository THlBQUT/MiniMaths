package fr.ensisa.minimaths;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.PreferenceFragmentCompat;

public class Settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        SharedPreferences preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
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
}
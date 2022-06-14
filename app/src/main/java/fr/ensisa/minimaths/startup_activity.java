package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;

public class startup_activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        Intent intent = new Intent(this, MainActivity.class);
        Runnable waiting = new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(2000);
                startActivity(intent);
            }
        };
        Thread thread1 = new Thread(waiting);
        thread1.start();
    }
}
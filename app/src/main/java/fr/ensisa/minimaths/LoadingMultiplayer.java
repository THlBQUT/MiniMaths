package fr.ensisa.minimaths;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class LoadingMultiplayer extends AppCompatActivity {

    private Animation animFadein;
    private CardView chargementCard;
    private boolean abortTheMission=false;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private String roomName;
    private String difficulty;
    private String role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading_game);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            roomName = extras.getString("ID_PARTY");
            difficulty = extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS);
            role = extras.getString("ROLE");
        }

        chargementCard = findViewById(R.id.texte_chargement);
        animFadein = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in);
        animFadein.setStartOffset(300);
        chargementCard.startAnimation(animFadein);

        database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("multiplayer_room/" + roomName);
        Log.e("roomName ", roomName);
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if( snapshot.getKey().equals("isReady"))
                    if( (boolean)snapshot.getValue() == true){
                        if(Objects.equals(role, "HOST")){
                            reference = database.getReference("ingame_room/" + roomName);
                            reference.get().addOnCompleteListener(task1 ->{
                                if(task1.getResult().getValue() == null){
                                    DatabaseReference child = reference.child("difficulty");
                                    child.setValue(difficulty.toLowerCase());
                                    DatabaseReference progress = reference.child("progress");
                                    progress.setValue(50);
                                    DatabaseReference combo1 = reference.child("comboHost");
                                    combo1.setValue(0);
                                    DatabaseReference combo2 = reference.child("comboGuest");
                                    combo2.setValue(0);
                                    database.getReference("/multiplayer_room/" + roomName).removeValue();
                                }
                                else {
                                    Toast.makeText(LoadingMultiplayer.this, "Erreur", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                        Intent multiLazer = new Intent(LoadingMultiplayer.this, LazerMulti.class);
                        multiLazer.putExtra("ID_PARTY", roomName);
                        multiLazer.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS,difficulty);
                        multiLazer.putExtra("ROLE",role);
                        startActivity(multiLazer);
                        finish();
                        Log.e("Difficulte", difficulty);
                    }
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        this.abortTheMission = true;
    }
}

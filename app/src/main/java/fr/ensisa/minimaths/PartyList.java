package fr.ensisa.minimaths;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class PartyList extends AppCompatActivity {

    private GoogleSignInAccount account;
    private String playerName;
    private ArrayList<Room> roomList= new ArrayList<Room>();
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;
    private LinearLayout linearLayout;
    private String difficulty;
    private DatabaseReference reference;
    private FirebaseDatabase database;
    private Vibrator vibrator;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);

        preferences = getSharedPreferences("SHARED_PREF_MAIN", MODE_PRIVATE);
        vibrator = (Vibrator) this.getSystemService(VIBRATOR_SERVICE);

        RecyclerView recyclerView = findViewById(R.id.rvRoom);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {this.difficulty= extras.getString(Constantes.ID_DIFFICULTY_NAME_EXTRAS);}
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(this, roomList);
        recyclerView.setAdapter(roomAdapter);

        for(Room r : roomList){
            Log.e("ROOM ", r.getName());
        }
        Log.e("ROOM SIZE ON CREATE", String.valueOf(roomList.size()));

        database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
        reference = database.getReference("multiplayer_room/");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Log.e("ADDED ONE ",snapshot.getKey());
                roomList.add(new Room(snapshot.getKey(), (String) snapshot.child("difficulty").getValue()));
                roomAdapter.notifyItemInserted(roomList.size()-1);
                Log.e("RoomList size ", String.valueOf(roomList.size()));
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.getKey().equals("isReady")){
                    Log.e("ParentKey :", previousChildName);
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int index = -1;
                for (int i = 0; i<roomList.size(); i++){
                    if (roomList.get(i).getName().equals(dataSnapshot.getKey().toString())){
                        Log.e("Deleted item ", String.valueOf(i));
                        index = i;
                    }
                }
                if(index != -1) roomList.remove(index);
                roomAdapter.notifyItemRemoved(index);
                for ( Room r : roomList){
                    Log.e("Room :" , r.getName());
                }
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    public void createParty(View v){
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
            playerName = account.getDisplayName();
        else{
            playerName = "Guest";
        }
        Log.e("Creating", "New entry inside roomList");
        reference = database.getReference("multiplayer_room/" + playerName);
        reference.get().addOnCompleteListener(task1 ->{
            if(task1.getResult().getValue() == null){
                DatabaseReference child = reference.child("difficulty");
                child.setValue(difficulty.toLowerCase());
                DatabaseReference dbRef = reference.child("isReady");
                dbRef.setValue(false);
                Intent loading = new Intent(this, LoadingMultiplayer.class);
                loading.putExtra("ID_PARTY", playerName);
                loading.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS,difficulty);
                loading.putExtra("ROLE","HOST");
                startActivity(loading);
            }
            else {
                Toast.makeText(this, "Une room a votre nom existe deja", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void backButton(View v){
        onBackPressed();
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToHome(View v){
        Intent home = new Intent(this, MainActivity.class);
        startActivity(home);
        overridePendingTransition(0, android.R.anim.slide_out_right);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }

    public void goToRanking(View v){
        Intent ranking = new Intent(this, Ranking.class);
        startActivity(ranking);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
        finish();
    }

    public void goToSettings(View v){
        Intent settingsIntent = new Intent(this, Settings.class);
        startActivity(settingsIntent);
        if (preferences.getBoolean("SHARED_PREF_MAIN_VIBRATION", true))
            vibrator.vibrate(VibrationEffect.createOneShot(30, VibrationEffect.DEFAULT_AMPLITUDE));
    }
}
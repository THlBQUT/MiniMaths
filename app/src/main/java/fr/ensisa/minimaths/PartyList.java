package fr.ensisa.minimaths;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class PartyList extends AppCompatActivity {

    private GoogleSignInAccount account;
    private String playerName;
    private ArrayList<Room> roomList= new ArrayList<Room>(){{
        add(new Room("room 1", "facile"));
        add(new Room("room 2", "moyen"));
        add(new Room("room 3", "difficile"));
    }};
    private RecyclerView recyclerView;
    private RoomAdapter roomAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_party_list);
        RecyclerView recyclerView = findViewById(R.id.rvRoom);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        roomAdapter = new RoomAdapter(this, roomList);
        recyclerView.setAdapter(roomAdapter);
    }

    public void createParty(View v){
        account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null)
            playerName = account.getDisplayName();
        else{
            playerName = "Guest";
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
        DatabaseReference reference = database.getReference("multiplayer_room/" + playerName);

        reference.get().addOnCompleteListener(task1 ->{
            if(task1.getResult().getValue() == null){
                DatabaseReference child = reference.child("ID");
                child.setValue(playerName);
            }
            else {
                Toast.makeText(this, "Une room a votre nom existe deja", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void launchRoom(View v){
        Toast.makeText(getApplicationContext(), "Room ",Toast.LENGTH_LONG).show();
    }
}
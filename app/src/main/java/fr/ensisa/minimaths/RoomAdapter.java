package fr.ensisa.minimaths;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

    private PartyList context;
    private ArrayList<Room> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    RoomAdapter(Context context, ArrayList<Room> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.context = (PartyList) context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.room_list_fragment, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Room room = mData.get(position);
        holder.roomName.setText(room.getName());
        holder.roomDifficulty.setText("Difficulté " + room.getDifficulty());
        switch (room.getDifficulty()){
            case "facile":
                holder.roomDifficulty.setTextColor(Color.GREEN);
                break;
            case "moyen":
                holder.roomDifficulty.setTextColor(Color.rgb(255,165,0));
                break;
            case "difficile":
                holder.roomDifficulty.setTextColor(Color.RED);
                break;
        }
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView roomName;
        TextView roomDifficulty;

        ViewHolder(View itemView) {
            super(itemView);
            roomName = itemView.findViewById(R.id.room_name);
            roomDifficulty = itemView.findViewById(R.id.room_difficulty);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            Log.e("Click on :",roomName.getText() + " : OK \r\n" + "difficulty : " + roomDifficulty.getText());
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
            FirebaseDatabase database = FirebaseDatabase.getInstance("https://minimaths-84e80-default-rtdb.europe-west1.firebasedatabase.app/");
            DatabaseReference reference = database.getReference("multiplayer_room/" + roomName.getText());
            Intent loading = new Intent(context, LoadingMultiplayer.class);
            loading.putExtra("ID_PARTY", roomName.getText());
            loading.putExtra(Constantes.ID_DIFFICULTY_NAME_EXTRAS, String.valueOf(roomDifficulty.getText()).split(" ")[1].toUpperCase());
            loading.putExtra("ROLE","GUEST");
            context.startActivity(loading);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    reference.child("isReady").setValue(true);
                    context.finish();
                }
            },2000);
        }
    }

    // convenience method for getting data at click position
    Room getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
package fr.ensisa.minimaths;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    private TextView roomName;
    private TextView roomDifficulty;

    public MyViewHolder(View itemview){
        super(itemview);

        roomName = (TextView) itemview.findViewById(R.id.room_name);
        roomDifficulty = (TextView) itemview.findViewById(R.id.room_difficulty);
    }

    public void display(Room room){

        roomName.setText(room.getName());
        roomDifficulty.setText(room.getDifficulty());
    }
}

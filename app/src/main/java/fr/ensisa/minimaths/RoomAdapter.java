package fr.ensisa.minimaths;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.ViewHolder> {

        private ArrayList<Room> mData;
        private LayoutInflater mInflater;
        private ItemClickListener mClickListener;

        // data is passed into the constructor
        RoomAdapter(Context context, ArrayList<Room> data) {
            this.mInflater = LayoutInflater.from(context);
            this.mData = data;
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
            holder.roomDifficulty.setText(room.getDifficulty());
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
                if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
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
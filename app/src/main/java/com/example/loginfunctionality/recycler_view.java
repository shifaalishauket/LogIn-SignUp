package com.example.loginfunctionality;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;


class noteListAdapter extends RecyclerView.Adapter<noteListAdapter.ViewHolder> {
    private List<Note> noteList;

    public noteListAdapter(List<Note> noteList) {
        this.noteList = noteList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_my_view, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Note note = noteList.get(position);
        holder.name.setText(note.getUsername());
        holder.emailStr.setText(note.getEmail());
        holder.pss.setText(note.getPassword());
        Bitmap myBitmap = BitmapFactory.decodeFile(note.getImagePath());

        holder.image.setImageBitmap(myBitmap);

    }

    @Override
    public int getItemCount() {

        return noteList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView emailStr;

        public TextView name;
        public TextView pss;
        private ImageView image;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);
            name = itemView.findViewById(R.id.q1);
            emailStr = itemView.findViewById(R.id.q2);
            pss = itemView.findViewById(R.id.q3);
            image = itemView.findViewById(R.id.imageView);

        }

    }
}


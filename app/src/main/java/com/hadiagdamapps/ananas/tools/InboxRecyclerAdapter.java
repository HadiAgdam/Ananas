package com.hadiagdamapps.ananas.tools;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.hadiagdamapps.ananas.tools.InboxModel;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hadiagdamapps.ananas.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class InboxRecyclerAdapter extends RecyclerView.Adapter<InboxRecyclerAdapter.Holder> {

    private final ArrayList<InboxModel> list;
    private final Context context;


    private void action(String username) {
        // TODO "open chat page with username"
    }

    public InboxRecyclerAdapter(ArrayList<InboxModel> list, Context context) {
        this.list = list;
        this.context = context;
    }


    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(context).inflate(R.layout.inbox_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        InboxModel model = list.get(position);

        ImageView image = holder.itemView.findViewById(R.id.profileImage);
        TextView chatName = holder.itemView.findViewById(R.id.chatName);
        TextView counterText = holder.itemView.findViewById(R.id.counterText);

        Picasso.get().load(model.getImage()).into(image);
        chatName.setText(model.getTitle());
        counterText.setText(String.valueOf(model.getCount()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                action(model.getUsername());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

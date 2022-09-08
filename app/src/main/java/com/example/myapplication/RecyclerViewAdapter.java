package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

    private final ListInterface listInterface;

    Context context;
    ArrayList<Pattern_List_Item> Pattern_List;

    public RecyclerViewAdapter(Context context, ArrayList<Pattern_List_Item> Pattern_List,
                               ListInterface listInterface){
        this.context = context;
        this.Pattern_List = Pattern_List;
        this.listInterface = listInterface;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.list_items, parent, false);

        return new MyViewHolder(view, listInterface);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.Name.setText(Pattern_List.get(position).getName());
        holder.Image.setImageResource(Pattern_List.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return Pattern_List.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView Image;
        TextView Name;


        public MyViewHolder(@NonNull View itemView, ListInterface listInterface) {
            super(itemView);

            Image = itemView.findViewById(R.id.imageView);
            Name = itemView.findViewById(R.id.textView);

            itemView.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view){
                    if(listInterface != null){
                        int pos = getAdapterPosition();
                        if(pos != RecyclerView.NO_POSITION){
                            listInterface.onItemClick(pos);
                        }
                    }

                }
            });
        }
    }
}

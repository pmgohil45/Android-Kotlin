package com.example.contact.contact.recycler_adpter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.contact.R;

import java.util.List;

public class Recycler_Adpter extends RecyclerView.Adapter<Recycler_Adpter.MyViewHolder>{

    private Context mContext;
    private int focusedItem = 0;
    private List<Contact_recycler> c_List;
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {


        View itemView = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.list_contact, viewGroup, false);
        return new MyViewHolder(itemView);


    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        Contact_recycler con=c_List.get(i);
        myViewHolder.Name.setText(con.getName());
    }

    @Override
    public int getItemCount() {
        return c_List.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
 public  TextView Name;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            Name=(TextView)itemView.findViewById(R.id.txt_name_recycler);
        }

    }
    public Recycler_Adpter(List<Contact_recycler> c_List)
    {
this.c_List=c_List;
    }
}

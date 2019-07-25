package com.sandeep.indplus.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sandeep.indplus.Interface.userdetails;
import com.sandeep.indplus.R;

public class UserView extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView txtphone, txtemail, txtcity,txtstate;
    public userdetails listner;

    public UserView(View itemView) {
        super(itemView);
        txtphone =itemView.findViewById(R.id.Phoneop);
        txtemail =itemView.findViewById(R.id.EmailIdop);
        txtstate =itemView.findViewById(R.id.Stateop);
        txtcity  =itemView.findViewById(R.id.Cityop);

    }

    @Override
    public void onClick(View v) {
        listner.onClick(v, getAdapterPosition(), false);
    }
}

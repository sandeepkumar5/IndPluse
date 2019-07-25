package com.sandeep.indplus;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.sandeep.indplus.Model.User;
import com.sandeep.indplus.ViewHolder.UserView;


public class ContentActivity extends AppCompatActivity {
    private RecyclerView userList;
    private DatabaseReference userRef;
    TextView t1,t2,t3,t4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content);
        userRef = FirebaseDatabase.getInstance().getReference().child("Users");
        userList = (findViewById(R.id.UserListRecycler));
        userList.setLayoutManager(new LinearLayoutManager(this));
        t1 =findViewById(R.id.Phoneop);
        t2 =findViewById(R.id.EmailIdop);
        t3 =findViewById(R.id.Stateop);
        t4 =findViewById(R.id.Cityop);

    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerOptions<User> options =
                new FirebaseRecyclerOptions.Builder<User>()
                        .setQuery(userRef, User.class)
                        .build();
        FirebaseRecyclerAdapter<User,UserView> adapter =
                new FirebaseRecyclerAdapter<User, UserView>(options) {
                    @Override
                    protected void onBindViewHolder(@NonNull UserView holder, int position, @NonNull User model) {
                        holder.txtphone.setText(model.getPhone());
                        holder.txtemail.setText(model.getEmail());
                        holder.txtstate.setText(model.getState());
                        holder.txtcity.setText(model.getCity());
                    }

                    @NonNull
                    @Override
                    public UserView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_details,parent,false);
                        UserView holder = new UserView(view);
                        return holder;
                    }
                };
        userList.setAdapter(adapter);
        adapter.startListening();
    }
}

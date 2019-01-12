package com.example.android.assignment;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    static FirebaseDatabase database = FirebaseDatabase.getInstance();
    static DatabaseReference userDatabase = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Read from the database
        final ArrayList<User> Users = new ArrayList<>();
        userDatabase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User newUser = dataSnapshot.getValue(User.class);
                newUser.setId(Integer.valueOf(dataSnapshot.getKey()));
                Users.add(newUser);
                mAdapter = new MyAdapter(Users);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User modifiedUser = dataSnapshot.getValue(User.class);
                modifiedUser.setId(Integer.valueOf(dataSnapshot.getKey()));
                for (User temp : Users) {
                    if( temp.getId() == Integer.valueOf(dataSnapshot.getKey()) ){
                        Users.set(Users.indexOf(temp), modifiedUser);
                        break;
                    }
                }
                mAdapter = new MyAdapter(Users);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                for (User temp : Users) {
                    if( temp.getId() == Integer.valueOf(dataSnapshot.getKey()) ){
                        Users.remove(temp);
                        break;
                    }
                }
                mAdapter = new MyAdapter(Users);
                mRecyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) { }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }
}

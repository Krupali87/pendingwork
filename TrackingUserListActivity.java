package com.app.gpsphonelocator_new.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.app.gpsphonelocator_new.R;
import com.app.gpsphonelocator_new.UserFriend;
import com.app.gpsphonelocator_new.UserRVAdapter;
import com.app.gpsphonelocator_new.ViewModal;
import com.app.gpsphonelocator_new.phone.view.dialog.DialogAddNewFriend;


import java.util.List;


public final class TrackingUserListActivity extends AppCompatActivity {

    private RecyclerView rvusertracked;

    private ViewModal viewmodal;

    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;
    public UserRVAdapter adapter;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_tracking_user_list);
        rvusertracked = findViewById(R.id.rv_user_tracked);
        ImageView add = findViewById(R.id.img_add_header);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), DialogAddNewFriend.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });
        rvusertracked.setLayoutManager(new LinearLayoutManager(this));
        rvusertracked.setHasFixedSize(true);

        final UserRVAdapter adapter = new UserRVAdapter();
        rvusertracked.setAdapter(adapter);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        viewmodal.alluser().observe(this, new Observer<List<UserFriend>>() {
            @Override
            public void onChanged(List<UserFriend> models) {
                adapter.submitList(models);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodal.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(rvusertracked);

        adapter.setOnItemClickListener(new UserRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserFriend userFriend) {
                Intent intent = new Intent(getApplicationContext(), DialogAddNewFriend.class);
                intent.putExtra(DialogAddNewFriend.EXTRA_ID, userFriend.getId());
                intent.putExtra(DialogAddNewFriend.EXTRA_NAME, userFriend.getname());
                intent.putExtra(DialogAddNewFriend.EXTRA_SECURITY_CODE, userFriend.getsecurityCode());
                intent.putExtra(DialogAddNewFriend.EXTRA_PHONE_NUMBER, userFriend.getphoneNumber());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });


        }
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(DialogAddNewFriend.EXTRA_NAME);
            String courseDescription = data.getStringExtra(DialogAddNewFriend.EXTRA_SECURITY_CODE);
            String courseDuration = data.getStringExtra(DialogAddNewFriend.EXTRA_PHONE_NUMBER);
            UserFriend model = new UserFriend(courseName, courseDescription, courseDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(DialogAddNewFriend.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(DialogAddNewFriend.EXTRA_NAME);
            String courseDesc = data.getStringExtra(DialogAddNewFriend.EXTRA_SECURITY_CODE);
            String courseDuration = data.getStringExtra(DialogAddNewFriend.EXTRA_PHONE_NUMBER);
            UserFriend model = new UserFriend(courseName, courseDesc, courseDuration);
            model.setId(id);

        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }


    }




}

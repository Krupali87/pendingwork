package com.app.gpsphonelocator_new.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.app.gpsphonelocator_new.R;
import com.app.gpsphonelocator_new.UserFriend;
import com.app.gpsphonelocator_new.UserRVAdapter;
import com.app.gpsphonelocator_new.ViewModal;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class DemoActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private ViewModal viewmodal;
    private static final int ADD_COURSE_REQUEST = 1;
    private static final int EDIT_COURSE_REQUEST = 2;

    public UserRVAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo);

        recycler = findViewById(R.id.recycler);
        FloatingActionButton fab = findViewById(R.id.idFABAdd);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getApplicationContext(), NewCourseActivity.class);
                startActivityForResult(intent, ADD_COURSE_REQUEST);
            }
        });
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setHasFixedSize(true);

        final UserRVAdapter adapter = new UserRVAdapter();
        recycler.setAdapter(adapter);

        viewmodal = ViewModelProviders.of(this).get(ViewModal.class);

        viewmodal.alluser().observe(this, new Observer<List<UserFriend>>() {
            @Override
            public void onChanged(List<UserFriend> models) {
                // when the data is changed in our models we are
                // adding that list to our adapter class.
                adapter.submitList(models);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                viewmodal.delete(adapter.getUserAt(viewHolder.getAdapterPosition()));
                Toast.makeText(getApplicationContext(), "Course deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycler);

        adapter.setOnItemClickListener(new UserRVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(UserFriend userFriend) {
                Intent intent = new Intent(getApplicationContext(), NewCourseActivity.class);
                intent.putExtra(NewCourseActivity.EXTRA_ID, userFriend.getId());
                intent.putExtra(NewCourseActivity.EXTRA_NAME, userFriend.getname());
                intent.putExtra(NewCourseActivity.EXTRA_SECURITY_CODE, userFriend.getsecurityCode());
                intent.putExtra(NewCourseActivity.EXTRA_PHONE_NUMBER, userFriend.getphoneNumber());

                startActivityForResult(intent, EDIT_COURSE_REQUEST);
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_COURSE_REQUEST && resultCode == RESULT_OK) {
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_NAME);
            String courseDescription = data.getStringExtra(NewCourseActivity.EXTRA_SECURITY_CODE);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_PHONE_NUMBER);
            UserFriend model = new UserFriend(courseName, courseDescription, courseDuration);
            viewmodal.insert(model);
            Toast.makeText(this, "Course saved", Toast.LENGTH_SHORT).show();
        } else if (requestCode == EDIT_COURSE_REQUEST && resultCode == RESULT_OK) {
            int id = data.getIntExtra(NewCourseActivity.EXTRA_ID, -1);
            if (id == -1) {
                Toast.makeText(this, "Course can't be updated", Toast.LENGTH_SHORT).show();
                return;
            }
            String courseName = data.getStringExtra(NewCourseActivity.EXTRA_NAME);
            String courseDesc = data.getStringExtra(NewCourseActivity.EXTRA_SECURITY_CODE);
            String courseDuration = data.getStringExtra(NewCourseActivity.EXTRA_PHONE_NUMBER);
            UserFriend model = new UserFriend(courseName, courseDesc, courseDuration);
            model.setId(id);

        } else {
            Toast.makeText(this, "Course not saved", Toast.LENGTH_SHORT).show();
        }


    }
}


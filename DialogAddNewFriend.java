package com.app.gpsphonelocator_new.phone.view.dialog;

import static android.app.Activity.RESULT_OK;
import static androidx.databinding.DataBindingUtil.setContentView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.app.gpsphonelocator_new.R;
import com.app.gpsphonelocator_new.activity.CaptureUserCode;
import com.app.gpsphonelocator_new.activity.NewCourseActivity;
import com.app.gpsphonelocator_new.database.RTDB_DEFINE;
import com.app.gpsphonelocator_new.database.UserDatabase;
import com.app.gpsphonelocator_new.database.UserTracked;
import com.app.gpsphonelocator_new.database.UserTrackedDao;
import com.app.gpsphonelocator_new.databinding.DialogAddNewFriendBinding;
import com.app.gpsphonelocator_new.phone.util.AppAuthen;
import com.app.gpsphonelocator_new.phone.util.Constants;
import com.app.gpsphonelocator_new.phone.util.DataExKt;
import com.app.gpsphonelocator_new.common.CustomToast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.journeyapps.barcodescanner.ScanOptions;

import java.util.HashMap;
import java.util.Map;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import kotlin.text.StringsKt;

public final class DialogAddNewFriend extends AppCompatActivity {
    private EditText etname, inputsecuritycode, etphonenumber;
    private ConstraintLayout saveBtn;
    private ImageView closedialog;

    public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_NAME";
    public static final String EXTRA_SECURITY_CODE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_SECURITY_CODE";
    public static final String EXTRA_PHONE_NUMBER = "com.gtappdevelopers.gfgroomdatabase.EXTRA_PHONE_NUMBER";


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_new_friend);

        etname = findViewById(R.id.idEdtName);
        inputsecuritycode = findViewById(R.id.idEdtsecuritycode);
        etphonenumber = findViewById(R.id.idEdtphoneNumber);
        saveBtn = findViewById(R.id.btn_add_friend);
        closedialog = findViewById(R.id.img_close_popup);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            etname.setText(intent.getStringExtra(EXTRA_NAME));
            inputsecuritycode.setText(intent.getStringExtra(EXTRA_SECURITY_CODE));
            etphonenumber.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String Name = etname.getText().toString();
                String securitycode = inputsecuritycode.getText().toString();
                String phonenum = etphonenumber.getText().toString();
                if (Name.isEmpty() || securitycode.isEmpty() || phonenum.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveUser(Name, securitycode, phonenum);
            }
        });

       closedialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              onBackPressed();
            }
        });
    }

    private void saveUser(String Name, String securitycode, String phonenum) {
        // inside this method we are passing
        // all the data via an intent.
        Intent data = new Intent();

        // in below line we are passing all our course detail.
        data.putExtra(EXTRA_NAME, Name);
        data.putExtra(EXTRA_SECURITY_CODE, securitycode);
        data.putExtra(EXTRA_PHONE_NUMBER, phonenum);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            // in below line we are passing our id.
            data.putExtra(EXTRA_ID, id);
        }

        // at last we are setting result as data.
        setResult(RESULT_OK, data);

        // displaying a toast message after adding the data
        Toast.makeText(this, "Course has been saved to Room Database. ", Toast.LENGTH_SHORT).show();
    }



}


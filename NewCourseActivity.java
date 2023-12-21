package com.app.gpsphonelocator_new.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.app.gpsphonelocator_new.R;

public class NewCourseActivity extends AppCompatActivity {

    private EditText nameEdt, securitycodeEdt, phoneNumberEdt;
    private Button saveBtn;

   public static final String EXTRA_ID = "com.gtappdevelopers.gfgroomdatabase.EXTRA_ID";
    public static final String EXTRA_NAME = "com.gtappdevelopers.gfgroomdatabase.EXTRA_NAME";
    public static final String EXTRA_SECURITY_CODE = "com.gtappdevelopers.gfgroomdatabase.EXTRA_SECURITY_CODE";
    public static final String EXTRA_PHONE_NUMBER = "com.gtappdevelopers.gfgroomdatabase.EXTRA_PHONE_NUMBER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_course);

        nameEdt = findViewById(R.id.idEdtName);
        securitycodeEdt = findViewById(R.id.idEdtsecuritycode);
       phoneNumberEdt = findViewById(R.id.idEdtphoneNumber);
        saveBtn = findViewById(R.id.idBtnSave);

        Intent intent = getIntent();
        if (intent.hasExtra(EXTRA_ID)) {
            // if we get id for our data then we are
            // setting values to our edit text fields.
            nameEdt.setText(intent.getStringExtra(EXTRA_NAME));
            securitycodeEdt.setText(intent.getStringExtra(EXTRA_SECURITY_CODE));
            phoneNumberEdt.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));
        }

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // getting text value from edittext and validating if
                // the text fields are empty or not.
                String Name = nameEdt.getText().toString();
                String securitycode = securitycodeEdt.getText().toString();
                String phonenum = phoneNumberEdt.getText().toString();
                if (Name.isEmpty() || securitycode.isEmpty() || phonenum.isEmpty()) {
                    Toast.makeText(NewCourseActivity.this, "Please enter the valid course details.", Toast.LENGTH_SHORT).show();
                    return;
                }
                // calling a method to save our course.
                saveUser(Name, securitycode, phonenum);
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

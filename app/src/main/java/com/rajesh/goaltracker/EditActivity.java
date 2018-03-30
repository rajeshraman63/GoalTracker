package com.rajesh.goaltracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class EditActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText et_editStartDate;
    private EditText et_editEndDate;
    private EditText et_editGoalDescription;
    private EditText et_editGoal;
    private Button btn_saveChanges;
    //private Button btn_delete;

    private Firebase mChildRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);


        et_editEndDate = (EditText)findViewById(R.id.et_editEndDate);
        et_editStartDate = (EditText)findViewById(R.id.et_editStartDate);
        et_editGoal = (EditText)findViewById(R.id.et_editGoal);
        et_editGoalDescription = (EditText)findViewById(R.id.et_editGoalDescription);
        btn_saveChanges = (Button) findViewById(R.id.btn_saveChanges);
        //btn_delete = (Button) findViewById(R.id.btn_editDelete);
        myCalendar = Calendar.getInstance();



        Intent i=getIntent();
        final String goal=i.getStringExtra("goal");
        String goalDescription=i.getStringExtra("goalDescription");
        String startDate=i.getStringExtra("startDate");
        String endDate=i.getStringExtra("endDate");

        Toast.makeText(this," " + goal + " " + goalDescription + " " +
                startDate + " " + endDate,Toast.LENGTH_SHORT).show();


        et_editGoal.setText(goal);
        et_editGoalDescription.setText(goalDescription);
        et_editStartDate.setText(startDate);
        et_editEndDate.setText(endDate);


        final DatePickerDialog.OnDateSetListener date3 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editLabel(et_editStartDate);
            }
        };


        final DatePickerDialog.OnDateSetListener date4 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                editLabel(et_editEndDate);
            }
        };



        et_editStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(EditActivity.this,date3,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        et_editEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(EditActivity.this, date4, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        btn_saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // deleting the pre-existing child value of the firebase
                mChildRef = new Firebase("https://goaltracker-375d7.firebaseio.com/"+goal.trim());
                mChildRef.setValue(null);

                String editGoal = et_editGoal.getText().toString().trim();
                String editGoalDescription = et_editGoalDescription.getText().toString().trim();
                String editStartDate = et_editStartDate.getText().toString().trim();
                String editEndDate = et_editEndDate.getText().toString().trim();


                if (!TextUtils.isEmpty(editGoal) && !TextUtils.isEmpty(editGoalDescription) && !TextUtils.isEmpty(editStartDate) &&
                        !TextUtils.isEmpty(editEndDate) ){



                    mChildRef = new Firebase("https://goaltracker-375d7.firebaseio.com/"+editGoal.trim());

                    Firebase mChild = mChildRef.child("goalDescription");
                    mChild.setValue(editGoalDescription);

                    mChild = mChildRef.child("startDate");
                    mChild.setValue(editStartDate);

                    mChild = mChildRef.child("endDate");
                    mChild.setValue(editEndDate);


                    //Toast.makeText(NewGoalActivity.this, " " + inputs,Toast.LENGTH_LONG).show();
                    //Log.v("Tag",""+inputs);

                    Toast.makeText(EditActivity.this,"Updated Successfully",Toast.LENGTH_SHORT).show();

                    //startActivity(new Intent(EditActivity.this,MainActivity.class));

                    finish();
                    //startActivity(getIntent());

                    startActivity(new Intent(EditActivity.this,MainActivity.class));

                }else{

                    Toast.makeText(EditActivity.this,"Do not leave the input fields Empty",Toast.LENGTH_SHORT).show();
                }

            }
        });



        // deleting the task


       /* btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String editGoal = et_editGoal.getText().toString().trim();
                String editGoalDescription = et_editGoalDescription.getText().toString().trim();
                String editStartDate = et_editStartDate.getText().toString().trim();
                String editEndDate = et_editEndDate.getText().toString().trim();

                if (!TextUtils.isEmpty(editGoal) && !TextUtils.isEmpty(editGoalDescription) && !TextUtils.isEmpty(editStartDate) &&
                        !TextUtils.isEmpty(editEndDate) ) {
                    mChildRef = new Firebase("https://goaltracker-375d7.firebaseio.com/"+editGoal.trim());
                    mChildRef.setValue(null);

                    Toast.makeText(EditActivity.this,"Deleted Successfully",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditActivity.this,MainActivity.class));

                }else {
                    Toast.makeText(EditActivity.this,"Do not leave the input fields Empty",Toast.LENGTH_SHORT).show();
                }
            }
        });*/


    }


    private void editLabel(EditText editText) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
        //hideSoftKeyBoard();
    }

    private void clearInputs(){
        et_editEndDate.setText("");
        et_editStartDate.setText("");
        et_editGoalDescription.setText("");
        et_editGoal.setText("");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}

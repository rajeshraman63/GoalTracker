package com.rajesh.goaltracker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.nfc.Tag;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NewGoalActivity extends AppCompatActivity {

    private Calendar myCalendar;
    private EditText et_startDate;
    private EditText et_endDate;
    private EditText et_goalDescription;
    private EditText et_goal;
    private Button btn_save;
    private Button btn_clear;
    private  String inputs = "";

    private Firebase mRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_goal);

        et_startDate =  (EditText)findViewById(R.id.et_startDate);
        et_endDate=  (EditText)findViewById(R.id.et_endDate);
        et_goal =  (EditText)findViewById(R.id.et_goal);
        et_goalDescription =  (EditText)findViewById(R.id.et_goalDescription);
        btn_save = (Button) findViewById(R.id.btn_save);
        btn_clear = (Button) findViewById(R.id.btn_clear);
        myCalendar = Calendar.getInstance();


        final DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(et_startDate);
            }
        };


        final DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel(et_endDate);
            }
        };



        et_startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(NewGoalActivity.this,date1,myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        et_endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(NewGoalActivity.this, date2, myCalendar.get(Calendar.YEAR),
                        myCalendar.get(Calendar.MONTH),myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });




        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String goal = et_goal.getText().toString().trim();
                String goalDescription = et_goalDescription.getText().toString().trim();

                String startDate = et_startDate.getText().toString().trim();
                String endDate = et_endDate.getText().toString().trim();

                if (!TextUtils.isEmpty(goal) && !TextUtils.isEmpty(goalDescription) && !TextUtils.isEmpty(startDate) &&
                        !TextUtils.isEmpty(endDate) ) {
                    inputs += goal + "\n";
                    inputs += goalDescription + "\n";
                    inputs += startDate + "\n";
                    inputs += endDate + "\n";

                    // Adding data to firebase

                    mRef = new Firebase("https://goaltracker-375d7.firebaseio.com/"+goal.trim());

                    Firebase mChild = mRef.child("goalDescription");
                    mChild.setValue(goalDescription);


                    mChild = mRef.child("startDate");
                    mChild.setValue(startDate);

                    mChild = mRef.child("endDate");
                    mChild.setValue(endDate);


                    Toast.makeText(NewGoalActivity.this, " " + inputs,Toast.LENGTH_LONG).show();
                    //Log.v("Tag",""+inputs);

                    finish();
                    startActivity(new Intent(NewGoalActivity.this,MainActivity.class));

                } else {
                    Toast.makeText(getApplicationContext(),"Do not leave the input fields empty",Toast.LENGTH_SHORT).show();
                }
            }
        });





        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                et_goal.setText("");
                et_goalDescription.setText("");
                et_startDate.setText("");
                et_endDate.setText("");
            }
        });


    }


    private void updateLabel(EditText editText) {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        editText.setText(sdf.format(myCalendar.getTime()));
        //hideSoftKeyBoard();
    }

    /*private void hideSoftKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

        if(imm.isAcceptingText()) { // verify if the soft keyboard is open
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }*/



    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}

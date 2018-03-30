package com.rajesh.goaltracker;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;


public class GoalAdapter extends ArrayAdapter<Goal> {

    //  Context context;
    public GoalAdapter(Context context, ArrayList<Goal> goals){
        super(context,0,goals);
        /// this.context = context;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        // Get the data item for this position
        final Goal goal=getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.goal_layout,parent,false);
        }
        // Lookup view for data population
        TextView tvGoal=(TextView)convertView.findViewById(R.id.tv_goal);
        TextView tvgoalDescription=(TextView)convertView.findViewById(R.id.tv_goalDescription);
        TextView tvStartDate=(TextView)convertView.findViewById(R.id.tv_startDate);
        TextView tvEndDate=(TextView)convertView.findViewById(R.id.tv_endDate);
        TextView tvStatus = (TextView)convertView.findViewById(R.id.tv_status);
        ProgressBar progressBar = (ProgressBar)convertView.findViewById(R.id.progressBar);

        Button btn_edit = (Button)convertView.findViewById(R.id.btn_edit);
        Button btn_delete = (Button)convertView.findViewById(R.id.btn_delete);


        assert goal != null;

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Firebase mChildRef = new Firebase("https://goaltracker-375d7.firebaseio.com/"+goal.getGoalName().trim());
                mChildRef.setValue(null);
                Toast.makeText(getContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show();

                //notifyAll();
                getContext().startActivity(new Intent(getContext(),MainActivity.class));
            }
        });




        // setting text to status text view

        Date date = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yy",Locale.US);
        String currDate = ft.format(date);

        int daysOver = 0;
        int totalDays = 0;
        int progress= 0;



            daysOver = dateDifference(goal.getStartDate(), currDate);
            totalDays = dateDifference(goal.getStartDate(), goal.getEndDate());

            progress = (int) (((double) daysOver / (double) totalDays) * 100);


            //tvStatus.setText(currDate + " : " + daysOver + " : " + totalDays + " : " + progress );

            if (daysOver <= totalDays && daysOver >= 0) {

                if (daysOver == 0) {

                    String temp0 = "Goal has started today." + "\n";

                    if (totalDays == 1)
                        temp0 += totalDays + " day is remaining.";
                    else
                        temp0 += totalDays + " days are remaining.";

                    tvStatus.setText(temp0);
                } else if (daysOver == 1) {
                    //tvStatus.setText("1 day passed." + "\n" + (totalDays - 1) + " days are remaining");

                    String temp1 = "1 day passed." + "\n";
                    if ((totalDays - 1) == 1) {
                        temp1 += "1 day is remaining.";
                    } else {
                        temp1 += (totalDays - 1) + " days are remaining.";
                    }
                    tvStatus.setText(temp1);
                } else {
                    tvStatus.setText(daysOver + " days passed." + "\n" + (totalDays - daysOver) + " days are remaining.");
                }

            } else if (daysOver > totalDays) {
                String tempOver = "Goal duration is over." + "\n";
                tempOver += "Duration of the goal was " + totalDays + "days.";
                tvStatus.setText(tempOver);

            } else {
                String tempInit = "Goal has not started yet." + "\n";
                tempInit += (Integer.valueOf(goal.getStartDate().substring(0, 2)) - Integer.valueOf(currDate.substring(0, 2))) + " days are remaining to start.";
                tvStatus.setText(tempInit);
            }


            // Populate the data into the template view using the data object
            tvGoal.setText(goal.getGoalName());
            tvgoalDescription.setText(goal.getGoalDescription());
            tvStartDate.setText(goal.getStartDate());
            tvEndDate.setText(goal.getEndDate());
            progressBar.setProgress(progress);



        final View finalConvertView = convertView;
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(finalConvertView.getContext(),EditActivity.class);
                intent.putExtra("goal",goal.getGoalName());
                intent.putExtra("goalDescription",goal.getGoalDescription());
                intent.putExtra("startDate",goal.getStartDate());
                intent.putExtra("endDate",goal.getEndDate());
                getContext().startActivity(intent);
            }
        });


        // Return the completed view to render on screen
        return convertView;
    }


    private int dateDifference(String startDateString,String endDateString){
        String format = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat myFormat = new SimpleDateFormat(format, Locale.US);

        //String startDateString = "01/03/2018";
        //String endDateString = "01/03/2019";

        Date startDate = null;
        Date endDate = null;

       if(startDateString != null && endDateString != null){
           try {
               startDate = myFormat.parse(startDateString);
               endDate = myFormat.parse(endDateString);

           } catch (ParseException e) {
               e.printStackTrace();
           }

           long diff = 0;

           if(endDate!=null && startDate!=null)
                diff = endDate.getTime() - startDate.getTime();

           // float days = (diff / (1000*60*60*24));

           return Integer.parseInt((TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS)+"").trim());
       }

       return  -1;
    }

}


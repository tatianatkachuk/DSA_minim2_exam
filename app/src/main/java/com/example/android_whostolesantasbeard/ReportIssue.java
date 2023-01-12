package com.example.android_whostolesantasbeard;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import android.content.Intent;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportIssue extends AppCompatActivity {
    // From activity
    TextView dateVal;
    TextView informerVal;
    TextView messageVal;
    Button submitButton;

    // API client
    IWSSBService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        service = APIClient.getClient().create(IWSSBService.class);

        dateVal = (EditText) findViewById(R.id.dateVal);
        informerVal = (EditText) findViewById(R.id.informerVal);
        messageVal = (EditText) findViewById(R.id.messageVal);

        submitButton = (Button) findViewById(R.id.submitButton);


        // Swap to register or main
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitReport();
            }
        });

    }
    public void submitReport(){
        String date = dateVal.getText().toString();
        String informer = informerVal.getText().toString();
        String message = messageVal.getText().toString();

        Issue i = new Issue(date, informer, message);

        Call<Issue> call = service.reportIssue(i);

        call.enqueue(new Callback<Issue>() {
            @Override
            public void onResponse(Call<Issue> call, Response<Issue> response) {
                Log.d("TAG",response.code()+"");
                if(response.code()==201) {
                    Issue issueInfo = response.body();
                    String date = issueInfo.getDate();
                    String informer = issueInfo.getInformer();
                    String message = issueInfo.getMessage();

                    Log.d("Date",date+" informer "+informer+" message"+message);

                    Toast toast = Toast.makeText(getApplicationContext(),"Successful!", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                    return;
                }

                if(response.code()==500){
                    Log.d("Error","Invalid information");
                    Toast toast = Toast.makeText(getApplicationContext(),"Invalid information! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });
                    return;
                }

                else{
                    Log.d("Error","Operation failed");
                    Toast toast = Toast.makeText(getApplicationContext(),"Operation failed! Please try again", Toast.LENGTH_LONG);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            toast.show();
                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<Issue> call, Throwable t) {
                call.cancel();
                Log.d("Error","Failure");
            }

        });
    }



}
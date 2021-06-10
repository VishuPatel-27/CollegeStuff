package com.vishupatel.clgstuff;


// MysyLogo Reference :- https://www.google.com/url?sa=i&url=https%3A%2F%2Fmysy.guj.nic.in%2F&psig=AOvVaw303fkg09EXrZc0eUbuNSwU&ust=1622204530074000&source=images&cd=vfe&ved=2ahUKEwiPjbX37OnwAhWq5TgGHUCFDy0QjB16BAgAEAg
// Digital Gujarat Logo Reference :- https://www.pngjoy.com/download/d1b4i5d8a4v7r9_gujarat-lions-logo-govt-of-gujarat-logo-hd/
// student logo :- https://toppng.com/show_download/167438/help-your-student-college-student-icon/large

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ScholarshipActivity extends AppCompatActivity {

    // Initialize variable
    ChipNavigationBar bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scholarship);

        // getting reference of bottom navigation bar
        bottomNavigationView = findViewById(R.id.chipBottomNavigation);

        // set home selected
        bottomNavigationView.setItemSelected(R.id.chip_menu_home,true);

        // perform itemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i){

                    case R.id.chip_menu_home:
                        break;

                    case R.id.chip_menu_userProfile:
                        startActivity(new Intent(ScholarshipActivity.this,UserProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.chip_menu_Communication:
                        startActivity(new Intent(ScholarshipActivity.this,CommunicationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });

        // for sending notification
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            NotificationChannel channel =
                    new NotificationChannel("ScholarshipNotification","ScholarshipNotification", NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        FirebaseMessaging.getInstance().subscribeToTopic("general")
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = "Successful";
                        if (!task.isSuccessful()) {
                            msg = "Failed";
                        }
                        // Notification successful
                        //Toast.makeText(ScholarshipActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });


    }

    public void startMysyActivity(View view){

        startActivity(new Intent(ScholarshipActivity.this,MysyActivity.class));

    }
    public void startDigitalActivity(View view){

        startActivity(new Intent(ScholarshipActivity.this,DigitalActivity.class));

    }

    public void startCollegeDocumentsActivity(View view){

        startActivity(new Intent(ScholarshipActivity.this,CollegeDocumentsActivity.class));
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ScholarshipActivity.this);
        alertDialog.setTitle("Close Application");
        alertDialog.setMessage("Are you sure you want to exit from this application ?");

        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // clear all parent activity and close entire application means activity
                finishAffinity();

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                // dismiss dialog box
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }



}
package com.vishupatel.clgstuff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class CommunicationActivity extends AppCompatActivity {

    // Initialize variable
    ChipNavigationBar bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communication);

        // getting reference of bottom navigation bar
        bottomNavigationView = findViewById(R.id.chipBottomNavigation);

        // set communication selected
        bottomNavigationView.setItemSelected(R.id.chip_menu_Communication,true);

        // perform itemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i){

                    case R.id.chip_menu_home:
                                startActivity(new Intent(CommunicationActivity.this,ScholarshipActivity.class));
                                overridePendingTransition(0,0);
                                break;

                    case R.id.chip_menu_userProfile:
                            startActivity(new Intent(CommunicationActivity.this,UserProfileActivity.class));
                            overridePendingTransition(0,0);
                            break;

                    case R.id.chip_menu_Communication:
                            break;
                }
            }
        });

    }


    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(CommunicationActivity.this);
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

    public void onClickShareBtn(View view){

        // share apk file with social application

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");

        String shareBody,appName ;

        shareBody  = "Download APK File From : https://drive.google.com/file/d/1W-2leaqEYsdGyigs91l-I1lLjJevdGxs/view?usp=sharing&hl=er";
        appName = "College Stuff";

        shareIntent.putExtra(Intent.EXTRA_SUBJECT,appName);
        shareIntent.putExtra(Intent.EXTRA_TEXT,shareBody);

        startActivity(Intent.createChooser(shareIntent,"Share Using"));

        //Toast.makeText(CommunicationActivity.this,"Share Application with your friends ",Toast.LENGTH_LONG).show();
    }

    public void onClickSupportBtn(View view){

        Toast.makeText(CommunicationActivity.this,"Support us with your feedback !",Toast.LENGTH_LONG).show();
    }
}
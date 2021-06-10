package com.vishupatel.clgstuff;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class DigitalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digital);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    // call method when user click on developer created back button
    public void onClickBack(View view){
        onBackPressed();
    }

    // for give info about bonafide certificate
    public void onClickBonafide(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(DigitalActivity.this);
        builder.setTitle("Information")
                .setMessage("You can download bonofide from college's documents section")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }
}
package com.vishupatel.clgstuff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;


public class MysyActivity extends AppCompatActivity {


    private FirebaseFirestore fStore;

    // Request code for selecting a PDF document.
    private static final int PICK_PDF_FILE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        fStore = FirebaseFirestore.getInstance();

        setContentView(R.layout.activity_mysy);

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false)
                    .setTitle("Information")
                    .setMessage("When you try to open any document pdf it might be possible that it would not be open at that time . So don't worry about it.It's due to network issue . Back to documents activity and try again!");

        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertDialog.show();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    // call method when user click on developer created back button
    public void onClickBack(View view){
        onBackPressed();
    }

    // documents
    public void onClickDocuments(View view){

        // start mysy documents activity
        startActivity(new Intent(MysyActivity.this,MysyDocuments.class));
    }

    public void onClickMenuItem(View view){

        Intent startIntent = new Intent(MysyActivity.this, PDF_ReaderActivity.class);

        switch (view.getId())
        {

            case R.id.mysy_menu_item2 :
            case R.id.mysy_menu_item2_btn :
                                        startIntent.putExtra("menuItem","2");
                                        break;

            case R.id.mysy_menu_item3 :
            case R.id.mysy_menu_item3_btn :
                                        startIntent.putExtra("menuItem","3");
                                        break;

            case R.id.mysy_menu_item4 :
            case R.id.mysy_menu_item4_btn :
                                        startIntent.putExtra("menuItem","4");
                                        break;

            case R.id.mysy_menu_item5 :
            case R.id.mysy_menu_item5_btn :
                                        startIntent.putExtra("menuItem","5");
                                        break;

            case R.id.mysy_menu_item6 :
            case R.id.mysy_menu_item6_btn :
                                        startIntent.putExtra("menuItem","6");
                                        break;

            case R.id.mysy_menu_item7 :
            case R.id.mysy_menu_item7_btn :
                                        startIntent.putExtra("menuItem","7");
                                        break;

        }
        // start pdf viewer activity
        startActivity(startIntent);
    }
}
package com.vishupatel.clgstuff;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

public class MysyDocuments extends AppCompatActivity {

    private RelativeLayout relativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mysy_documents);

    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    // call method when user click on developer created back button
    public void onClickBack(View view){
        onBackPressed();
    }

    // on click fresh application
    public void onClickFreshApplication(View view){

        Intent startContainerActivity = new Intent(MysyDocuments.this,ContainerActivity.class);
        startContainerActivity.putExtra("ApplicationType","1");

        startActivity(startContainerActivity);
    }

    // on click renewal application
    public void onClickRenewalApplication(View view){

        Intent startContainerActivity = new Intent(MysyDocuments.this,ContainerActivity.class);
        startContainerActivity.putExtra("ApplicationType","2");

        startActivity(startContainerActivity);
    }

    // on click delayed application
    public void onClickDelayedApplication(View view){

        Intent startContainerActivity = new Intent(MysyDocuments.this,ContainerActivity.class);
        startContainerActivity.putExtra("ApplicationType","3");

        startActivity(startContainerActivity);
    }
}
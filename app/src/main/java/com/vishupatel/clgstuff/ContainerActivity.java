package com.vishupatel.clgstuff;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.vishupatel.clgstuff.FragmentsMysy.DelayedApplicationMysy;
import com.vishupatel.clgstuff.FragmentsMysy.FreshApplicationMysy;
import com.vishupatel.clgstuff.FragmentsMysy.RenewalApplicationMysy;

public class ContainerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        onLoadFragments(getIntent());
    }

    // This method for loading fragments according to user choose option
    private void onLoadFragments(Intent intent) {

        String menuId = intent.getStringExtra("ApplicationType");
        Fragment fragment = null;

        switch (menuId){

            case "1":
                    // load fresh application fragment
                    fragment = new FreshApplicationMysy();
                    break;

            case "2":
                    // load renewal application fragment
                    fragment = new RenewalApplicationMysy();
                    break;

            case "3":
                    // load delayed application fragment
                    fragment = new DelayedApplicationMysy();
                    break;
        }

        // start load fragment according to user choice
        getSupportFragmentManager().beginTransaction().replace(R.id.frameContainer,fragment).commit();
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
    }

    // call method when user click on developer created back button
    public void onClickBack(View view){
        onBackPressed();
    }

    public void onClickITReturn(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(ContainerActivity.this);
        builder.setTitle("Information")
                .setMessage("Required Income certificate respective to your fresh application (1st Application). It might be IT return or self declaration")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }


    public void onClickInstruction(View view){

        Intent startIntent = new Intent(ContainerActivity.this, PDF_ReaderActivity.class);
        startIntent.putExtra("menuItem","6");
        startActivity(startIntent);

    }

    public void onClickSelfDeclaration(View view){

        AlertDialog.Builder builder = new AlertDialog.Builder(ContainerActivity.this);
        builder.setTitle("Information")
                .setMessage("You can download format of self declaration from college document section if your family income less than 2.5 lacs")
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
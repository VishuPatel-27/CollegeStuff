package com.vishupatel.clgstuff;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText enteredEmail, enteredPassword;
    private FirebaseAuth mAuth;
    private CircularProgressButton loginButton;

    // avoid login phase every time when user already logged into application
    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            finish();
            //startActivity(new Intent(LoginActivity.this, MainContentActivity.class));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //getting reference of email and password input field
        enteredEmail = findViewById(R.id.editTextLoginEmail);
        enteredPassword = findViewById(R.id.editTextLoginPassword);
        loginButton = findViewById(R.id.cirLoginButton);

        // getting reference of firebase authentication
        mAuth = FirebaseAuth.getInstance();

        if(!(isConnected())){
            showDialog();
        }

    }

    public void onRedirectRegistration(View view) {

        startActivity(new Intent(this, RegisterActivity.class));
    }

    public void onLoginClick(View view) {

        String userEmail, userPassword;

        userEmail = enteredEmail.getText().toString();
        userPassword = enteredPassword.getText().toString();

        if(userEmail.isEmpty()){
            enteredEmail.setError("EmailId is required for login!");
            enteredEmail.requestFocus();
            return;
        }
        if(userPassword.isEmpty()){
            enteredPassword.setError("Password id required for login!");
            enteredPassword.requestFocus();
            return;
        }

        // set visibility of progressbar
        loginButton.startAnimation();

        mAuth.signInWithEmailAndPassword(userEmail, userPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(LoginActivity.this, "Logged In Successfully!", Toast.LENGTH_LONG).show();
                loginButton.stopAnimation();
                loginButton.revertAnimation();
                //startActivity(new Intent(LoginActivity.this, MainContentActivity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(LoginActivity.this, "EmailId or password is Wrong!", Toast.LENGTH_LONG).show();
                loginButton.stopAnimation();
                loginButton.revertAnimation();
            }
        });


    }

    public void setNewPassword(View view) {

        String userEmail = enteredEmail.getText().toString();

        if (userEmail.isEmpty()) {
            enteredEmail.setError("Email is required is for reset your password!");
            enteredEmail.requestFocus();
            return;
        } else {
            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Your Password ?");
            passwordResetDialog.setMessage("Reset password link has been set to this mail " + userEmail + "");

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    mAuth.sendPasswordResetEmail(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(LoginActivity.this, "Reset link has been sent to your email !", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(LoginActivity.this, "Reset link has Not been sent to your email ! " + e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                     dialogInterface.dismiss();
                }
            });
            passwordResetDialog.create().show();
        }
    }

    private boolean isConnected() {

        ConnectivityManager connectivityManager = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);

        // getting information about mobile data
        NetworkInfo mobileNetworkInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_MOBILE);
        // getting information about wifi connection
        NetworkInfo wifiInfo = connectivityManager.getNetworkInfo(connectivityManager.TYPE_WIFI);

        //Log.i("temp", String.valueOf( (mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) || (wifiInfo != null && wifiInfo.isConnected())));

        if((mobileNetworkInfo != null && mobileNetworkInfo.isConnected()) || (wifiInfo != null && wifiInfo.isConnected())) {
            return true;
        }else {
            return false;
        }
    }

    // show dialog box to the user
    private  void showDialog() {

        AlertDialog.Builder builder  = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("Network Error")
                .setMessage("Please connect to the internet for further process !")
                .setCancelable(false)
                .setPositiveButton("Connect", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        AlertDialog.Builder builderData = new AlertDialog.Builder(LoginActivity.this);
                        builderData.setTitle("Network connectivity")
                                    .setMessage("Which method do you want to use ?")
                                    .setCancelable(false)
                                    .setPositiveButton("MobileData", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        // show the mobile data setting screen to the user
                                        startActivity(new Intent(Settings.ACTION_NETWORK_OPERATOR_SETTINGS));
                                    }
                                }).setNegativeButton("WiFi", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                // show the wifi setting screen to the user
                                startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            }
                        });
                     builderData.create().show();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                });
        builder.create().show();
    }

}
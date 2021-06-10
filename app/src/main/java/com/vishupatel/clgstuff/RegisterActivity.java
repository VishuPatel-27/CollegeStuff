package com.vishupatel.clgstuff;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class RegisterActivity extends AppCompatActivity{

    private FirebaseAuth mAuth;
    private FirebaseFirestore fstore;
    private TextInputEditText name,email,mobileNo,enrollmentNo,currentSem,Password;
    private CircularProgressButton regButton;
    private AutoCompleteTextView textCaste;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        String []listOfCaste = getResources().getStringArray(R.array.caste_of_student);
        textCaste = findViewById(R.id.editTextCaste);
        // set the set of string array values to drop down menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dropdown_resource_file,listOfCaste);
        textCaste.setAdapter(adapter);

        // get instance of authentication
        mAuth = FirebaseAuth.getInstance();
        // get instance of fire-store
        fstore = FirebaseFirestore.getInstance();

        name = findViewById(R.id.editTextName);
        email = findViewById(R.id.editTextEmail);
        mobileNo = findViewById(R.id.editTextMobile);
        enrollmentNo = findViewById(R.id.editTextEnrollment);
        currentSem = findViewById(R.id.editTextSem);
        Password = findViewById(R.id.editTextPassword);
        regButton = findViewById(R.id.cirRegisterButton);

    }

    // when user hit back button on UI
    public void onRedirectLogin(View view){
        startActivity(new Intent(this,LoginActivity.class));
    }

    // when user hit register button
    public void onRegisterClick(View view){

        // getting all entered data from text field for validation like not empty!

        String enteredName = name.getText().toString().trim();
        String enteredEmail = email.getText().toString().trim();
        String enteredMobileNo = mobileNo.getText().toString().trim();
        String enteredEnrl = enrollmentNo.getText().toString().trim();
        String enteredSem = currentSem.getText().toString().trim();
        String enteredCaste = textCaste.getText().toString().trim();
        String enteredPass = Password.getText().toString().trim();


        // validate name input field
        if(enteredName.isEmpty()){
            name.setError("Your Name is required for registration !");
            name.requestFocus();
            return;
        }

        // validate email field
        if(enteredEmail.isEmpty()){
            email.setError("Your email is required for registration !");
            email.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()){
            email.setError("Please enter valid email address !");
            email.requestFocus();
            return;
        }

        // validate mobile no field
        if(enteredMobileNo.isEmpty()){
            mobileNo.setError("Your mobile No. is required for registration !");
            mobileNo.requestFocus();
            return;
        }
        if(enteredMobileNo.length()<10) {
            mobileNo.setError("MobileNo length should be 10!");
            mobileNo.requestFocus();
            return;
        }

        // validate enrollment no field
        if(enteredEnrl.isEmpty()){
            enrollmentNo.setError("Your enrollment no. is required for registration !");
            enrollmentNo.requestFocus();
            return;
        }
        if(enteredEnrl.length()<12) {
            enrollmentNo.setError("EnrollmentNo length should be 12!");
            enrollmentNo.requestFocus();
            return;
        }

        //validate sem field
        if(enteredSem.isEmpty()){
            currentSem.setError("Your current sem is required for registration !");
            currentSem.requestFocus();
            return;
        }

        //validate caste field
        if(enteredCaste.equals("Choose Your Caste")) {
            textCaste.setError("Your caste is required for registration!");
            textCaste.requestFocus();
            return;
        }

        // validate password field
        if(enteredPass.isEmpty()){
            Password.setError("Password is required for registration !");
            Password.requestFocus();
            return;
        }

        if(enteredPass.length() < 6){
            Password.setError("Minimum password length should be 6 character !");
            Password.requestFocus();
            return;
        }

        //set visibility of register button
        regButton.startAnimation();

        // register the user in firebase
        mAuth.createUserWithEmailAndPassword(enteredEmail,enteredPass)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // on successful registrations process
                        if(task.isSuccessful()){

                            //create collection users for storing data
                            //inside collection users we are storing another collection by userID

                            DocumentReference documentReference = fstore.collection("Users").document(mAuth.getCurrentUser().getUid());
                            //create hash map for storing data of user
                            Map<String,Object> user = new HashMap<>();

                            // store data of user using put method
                            user.put("userName",enteredName);
                            user.put("userEmail",enteredEmail);
                            user.put("userPhone",enteredMobileNo);
                            user.put("userEnrollmentNo",enteredEnrl);
                            user.put("userCurrentSem",enteredSem);
                            user.put("userCaste",enteredCaste);
                            user.put("userPassword",enteredPass);


                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(RegisterActivity.this,"User has been registered successfully!",Toast.LENGTH_LONG).show();
                                    resetAllDetails();
                                    regButton.stopAnimation();
                                    startActivity(new Intent(getApplicationContext(),LoginActivity.class));
                                    regButton.setVisibility(View.GONE);
                                }

                            });

                        }
                        // user facing error while registering
                        else{
                            Toast.makeText(RegisterActivity.this,"Failed to register! Error: "+task.getException().getMessage() ,Toast.LENGTH_LONG).show();
                            resetAllDetails();
                        }
                    }
                });
        }
    public void resetAllDetails() {
        name.getText().clear();
        email.getText().clear();
        mobileNo.getText().clear();
        enrollmentNo.getText().clear();
        currentSem.getText().clear();
        textCaste.getText().clear();
        Password.getText().clear();
    }

}
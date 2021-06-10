package com.vishupatel.clgstuff;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import de.hdodenhof.circleimageview.CircleImageView;


public class UserProfileActivity extends AppCompatActivity {

    // Initialize variable
    ChipNavigationBar bottomNavigationView;

    // profile circle image view
    CircleImageView profileImageView;

    private FirebaseFirestore fStore;
    private FirebaseAuth mAuth;
    private DocumentReference documentReference;
    private TextInputEditText enteredUserName,enteredUserEmail,enteredUserMobileNo,enteredUserEnrlNo,enteredCurrentSem;
    private AutoCompleteTextView enteredUserCaste;
    private TextView profileName , profileEmail;
    private String userName,userEmail,userPhone,userEnrollmentNo,userCurrentSem,userCaste;
    private CircularProgressButton cirUpdateBtn;

    // for user profile image
    private Uri imageURI;
    private String myURI = "";

    // for set user profile image
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        //getting reference of fire store
        fStore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();

        // initialize documentReference variable with current user id
        documentReference = fStore.collection("Users").document(mAuth.getCurrentUser().getUid());

        // edit input text

        enteredUserName = findViewById(R.id.profileTIEditText_Name);
        enteredUserEmail = findViewById(R.id.profileTIEditText_Email);
        enteredUserMobileNo = findViewById(R.id.profileTIEditText_MobileNo);
        enteredUserEnrlNo = findViewById(R.id.profileTIEditText_EnrlNo);
        enteredCurrentSem = findViewById(R.id.profileTIEditText_CurrentSem);
        profileEmail = findViewById(R.id.profileEmail);
        profileName = findViewById(R.id.profileName);

        // update button
        cirUpdateBtn = findViewById(R.id.cirUpdateBtn);

        // reference of  userprofile image view
        profileImageView = findViewById(R.id.profileImage);

        // set dropdown list item using adapter
        String []listOfCaste = getResources().getStringArray(R.array.caste_of_student);
        enteredUserCaste = findViewById(R.id.profileTIEditText_Caste);
        // set the set of string array values to drop down menu
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.dropdown_resource_file,listOfCaste);
        enteredUserCaste.setAdapter(adapter);

        //load user data form firestore
        showUserData();

        // getting reference of bottom navigation bar
        bottomNavigationView = findViewById(R.id.chipBottomNavigation);

        // set home selected
        bottomNavigationView.setItemSelected(R.id.chip_menu_userProfile,true);

        // perform itemSelectedListener
        bottomNavigationView.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {

                switch (i){

                    case R.id.chip_menu_home:
                        startActivity(new Intent(UserProfileActivity.this,ScholarshipActivity.class));
                        overridePendingTransition(0,0);
                        break;

                    case R.id.chip_menu_userProfile:
                        break;

                    case R.id.chip_menu_Communication:
                        startActivity(new Intent(UserProfileActivity.this,CommunicationActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
            }
        });

    }

    // show user data into user profile in which we are fetching data from the fire store
    public void showUserData(){

        ProgressDialog progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Fetch Profile Data");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

       documentReference.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
           @Override
           public void onComplete(@NonNull Task<DocumentSnapshot> task) {

               userName = task.getResult().getString("userName");
               userEmail = task.getResult().getString("userEmail");
               userPhone = task.getResult().getString("userPhone");
               userEnrollmentNo = task.getResult().getString("userEnrollmentNo");
               userCurrentSem = task.getResult().getString("userCurrentSem");
               userCaste = task.getResult().getString("userCaste");

               StorageReference temp = storageReference.child("userProfileImages/" + mAuth.getUid().toString());

               if (temp != null) {

                   temp.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                       @Override
                       public void onSuccess(Uri uri) {
                           // set user profile image
                           Picasso.get().load(uri).into(profileImageView);
                       }
                   }).addOnFailureListener(new OnFailureListener() {
                       @Override
                       public void onFailure(@NonNull Exception e) {
                           Toast.makeText(UserProfileActivity.this, "Failed to download", Toast.LENGTH_LONG).show();
                       }
                   });
               } else{
                      // this part for new user when there is no image url for new user
                   // null object for storage reference
               }

               enteredUserName.setText(userName);
               enteredUserEmail.setText(userEmail);
               enteredUserMobileNo.setText(userPhone);
               enteredUserEnrlNo.setText(userEnrollmentNo);
               enteredCurrentSem.setText(userCurrentSem);
               enteredUserCaste.setText(userCaste);


               // set profile name and email beside of user dp
               profileName.setText(enteredUserName.getText().toString());
               profileEmail.setText(enteredUserEmail.getText().toString());

               progressDialog.dismiss();
           }
       });


    }

    public void onUpdateUserData(View view) {

        cirUpdateBtn.startAnimation();

        // getting all entered data from text field for validation like not empty!

        String enteredName = enteredUserName.getText().toString().trim();
        String enteredEmail = enteredUserEmail.getText().toString().trim();
        String enteredMobileNo = enteredUserMobileNo.getText().toString().trim();
        String enteredEnrl = enteredUserEnrlNo.getText().toString().trim();
        String enteredSem = enteredCurrentSem.getText().toString().trim();
        String enteredCaste = enteredUserCaste.getText().toString().trim();

        // validate user data
        boolean isDataValdiate = validateUserData(enteredName,enteredEmail,enteredMobileNo,enteredEnrl,enteredSem,enteredCaste);

        if(isDataValdiate) {
            // create map for store updated user data into fire store
            Map<String, Object> mapUpdatedUserData = new HashMap<>();

            if (enteredName != userName) {
                mapUpdatedUserData.put("userName", enteredName);
            }
            enteredUserName.clearFocus();

            if (enteredEmail != userEmail) {
                mapUpdatedUserData.put("userEmail", enteredEmail);
            }
            enteredUserEmail.clearFocus();

            if (enteredMobileNo != userPhone) {
                mapUpdatedUserData.put("userPhone", enteredMobileNo);
            }
            enteredUserMobileNo.clearFocus();

            if (enteredEnrl != userEnrollmentNo) {
                mapUpdatedUserData.put("userEnrollmentNo", enteredEnrl);
            }
            enteredUserEnrlNo.clearFocus();

            if (enteredSem != userCurrentSem) {
                mapUpdatedUserData.put("userCurrentSem", enteredSem);
            }
            enteredCurrentSem.clearFocus();

            if (enteredCaste != userCaste) {
                mapUpdatedUserData.put("userCaste", enteredCaste);
            }
            enteredUserCaste.clearFocus();

            documentReference.update(mapUpdatedUserData)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UserProfileActivity.this, "Updated data successfully!", Toast.LENGTH_LONG).show();

                            cirUpdateBtn.stopAnimation();
                            cirUpdateBtn.revertAnimation();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(UserProfileActivity.this, "Can't Updated Data ! Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    cirUpdateBtn.stopAnimation();
                    cirUpdateBtn.revertAnimation();
                }
            });

        }
        cirUpdateBtn.stopAnimation();
        cirUpdateBtn.revertAnimation();
    }

    public boolean validateUserData(String enteredName,String enteredEmail,String enteredMobileNo,String enteredEnrl ,
                                 String enteredSem ,String enteredCaste){

        // validate name input field
        if(enteredName.isEmpty()){
            enteredUserName.setError("Your Name is required for registration !");
            enteredUserName.requestFocus();
            return false;
        }

        // validate email field
        if(enteredEmail.isEmpty()){
            enteredUserEmail.setError("Your email is required for registration !");
            enteredUserEmail.requestFocus();
            return false;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(enteredEmail).matches()){
            enteredUserEmail.setError("Please enter valid email address !");
            enteredUserEmail.requestFocus();
            return false;
        }

        // validate mobile no field
        if(enteredMobileNo.isEmpty()){
            enteredUserMobileNo.setError("Your mobile No. is required for registration !");
            enteredUserMobileNo.requestFocus();
            return false;
        }
        if(enteredMobileNo.length()<10) {
            enteredUserMobileNo.setError("MobileNo length should be 10!");
            enteredUserMobileNo.requestFocus();
            return false;
        }

        // validate enrollment no field
        if(enteredEnrl.isEmpty()){
            enteredUserEnrlNo.setError("Your enrollment no. is required for registration !");
            enteredUserEnrlNo.requestFocus();
            return false;
        }
        if(enteredEnrl.length()<12) {
            enteredUserEnrlNo.setError("EnrollmentNo length should be 12!");
            enteredUserEnrlNo.requestFocus();
            return false;
        }

        //validate sem field
        if(enteredSem.isEmpty()){
            enteredCurrentSem.setError("Your current sem is required for registration !");
            enteredCurrentSem.requestFocus();
            return false;
        }

        //validate caste field
        if(enteredCaste.equals("Choose Your Caste")) {
            enteredUserCaste.setError("Your caste is required for registration!");
            enteredUserCaste.requestFocus();
            return false;
        }

        return true;
    }

    // reset user password
    public void setNewPassword(@NotNull View view) {

            AlertDialog.Builder passwordResetDialog = new AlertDialog.Builder(view.getContext());
            passwordResetDialog.setTitle("Reset Your Password ?");
            passwordResetDialog.setCancelable(false);
            passwordResetDialog.setMessage("Reset password link has been set to this mail " + userEmail + "");

            passwordResetDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    mAuth.sendPasswordResetEmail(userEmail).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(UserProfileActivity.this, "Reset link has been sent to your email !", Toast.LENGTH_LONG).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(UserProfileActivity.this, "Reset link has Not been sent to your email ! " + e.getMessage(), Toast.LENGTH_LONG).show();
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

    // logout form application
    public void logoutFromApp(@NotNull View view){

        AlertDialog.Builder logoutDialog = new AlertDialog.Builder(view.getContext());
        logoutDialog.setMessage("Are you sure you want to logout form application ?");
        logoutDialog.setTitle("Logout");
        logoutDialog.setCancelable(false);

        logoutDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mAuth.signOut();
                finishAffinity();
                startActivity(new Intent(UserProfileActivity.this,LoginActivity.class));
                overridePendingTransition(0,0);

            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        logoutDialog.create().show();

    }

    // change user profile image
    public void changeUserProfileImage(View view){

        CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(UserProfileActivity.this);
        // upload profile image to firestore
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK && data != null){

            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            imageURI = result.getUri();

            profileImageView.setImageURI(imageURI);
            uploadProfileImage();

        }
        else{
            //Toast.makeText(UserProfileActivity.this,"Error,Try Again! Error Code :"+String.valueOf(requestCode) ,Toast.LENGTH_LONG).show();
        }
    }

    private void uploadProfileImage() {

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Set Your Profile Image");
        progressDialog.setMessage("Please wait, While we are setting your profile image ");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        if(imageURI != null) {

            StorageReference imageRef = storageReference.child("userProfileImages/"+mAuth.getUid().toString());

            imageRef.putFile(imageURI).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(UserProfileActivity.this, "Profile Image updated successfully!", Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(UserProfileActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        progressDialog.dismiss();
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(UserProfileActivity.this);
        alertDialog.setTitle("Close Application");
        alertDialog.setCancelable(false);
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

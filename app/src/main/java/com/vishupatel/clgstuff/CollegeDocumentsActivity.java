package com.vishupatel.clgstuff;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static android.os.Environment.DIRECTORY_DOWNLOADS;

public class CollegeDocumentsActivity extends AppCompatActivity {

    StorageReference storageReference;
    FirebaseStorage firebaseStorage;
    StorageReference referenceForDownload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_documents);
        firebaseStorage = FirebaseStorage.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickBack(View view){
        onBackPressed();
    }

    public void onClickDownload(View view){

        ProgressDialog progressDialog =  new ProgressDialog(this);
        progressDialog.setMessage("Please wait a while Your download is ready !");
        progressDialog.show();
        progressDialog.setCanceledOnTouchOutside(false);


        String fileName = null;
        storageReference = firebaseStorage.getReference();

        switch (view.getId()) {


            case R.id.clg_menu_item_1:
            case R.id.clg_menu_item_btn_1:
                referenceForDownload = storageReference.child("CollegeDocuments/MYSY_Self_declaration_for_Non_IT_returns2021.pdf");
                fileName = "MYSY_Self_declaration_for_Non_IT_returns";
                break;

            case R.id.clg_menu_item_2:
            case R.id.clg_menu_item_btn_2:
                referenceForDownload = storageReference.child("CollegeDocuments/MYSY_Selfdeclarationform_new2021.pdf");
                fileName = "MYSY_Self_Declaration";
                break;


            case R.id.clg_menu_item_3:
            case R.id.clg_menu_item_btn_3:
                referenceForDownload = storageReference.child("CollegeDocuments/MYSY_FRESHER_Institute_Letter_Head.pdf");
                fileName = "MYSY_FRESHER_Institute_Letter_Head";
                break;

            case R.id.clg_menu_item_4:
            case R.id.clg_menu_item_btn_4:
                referenceForDownload = storageReference.child("CollegeDocuments/MYSY_RENEWAL_INSTITUTE_LETTER_HEAD.pdf");
                fileName = "MYSY_RENEWAL_INSTITUTE_LETTER_HEAD";
                break;

            case R.id.clg_menu_item_5:
            case R.id.clg_menu_item_btn_5:
                referenceForDownload = storageReference.child("CollegeDocuments/APPLICATION_BONAFIED_CERTIFICATE.pdf");
                fileName = "APPLICATION_BONAFIED_CERTIFICATE";
                break;

            case R.id.clg_menu_item_6:
            case R.id.clg_menu_item_btn_6:
                referenceForDownload = storageReference.child("CollegeDocuments/Form_Character_certificate.pdf");
                fileName = "Form_Character_certificate";
                break;
        }

        Log.i("IDforView", String.valueOf(view.getId()));

        String finalFileName = fileName;

        referenceForDownload.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {

                progressDialog.dismiss();
                DownloadFile(CollegeDocumentsActivity.this, finalFileName, ".pdf", DIRECTORY_DOWNLOADS, uri.toString());
                Toast.makeText(CollegeDocumentsActivity.this,"Check your Downloads Folder ",Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(CollegeDocumentsActivity.this, "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    public void DownloadFile(Context context, String FileName , String fileExtension, String destinationDirectory,String url){

        // download file into default downloads folder
        DownloadManager downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri urlForDownload = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(urlForDownload);

        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setDestinationInExternalFilesDir(context,destinationDirectory,FileName+fileExtension);

        downloadManager.enqueue(request);

    }
}
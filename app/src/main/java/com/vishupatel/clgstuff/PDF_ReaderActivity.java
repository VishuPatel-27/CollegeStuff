package com.vishupatel.clgstuff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PDF_ReaderActivity extends AppCompatActivity {

    private WebView webview;
    private ProgressBar progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pdf_reader);

        // initialize variables
        webview = (WebView) findViewById(R.id.webview);


        webview.getSettings().setJavaScriptEnabled(true);

        // call method for loading data from pdf url
        onLoadPDFDocument(getIntent());

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void onClickBack(View view){
        onBackPressed();
    }

    private void onLoadPDFDocument(Intent intent) {

        String valueOfMenuItem = intent.getStringExtra("menuItem");
        String url= "";
        String pdf = "";

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Fetch Document");
        progressDialog.setMessage("Please wait for while when application fetch document");
        progressDialog.setIndeterminate(false);
        progressDialog.setCancelable(false);
        progressDialog.show();

        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                progressDialog.dismiss();
            }
        });

        switch (valueOfMenuItem)
        {

            case "2" :
                pdf = "https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FAadhaar_linking.pdf?alt=media&token=ccd6ba9b-d3ef-4568-8c4d-ce5f2d53a5de";
                break;

            case "3" :
                pdf="https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FMYSY_HC.pdf?alt=media&token=4178e9e3-c69e-4c0e-aa53-623db1905e93";
                break;

            case "4" :
                pdf="https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FInstruction%20for_returning_sahay.pdf?alt=media&token=a6bff7bd-40a6-498a-87f1-30b1c9004ec5";
                break;

            case "5" :
                pdf="https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FGUIDELINES_FOR_STUDENTS_OF_TECHNICAL_DEPARTMENT.pdf?alt=media&token=a1e919ad-cd2f-4893-ae21-9169725fae8d";
                break;

            case "6" :
                pdf="https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FInstructionstoStudents2021.pdf?alt=media&token=e033a066-98ed-4434-924e-aff52713e1c2";
                break;

            case "7" :
                pdf="https://firebasestorage.googleapis.com/v0/b/clg-stuff.appspot.com/o/MYSY%2FInstruction_pending_doc.pdf?alt=media&token=edd2634e-da83-490c-a5b8-9b80c84efe2a";
                break;

        }

        try {
            url = URLEncoder.encode(pdf,"UTF-8");
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        webview.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url="+url);

    }
}
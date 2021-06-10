
package com.vishupatel.clgstuff.SplashScreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.vishupatel.clgstuff.LoginActivity;
import com.vishupatel.clgstuff.R;

public class SplashScreenActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 3000;
    // variables
    ImageView appIcon;
    TextView textView;
    Animation topAnimation,btmAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        super.onCreate(savedInstanceState);

        //Animation
        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        btmAnimation = AnimationUtils.loadAnimation(this,R.anim.bottom_animation);

        //Hooks
        appIcon = findViewById(R.id.splashImageView);
        textView = findViewById(R.id.splashTextView);


        textView.setAnimation(btmAnimation);
        appIcon.setAnimation(topAnimation);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        },SPLASH_SCREEN);
    }
}
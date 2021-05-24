package com.vishupatel.clgstuff;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.vishupatel.clgstuff.Fragments.AboutFragment;
import com.vishupatel.clgstuff.Fragments.CommunicationFragment;
import com.vishupatel.clgstuff.Fragments.ScholarshipFragment;
import com.vishupatel.clgstuff.Fragments.UserProfileFragment;

public class MainContentActivity extends AppCompatActivity {

    // Initialize variable
    MeowBottomNavigation bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_content);

        // Assign Variable
        bottomNavigation = findViewById(R.id.bottom_navigation);

        // Add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1,R.drawable.ic_scholarship));
        bottomNavigation.add(new MeowBottomNavigation.Model(2,R.drawable.ic_user_icon));

        //Icons made by https://www.flaticon.com/  title="Flaticon"
        bottomNavigation.add(new MeowBottomNavigation.Model(3,R.drawable.ic_communicationicon));
        bottomNavigation.add(new MeowBottomNavigation.Model(4,R.drawable.ic_about));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {

                // Initialize fragments
                Fragment fragment = null;

                switch(item.getId()){

                    case 1 :
                            // When selected item id is 1
                            // Initialize scholarship fragment
                            fragment = new ScholarshipFragment();
                            break;

                    case 2:
                            // When selected item id is 2
                            // Initialize user profile fragment
                            fragment = new UserProfileFragment();
                            break;

                    case 3:
                            // When selected item id is 3
                            // Initialize communication fragment
                            fragment = new CommunicationFragment();
                            break;

                    case 4:
                            // When selected item id is 4
                            // Initialize about fragment
                            fragment = new AboutFragment();
                            break;

                }

                // load fragment

                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container,fragment)
                        .commit();
            }
        });

        // set scholarship fragment initially selected when application in open

        bottomNavigation.show(2,true);

        // on click listener
        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

                Toast.makeText(MainContentActivity.this, "You Clicked  "+ item.getId(), Toast.LENGTH_SHORT).show();

            }
        });


    }
}
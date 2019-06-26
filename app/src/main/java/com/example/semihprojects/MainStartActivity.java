package com.example.semihprojects;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class MainStartActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private AppBarLayout appBarLayout;
    private ViewPager viewPager;

    private EditText email,password;
    private Button sign_in, sign_up;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_start);
        tabLayout = findViewById(R.id.tablayout_id);
        appBarLayout = findViewById(R.id.appbarid);
        viewPager = findViewById(R.id.viewpager);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.AddFragment(new FragmentLogin(), "Login");
        adapter.AddFragment(new FragmentRegister(), "Register");
        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        sign_in = findViewById(R.id.sign_in);
        // sign_up = findViewById(R.id.sign_up);


    }
}

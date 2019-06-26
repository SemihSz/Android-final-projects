package com.example.semihprojects;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hitomi.cmlibrary.CircleMenu;
import com.hitomi.cmlibrary.OnMenuSelectedListener;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToogle;
    CircleMenu circleMenu;
    private TextView userNameView, email;
    CircleImageView profile_image;
    private FirebaseAuth mAuth;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circleMenu = findViewById(R.id.circle_menu);
        mDrawerLayout = findViewById(R.id.drawerLayout);


        mAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = mAuth.getCurrentUser();
        //String user = intent.getStringExtra("userdid");

        try {

            if (user != null) {
                userId = user.getUid();
                System.out.println("id: " + userId);
                data_Retrieve(userId);
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
        mToogle = new ActionBarDrawerToggle(this,mDrawerLayout,R.string.open,R.string.close);
        mDrawerLayout.addDrawerListener(mToogle);
        mToogle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        NavigationView navigationView = findViewById(R.id.navigationView);
        View header_view = navigationView.getHeaderView(0);
        userNameView = header_view.findViewById(R.id.navigationUsername);
        email = header_view.findViewById(R.id.mailText);
        profile_image = header_view.findViewById(R.id.profile_image);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                if (menuItem.getItemId() == R.id.logouts) {
                   signOut();
                }
                return true;

            }
        });

        circleMenu.setMainMenu(Color.parseColor("#6495ED"), R.drawable.add, R.drawable.remove)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.search)
                .addSubMenu(Color.parseColor("#009688"), R.drawable.currency)
                .addSubMenu(Color.parseColor("#6495ED"), R.drawable.country)
                .addSubMenu(Color.parseColor("#258CFF"), R.drawable.cal)
                .setOnMenuSelectedListener(new OnMenuSelectedListener() {
                    @Override
                    public void onMenuSelected(int index) {
                        Handler handler = new Handler();
                        switch (index) {
                            case 0:
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "this", Toast.LENGTH_LONG).show();
                                        IntentMethodSearched();
                                    }
                                }, 555);
                                break;
                            case 1:
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Currency", Toast.LENGTH_LONG).show();
                                        currencyIntent();
                                    }
                                }, 555);
                                break;
                            case 2:
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Country", Toast.LENGTH_LONG).show();
                                        startActivity(new Intent(MainActivity.this, MainMapActivity.class));
                                    }
                                }, 555);
                                break;
                            case 3:
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(getApplicationContext(), "Scores Calculator Activity", Toast.LENGTH_LONG).show();
                                        Intent intent = new Intent(MainActivity.this, MidtermActivity.class);
                                        intent.putExtra("userıd", userId);
                                        startActivity(intent);
                                    }
                                }, 555);
                                break;

                        }
                    }
                });

    }

    public void personalInformation() {

    }

    public void helpQuestion() {

    }

    public void signOut() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage(R.string.sign_out).setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getApplicationContext(), R.string.stay_in_app, Toast.LENGTH_LONG).show();

            }
        }).setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getApplicationContext(), R.string.sign_out_toast, Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, MainStartActivity.class);
                startActivity(intent);
            }
        });
        builder.show();
    }

    private void currencyIntent() {
        Intent intent = new Intent(MainActivity.this, CurrencyActivity.class);
        intent.putExtra("userıd", userId);
        startActivity(intent);
    }

    private void data_Retrieve(String userId) {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        DatabaseReference mRefs = databaseReference.child("Users").child(userId);

        mRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                try {
                    if (dataSnapshot.getValue() != null) {
                        User user = dataSnapshot.getValue(User.class);
                        userNameView.setText(user.getUsername());
                        email.setText(user.getEmail());
                        System.out.println("User name: " + userNameView);
                        System.out.println("Email is : " + email);
                    }

                } catch (DatabaseException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mToogle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void IntentMethodSearched() {
        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
        startActivity(intent);
    }


}

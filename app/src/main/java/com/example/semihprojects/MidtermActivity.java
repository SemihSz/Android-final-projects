package com.example.semihprojects;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MidtermActivity extends AppCompatActivity {

    private String userdId;
    private EditText midtermScore, projectScore, finalScore, percMid, percPro, percFinal;
    private Button calculate;
    ListView general_Scores;
    private int attemp = 0;
    ArrayAdapter<String> arrayAdapter;
    private ArrayList<String> dataShow = new ArrayList<>();
    private ArrayList<String> arry = new ArrayList<>();
    private FirebaseAuth mAuth;
    DatabaseReference myRef;
    private FirebaseDatabase mFirebaseDatabase;
    public static final String Shared_pref = "sharedPrefs";
    public static final String text = "sharedPrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_midterm);

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();

        userdId = getIntent().getStringExtra("userıd");

        midtermScore = findViewById(R.id.midtermscore);
        projectScore = findViewById(R.id.projectscore);
        finalScore = findViewById(R.id.finalscore);
        percMid = findViewById(R.id.midtermpercentage);
        percPro = findViewById(R.id.projectpercentage);
        percFinal = findViewById(R.id.finalpercentage);
        calculate = findViewById(R.id.calculate);
        general_Scores = findViewById(R.id.midtermList);
        arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,dataShow);

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!TextUtils.isEmpty(midtermScore.getText().toString())&&
                        !TextUtils.isEmpty(projectScore.getText().toString()) &&
                        !TextUtils.isEmpty(finalScore.getText().toString())&&
                        !TextUtils.isEmpty(percMid.getText().toString())&&
                        !TextUtils.isEmpty(percPro.getText().toString())&&
                        !TextUtils.isEmpty(percFinal.getText().toString())
                ){
                    double s1 = Double.parseDouble(midtermScore.getText().toString());
                    double s2 = Double.parseDouble(projectScore.getText().toString());
                    double s3 = Double.parseDouble(finalScore.getText().toString());
                    double p1 = Double.parseDouble(percMid.getText().toString());
                    double p2 = Double.parseDouble(percPro.getText().toString());
                    double p3 = Double.parseDouble(percFinal.getText().toString());
                    double new_result = calculateGpa(s1,s2,s3,p1,p2,p3);
                    String string_final_result = Double.toString(new_result);
                    saveInFirebase(string_final_result);

                    //dateResult();
                    System.out.println(dataShow);
                    general_Scores.setAdapter(arrayAdapter);
                    arrayAdapter.notifyDataSetChanged();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dataShow.clear();
                        }
                    },10);

                }
                else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(MidtermActivity.this);
                    builder.setMessage(R.string.alert).
                            setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    });
                    builder.show();
                }
            }
        });

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    private void saveInFirebase(String result) {
        System.out.println("Attempt : "+ attemp);
        if(attemp > 0){
            Date date = new Date();
            Date currentTime = Calendar.getInstance().getTime();
            String cT = currentTime.toString();
            int stringAtempt = loadData();
            String stringDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
            String genral_result = "Dates: " + stringDate + " " + "Result: " + result;
            Scores scores = new Scores(genral_result );
            myRef.child("Scores").child(userdId).child("Deneme: " + stringAtempt).setValue(scores);
            savedData();
        }
        else {
            Date date = new Date();
            Date currentTime = Calendar.getInstance().getTime();
            String cT = currentTime.toString();
            String stringAtempt = Integer.toString(attemp);
            String stringDate = DateFormat.getDateInstance(DateFormat.MEDIUM).format(date);
            String genral_result = "Dates: " + stringDate + " " + "Result: " + result;
            Scores scores = new Scores(genral_result );
            myRef.child("Scores").child(userdId).child("Deneme: " + stringAtempt).setValue(scores);
            savedData();
        }
        attemp = attemp + 1;


    }

    private double calculateGpa(double m, double p,double f, double mp,double pp, double fp){

        return ((m*mp)/100.0) + ((p*pp)/100.0) + ((f*fp)/100.0);
    }
    public void showData(){
        try{
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            DatabaseReference mRefs = databaseReference.child("Scores").child(userdId);
            mRefs.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    try{
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            arry.add(ds.getKey());
                        }
                        System.out.println(arry);


                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });



            System.out.println("aflşkaşflajkflşajlfalş"+dataShow);


        }catch (NullPointerException ex){
            ex.printStackTrace();
        }

    }

    private void savedData(){
        SharedPreferences sharedPreferences = getSharedPreferences(Shared_pref,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(userdId, attemp);
        editor.apply();
    }
    public int loadData(){
        SharedPreferences sharedPreferences =  getSharedPreferences(Shared_pref,MODE_PRIVATE);
        int attemps = sharedPreferences.getInt(userdId, attemp);
        return attemps;
    }

}

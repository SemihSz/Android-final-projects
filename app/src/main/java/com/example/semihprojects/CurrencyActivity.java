package com.example.semihprojects;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class CurrencyActivity extends AppCompatActivity {
    private static TextView tryText;
    private static TextView cadText;
    private static TextView usdText;
    private static TextView jpyText;
    private static TextView chfText;
    private static TextView converterText;
    private static Button converterButton;
    private static EditText converterEditText;
    private static String tl;
    boolean contorl_click = false;
    private static double euro;
    private String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);
        tryText = findViewById(R.id.tryText);
        cadText = findViewById(R.id.cadText);
        usdText = findViewById(R.id.usdText);
        jpyText = findViewById(R.id.jpyText);
        chfText = findViewById(R.id.chfText);
        converterButton = findViewById(R.id.converter);
        converterEditText = findViewById(R.id.convertedittext);
        converterText = findViewById(R.id.eurotl);
        userId = getIntent().getStringExtra("userıd");
        converterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int s = Integer.parseInt(converterEditText.getText().toString());
                double convert_tl = Double.parseDouble(tl);
                double new_money = s/convert_tl;
                converterText.setText(s + " Euros == " + new_money +"TL");

            }
        });
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
    public void getRates(View view){
        DownloadData downloadDate = new DownloadData();
        try{

            String url = "http://data.fixer.io/api/latest?access_key=bf6d677ad7b89a38d7e9dc5c57c6d87a&format=1";
            downloadDate.execute(url);


        }catch (Exception e){

        }
    }
    public void getConver(View view){

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(CurrencyActivity.this, MainActivity.class);
        intent.putExtra("userıd", userId);
        startActivity(intent);

    }
    @Override
    public boolean onSupportNavigateUp() {

        onBackPressed();
        return true;
    }



    private static class DownloadData extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String result = "";
            URL url;
            HttpURLConnection httpURLConnection;

            try{
                url = new URL(strings[0]);
                httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream ınputStream = httpURLConnection.getInputStream();
                InputStreamReader ınputStreamReader = new InputStreamReader(ınputStream);

                int data  = ınputStreamReader.read();

                while (data > 0){
                    char character = (char) data;
                    result+= character;
                    data = ınputStreamReader.read();
                }

            }
            catch (Exception e){
                e.printStackTrace();
                return null;
            }
            return result;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try{
                JSONObject jsonObject = new JSONObject(s);
                String base = jsonObject.getString("base");
                //System.out.println("base: " + base);
                String rates = jsonObject.getString("rates");
                //System.out.println("Rates: " + rates);

                JSONObject jsonObject1 = new JSONObject(rates);
                String turkishLira = jsonObject1.getString("TRY");
                tl = turkishLira;
//                int num = Integer.parseInt(converterEditText.getText().toString());
//                double new_number = num/euro;
//                System.out.println("erou" + new_number);
                tryText.setText("TRY: " + turkishLira);
                System.out.println("");
                String usd = jsonObject1.getString("USD");
                usdText.setText("USD: " + usd);

                String cad = jsonObject1.getString("CAD");
                cadText.setText("CAD: " + cad);

                String jpy = jsonObject1.getString("JPY");
                jpyText.setText("JPY: " + jpy);

                String chf = jsonObject1.getString("CHF");
                chfText.setText("CHF: " + chf);

            }
            catch (Exception e){

            }
            //System.out.println("Alınan data: " + s);
        }
    }
}
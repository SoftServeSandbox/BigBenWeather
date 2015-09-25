package com.example.administrator.myapplication;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;

public class MainActivity extends AppCompatActivity {

    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;
    private TextView textView6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyAsyncTask task = new MyAsyncTask();

        this.setContentView(R.layout.activity_main);
        textView1 = (TextView) this.findViewById(R.id.textView);
        textView2 = (TextView) this.findViewById(R.id.textView2);
        textView3 = (TextView) this.findViewById(R.id.textView3);
        textView4 = (TextView) this.findViewById(R.id.textView4);
        textView5 = (TextView) this.findViewById(R.id.textView5);
        textView6 = (TextView) this.findViewById(R.id.textView6);
        task.execute("edededede");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public class MyAsyncTask extends AsyncTask<String, Void, JSONObject> {

        @Override
        protected JSONObject doInBackground(String... params) {
            InputStream stream = null;
            try {
                stream = new URL("http://api.openweathermap.org/data/2.5/weather?q=London&units=metric").openStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            InputStreamToStringExample ise = new InputStreamToStringExample();
            String result = ise.getStringFromInputStream(stream);
            try {
                return new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(JSONObject result) {
            try {
                textView1.setText("Clouds: " + String.valueOf(result.getJSONObject("clouds").getInt("all")));
                textView2.setText("Temperature: " + String.valueOf(result.getJSONObject("main").getInt("temp")));
                textView3.setText("Pressure: " + String.valueOf(result.getJSONObject("main").getInt("pressure")));
                textView4.setText("Humidity: " + String.valueOf(result.getJSONObject("main").getInt("humidity")));
                textView5.setText("Min temperature: " + String.valueOf(result.getJSONObject("main").getInt("temp_min")));
                textView6.setText("Max temperature: " + String.valueOf(result.getJSONObject("main").getInt("temp_max")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public class InputStreamToStringExample {

        // convert InputStream to String
        private String getStringFromInputStream(InputStream is) {

            BufferedReader br = null;
            StringBuilder sb = new StringBuilder();

            String line;
            try {

                br = new BufferedReader(new InputStreamReader(is));
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            return sb.toString();

        }

    }
}


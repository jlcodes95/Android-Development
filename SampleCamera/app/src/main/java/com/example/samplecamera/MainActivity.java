package com.example.samplecamera;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "DEBUG_MAIN";
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //MARK: Click handlers

    public void onClickDefaultListView(View view) {
        ArrayList<Employee> employees = new ArrayList();
        Log.d(TAG, "default list view");
        //reading from json file
        //1. Get json file from assets folder
        String FILENAME = "employees.json";
        String jsonString;

        try {
            //open file
            InputStream fileData = getAssets().open(FILENAME);

            //get information about file
            int fileSize = fileData.available();

            //setup array to store each piece of data in the file
            //size of array is the same size as the file
            byte[] buffer = new byte[fileSize];

            //get all data from file
            fileData.read(buffer);

            //close file
            jsonString = new String(buffer, "UTF-8");

            //convert data to json
            Log.d(TAG, jsonString);

        }catch (IOException e) {
            Log.d(TAG, "Error opening file.");
            e.printStackTrace();
            return;
        }

        //2. Read file & convert into JSON format
        //A. Convert string to JSON array
        //B. Convert String to JSON object
        try {
            JSONArray json = new JSONArray(jsonString);

            //3. Get data from JSON
            for (int i = 0; i < json.length(); i++) {
                JSONObject obj = json.getJSONObject(i);
                Log.d(TAG, "Name: "+ obj.getString("firstName") + " " + obj.getString("lastName"));
                Log.d(TAG, "Location: "+ obj.getString("region"));
                employees.add(new Employee(obj.getString("firstName"), obj.getString("lastName"), LocalDate.parse(obj.getString("dateHired"))));
            }
            Log.d(TAG, employees.toString());
            Intent i = new Intent(this, EmployeeActivity.class);
            i.putExtra("employees", employees);
            this.startActivity(i);

        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClickCustomListView(View view) {
        Log.d(TAG, "default list view");
        //reading from json file
        //1. Get json file from assets folder
        String FILENAME = "person.json";
        String jsonString;

        try {
            //open file
            InputStream fileData = getAssets().open(FILENAME);

            //get information about file
            int fileSize = fileData.available();

            //setup array to store each piece of data in the file
            //size of array is the same size as the file
            byte[] buffer = new byte[fileSize];

            //get all data from file
            fileData.read(buffer);

            //close file
            jsonString = new String(buffer, "UTF-8");

            //convert data to json
            Log.d(TAG, jsonString);

        }catch (IOException e) {
            Log.d(TAG, "Error opening file.");
            e.printStackTrace();
            return;
        }

        //2. Read file & convert into JSON format
        //A. Convert string to JSON array
        //B. Convert String to JSON object
        try {
            JSONObject json = new JSONObject(jsonString);

            //3. Get data from JSON
            String name = json.getString("firstname") + " " + json.getString("lastname");
            int age = json.getInt("age");
            JSONObject coordinates = json.getJSONObject("coordinates");
            String location = "(" + coordinates.getDouble("lat") + ", " + coordinates.getDouble("lat") + ")";
            JSONArray interests = json.getJSONArray("interests");
            String interest_string = "";
            for (int i = 0; i < interests.length(); i++) {
                interest_string += interests.get(i) + " ";
            }

            Log.d(TAG, "name: " + name + "; age: " + age + "; location: " + location + "; interest: " + interest_string);
            JSONArray jobs = json.getJSONArray("jobs");
            for (int i = 0; i < jobs.length(); i++) {
                JSONObject obj = jobs.getJSONObject(i);
                Log.d(TAG, "Employer: " + obj.getString("employer") + "; Title: " + obj.getString("title") + "; Years: " + obj.getInt("years"));
            }


        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void getFromWeb(View view) {
        String URL = "https://jsonplaceholder.typicode.com/users";

        Request request = new Request.Builder().url(URL).build();
        final Context self = this;

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "Request failed");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                Log.d(TAG, "Request success");
                String jsonDataFromAPI = response.body().string();

                try {
                    ArrayList<Employee> employees = new ArrayList<Employee>();
                    JSONArray data = new JSONArray(jsonDataFromAPI);
                    for (int i = 0; i < data.length(); i++) {
                        JSONObject obj = data.getJSONObject(i);
                        Employee em = new Employee(obj.getString("name"), "", LocalDate.now());
                        employees.add(em);
                    }
//                    self.redirectEmployeeActivity(employees);
                }catch (Exception e) {
                    Log.d(TAG, "Error");
                    e.getStackTrace();
                    return;
                }
            }
        });
    }

//    public void redirectEmployeeActivity(ArrayList<Employee> employees) {
//        Intent i = new Intent(this, EmployeeActivity.class);
//        i.putExtra("employees", employees);
//        this.startActivity(i);
//    }

}

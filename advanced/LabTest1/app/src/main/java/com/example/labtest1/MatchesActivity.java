package com.example.labtest1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MatchesActivity extends AppCompatActivity implements IOnRowClickedListener{

    private final String TAG = "DEBUG_MATCHES";
    private final String BASE_URL = "https://randomuser.me/api/?noinfo&results=10";
    private final String URI_ARG_GENDER = "&gender=";
    private final int CODE = 1;
    private String criteria;
    private ArrayList<Person> matches;
    private RecyclerView recyclerView;
    private MatchesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);
        Log.d(TAG, "inside matches");

        //set up components
        this.matches = new ArrayList<>();
        this.recyclerView = (RecyclerView) findViewById(R.id.rvMatches);
        this.adapter = new MatchesAdapter(matches, this);
        this.recyclerView.setAdapter(this.adapter);
        this.recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //attempt to get data
        try {
            this.criteria = getIntent().getStringExtra("criteria");
            Log.d(TAG, "the criteria is: " + this.criteria);
            getMatch();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getMatch() {
        String URL = BASE_URL;
        URL += URI_ARG_GENDER + this.criteria;
        this.matches.clear();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder().url(URL).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                Log.d(TAG, "Request failed");
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.d(TAG, "Request success");
                final String jsonDataFromAPI = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        parseMatch(jsonDataFromAPI);
                    }
                });
            }
        });

    }

    public void parseMatch(String jsonData) {
        try {
            JSONObject data = new JSONObject(jsonData);
            JSONArray results = data.getJSONArray("results");
            Log.d(TAG, "Total results: " + results.length());
            for (int i = 0; i < results.length(); i++) {
                JSONObject obj = results.getJSONObject(i);
                JSONObject name = obj.getJSONObject("name");
                JSONObject dob = obj.getJSONObject("dob");
                JSONObject coord = obj.getJSONObject("location").getJSONObject("coordinates");
                JSONObject picture = obj.getJSONObject("picture");
                Person person = new Person(
                        name.getString("first"),
                        name.getString("last"),
                        dob.getInt("age"),
                        coord.getDouble("latitude"),
                        coord.getDouble("longitude"),
                        picture.getString("large"));
                matches.add(person);
                Log.d(TAG, "added: " + person.getFullName());
            }
            this.adapter.notifyDataSetChanged();
        }catch (Exception e) {
            Log.d(TAG, "Error: " + e.getMessage());
            e.getStackTrace();
            return;
        }
    }

    @Override
    public void onRowClicked(int position) {
        Log.d(TAG, "Person name is: " + this.matches.get(position).getFullName());
        Intent intent = new Intent(this, PersonDetailsActivity.class);
        Person person = this.matches.get(position);
        intent.putExtra("person", person);
        intent.putExtra("index", position);
        startActivityForResult(intent, CODE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK){
            Person person = data.getParcelableExtra("person");
            this.matches.remove(data.getIntExtra("index", 0));
            Log.d(TAG, "successfully removed: " + this.matches.size());
            this.adapter.notifyDataSetChanged();
        }
    }
}

package com.example.dairy;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class event_view extends AppCompatActivity {
    RecyclerView rView;
    eventAdapter adapter;
    Context context;
    ArrayList<Events> events;
    String emailHol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editp);

        Intent intent=getIntent();
        emailHol =intent.getStringExtra("email_id");
//        Toast.makeText(event_view.this, "ffffff"+emailHol, Toast.LENGTH_SHORT).show();

//        email = getIntent().getExtras().getString("email_id");


        rView = findViewById(R.id.r1);
        rView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new eventAdapter(this);
        rView.setAdapter(adapter);
        events = new ArrayList<Events>();

        getData();
    }

    private void getData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

//        String url = "https://sinchdairy.000webhostapp.com/projectd/eApi.php?email=shilpa@gmail.com";


        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://sinchdairy.000webhostapp.com/projectd/eApi.php?email="+emailHol, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Events eventss = new Events();
                        eventss.setDdate(jsonObject.getString("date"));
                        eventss.setFt(jsonObject.getString("ft"));
                        eventss.setTt(jsonObject.getString("tt"));
                        eventss.setPo(jsonObject.getString("po"));

                        events.add(eventss);

                    }
                }
                catch (JSONException e){
                    Toast.makeText(event_view.this, "JSON is not valid", Toast.LENGTH_SHORT).show();

                }
                adapter.setData(events);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(event_view.this, "Error Occured ", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }
}

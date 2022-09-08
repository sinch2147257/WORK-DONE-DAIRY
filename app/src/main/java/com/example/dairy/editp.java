package com.example.dairy;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
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

public class editp extends AppCompatActivity {
//    String url = "https://sinchdairy.000webhostapp.com/projectd/Api.php";
    RecyclerView recyclerView;
    dataAdapter adapter;
    Context context;
    ArrayList<JsonDataList> jsonDataLists;
    String emailHo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_editp);

        Intent intent=getIntent();
        emailHo =intent.getStringExtra("email_id");
//      Toast.makeText(editp.this, "ffffff"+emailHo, Toast.LENGTH_SHORT).show();


        recyclerView = findViewById(R.id.r1);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new dataAdapter(this);
        recyclerView.setAdapter(adapter);
        jsonDataLists = new ArrayList<JsonDataList>();

        getData();
    }

    private void getData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest("https://sinchdairy.000webhostapp.com/projectd/Api.php?email="+emailHo, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i=0; i<response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        JsonDataList jsonDataList = new JsonDataList();
                        jsonDataList.setDatedis(jsonObject.getString("date"));
                        jsonDataList.setFrom_time(jsonObject.getString("from_time"));
                        jsonDataList.setTo_time(jsonObject.getString("to_time"));
                        jsonDataList.setPortion(jsonObject.getString("portion"));
                        jsonDataList.setTclass(jsonObject.getString("tclass"));
                        jsonDataList.setId(jsonObject.getString("workdone_id"));
                        jsonDataLists.add(jsonDataList);

                    }
                }
                catch (JSONException e){
                    Toast.makeText(editp.this, "JSON is not valid", Toast.LENGTH_SHORT).show();

                }
                adapter.setData(jsonDataLists);
                adapter.notifyDataSetChanged();
                progressDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(editp.this, "Error Occured ", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonArrayRequest);


    }


}

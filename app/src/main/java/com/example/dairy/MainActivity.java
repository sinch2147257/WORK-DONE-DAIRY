package com.example.dairy;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {


    EditText Email, Password;
    Button LogIn ;
    String PasswordHolder, EmailHolder;
//    String emailH;

    //String finalResult ;
    //String HttpURL = "https://shilpaproject.000webhostapp.com/login/login.php";
    Boolean CheckEditText ;
    // ProgressDialog progressDialog;
    //HashMap<String,String> hashMap = new HashMap<>();
    //HttpParse httpParse = new HttpParse();
    public static final String UserEmail = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        setContentView(R.layout.activity_main);

        Email = (EditText)findViewById(R.id.email);
        Password = (EditText)findViewById(R.id.password);
        LogIn = (Button)findViewById(R.id.LogIn);

//        Email.setText(" ");
//        Password.setText(" ");

        LogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                CheckEditTextIsEmptyOrNot();

                if(CheckEditText){

                    getTeacher();


                }
                else {

                    Toast.makeText(MainActivity.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }
            }

            public void CheckEditTextIsEmptyOrNot(){
                EmailHolder=Email.getText().toString();
                PasswordHolder=Password.getText().toString();

                if(TextUtils.isEmpty(EmailHolder)|| TextUtils.isEmpty(PasswordHolder))
                {
                    CheckEditText=false;
                }
                else{
                    CheckEditText=true;
                }
            }


            private void getTeacher() {
                final StringRequest stringRequest1 = new StringRequest(Request.Method.GET, "https://sinchdairy.000webhostapp.com/projectd/login.php",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    EmailHolder = Email.getText().toString();
                                    PasswordHolder = Password.getText().toString();

                                    JSONObject obj = new JSONObject(response);
                                    JSONArray heroArray = obj.getJSONArray("teacher_details");


                                    for (int i = 0; i < heroArray.length(); i++) {

                                        JSONObject jsonObject = heroArray.getJSONObject(i);

                                        String email = jsonObject.getString("id");
                                        String password = jsonObject.getString("password");

                                        Bundle extras = new Bundle();
                                        if(email.equals(EmailHolder) && password.equals(PasswordHolder)){
                                                            Intent in = new Intent(MainActivity.this,home.class);
                                                            in.putExtra(UserEmail, email);
//                                                            in.putExtra("name", "sss");
                                                            Toast.makeText(MainActivity.this, "logged in successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(in);
                                        }
                                    }


                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                final RequestQueue requestQueue1 = Volley.newRequestQueue(MainActivity.this);
                requestQueue1.add(stringRequest1);
            }

        });
    }
    public void onclickR(View view){
        startActivity(new Intent(this,signup.class));

    }
}

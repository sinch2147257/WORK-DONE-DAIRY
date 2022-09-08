package com.example.dairy;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class signup extends AppCompatActivity {


    Button register;
    EditText Name, Contact,Address, Email, Password ;
    String Name_Holder, Contact_Holder,Address_Holder, EmailHolder, PasswordHolder;
    Boolean CheckEditText, NameCheckVal,PasswordCheck,ContactCheck,EmailCheck;



    String webhost_path="https://sinchdairy.000webhostapp.com/projectd/register.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_signup);

        //Assign Id'S
        Name = (EditText) findViewById(R.id.editText_Name);
        Contact = (EditText) findViewById(R.id.editText_contact);
        Address = (EditText) findViewById(R.id.editText_address);
        Email = (EditText) findViewById(R.id.editText_email);
        Password = (EditText) findViewById(R.id.editText_password);

        register = (Button) findViewById(R.id.btn_register);

        Name.setText("");
        Contact.setText("");
        Address.setText("");
        Email.setText("");
        Password.setText("");





        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Checking whether EditText is Empty or Not
                CheckEditTextIsEmptyOrNot();
                NameCheck();
                PasswordCheckFunction();
                ContactCheckFunction();
                EmailCheckFunction();

                if((CheckEditText)&&(NameCheckVal)&&(PasswordCheck)&&(ContactCheck)&&(EmailCheck)) {


                    register_User(Name_Holder, Contact_Holder, Address_Holder, EmailHolder, PasswordHolder);
                }
                else{

                    // If EditText is empty then this block will execute .
                    Toast.makeText(signup.this, "Please fill all form fields.", Toast.LENGTH_LONG).show();

                }

            }
        });

    }

    public void CheckEditTextIsEmptyOrNot(){

        Name_Holder = Name.getText().toString();
        Contact_Holder = Contact.getText().toString();
        Address_Holder = Address.getText().toString();
        EmailHolder = Email.getText().toString();
        PasswordHolder = Password.getText().toString();



        if(TextUtils.isEmpty(EmailHolder) || TextUtils.isEmpty(Name_Holder) || TextUtils.isEmpty(Contact_Holder) || TextUtils.isEmpty(Address_Holder) || TextUtils.isEmpty(PasswordHolder) )
        {
            CheckEditText = false;

        }
        else {

            CheckEditText = true ;
        }

    }
    public void EmailCheckFunction() {

        Pattern p5=Pattern.compile("(?=.*[@.])");
        Matcher m5=p5.matcher(EmailHolder);

        if(!(m5.find()))
        {
            Email.requestFocus();
            Email.setError("Enter a valid email");
            EmailCheck = false;

        }
        else
            EmailCheck=true;
    }
    public void NameCheck() {

        if(!(Name_Holder.matches("[a-zA-Z ]+")))
        {
            Name.requestFocus();
            Name.setError("Only Alphabets and spaces are allowed");
            NameCheckVal = false;
        }
        else
        {
            NameCheckVal=true;
        }
    }
    public void PasswordCheckFunction() {
        Pattern p1=Pattern.compile("(?=.*[A-Z])");
        Matcher m1=p1.matcher(PasswordHolder);

        Pattern p2=Pattern.compile("(?=.*[@#$%^&+=])");
        Matcher m2=p2.matcher(PasswordHolder);

        Pattern p3=Pattern.compile("(?=.*[0-9])");
        Matcher m3=p3.matcher(PasswordHolder);

        Pattern p4=Pattern.compile(" ");
        Matcher m4=p4.matcher(PasswordHolder);

        if(!(m1.find()))
        {
            Password.requestFocus();
            Password.setError("Password must contain an uppercase character");
            PasswordCheck = false;
        }

        else if(!(m2.find()))
        {
            Password.requestFocus();
            Password.setError("Password must contain a special character");
            PasswordCheck = false;
        }
        else if(!(m3.find()))
        {
            Password.requestFocus();
            Password.setError("Password must contain a number");
            PasswordCheck = false;
        }
        else if(m4.find())
        {
            Password.requestFocus();
            Password.setError("Password must not contain a space or tab");
            PasswordCheck = false;
        }
        else if(PasswordHolder.length()<6)
        {
            Password.requestFocus();
            Password.setError("Password must be at least 6 characters long");
            PasswordCheck = false;
        }
        else
            PasswordCheck=true;
    }
    public void ContactCheckFunction(){
        if(Contact_Holder.length()!=10)
        {
            Contact.requestFocus();
            Contact.setError("Enter a valid contact number");
            ContactCheck = false;
        }
        else
            ContactCheck = true;

    }


    public void register_User(String Name_Holder,String Contact_Holder,String Address_Holder,String EmailHolder, String PasswordHolder)
    {
        final StringRequest stringRequest1=new StringRequest(Request.Method.POST, webhost_path, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try{
                    if(response.equals("Success"))
                    {
                        Toast.makeText(signup.this, "Registered Successfully", Toast.LENGTH_SHORT).show();
                        Intent in=new Intent(signup.this, MainActivity.class);
                        startActivity(in);
                    }else{
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            protected Map<String,String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", EmailHolder);
                params.put("name", Name_Holder);
                params.put("contact", Contact_Holder);
                params.put("address", Address_Holder);
                params.put("password", PasswordHolder);
                return params;
            }
        };

        final RequestQueue requestQueue1 = Volley.newRequestQueue(signup.this);
        requestQueue1.add(stringRequest1);


    }
    public void onclickL(View view){
        startActivity(new Intent(this,MainActivity.class));

    }
    }

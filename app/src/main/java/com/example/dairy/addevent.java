package com.example.dairy;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.time.Year;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class addevent extends AppCompatActivity {

    EditText ft,tt,classn,portion;
    TextView eid;
    Button submit;
    String date_Holder, ft_Holder,tt_Holder,classnHolder, portionHolder, valueHolder;
    String webhost_path= "https://sinchdairy.000webhostapp.com/projectd/insert.php";
    String emailHo;
    private DatePickerDialog datePickerDialog;
    private EditText dateButton,timeButton,timeButton2;
    int hour, minute;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_addevent);
        initDatePicker();
        dateButton = (EditText)findViewById(R.id.date);
        dateButton.setText(getTodayDate());
        timeButton = (EditText)findViewById(R.id.t2);
        timeButton2 = (EditText)findViewById(R.id.editText2);

        eid= (TextView)findViewById(R.id.eid);
        Intent intent=getIntent();
        emailHo =intent.getStringExtra("email_id");
        eid.setText(emailHo);



//Assign Id'S

        //ft = (EditText) findViewById(R.id.t2);
       // tt = (EditText) findViewById(R.id.editText2);
        classn = (EditText) findViewById(R.id.editText3);
        portion = (EditText) findViewById(R.id.editText4);




        submit = (Button) findViewById(R.id.button);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date_Holder = dateButton.getText().toString();
                ft_Holder = timeButton.getText().toString();
                tt_Holder = timeButton2.getText().toString();
                classnHolder = classn.getText().toString();
                portionHolder = portion.getText().toString();
                valueHolder=eid.getText().toString();

                register_User(date_Holder,ft_Holder,tt_Holder,classnHolder,portionHolder,valueHolder);

            }
            private void register_User(String date_Holder, String ft_Holder, String tt_Holder, String classnHolder, String portionHolder,String valueHolder) {
                final StringRequest stringRequest1=new StringRequest(Request.Method.POST, webhost_path, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            if(response.equals("Success"))
                            {
                                Toast.makeText(getApplicationContext(), "Added successfully", Toast.LENGTH_SHORT).show();
                                Intent in=new Intent(addevent.this, home.class);
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
                        params.put("date", date_Holder);
                        params.put("from_time", ft_Holder);
                        params.put("to_time", tt_Holder);
                        params.put("tclass", classnHolder);
                        params.put("portion", portionHolder);
                        params.put("id",valueHolder);
                        return params;
                    }
                };

                final RequestQueue requestQueue1 = Volley.newRequestQueue(addevent.this);
                requestQueue1.add(stringRequest1);
            }
        });
    }

    private String getTodayDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDataString(year,month,day);

    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {

                month = month + 1;
                String date = makeDataString(year, month, day);
                dateButton.setText(date);

            }

        };
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_DARK;
                datePickerDialog = new DatePickerDialog(this,style,dateSetListener,year,month,day);

    }

    private String makeDataString(int year, int month, int day)
    {
        return year + "-" + getMonthFormat(month) +  "-" + day;
    }

    private String getMonthFormat(int month) {
        if(month == 1)
            return "1";
        if(month == 2)
            return "2";
        if(month == 3)
            return "3";
        if(month == 4)
            return "4";
        if(month == 5)
            return "5";
        if(month == 6)
            return "6";
        if(month == 7)
            return "7";
        if(month == 8)
            return "8";
        if(month == 9)
            return "9";
        if(month == 10)
            return "9";
        if(month == 11)
            return "11";
        if(month == 12)
            return "12";

        return "JAN";

    }


    public void opendatepicker(View view)
    {
        datePickerDialog.show();
    }

    public void popTimePicker(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                timeButton.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));

            }
        };

        int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
    public void popTimePicker1(View view)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;

                timeButton2.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

         int style = AlertDialog.THEME_HOLO_DARK;

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, style, onTimeSetListener, hour, minute, true);

        timePickerDialog.setTitle("Select Time");
        timePickerDialog.show();
    }
}









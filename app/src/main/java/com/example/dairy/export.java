package com.example.dairy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class export extends AppCompatActivity {

    Button download ;
    ProgressDialog pdDialog;
    private static final int PERMISSION_REQUEST_CODE = 100;
    //php code URL path
//    String URL = "https://sinchdairy.000webhostapp.com/projectd/export.php";
    StringBuilder data;
    String emailHo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_export);

        Intent intent=getIntent();
        emailHo =intent.getStringExtra("email_id");
     // Toast.makeText(export.this, "ffffff"+emailHo, Toast.LENGTH_SHORT).show();

        download=(Button)findViewById(R.id.download);
        pdDialog = new ProgressDialog(export.this);
        pdDialog.setMessage("Fetching Data...");
        pdDialog.setCancelable(false);

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // to store csv file we need to write storage permission
                // here we are checking is write permission is granted or no
                if(checkPermission())
                {
                    FetchData("https://sinchdairy.000webhostapp.com/projectd/export.php?email="+emailHo);

                }else{
                    // If permission is not granted we will request for the Permission
                    requestPermission();
                }
            }
        });
    }

    // fetch data from server
    private void FetchData(String url)
    {
        pdDialog.show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        //we get the successful in String response
                        Log.e("MY_DATA",response);
                        try{
                            pdDialog.dismiss();

                            if(response.equals("NONE"))
                            {
                                Toast.makeText(export.this,"NO Data Found",Toast.LENGTH_LONG).show();
                                pdDialog.dismiss();
                            }else{

                                pdDialog.dismiss();
                                // In String response we get full data in a form of list
                                splitdata(response);
                            }

                        } catch (Exception e) {
                            e.printStackTrace();

                            pdDialog.dismiss();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pdDialog.dismiss();

            }
        })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String,String> params = new HashMap<>();


                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(export.this);
        requestQueue.add(stringRequest);
    }

    private void splitdata(String response) {

        System.out.println("GET DATA IS "+response);

        // response will have a @ symbol so that we can split individual user data
        String res_data[] = response.split("@");

        //StringBuilder  to store the data
        data = new StringBuilder();

        //row heading to store in CSV file
        data.append("DATE,FROMTIME,TOTIME,CLASS,PORTION");

        for(int i = 0; i<res_data.length;i++){
            //then we split each user data using # symbol as we have in the response string
            final String[] each_user =res_data[i].split("#");


            System.out.println("Splited # date: "+ each_user[0]);
            System.out.println("Splited # from_time? : "+ each_user[1]);
            System.out.println("Splited # to_time? : "+ each_user[2]);
            System.out.println("Splited # tclass ? : "+ each_user[3]);
            System.out.println("Splited # portion ? : "+ each_user[4]);

            // then add each user data in data string builder
            data.append("\n"+ each_user[0]+","+ each_user[1]+","+ each_user[2]+","+ each_user[3]+","+ each_user[4]);



        }
        CreateCSV(data);
    }
    private void CreateCSV(StringBuilder data) {

        Calendar calendar = Calendar.getInstance();
        long time= calendar.getTimeInMillis();

        try {
            //
            FileOutputStream out = openFileOutput("CSV_Data_"+time+".csv", Context.MODE_PRIVATE);

            //store the data in CSV file by passing String Builder data
            out.write(data.toString().getBytes());
            out.close();

            Context context = getApplicationContext();
            final File newFile = new File(Environment.getExternalStorageDirectory(),"SimpleCVS");
            if(!newFile.exists())
            {
                newFile.mkdir();
            }

            File file = new File(context.getFilesDir(),"CSV_Data_"+time+".csv");


            Uri path = FileProvider.getUriForFile(context,"com.example.dataintocsvformat",file);

            //once the file is ready a share option will pop up using which you can share
            // the same CSV from via Gmail or store in Google Drive
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/csv");
            intent.putExtra(Intent.EXTRA_SUBJECT, "Data");
            intent.putExtra(Intent.EXTRA_STREAM, path);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            startActivity(Intent.createChooser(intent,"Excel Data"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    // checking permission To WRITE
    private boolean checkPermission() {
        int result = ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    // request permission for WRITE Access
    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(export.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(export.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(export.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
    }
}


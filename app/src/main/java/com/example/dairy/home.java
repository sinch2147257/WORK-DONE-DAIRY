package com.example.dairy;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import java.util.HashMap;

public class home extends AppCompatActivity {

    GridLayout gridLayout;
    private Object CardView;
    View first,second,third,fourth,fifth,sixth;
    TextView wel;
    Animation topAnimation, middleAnimation;
    String EmailHolder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        setContentView(R.layout.activity_home);

        Intent intent=getIntent();
        EmailHolder =intent.getStringExtra(MainActivity.UserEmail);
//        Toast.makeText(home.this, "ffffff"+EmailHolder, Toast.LENGTH_SHORT).show();

//        Bundle bundle=getIntent().getExtras();
//        if(bundle !=null){
//            emailH=bundle.getString("email_id");
//        }
//        Intent intent = getIntent();
//        emailH = intent.getStringExtra("name");

//        Intent intent = getIntent();
//        Bundle bd = intent.getExtras();
//        if(bd != null)
//        {
//             email = (String) bd.get("email_id");
//
//        }
//        email = getIntent().getExtras().getString("email_id");

        topAnimation = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        middleAnimation = AnimationUtils.loadAnimation(this,R.anim.middle_animation);

        first=findViewById(R.id.first);
        second=findViewById(R.id.second);
        third=findViewById(R.id.third);
        fourth=findViewById(R.id.fourth);
        fifth=findViewById(R.id.fiFth);
        sixth=findViewById(R.id.sixth);

        wel= findViewById(R.id.wel);

        first.setAnimation(topAnimation);
        second.setAnimation(topAnimation);
        third.setAnimation(topAnimation);
        fourth.setAnimation(topAnimation);
        fifth.setAnimation(topAnimation);
        sixth.setAnimation(topAnimation);

        wel.setAnimation(middleAnimation);



        gridLayout=(GridLayout)findViewById(R.id.mainGrid);

        CardView cal =findViewById(R.id.cal);
        CardView edit=findViewById(R.id.edit);
        CardView download=findViewById(R.id.download);
        CardView event=findViewById(R.id.event);
        CardView eventview=findViewById(R.id.event_view);
        CardView todo = findViewById(R.id.todo);





        cal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),addevent.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),editp.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });

        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),export.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });
        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),events_add.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });
        eventview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),event_view.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });

         todo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),todoList.class);
                intent.putExtra("email_id",EmailHolder);
                startActivity(intent);
            }
        });
    }
    public void onclickLog(View view){
        startActivity(new Intent(this,MainActivity.class));

    }
    }



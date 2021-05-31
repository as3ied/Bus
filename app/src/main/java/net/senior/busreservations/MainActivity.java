package net.senior.busreservations;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
Button student;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        student=findViewById(R.id.button3);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, StudentHomeActivity.class));

            }
        });
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Bus Reservation"+ "</font>"));

    }

    public void manager(View view) {
        startActivity(new Intent(MainActivity.this,ManagerActivity.class));
    }

    public void driver(View view) {
        startActivity(new Intent(MainActivity.this,DriverActivity.class));

    }


}
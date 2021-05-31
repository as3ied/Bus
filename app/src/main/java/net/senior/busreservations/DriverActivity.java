package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.DriverHomeScreen;
import net.senior.busreservations.R;
import net.senior.busreservations.model.Driver;

public class DriverActivity extends AppCompatActivity {
    EditText name, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);
        name = findViewById(R.id.name);
        password = findViewById(R.id.password);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Driver"+ "</font>"));

    }

    public void signIn(View view) {
        FirebaseDatabase.getInstance().getReference("driver").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(Driver.class).getName().equals(name.getText().toString()) && snapshot.getValue(Driver.class).getPassword().equals(password.getText().toString())) {

                    Intent i = new Intent(DriverActivity.this, DriverHomeScreen.class);
                    i.putExtra("driverCode", snapshot.getValue(Driver.class).getCode());

                    startActivity(i);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
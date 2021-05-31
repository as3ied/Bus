package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.adapter.DriverHomeAdapter;
import net.senior.busreservations.model.StudentSubscribes;

import java.util.ArrayList;
import java.util.List;

public class MyReservation extends AppCompatActivity {
    RecyclerView rec;
    List<StudentSubscribes> reservations;
    DriverHomeAdapter driverHomeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_reservation);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"My Reservations"+ "</font>"));

        rec = findViewById(R.id.rec);
        reservations = new ArrayList();
        rec.setLayoutManager(new LinearLayoutManager(this));
        getMyData();

    }

    public void getMyData() {
        FirebaseDatabase.getInstance().getReference("reservation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(StudentSubscribes.class).getStudentCode().equals(getIntent().getStringExtra("stdCode"))) {
                    reservations.add(snapshot.getValue(StudentSubscribes.class));

                    driverHomeAdapter = new DriverHomeAdapter(reservations);
                    rec.setAdapter(driverHomeAdapter);
                    driverHomeAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                driverHomeAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
driverHomeAdapter.notifyDataSetChanged();
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
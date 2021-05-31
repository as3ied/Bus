package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.senior.busreservations.adapter.DriverHomeAdapter;
import net.senior.busreservations.model.Bus;
import net.senior.busreservations.model.DriverBus;
import net.senior.busreservations.model.StudentSubscribes;

import java.util.ArrayList;
import java.util.List;

public class DriverHomeScreen extends AppCompatActivity {
    RecyclerView rec;
    DriverHomeAdapter driverHomeAdapter;
    List<StudentSubscribes> studentSubscribesList;
    Bus bus = new Bus();
TextView myCar;
    DriverBus driverBus=new DriverBus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_home_screen);

        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Resevations"+ "</font>"));
   myCar=findViewById(R.id.myCar);
        FirebaseDatabase.getInstance().getReference("driverbus").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot d:snapshot.getChildren()){
                if(d.getValue(DriverBus.class).getDriverId().equals(getIntent().getStringExtra("driverCode"))){
                  driverBus=d.getValue(DriverBus.class);
                    myCar.append(d.getValue(DriverBus.class).getBusId());
                }
            }}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        studentSubscribesList = new ArrayList();
        rec = findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        FirebaseDatabase.getInstance().getReference("reservation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getValue(StudentSubscribes.class).getBusCode().equals(driverBus.getBusId() + "")) {
                    studentSubscribesList.add(snapshot.getValue(StudentSubscribes.class));
                }
                driverHomeAdapter = new DriverHomeAdapter(studentSubscribesList);
                rec.setAdapter(driverHomeAdapter);
                driverHomeAdapter.notifyDataSetChanged();
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
package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.senior.busreservations.adapter.StudentReservAdapter;
import net.senior.busreservations.model.Bus;
import net.senior.busreservations.model.DriverBus;
import net.senior.busreservations.model.Student;
import net.senior.busreservations.model.StudentSubscribes;

import java.util.ArrayList;
import java.util.List;

public class StudentActivity extends AppCompatActivity {
    RecyclerView rec;
    StudentReservAdapter studentReservAdapter;
    List<Bus> buses;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    StudentSubscribes subscribes;
    String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" + "Subscribe" + "</font>"));

        subscribes = new StudentSubscribes();
        rec = findViewById(R.id.rec);
        rec.setLayoutManager(new LinearLayoutManager(this));
        buses = new ArrayList();


        database.getReference("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if (snapshot.getKey().equals("code")) {
                    subscribes.setStudentCode(snapshot.getValue(String.class));
                }
                if (snapshot.getKey().equals("name")) {
                    subscribes.setStudentName(snapshot.getValue(String.class));
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


        database.getReference("bus").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                buses.add(snapshot.getValue(Bus.class));
                studentReservAdapter = new StudentReservAdapter(buses);
                studentReservAdapter.setOnSubscribeListener(new StudentReservAdapter.OnSubscribeListener() {
                    @Override
                    public void onSubscribed(int pos) {
                        subscribes.setPeriod(buses.get(pos).getPeriod());
                        if (buses.get(pos).getPassengers() > 0) {
                            subscribes.setBusCode(buses.get(pos).getNum() + "");
                            database.getReference("driverbus").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    for (DataSnapshot d : snapshot.getChildren()) {
                                        if (d.getValue(DriverBus.class) != null ) {
                                            if (d.getValue(DriverBus.class).getBusId().equals(subscribes.getBusCode())) {
                                                subscribes.setDriverName(d.getValue(DriverBus.class).getDriverName());
                                                database.getReference("reservation").child(database.getReference("reservation").push().getKey()).setValue(subscribes).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {

                                                        database.getReference("bus").addChildEventListener(new ChildEventListener() {
                                                            @Override
                                                            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                                                                if (snapshot.getValue(Bus.class).getNum() == buses.get(pos).getNum()) {
                                                                    key = snapshot.getKey();
                                                                    buses.get(pos).setPassengers(buses.get(pos).getPassengers() - 1);
                                                                    database.getReference("bus").child(key).setValue(buses.get(pos));
                                                                    studentReservAdapter.notifyDataSetChanged();
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
                                                        Toast.makeText(StudentActivity.this, "subscribed successfuly", Toast.LENGTH_SHORT).show();
                                                    }
                                                });

                                            }
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        }
                    }
                });
                rec.setAdapter(studentReservAdapter);


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

    @Override
    public void onBackPressed() {

finish();    }

    public void myReservations(View view) {
        Intent i=new Intent(this,MyReservation.class);
        i.putExtra("stdCode",subscribes.getStudentCode());
        startActivity(i);
    }
}
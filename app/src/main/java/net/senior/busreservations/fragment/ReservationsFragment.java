package net.senior.busreservations.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.R;
import net.senior.busreservations.adapter.DriverHomeAdapter;
import net.senior.busreservations.model.StudentSubscribes;

import java.util.ArrayList;
import java.util.List;

public class ReservationsFragment extends Fragment {
    RecyclerView rec;
    List<StudentSubscribes> reservations;
    DriverHomeAdapter driverHomeAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservations, container, false);
        rec = v.findViewById(R.id.rec);
        reservations = new ArrayList();
        rec.setLayoutManager(new LinearLayoutManager(getContext()));
        return v;
    }
    public void getData(){
        FirebaseDatabase.getInstance().getReference("reservation").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                reservations.add(snapshot.getValue(StudentSubscribes.class));

                driverHomeAdapter = new DriverHomeAdapter(reservations);
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
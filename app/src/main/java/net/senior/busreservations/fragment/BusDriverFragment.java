
package net.senior.busreservations.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import net.senior.busreservations.R;
import net.senior.busreservations.model.Driver;
import net.senior.busreservations.model.DriverBus;

import java.util.ArrayList;
import java.util.List;

public class BusDriverFragment extends Fragment {
    Spinner buses, drivers;
    Button add, delete;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    List<String> busses;
    List<Driver> allDrivers;
    List<String> allDriversName;
    List<String> allDriversCode;
    List<DriverBus> allData;
    DriverBus driverBus;
    String pushKey;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bus_driver, container, false);

        buses = v.findViewById(R.id.buses);
        drivers = v.findViewById(R.id.drivers);
        add = v.findViewById(R.id.add);
        delete = v.findViewById(R.id.delete);
        driverBus = new DriverBus();
        busses = new ArrayList();
        allDrivers = new ArrayList();
        allDriversName = new ArrayList();
        allDriversCode = new ArrayList();
        allData = new ArrayList();
        getAllData();
        database.getReference().child("driver").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                allDrivers.add(snapshot.getValue(Driver.class));


                allDriversName.add(snapshot.getValue(Driver.class).getName());
                allDriversCode.add(snapshot.getValue(Driver.class).getCode());


                ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter(
                        getContext(), R.layout.spinner, allDriversName);
                spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                drivers.setAdapter(spinnerArrayAdapter);


                drivers.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        driverBus.setDriverName(allDrivers.get(i).getName());
                        driverBus.setDriverId(allDrivers.get(i).getCode());
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });


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
        database.getReference().child("bus").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                for (DataSnapshot d : snapshot.getChildren()) {
                    if (d.getKey().equals("num")) {
                        busses.add(d.getValue(Integer.class).toString());
                        if (getActivity() != null) {
                            ArrayAdapter<String> spinnerArrayAdapter2 = new ArrayAdapter(
                                    getContext(), R.layout.spinner, busses);
                            spinnerArrayAdapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            buses.setAdapter(spinnerArrayAdapter2);

                        }

                        buses.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                driverBus.setBusId(adapterView.getItemAtPosition(i).toString());
                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> adapterView) {

                            }
                        });
                    }
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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkExistance();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                database.getReference("driverbus").child(driverBus.getPushkey()).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Bus Driver disconnected Successfully", Toast.LENGTH_SHORT).show();
                        for (int i = 0; i < allData.size(); i++) {

                            if (allData.get(i).getBusId().equals(driverBus.getBusId())&& allData.get(i).getDriverId().equals(driverBus.getDriverId())) {
                                allData.remove(i);
                                i--;//Important!
                            }
                        }


                    }
                });
            }});
        return v;
    }

    public void checkExistance() {
        for (DriverBus d : allData) {
            if (d != null && driverBus != null) {
                if (d.getDriverId().equals(driverBus.getDriverId()) || d.getBusId().equals(driverBus.getBusId())) {

                    Toast.makeText(getContext(), "Either driver or bus is already taken", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        }
        pushKey = database.getReference("driverbus").push().getKey();

        driverBus.setPushkey(pushKey);
        database.getReference("driverbus").child(pushKey).setValue(driverBus).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                Toast.makeText(getContext(), "Bus Driver connected Successfully", Toast.LENGTH_SHORT).show();

            }
        });


    }

    public void getAllData() {
allData.clear();
        database.getReference("driverbus").addValueEventListener(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot d : snapshot.getChildren())
                    allData.add(d.getValue(DriverBus.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });
    }
}
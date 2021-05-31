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
import android.widget.EditText;
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
import net.senior.busreservations.model.Bus;

import java.util.ArrayList;
import java.util.List;

public class BusFragment extends Fragment {

    EditText price, pasns, type, time, num;
    Spinner lines;
    List<String> linesList=new ArrayList();
    List<String> stations=new ArrayList();
    Button add;
    Bus bus;
    FirebaseDatabase database = FirebaseDatabase.getInstance();



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_bus, container, false);
        time = v.findViewById(R.id.startTime);
        num = v.findViewById(R.id.busNum);
        type = v.findViewById(R.id.busType);
        pasns = v.findViewById(R.id.passengersNo);
        price = v.findViewById(R.id.price);
        lines = v.findViewById(R.id.lines);
        add = v.findViewById(R.id.add);
        bus = new Bus();
database.getReference("lines").addChildEventListener(new ChildEventListener() {


    @Override
    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName)
    {
        linesList.add(snapshot.getKey());
        if(getActivity()!=null){
        ArrayAdapter<CharSequence> adapter = new ArrayAdapter(getContext(), R.layout.spinner, linesList);
        adapter.setDropDownViewResource(R.layout.spinner);
        lines.setAdapter(adapter);}
        lines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bus.setLine(adapterView.getItemAtPosition(i).toString());
                database.getReference("lines").addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                        if(snapshot.getKey().equals(adapterView.getItemAtPosition(i).toString())){
stations=(List<String>)snapshot.getValue();
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


        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bus.setNum(Integer.parseInt(num.getText().toString()));
                bus.setPassengers(Integer.parseInt(pasns.getText().toString()));
                bus.setPrice(Integer.parseInt(price.getText().toString()));
                bus.setTime(time.getText().toString());
                bus.setType(type.getText().toString());
                bus.setStations(stations);
                database.getReference("bus").child( database.getReference("bus").push().getKey()).setValue(bus).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Bus added Successfully", Toast.LENGTH_SHORT).show();
                   num.setText("");
                   pasns.setText("");
                   price.setText("");
                   time.setText("");
                   type.setText("");


                    }
                });

            }
        });

        return v;
    }
}
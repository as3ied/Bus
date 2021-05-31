package net.senior.busreservations.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.R;
import net.senior.busreservations.adapter.MyAdapter;
import net.senior.busreservations.model.Stations;

import java.util.ArrayList;
import java.util.List;

public class LineFragment extends Fragment {
    Spinner stations;
    EditText name;
    Button add;
    List<Stations> listStations = new ArrayList();
    List<String> selectedStations = new ArrayList();
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_line, container, false);
        stations = v.findViewById(R.id.stations);
        name = v.findViewById(R.id.lineName);
        add = v.findViewById(R.id.add);
        listStations.add(new Stations("Select Stations", false));
        listStations.add(new Stations("Ataba", false));
        listStations.add(new Stations("Giza", false));
        listStations.add(new Stations("Ramses", false));
        listStations.add(new Stations("Tahrer", false));
        listStations.add(new Stations("Doky", false));
        listStations.add(new Stations("Maadi", false));
        listStations.add(new Stations("Helwan", false));
        listStations.add(new Stations("Roxy", false));
        listStations.add(new Stations("Mosky", false));
        listStations.add(new Stations("Shobra", false));

        MyAdapter myAdapter = new MyAdapter(getContext(), 0,
                listStations);
        stations.setAdapter(myAdapter);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectedStations.clear();
                for (Stations s : listStations) {
                    if (s.isSelected()) {
                        selectedStations.add(s.getTitle());
                    }
                    database.getReference("lines").child(name.getText().toString()).setValue(selectedStations).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            name.setText("");
                            for (Stations s : listStations) {

                                s.setSelected(false);
                            }

                        }
                    });
                }
                Toast.makeText(getContext(), "Line Added Successfully", Toast.LENGTH_SHORT).show();

            }
        });
        return v;
    }
}
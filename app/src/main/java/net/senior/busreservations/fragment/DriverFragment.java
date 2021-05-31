package net.senior.busreservations.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.R;
import net.senior.busreservations.model.Driver;

public class DriverFragment extends Fragment {


    EditText name, password, phone, code;
    Button add;
    Driver driver;
    FirebaseDatabase database = FirebaseDatabase.getInstance();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=         inflater.inflate(R.layout.fragment_driver, container, false);
        name=v.findViewById(R.id.name);
        password=v.findViewById(R.id.password);
        phone=v.findViewById(R.id.phone);
        code=v.findViewById(R.id.id);
        add=v.findViewById(R.id.add);
        driver=new Driver();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                driver.setCode(code.getText().toString());
                driver.setName(name.getText().toString());
                driver.setPassword(password.getText().toString());
                driver.setPhone(phone.getText().toString());
                database.getReference("driver").child( database.getReference("driver").push().getKey()).setValue(driver).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Driver added Successfully", Toast.LENGTH_SHORT).show();
                        name.setText("");
                        code.setText("");
                        password.setText("");
                        phone.setText("");
                    }
                });

            }
        });



        return v;
    }
}
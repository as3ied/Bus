package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import net.senior.busreservations.model.Student;

public class SignUp extends AppCompatActivity {
EditText name,code,phone,password,address,university,email;
Student student;
FirebaseDatabase database=FirebaseDatabase.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Student"+ "</font>"));

        name=findViewById(R.id.name);
        code=findViewById(R.id.code);
        phone=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        address=findViewById(R.id.address);
        university=findViewById(R.id.university);
        student=new Student();
    }

    public void signUp(View view) {
        student.setName(name.getText().toString());
        student.setAddress(address.getText().toString());
        student.setCode(code.getText().toString());
        student.setPassword(password.getText().toString());
        student.setPhone(phone.getText().toString());
        student.setUniversity(university.getText().toString());
        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            database.getReference("student").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).setValue(student).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Toast.makeText(SignUp.this, "Student Added Successfuly", Toast.LENGTH_SHORT).show();
                                    university.setText("");
                                    phone.setText("");
                                    email.setText("");
                                    password.setText("");
                                    address.setText("");
                                    name.setText("");
                                    code.setText("");
                                    finish();
                                }
                            });

                        }
                    }
                });


    }
}
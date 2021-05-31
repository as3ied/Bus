package net.senior.busreservations.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.senior.busreservations.R;
import net.senior.busreservations.model.Bus;
import net.senior.busreservations.model.StudentSubscribes;

import java.util.List;

public class DriverHomeAdapter extends RecyclerView.Adapter<DriverHomeAdapter.ViewHolder> {
    List<StudentSubscribes> subscribes;


    public DriverHomeAdapter(List<StudentSubscribes> subscribes) {
        this.subscribes = subscribes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_home_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.studentNum.setText("Student Code : " + subscribes.get(position).getStudentCode());

        holder.busNum.setText("Bus Number : " + subscribes.get(position).getBusCode() + "");
        holder.driverName.setText("Driver Name : " + subscribes.get(position).getDriverName() );
        holder.studentName.setText("Student Name : " + subscribes.get(position).getStudentName() );


    }


    @Override
    public int getItemCount() {
        return subscribes.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView busNum, studentNum,studentName,driverName;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            busNum = itemView.findViewById(R.id.busNum);
            studentNum = itemView.findViewById(R.id.studentCode);
            studentName = itemView.findViewById(R.id.studentName);
            driverName = itemView.findViewById(R.id.driverName);


        }
    }
}


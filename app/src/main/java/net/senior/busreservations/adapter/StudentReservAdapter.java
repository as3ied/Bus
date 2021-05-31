package net.senior.busreservations.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import net.senior.busreservations.R;
import net.senior.busreservations.StudentActivity;
import net.senior.busreservations.model.Bus;

import java.util.List;

public class StudentReservAdapter extends RecyclerView.Adapter<StudentReservAdapter.ViewHolder> {
    List<Bus> buses;
    OnSubscribeListener onSubscribeListener;

    public void setOnSubscribeListener(OnSubscribeListener onSubscribeListener) {
        this.onSubscribeListener = onSubscribeListener;
    }

    public StudentReservAdapter(List<Bus> buses) {
        this.buses = buses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.student_reserv_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.busTime.setText("Time : "+buses.get(position).getTime());
        holder.passNum.setText("Avilable Seats : "+buses.get(position).getPassengers()+"");
        holder.price.setText("Ticket Price : "+buses.get(position).getPrice()+"");
        holder.line.setText("Stations "+buses.get(position).getStations().toString());
        holder.busNum.setText("Bus Number : "+buses.get(position).getNum()+"");

        if (buses.get(position).getPassengers() == 0) {
            holder.subscribe.setEnabled(false);
            holder.subscribe.setTextColor(Color.GRAY);
        }
        holder.subscribe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSubscribeListener.onSubscribed(position);

            }
        });
    }


    @Override
    public int getItemCount() {
        return buses.size();
    }


    public interface OnSubscribeListener {
        public void onSubscribed(int pos);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView busNum, passNum, busTime, price, line;
        Button subscribe;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            busNum = itemView.findViewById(R.id.busNum);
            passNum = itemView.findViewById(R.id.passNum);
            busTime = itemView.findViewById(R.id.bustime);
            price = itemView.findViewById(R.id.price);
            line = itemView.findViewById(R.id.line);
            subscribe = itemView.findViewById(R.id.subscribe);


        }
    }
}


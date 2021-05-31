package net.senior.busreservations;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import net.senior.busreservations.fragment.BusDriverFragment;
import net.senior.busreservations.fragment.BusFragment;
import net.senior.busreservations.fragment.DriverFragment;
import net.senior.busreservations.fragment.LineFragment;
import net.senior.busreservations.fragment.ReservationsFragment;

public class ManagerActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Manager"+ "</font>"));

        getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReservationsFragment()).commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manager_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.addBus:
               getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Add Bus"+ "</font>"));

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BusFragment()).commit();
                break;
            case R.id.addDriver:
                getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Add Driver"+ "</font>"));

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new DriverFragment()).commit();
                break;
            case R.id.addBusToDriver:
                getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Connect Bus With Driver"+ "</font>"));

                getSupportFragmentManager().beginTransaction().replace(R.id.container, new BusDriverFragment()).commit();
                break;
                case R.id.addLine:
                    getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Add Line"+ "</font>"));

                    getSupportFragmentManager().beginTransaction().replace(R.id.container, new LineFragment()).commit();
                break;
 case R.id.showReservation:
     getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#D9D583\">" +"Show Reservations"+ "</font>"));

     getSupportFragmentManager().beginTransaction().replace(R.id.container, new ReservationsFragment()).commit();
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
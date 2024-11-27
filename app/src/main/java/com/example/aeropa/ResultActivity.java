package com.example.aeropa;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aeropa.Adapter.FlightAdapter;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.databinding.ActivityResultBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class ResultActivity extends BaseActivity {
    private ActivityResultBinding binding;
    private String from;
    private String to;
    private String date;
    private int passengerCount = 1;
    private int flightsCount;
    private int baggageCount = 0;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
    private SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
    private Calendar calendar = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        initPickDate();
        searchFlights();
        initPassenger();
        initExtraBaggage();

    }

    private void searchFlights() {
        binding.searchFlightsResultBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initList();
            }
        });
    }

    private void initPassenger(){
            binding.plusPassengerResultIV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(passengerCount < 5){
                        passengerCount++;
                        binding.passengerCountResultTV.setText(passengerCount +"");
//                        binding.warningHomeTV.setVisibility(View.GONE);
                    }else if(passengerCount == 5){
//                        binding.warningHomeTV.setVisibility(View.VISIBLE);
                    }
                }
            });

            binding.minusPassengerResultTV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(passengerCount>1){
                        passengerCount--;
                        binding.passengerCountResultTV.setText(passengerCount + "");
//                        binding.warningHomeTV.setVisibility(View.GONE);
                    }
                }
            });
        }
        private void initExtraBaggage(){
//        if(baggageCount==0) {
//            binding.baggageCountHomeTV.setText("0");
//        }
        binding.plusBaggageResultIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baggageCount >= 0 ){
                    baggageCount++;
                    binding.baggageCountResultTV.setText(baggageCount + "(x 25Kg)");
                }
            }
        });

        binding.minusBaggageResultTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(baggageCount > 0){
                    baggageCount--;
                    if(baggageCount == 0){
                        binding.baggageCountResultTV.setText("No Baggage");
                    }else {
                        binding.baggageCountResultTV.setText(baggageCount + "(x 25Kg)");
                    }
                }
            }
        });
    }

    private void initPickDate() {
        Calendar calendarCurrent = Calendar.getInstance();

        String todayDate = dateFormat.format(calendar.getTime());
        String todayDay = dayFormat.format(calendar.getTime());
        binding.dateResultTV.setText(todayDate);
        binding.dayResultTV.setText(todayDay);

        binding.dateResultTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePickerDialog(binding.dayResultTV, binding.dateResultTV);
            }
        });
    }

    public void showDatePickerDialog(TextView dayTextView, TextView dateTextView){
//        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay)->{
            calendar.set(selectedYear,selectedMonth,selectedDay);
            String formattedDate = dateFormat.format(calendar.getTime());
            String formattedDay = dayFormat.format(calendar.getTime());

            dateTextView.setText((formattedDate));
            dayTextView.setText(formattedDay);
        }, year, month, day);
        datePickerDialog.show();
    }

    private void getIntentExtra() {
        from = getIntent().getStringExtra("from");
        to = getIntent().getStringExtra("to");
        date = getIntent().getStringExtra("date");
        passengerCount = getIntent().getIntExtra("passengerCount", 1);
    }

    private void initList() {
        DatabaseReference myRef = db.getReference("Flights");
        ArrayList<Flight> list = new ArrayList<>();
//        list.add(new Flight("AB123", "2h 40m", "5 oct,2024", "Marseille", "Paris", 150, 99.99, "1", "D1,B2,B3,F3,E2,D1,D5,A6", "23:05", "10:00", "AE77"));
//        Log.d("FlightLi", "Size: " + list.size());
        Query query = myRef.orderByChild("from").equalTo(from);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                flightsCount = 0;
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Flight flight = issue.getValue(Flight.class);
                        if(flight.getTo().equals(to)){
                            list.add(flight);
                            flightsCount++;
                            Log.d("Test","64");
                        }

//                        if(flight.getTo().equals(to) && flight.getDate().equals(date)){
//                            list.add(flight);
//                        }

                        if(!list.isEmpty()){
                            Log.d("Test","list not empty");

                            FlightAdapter adapter = new FlightAdapter(list, passengerCount);
                            binding.resultResultRV.setLayoutManager(new LinearLayoutManager(ResultActivity.this
                                    , LinearLayoutManager.VERTICAL, false));
//                            binding.resultHomeRV.setAdapter(new FlightAdapter(list));
                            binding.resultResultRV.setAdapter(adapter);
                            Log.d("Test", "80");
                            Log.d("Test", "flightsCount: " + flightsCount);
                        }
                        else {
                            Log.d("Test","list empty");
                        }
                        binding.flightsCountResultTV.setText(flightsCount + " Flights Available");

                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
}
//package com.example.aeropa;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
//import androidx.annotation.NonNull;
//
//import com.example.aeropa.Model.Location;
//import com.example.aeropa.databinding.ActivityMainBinding;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//public class mainactivitytemp {
//    package com.example.aeropa;
//
//import android.app.DatePickerDialog;
//import android.content.Intent;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.TextView;
//
////import com.example.aeropa.Model.City;
//import androidx.annotation.NonNull;
//import androidx.recyclerview.widget.LinearLayoutManager;
//
//import com.example.aeropa.Adapter.FlightAdapter;
//import com.example.aeropa.Model.Flight;
//import com.example.aeropa.Model.Location;
//import com.example.aeropa.databinding.ActivityMainBinding;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.Locale;
//
//    public class MainActivity extends BaseActivity {
//        private ActivityMainBinding binding;
//
//        private int passengerCount = 1;
//        private int baggageCount = 0;
//        private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
//        private Calendar calendar = Calendar.getInstance();
//
//        private String from;
//        private String to;
//        private String date;
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//            binding = ActivityMainBinding.inflate(getLayoutInflater());
//            setContentView(binding.getRoot());
//            binding.warningHomeTV.setVisibility(View.GONE);
//
//            if(user == null){
//                Intent intent = new Intent(com.example.aeropa.MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//                finish();
//            }else{
//                initLocation();
//                initPassenger();
//                initExtraBaggage();
//                initPickDate();
//                testRegister();
//                testLogin();
//                testLogout();
////        setVariable();
////        getValues();
//                searchFlight();
////        initList();
////        loadCities();
////        loadDate();
////        loadPassenger();
//            }
//        }
//
//        private void testLogout() {
//            binding.toLogoutMainBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    mAuth.signOut();
//                }
//            });
//        }
//
//        private void testLogin() {
//            binding.toLoginMainBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toLogin();
//                }
//            });
//        }
//
//        private void testRegister() {
//            binding.toRegisterMainBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    toRegister();
//                }
//            });
//        }
//
//        private void toLogin() {
//            Intent intent = new Intent(com.example.aeropa.MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
//
//        private void toRegister() {
//            Intent intent = new Intent(com.example.aeropa.MainActivity.this, RegisterActivity.class);
//            startActivity(intent);
//        }
//
//        private void getValues() {
////        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
////        intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem()).getName());
//            from = binding.departureHomeSpinner.getSelectedItem().toString();
//            to = binding.arrivalHomeSpinner.getSelectedItem().toString();
//            date = binding.inputDateHomeTV.getText().toString();
//        }
//
//        private void sendData(){
//            Intent intent = new Intent(com.example.aeropa.MainActivity.this, ResultActivity.class);
//            intent.putExtra("from", from);
//            intent.putExtra("to", to);
//            intent.putExtra("date", date);
//            intent.putExtra("passengerCount", passengerCount);
//            startActivity(intent);
//        }
//
////    private void setVariable() {
////        binding.searchFlightsHomeBtn.setOnClickListener(new View.OnClickListener() {
////            @Override
////            public void onClick(View view) {
////                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
//////                intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem().getName());
////                intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem()).getName());
////                intent.putExtra("to", ((Location)binding.arrivalHomeSpinner.getSelectedItem()).getName());
////                intent.putExtra("date", binding.inputDateHomeTV.getText().toString());
////                intent.putExtra("numPassenger", passengerCount);
////                startActivity(intent);
////            }
////        });
////    }
//
//
//        private void initLocation(){
//            binding.departureMainPB.setVisibility(View.VISIBLE);
//            binding.arrivalMainPB.setVisibility(View.VISIBLE);
//            DatabaseReference myRef = db.getReference("Locations");
//            ArrayList<Location> list = new ArrayList<>();
//            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    if(snapshot.exists()){
//                        for(DataSnapshot issue:snapshot.getChildren()){
//                            list.add(issue.getValue(Location.class));
//
//                        }
//                        ArrayAdapter<Location> adapter = new ArrayAdapter<>(com.example.aeropa.MainActivity.this, R.layout.sp_item, list);
//                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                        binding.departureHomeSpinner.setAdapter(adapter);
//                        binding.arrivalHomeSpinner.setAdapter(adapter);
//                        binding.departureHomeSpinner.setSelection(1);
//                        binding.departureHomeProgBar.setVisibility(View.GONE);
//                        binding.arrivalHomeProgBar.setVisibility(View.GONE);
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
//        }
//
//        private void initPassenger(){
//            binding.plusPassengerHomeTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(passengerCount < 5){
//                        passengerCount++;
//                        binding.passengerCountHomeTV.setText(passengerCount + " Passenger");
//                        binding.warningHomeTV.setVisibility(View.GONE);
//                    }else if(passengerCount == 5){
//                        binding.warningHomeTV.setVisibility(View.VISIBLE);
//                    }
//                }
//            });
//
//            binding.minusPassengerHomeTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(passengerCount>1){
//                        passengerCount--;
//                        binding.passengerCountHomeTV.setText(passengerCount + " Passenger");
//                        binding.warningHomeTV.setVisibility(View.GONE);
//                    }
//                }
//            });
//        }
//
//        private void initExtraBaggage(){
////        if(baggageCount==0) {
////            binding.baggageCountHomeTV.setText("0");
////        }
//            binding.plusBaggageHomeTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(baggageCount >= 0 ){
//                        baggageCount++;
//                        binding.baggageCountHomeTV.setText(baggageCount + "(x 25Kg)");
//                    }
//                }
//            });
//
//            binding.minusBaggageHomeTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if(baggageCount > 0){
//                        baggageCount--;
//                        if(baggageCount == 0){
//                            binding.baggageCountHomeTV.setText("No Baggage");
//                        }else {
//                            binding.baggageCountHomeTV.setText(baggageCount + "(x 25Kg)");
//                        }
//                    }
//                }
//            });
//        }
//
//        public void initPickDate(){
//            Calendar calendarCurrent = Calendar.getInstance();
//            String todayDate = dateFormat.format(calendar.getTime());
//            binding.inputDateHomeTV.setText(todayDate);
//
//
//            binding.inputDateHomeTV.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    showDatePickerDialog(binding.inputDateHomeTV);
//                }
//            });
//        }
//
//        public void showDatePickerDialog(TextView textView){
////        final Calendar calendar = Calendar.getInstance();
//            int year = calendar.get(Calendar.YEAR);
//            int month = calendar.get(Calendar.MONTH);
//            int day = calendar.get(Calendar.DAY_OF_MONTH);
//
//
//            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay)->{
//                calendar.set(selectedYear,selectedMonth,selectedDay);
//                String formattedDate = dateFormat.format(calendar.getTime());
//                textView.setText((formattedDate));
//            }, year, month, day);
//            datePickerDialog.show();
//        }
//
//        private void searchFlight() {
//            binding.searchFlightsHomeBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    getValues();
////                initList();
//                    sendData();
//                }
//            });
//        }
//
//
////    private void initList() {
////        DatabaseReference myRef = db.getReference("Flights");
////        ArrayList<Flight> list = new ArrayList<>();
//////        list.add(new Flight("AB123", "2h 40m", "5 oct,2024", "Marseille", "Paris", 150, 99.99, "1", "D1,B2,B3,F3,E2,D1,D5,A6", "23:05", "10:00", "AE77"));
//////        Log.d("FlightLi", "Size: " + list.size());
////        Query query = myRef.orderByChild("from").equalTo(from);
////        query.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot snapshot) {
////                if(snapshot.exists()){
////                    for(DataSnapshot issue : snapshot.getChildren()){
////                        Flight flight = issue.getValue(Flight.class);
////                        if(flight.getTo().equals(to)){
////                            list.add(flight);
////                        }
////
//////                        if(flight.getTo().equals(to) && flight.getDate().equals(date)){
//////                            list.add(flight);
//////                        }
////
////                        if(!list.isEmpty()){
////                            Log.d("Test","list not empty");
////
////                            FlightAdapter adapter = new FlightAdapter(list, passengerCount);
////                            binding.resultHomeRV.setLayoutManager(new LinearLayoutManager(MainActivity.this
////                                    , LinearLayoutManager.VERTICAL, false));
//////                            binding.resultHomeRV.setAdapter(new FlightAdapter(list));
////                            binding.resultHomeRV.setAdapter(adapter);
////                        }
////                        else {
////                            Log.d("Test","list empty");
////                        }
////                    }
////                }
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError error) {
////
////            }
////        });
////
////    }
//
//
//
//
//
//    }
//}

package com.example.aeropa;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//import com.example.aeropa.Model.City;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aeropa.Adapter.FlightAdapter;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.Model.Location;
import com.example.aeropa.databinding.ActivityMainBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;

    private int passengerCount = 1;
    private int baggageCount = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
    private Calendar calendar = Calendar.getInstance();

    private String from = "";
    private String to = "";
    private String date;

    private Fragment selectedFragment = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.setStatusBarColor(ContextCompat.getColor(this, R.color.darkblue));
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main_activity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

//        setContentView(binding.getRoot());
//        binding.warningHomeTV.setVisibility(View.GONE);

//        binding.chipNavMainCNB.setItemSelected(R.id.nav_home, true);

//        if(user == null){
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//            finish();
//        }else{

        String fragment = getIntent().getStringExtra("fragment");
        if ("TicketsFragment".equals(fragment)) {
            startFragment(new TicketsFragment());
        } else {
            startFragment(new HomeFragment());
        }

        binding.chipNavMainCNB.setItemSelected(R.id.nav_home, true);
            navbar();
//            initLocation();
//            initPassenger();
//            initExtraBaggage();
//            initPickDate();
//            testRegister();
//            testLogin();
//            testLogout();
//        setVariable();
//        getValues();
//            switchDestination();
//            searchFlight();
//        initList();
//        loadCities();
//        loadDate();
//        loadPassenger();
//        }
    }

    private void startFragment(Fragment selectedFragment) {
        if (selectedFragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, selectedFragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }
    }

    private void navbar() {
        binding.chipNavMainCNB.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                if (i == R.id.nav_home) {
                    selectedFragment = new HomeFragment();
                } else if (i == R.id.nav_tickets) {
                    selectedFragment = new TicketsFragment();
                } else if (i == R.id.nav_profile) {
                    selectedFragment = new ProfileFragment();
                }

                startFragment(selectedFragment);

            }
        });
    }

//    private void switchDestination() {
//        binding.changeMainIV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                int selectedTemp = binding.departureMainSpinner.getSelectedItemPosition();
//                binding.departureMainSpinner.setSelection(binding.arrivalMainSpinner.getSelectedItemPosition());
//                binding.arrivalMainSpinner.setSelection(selectedTemp);
//            }
//        });
//    }

//    private void testLogout() {
//        binding.toLogoutMainBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mAuth.signOut();
//            }
//        });
//    }

//    private void testLogin() {
//        binding.toLoginMainBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toLogin();
//            }
//        });
//    }

//    private void testRegister() {
//        binding.toRegisterMainBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toRegister();
//            }
//        });
//    }

    private void toLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    private void toRegister() {
        Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
        startActivity(intent);
    }

//    private void getValues() {
////        Intent intent = new Intent(MainActivity.this, SearchActivity.class);
////        intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem()).getName());
//        from = binding.departureMainSpinner.getSelectedItem().toString();
//        to = binding.arrivalMainSpinner.getSelectedItem().toString();
////        date = binding.inputDateHomeTV.getText().toString();
//    }

//    private void sendData(){
//        Intent intent = new Intent(MainActivity.this, ResultActivity.class);
//        intent.putExtra("from", from);
//        intent.putExtra("to", to);
////        intent.putExtra("date", date);
////        intent.putExtra("passengerCount", passengerCount);
//        startActivity(intent);
//    }

//    private void setVariable() {
//        binding.searchFlightsHomeBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
////                intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem().getName());
//                intent.putExtra("from", ((Location)binding.departureHomeSpinner.getSelectedItem()).getName());
//                intent.putExtra("to", ((Location)binding.arrivalHomeSpinner.getSelectedItem()).getName());
//                intent.putExtra("date", binding.inputDateHomeTV.getText().toString());
//                intent.putExtra("numPassenger", passengerCount);
//                startActivity(intent);
//            }
//        });
//    }


//    private void initLocation(){
//        binding.departureMainPB.setVisibility(View.VISIBLE);
//        binding.arrivalMainPB.setVisibility(View.VISIBLE);
//        DatabaseReference myRef = db.getReference("Locations");
//        ArrayList<Location> list = new ArrayList<>();
//        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot issue:snapshot.getChildren()){
//                        list.add(issue.getValue(Location.class));
//
//                    }
//                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(MainActivity.this, R.layout.sp_item, list);
//                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                    binding.departureMainSpinner.setAdapter(adapter);
//                    binding.arrivalMainSpinner.setAdapter(adapter);
//                    binding.departureMainSpinner.setSelection(1);
//                    binding.departureMainPB.setVisibility(View.GONE);
//                    binding.arrivalMainPB.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

//    private void initPassenger(){
//        binding.plusPassengerHomeTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(passengerCount < 5){
//                    passengerCount++;
//                    binding.passengerCountHomeTV.setText(passengerCount + " Passenger");
//                    binding.warningHomeTV.setVisibility(View.GONE);
//                }else if(passengerCount == 5){
//                    binding.warningHomeTV.setVisibility(View.VISIBLE);
//                }
//            }
//        });
//
//        binding.minusPassengerHomeTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(passengerCount>1){
//                    passengerCount--;
//                    binding.passengerCountHomeTV.setText(passengerCount + " Passenger");
//                    binding.warningHomeTV.setVisibility(View.GONE);
//                }
//            }
//        });
//    }

//    private void initExtraBaggage(){
////        if(baggageCount==0) {
////            binding.baggageCountHomeTV.setText("0");
////        }
//        binding.plusBaggageHomeTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(baggageCount >= 0 ){
//                    baggageCount++;
//                    binding.baggageCountHomeTV.setText(baggageCount + "(x 25Kg)");
//                }
//            }
//        });
//
//        binding.minusBaggageHomeTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(baggageCount > 0){
//                    baggageCount--;
//                    if(baggageCount == 0){
//                        binding.baggageCountHomeTV.setText("No Baggage");
//                    }else {
//                        binding.baggageCountHomeTV.setText(baggageCount + "(x 25Kg)");
//                    }
//                }
//            }
//        });
//    }

//    public void initPickDate(){
//        Calendar calendarCurrent = Calendar.getInstance();
//        String todayDate = dateFormat.format(calendar.getTime());
//        binding.inputDateHomeTV.setText(todayDate);
//
//
//        binding.inputDateHomeTV.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDatePickerDialog(binding.inputDateHomeTV);
//            }
//        });
//    }

    public void showDatePickerDialog(TextView textView){
//        final Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, selectedYear, selectedMonth, selectedDay)->{
            calendar.set(selectedYear,selectedMonth,selectedDay);
            String formattedDate = dateFormat.format(calendar.getTime());
            textView.setText((formattedDate));
        }, year, month, day);
        datePickerDialog.show();
    }

//    private void searchFlight() {
//        binding.continueMainBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                getValues();
////                initList();
//                sendData();
//            }
//        });
//    }


//    private void initList() {
//        DatabaseReference myRef = db.getReference("Flights");
//        ArrayList<Flight> list = new ArrayList<>();
////        list.add(new Flight("AB123", "2h 40m", "5 oct,2024", "Marseille", "Paris", 150, 99.99, "1", "D1,B2,B3,F3,E2,D1,D5,A6", "23:05", "10:00", "AE77"));
////        Log.d("FlightLi", "Size: " + list.size());
//        Query query = myRef.orderByChild("from").equalTo(from);
//        query.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()){
//                    for(DataSnapshot issue : snapshot.getChildren()){
//                        Flight flight = issue.getValue(Flight.class);
//                        if(flight.getTo().equals(to)){
//                            list.add(flight);
//                        }
//
////                        if(flight.getTo().equals(to) && flight.getDate().equals(date)){
////                            list.add(flight);
////                        }
//
//                        if(!list.isEmpty()){
//                            Log.d("Test","list not empty");
//
//                            FlightAdapter adapter = new FlightAdapter(list, passengerCount);
//                            binding.resultHomeRV.setLayoutManager(new LinearLayoutManager(MainActivity.this
//                                    , LinearLayoutManager.VERTICAL, false));
////                            binding.resultHomeRV.setAdapter(new FlightAdapter(list));
//                            binding.resultHomeRV.setAdapter(adapter);
//                        }
//                        else {
//                            Log.d("Test","list empty");
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }





}
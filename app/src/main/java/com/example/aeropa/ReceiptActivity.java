package com.example.aeropa;

import android.os.Bundle;

import androidx.annotation.NonNull;

import com.example.aeropa.Model.Book;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.databinding.ActivityReceiptBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ReceiptActivity extends BaseActivity {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    private ActivityReceiptBinding binding;
    private Book book;
    private Flight flight;
    private String fromCode, toCode;
    private final HashMap<String, String> locationMap = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityReceiptBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();



        fetchLocationData();
        nama();
    }

    private void nama() {
        String nama = db.getReference("Users").child(user.getUid()).child("Name").toString();
    }

    private void fetchLocationData() {
        DatabaseReference locationRef = db.getReference("Locations");

        locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot data : snapshot.getChildren()){
                    String cityName = data.child("Name").getValue(String.class);
                    String airportCode = data.child("Id").getValue(String.class);
                    if (cityName != null && airportCode != null) {
                        locationMap.put(cityName, airportCode);
                    }
                }
                getBookExtra();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    private void getBookExtra() {


        book = (Book) getIntent().getSerializableExtra("currentBook");
        flight = (Flight) getIntent().getSerializableExtra("flight");

        fromCode = locationMap.getOrDefault(flight.getFrom(), "Unknown");
        toCode = locationMap.getOrDefault(flight.getTo(), "Unknown");

        binding.dptCodeReceiptTV.setText(fromCode);
        binding.arvCodeReceiptTV.setText(toCode);
        binding.dptCityReceiptTV.setText(flight.getFrom());
        binding.arvCityReceiptTV.setText(flight.getTo());
        binding.flightDateReceiptTV.setText(flight.getDate());
        binding.flightCodeReceiptTV.setText(flight.getPlaneCode());
        binding.seatsReceiptTV.setText(book.getSeats());

        DatabaseReference userRef = db.getReference("User").child(user.getUid()).child("UserName");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.getValue(String.class); // Ambil nilai userName
                if (userName != null) {
                    binding.passengerNameBoardingTV.setText(userName); // Tampilkan di TextView
                } else {
                    binding.passengerNameBoardingTV.setText("NULL"); // Fallback jika userName tidak ditemukan
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.passengerNameBoardingTV.setText("Error fetching name");
            }
        });

    }
}
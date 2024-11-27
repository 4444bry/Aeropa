package com.example.aeropa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aeropa.Adapter.FlightAdapter;
import com.example.aeropa.Adapter.ReccommendationAdapter;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.Model.Location;
import com.example.aeropa.Model.Reccommendation;
import com.example.aeropa.databinding.HomeFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class HomeFragment extends Fragment {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    private HomeFragmentBinding binding;
    private int passengerCount = 1;
    private int baggageCount = 0;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("d MMM, yyyy", Locale.ENGLISH);
    private Calendar calendar = Calendar.getInstance();

    private String from = "";
    private String to = "";
    private String date;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Home");
        binding = HomeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();

//        if (user == null) {
//            Intent intent = new Intent(requireContext(), LoginActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
//        } else {
            initLocation();
            switchDestination();
            searchFlight();
            initReccomendation();
//        }
    }



    private void initReccomendation() {
        DatabaseReference myRef = db.getReference("Reccommendation");
        ArrayList<Reccommendation> list = new ArrayList<>();
//        list.add(new Flight("AB123", "2h 40m", "5 oct,2024", "Marseille", "Paris", 150, 99.99, "1", "D1,B2,B3,F3,E2,D1,D5,A6", "23:05", "10:00", "AE77"));
//        Log.d("FlightLi", "Size: " + list.size());
//        Query query = myRef.orderByChild("CityName");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Reccommendation reco = issue.getValue(Reccommendation.class);
                        list.add(reco);

                        if(!list.isEmpty()){
                            Log.d("Test","list not empty");

                            ReccommendationAdapter adapter = new ReccommendationAdapter(list);
                            binding.recommendationListHomeRV.setLayoutManager(new LinearLayoutManager(getContext()
                                    , LinearLayoutManager.HORIZONTAL, false));
//                            binding.resultHomeRV.setAdapter(new FlightAdapter(list));
                            binding.recommendationListHomeRV.setAdapter(adapter);
                            Log.d("Test", "80");
                        }
                        else {
                            Log.d("Test","list empty");
                        }
                    }
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding ke null untuk mencegah memory leaks
        binding = null;
    }

    private void switchDestination() {
        binding.changeMainIV.setOnClickListener(view -> {
            int selectedTemp = binding.departureMainSpinner.getSelectedItemPosition();
            binding.departureMainSpinner.setSelection(binding.arrivalMainSpinner.getSelectedItemPosition());
            binding.arrivalMainSpinner.setSelection(selectedTemp);
        });
    }

    private void initLocation() {
        binding.departureMainPB.setVisibility(View.VISIBLE);
        binding.arrivalMainPB.setVisibility(View.VISIBLE);
        DatabaseReference myRef = db.getReference("Locations");
        ArrayList<Location> list = new ArrayList<>();
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot issue : snapshot.getChildren()) {
                        list.add(issue.getValue(Location.class));
                    }
                    ArrayAdapter<Location> adapter = new ArrayAdapter<>(requireContext(), R.layout.sp_item, list);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    binding.departureMainSpinner.setAdapter(adapter);
                    binding.arrivalMainSpinner.setAdapter(adapter);
                    binding.departureMainSpinner.setSelection(1);
                    binding.departureMainPB.setVisibility(View.GONE);
                    binding.arrivalMainPB.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("Error", "Database error: " + error.getMessage());
            }
        });
    }

    private void searchFlight() {
        binding.continueMainBtn.setOnClickListener(view -> {
            getValues();
            sendData();
        });
    }

    private void getValues() {
        from = binding.departureMainSpinner.getSelectedItem().toString();
        to = binding.arrivalMainSpinner.getSelectedItem().toString();
    }

    private void sendData() {
        Intent intent = new Intent(requireActivity(), ResultActivity.class);
        intent.putExtra("from", from);
        intent.putExtra("to", to);
        startActivity(intent);
    }
}

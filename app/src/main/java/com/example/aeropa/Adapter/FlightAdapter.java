package com.example.aeropa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

//import com.bumptech.glide.Glide;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.SeatActivity;
import com.example.aeropa.databinding.ViewholderFlightBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

public class FlightAdapter extends RecyclerView.Adapter<FlightAdapter.ViewHolder> {
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final HashMap<String, String> locationMap = new HashMap<>();
    private final ArrayList<Flight> flights;
    private Context context;
    private int passengerCount;
    private String fromCode, toCode;

    public FlightAdapter(ArrayList<Flight> flights, int passengerCount) {
        this.flights = flights;
        this.passengerCount = passengerCount;

        DatabaseReference locationRef = db.getReference("Locations");
        locationRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String cityName = data.child("Name").getValue(String.class);
                    String airportCode = data.child("Id").getValue(String.class);
                    if (cityName != null && airportCode != null) {
                        locationMap.put(cityName, airportCode);
                    }
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", "Failed to fetch locations: " + error.getMessage());
            }
        });
    }



    @NonNull
    @Override
    public FlightAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderFlightBinding binding = ViewholderFlightBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull FlightAdapter.ViewHolder holder, int position) {
        Flight flight = flights.get(position);

        fromCode = locationMap.getOrDefault(flight.getFrom(), "Unknown");
        toCode = locationMap.getOrDefault(flight.getTo(), "Unknown");

        holder.binding.dptCityCodeFVHTV.setText(fromCode);
        holder.binding.arvCityCodeFVHTV.setText(toCode);

        holder.binding.flightDateFVHTV.setText(flight.getDate());
        holder.binding.dptCityFVHTV.setText(flight.getFrom());
        holder.binding.arvCityFVHTV.setText(flight.getTo());
        holder.binding.flightTimeFVHTV.setText(flight.getArriveTime());
        holder.binding.planeIDFVHTV.setText(flight.getPlaneCode().toString());
        holder.binding.dptTimeFVHTV.setText(flight.getTimeDeparture());
        holder.binding.arvTimeFVHTV.setText(flight.getTimeArrival());
        holder.binding.ticketPriceFVHTV.setText("$" + flight.getPrice().toString());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, SeatActivity.class);
                intent.putExtra("flight", flight);
                intent.putExtra("passengerCount", passengerCount);
                intent.putExtra("fromCode", fromCode);
                intent.putExtra("toCode", toCode);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return flights.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderFlightBinding binding;
        public ViewHolder(ViewholderFlightBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

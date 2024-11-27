package com.example.aeropa.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.Model.Reccommendation;
import com.example.aeropa.databinding.ViewholderFlightBinding;
import com.example.aeropa.databinding.ViewholderReccomendationBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReccommendationAdapter extends RecyclerView.Adapter<ReccommendationAdapter.RecoViewHolder> {
    private final ArrayList<Reccommendation> reco;
    private Context context;

    public ReccommendationAdapter(ArrayList<Reccommendation> reco) {
        this.reco = reco;
    }

    @NonNull
    @Override
    public ReccommendationAdapter.RecoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderReccomendationBinding binding = ViewholderReccomendationBinding.inflate(LayoutInflater.from(context),parent,false);
        return new RecoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReccommendationAdapter.RecoViewHolder holder, int position) {
        Reccommendation reccommendation = reco.get(position);

        if (reccommendation.getFlights() == null) {
            fetchFlights(reccommendation.getCityName(), reccommendation);
        }

        double lowestPrice = reccommendation.getLowestPrice();

        Glide.with(context).load(reccommendation.getCityPhoto()).into(holder.binding.cityImageReccommendationIV);
        holder.binding.cityNameReccommendationTV.setText(reccommendation.getCityName() + ", " +
                reccommendation.getCityCountry());

        if(lowestPrice != -1){
            holder.binding.priceReccommendationTV.setText("from" + " $" + lowestPrice);
        }


    }

    @Override
    public int getItemCount() {
        return reco.size();
    }

    public class RecoViewHolder extends RecyclerView.ViewHolder {
        ViewholderReccomendationBinding binding;
        public RecoViewHolder(@NonNull ViewholderReccomendationBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void fetchFlights(String cityName, Reccommendation reccommendation) {
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("Flights");

        databaseRef.orderByChild("to").equalTo(cityName)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        List<Flight> flights = new ArrayList<>();
                        for (DataSnapshot flightSnapshot : snapshot.getChildren()) {
                            Flight flight = flightSnapshot.getValue(Flight.class);
                            flights.add(flight);
                        }
                        reccommendation.setFlights(flights); // Setter untuk mengisi daftar penerbangan
                        notifyDataSetChanged(); // Perbarui tampilan adapter
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}



package com.example.aeropa.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeropa.Model.Book;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.R;
import com.example.aeropa.ReceiptActivity;
import com.example.aeropa.databinding.ViewholderMyticketsBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

public class MyTicketsAdapter extends RecyclerView.Adapter<MyTicketsAdapter.MyTicketsViewHolder>{
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    private final HashMap<Integer, Flight> flightDetails = new HashMap<>();

    private ArrayList<Book> book;
    private Context context;

    public MyTicketsAdapter(ArrayList<Book> book) {
        this.book = book;

        DatabaseReference flightRef = db.getReference("Flights");
        flightRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Flight flight = data.getValue(Flight.class);
                    if(flight != null && flight.getFlightCode() != null){
                        flightDetails.put(flight.getFlightCode(), flight);
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

    private String getCountdown(String flightDate) {
        try {

//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM,yyyy", Locale.ENGLISH);
            DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                    .parseCaseInsensitive() // Abaikan perbedaan huruf besar/kecil
                    .appendPattern("d MMM,yyyy")
                    .toFormatter(Locale.ENGLISH);
            LocalDate targetDate = LocalDate.parse(flightDate, formatter);

            LocalDate today = LocalDate.now();
            long daysLeft = ChronoUnit.DAYS.between(today, targetDate);

            // Tampilkan hasil
            if (daysLeft > 0) {
                return daysLeft + " days remaining";
            } else if (daysLeft == 0) {
                return "Today";
            } else {
                return "Flight Passed";
            }
        } catch (DateTimeParseException e) {
            e.printStackTrace();
            return "Invalid Date";
        }
    }

    @NonNull
    @Override
    public MyTicketsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ViewholderMyticketsBinding binding = ViewholderMyticketsBinding.inflate(LayoutInflater.from(context),parent,false);
        return new MyTicketsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTicketsViewHolder holder, int position) {
        Book currentBook = book.get(position);
        Integer flightCode = currentBook.getFlightCode();

        Flight flight = flightDetails.get(flightCode);

        if (flight != null) {
            holder.binding.destinationMtVHTV.setText(flight.getFrom() + " to " + flight.getTo());
            holder.binding.hourMtVHTV.setText(flight.getTimeDeparture() + " - " + flight.getTimeArrival() + ",");
            holder.binding.durationMtVHTV.setText(flight.getArriveTime());
            holder.binding.flightDateMtVHTV.setText(flight.getDate());
            holder.binding.numPassengerMtVHTV.setText(currentBook.getNumPassenger() + " Passengers");
            holder.binding.seatsMtVHTV.setText(currentBook.getSeats());

            holder.binding.countdownMtVHTV.setText(getCountdown(flight.getDate()));
            Log.d("date", flight.getDate());

            if(holder.binding.countdownMtVHTV.getText().equals("Flight Passed")){
                holder.binding.constraintLayout27.setBackgroundResource(R.drawable.red_bg_toprad15);
            }else{
                holder.binding.constraintLayout27.setBackgroundResource(R.drawable.yellow_bg_toprad15);
            }

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ReceiptActivity.class);
                    intent.putExtra("currentBook", currentBook);
                    intent.putExtra("flight", flight);
                    context.startActivity(intent);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return book.size();
    }

    public class MyTicketsViewHolder extends RecyclerView.ViewHolder{
        private final ViewholderMyticketsBinding binding;
        public MyTicketsViewHolder(ViewholderMyticketsBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

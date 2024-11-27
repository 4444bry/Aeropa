package com.example.aeropa.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aeropa.Model.Seat;
import com.example.aeropa.R;
import com.example.aeropa.databinding.SeatItemBinding;

import java.util.ArrayList;
import java.util.List;

public class SeatAdapter extends RecyclerView.Adapter<SeatAdapter.SeatViewHolder> {
    private final List<Seat> seatList;
    private final Context context;
    private ArrayList<String> SelectedSeatName = new ArrayList<>();
    private SelectedSeat selectedSeat;
    private int passengerCount;

    public SeatAdapter(List<Seat> seatList, Context context, SelectedSeat selectedSeat, int passengerCount) {
        this.seatList = seatList;
        this.context = context;
        this.selectedSeat = selectedSeat;
        this.passengerCount = passengerCount;
    }

    @NonNull
    @Override
    public SeatAdapter.SeatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        SeatItemBinding binding = SeatItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new SeatViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeatAdapter.SeatViewHolder holder, int position) {
        Seat seat = seatList.get(position);
        holder.binding.SeatImageView.setText(seat.getName());

        switch(seat.getStatus()){
            case AVAILABLE:
                holder.binding.SeatImageView.setBackgroundResource(R.drawable.ic_seat_available);
                holder.binding.SeatImageView.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case UNAVAILABLE:
                holder.binding.SeatImageView.setBackgroundResource(R.drawable.ic_seat_unavailable);
                holder.binding.SeatImageView.setTextColor(context.getResources().getColor(R.color.white));
                break;
            case SELECTED:
                holder.binding.SeatImageView.setBackgroundResource(R.drawable.ic_seat_selected);
                holder.binding.SeatImageView.setTextColor(context.getResources().getColor(R.color.black));
                break;
            case EMPTY:
                holder.binding.SeatImageView.setBackgroundResource(R.drawable.ic_seat_empty);
                holder.binding.SeatImageView.setTextColor(Color.parseColor("#00000000"));
                break;
        }

        holder.binding.SeatImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(seat.getStatus() == Seat.SeatStatus.AVAILABLE && SelectedSeatName.size() < passengerCount){
                    seat.setStatus(Seat.SeatStatus.SELECTED);
                    SelectedSeatName.add(seat.getName());
                    notifyItemChanged(position);
                }else if(seat.getStatus() == Seat.SeatStatus.SELECTED){
                    seat.setStatus(Seat.SeatStatus.AVAILABLE);
                    SelectedSeatName.remove(seat.getName());
                    notifyItemChanged(position);
                }
                String selected = SelectedSeatName.toString()
                        .replace("[", "")
                        .replace("]", "")
                        .replace(" ", "");
                selectedSeat.Return(selected, SelectedSeatName.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return seatList.size();
    }

    public class SeatViewHolder extends RecyclerView.ViewHolder {
        SeatItemBinding binding;
        public SeatViewHolder(@NonNull SeatItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public interface  SelectedSeat{
        void Return(String selectedName, int num);
    }
}

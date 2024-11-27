package com.example.aeropa;

import static java.lang.Math.floor;
import static java.lang.Math.round;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.aeropa.Adapter.SeatAdapter;
import com.example.aeropa.Model.Flight;
import com.example.aeropa.Model.Seat;
import com.example.aeropa.databinding.ActivitySeatBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeatActivity extends BaseActivity {
    private ActivitySeatBinding binding;
    private Flight flight;
    private Double price = 0.0;
    private int num = 0;
    private int passengerCount;
    private String fromCode, toCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySeatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        getIntentExtra();
        setTicketDetails();
        initSeatList();
        backToMain();
        toPayment();
        setBookingExtra();


    }

    private void setBookingExtra() {

    }

    private void toPayment() {
        binding.confirmOrderSeatBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(user == null){
                    Intent intent = new Intent(SeatActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(SeatActivity.this, "Login to book", Toast.LENGTH_SHORT).show();
                }else{
                    if(num == passengerCount){
                        Intent intent = new Intent(SeatActivity.this, PaymentActivity.class);
                        intent.putExtra("flightCode", flight.getFlightCode());
                        intent.putExtra("userId", user.getUid());
                        intent.putExtra("numPassenger", passengerCount);
                        intent.putExtra("seats", binding.seatsSelectedSeatTV.getText().toString());
                        startActivity(intent);
                    }else{
                        Toast.makeText(SeatActivity.this, "Please select " + (passengerCount-num) + " more seats", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }

    private void setTicketDetails() {

    }

    private void backToMain() {
        binding.backToMainSeatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SeatActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initSeatList() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 7);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup(){
            @Override
            public int getSpanSize(int position) {
                return (position % 7 == 3) ? 1 : 1;
            }
        });

        binding.seatsSeatRV.setLayoutManager(gridLayoutManager);

        List<Seat> seatList = new ArrayList<>();
        int row = 0;
        int numberSeats = flight.getNumberSeat() + (flight.getNumberSeat() / 7) + 1;

        Map<Integer, String> seatAlphabetMap = new HashMap<>();
        seatAlphabetMap.put(0,"A");
        seatAlphabetMap.put(1,"B");
        seatAlphabetMap.put(2,"C");
        seatAlphabetMap.put(4,"D");
        seatAlphabetMap.put(5,"E");
        seatAlphabetMap.put(6,"F");

        for(int i = 0; i < numberSeats; i++){
            if(i % 7 == 0){
                row++;
            }
            if(i % 7 == 3){
                seatList.add(new Seat(Seat.SeatStatus.EMPTY, String.valueOf(row)));
            }
            else{
                String seatName = seatAlphabetMap.get(i % 7) + row;
                Seat.SeatStatus seatStatus = flight.getReservedSeats().contains(seatName)? Seat.SeatStatus.UNAVAILABLE: Seat.SeatStatus.AVAILABLE;
                seatList.add(new Seat(seatStatus, seatName));
            }
        }

        SeatAdapter seatAdapter = new SeatAdapter(seatList, this, (selectedName, num) -> {
            binding.seatsSelectedAmountSeatTV.setText(num + "/" +passengerCount +" seats selected");
            binding.seatsSelectedSeatTV.setText(selectedName);
//            Log.d("SeatActivity", "price test database: " + flight.getPrice());
            DecimalFormat df = new DecimalFormat("#.##");
//            price = (Double.valueOf(df.format(num * flight.getPrice())));
            price = Math.round(num * flight.getPrice() * 100.0) / 100.0;
//            price = (double) round(num * flight.getPrice());
            this.num = num;
            binding.totalPriceSeatTV.setText("$"  + price);
        }, passengerCount);

        binding.seatsSeatRV.setAdapter(seatAdapter);
        binding.seatsSeatRV.setNestedScrollingEnabled(false);
    }

    private void getIntentExtra() {
        flight = (Flight) getIntent().getSerializableExtra("flight");

        binding.dptCitySeatTV.setText(flight.getFrom());
        binding.arvCitySeatTV.setText(flight.getTo());
        binding.timeTicketSeatTV.setText(flight.getArriveTime());
        binding.flightIdSeatTV.setText(flight.getPlaneCode());
        binding.seatPriceSeatTV.setText(flight.getPrice().toString());
        binding.flightDateSeatTV.setText(flight.getDate());
        binding.dptTimeSeatTV.setText(flight.getTimeDeparture());
        binding.arvTimeSeatTV.setText(flight.getTimeArrival());

        passengerCount = getIntent().getIntExtra("passengerCount", 1);
        fromCode = getIntent().getStringExtra("fromCode");
        toCode = getIntent().getStringExtra("toCode");

        binding.seatsSelectedAmountSeatTV.setText(num + "/" +passengerCount +" seats selected");
        binding.departureCodeSeatTV.setText(fromCode);
        binding.arrivalCodeSeatTV.setText(toCode);
    }
}
package com.example.aeropa;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aeropa.databinding.ActivityPaymentBinding;
import com.example.aeropa.databinding.ContactSheetBinding;
import com.example.aeropa.databinding.PassengerSheetBinding;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class PaymentActivity extends BaseActivity {
    private ActivityPaymentBinding binding;
    private Button dialogButton;
    private String passengerName, passportId, phoneNumber, email;


    private String bookingId;
    private int flightCode;
    private String userId;
    private int numPassenger;
    private String seats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityPaymentBinding.inflate(getLayoutInflater());
//        passengerSheetBinding = PassengerSheetBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
//        dialogButton = findViewById(R.id.confirmChange_Payment_Btn);




        binding.editPassengerPaymentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPassengerDataDialog();
            }
        });

        binding.editContactPaymentIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showContactDataDialog();
            }
        });

        binding.placeOrderPaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getBookingExtra();
                doBooking();
                Intent intent = new Intent(PaymentActivity.this, MainActivity.class);
                intent.putExtra("fragment", "TicketsFragment");
                startActivity(intent);
                finish();
            }
        });


    }

    private void doBooking() {
        if(user == null){
            Toast.makeText(PaymentActivity.this, "Please login first", Toast.LENGTH_SHORT).show();
            return;
        }else{
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference databaseReference = database.getReference("User");
            getBookingExtra();

            DatabaseReference bookingRef = databaseReference.child(userId).child("bookingList").push();
            String bookingId = bookingRef.getKey();
            HashMap<String, Object> bookingData = new HashMap<>();
            bookingData.put("flightCode", flightCode);
            bookingData.put("userId", userId);
            bookingData.put("numPassenger", numPassenger);
            bookingData.put("seats", seats);

            databaseReference.child(userId).child("bookingList").child(bookingId).setValue(bookingData).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(PaymentActivity.this, "Booking successful", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(PaymentActivity.this, "Booking failed", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    private void getBookingExtra() {
        flightCode = getIntent().getIntExtra("flightCode", 0);
        userId = getIntent().getStringExtra("userId");
        numPassenger = getIntent().getIntExtra("numPassenger", 0);
        seats = getIntent().getStringExtra("seats");

    }

    private void showContactDataDialog() {
        ContactSheetBinding contactSheetBinding;
        contactSheetBinding = ContactSheetBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(contactSheetBinding.getRoot());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        contactSheetBinding.confirmChangeContactdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phoneNumber = contactSheetBinding.phoneNumberContactdataET.getText().toString();
                email = contactSheetBinding.passengerEmailContactdataET.getText().toString();

                if(phoneNumber.isBlank()){
                    contactSheetBinding.textInputLayout3.setError("Phone number must be filled");
                }if(email.isBlank()){
                    contactSheetBinding.textInputLayout4.setError("Email address must be filled");
                }else{
                    // Update TextView di layout utama
                    binding.phoneNumberPaymentTV.setText(phoneNumber);
                    binding.emailPaymentTV.setText(email);

                    // Tutup dialog setelah konfirmasi
                    dialog.dismiss();
                }
            }
        });
    }


    private void showPassengerDataDialog() {
        PassengerSheetBinding passengerSheetBinding;
        passengerSheetBinding = PassengerSheetBinding.inflate(getLayoutInflater());
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(passengerSheetBinding.getRoot());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

        passengerSheetBinding.confirmChangePaymentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ambil teks dari EditText dalam dialog
                passengerName = passengerSheetBinding.passengerNamePassengerdataET.getText().toString();
                passportId = passengerSheetBinding.passportIdPassengerdataET.getText().toString();

                if(passengerName.isBlank()){
                    passengerSheetBinding.textInputLayout3.setError("Name must be filled");
                }if(passportId.isBlank()){
                    passengerSheetBinding.textInputLayout4.setError("Id must be filled");
                }else{
                    // Update TextView di layout utama
                    binding.passengerNamePaymentTV.setText(passengerName);
                    binding.passengerPassportIdPaymentTV.setText(passportId);

                    // Tutup dialog setelah konfirmasi
                    dialog.dismiss();
                }


            }
        });


    }


}
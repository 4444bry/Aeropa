package com.example.aeropa;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.aeropa.Adapter.MyTicketsAdapter;
import com.example.aeropa.Model.Book;
import com.example.aeropa.databinding.ProfileFragmentBinding;
import com.example.aeropa.databinding.TicketsFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TicketsFragment extends Fragment {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    TicketsFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Tickets");
        binding = TicketsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseDatabase.getInstance();

        if (user == null) {
//            Intent intent = new Intent(requireContext(), LoginActivity.class);
//            startActivity(intent);
//            requireActivity().finish();
        } else {
            initTicketList();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void initTicketList() {
        DatabaseReference myRef = db.getReference("User");
        ArrayList<Book> list = new ArrayList<>();

        myRef.child(user.getUid()).child("bookingList").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot issue : snapshot.getChildren()){
                        Book book = issue.getValue(Book.class);
                        list.add(book);

                        if(!list.isEmpty()){
                            MyTicketsAdapter adapter = new MyTicketsAdapter(list);
                            binding.myTicketsTicketsRV.setLayoutManager(new LinearLayoutManager(getContext(),
                                    LinearLayoutManager.VERTICAL, false));
                            binding.myTicketsTicketsRV.setAdapter(adapter);
                            Log.d("Test", "80");
                        }else{
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
}

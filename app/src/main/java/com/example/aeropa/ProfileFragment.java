package com.example.aeropa;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.aeropa.databinding.HomeFragmentBinding;
import com.example.aeropa.databinding.ProfileFragmentBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {
    FirebaseDatabase db;
    FirebaseAuth mAuth;
    FirebaseUser user;

    ProfileFragmentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getActivity().setTitle("Profile");
        binding = ProfileFragmentBinding.inflate(inflater, container, false);
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
            isNotLogin();
            toSignInRegister();
        } else {
            isLogin();
//            isNotLogin();
            doLogOut();
//            toSignInRegister();
        }

        accountFeatures();
    }

    private void accountFeatures() {
        binding.yourDetailsProfileCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
            }
        });

        binding.customerServiceProfileCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
            }
        });

        binding.settingsProfileCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
            }
        });

        binding.privacyProfileCL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(requireContext(), "Feature not available yet", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getName(){
        DatabaseReference userRef = db.getReference("User").child(user.getUid()).child("UserName");
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userName = snapshot.getValue(String.class); // Ambil nilai userName
                if (userName != null) {
                    binding.userNameProfileTV.setText(userName); // Tampilkan di TextView
                } else {
                    binding.userNameProfileTV.setText("NULL"); // Fallback jika userName tidak ditemukan
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                binding.userNameProfileTV.setText("Error fetching name");
            }
        });
    }

    private void isNotLogin() {
        if(user == null){
            binding.signInRegisterProfileBtn.setVisibility(View.VISIBLE);
            binding.textView41.setVisibility(View.VISIBLE);
            binding.userNameProfileTV.setVisibility(View.GONE);
            binding.userIdProfileTV.setVisibility(View.GONE);
            binding.logOutProfileBtn.setVisibility(View.GONE);
            binding.profilePictureProfileIV.setVisibility(View.GONE);
        }else{
//            isLogin();
        }
    }

    private void isLogin() {
        if(user != null){
            getName();
            binding.userIdProfileTV.setText("User id - " + user.getUid());
            binding.signInRegisterProfileBtn.setVisibility(View.GONE);
            binding.textView41.setVisibility(View.GONE);
            binding.userNameProfileTV.setVisibility(View.VISIBLE);
            binding.userIdProfileTV.setVisibility(View.VISIBLE);
            binding.logOutProfileBtn.setVisibility(View.VISIBLE);
            binding.profilePictureProfileIV.setVisibility(View.VISIBLE);
        }else{
//            isNotLogin();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Set binding ke null untuk mencegah memory leaks
        binding = null;
    }

    private void doLogOut() {
        binding.logOutProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(requireContext(), "Sign Out Success", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(requireContext(), MainActivity.class);
                intent.putExtra("fragment", "HomeFragment"); // Kirim info fragment
                startActivity(intent);
                requireActivity().finish();
            }
        });
    }

    private void toSignInRegister() {
        binding.signInRegisterProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(requireActivity(), RegisterActivity.class);
                startActivity(intent);
            }
        });

    }
}

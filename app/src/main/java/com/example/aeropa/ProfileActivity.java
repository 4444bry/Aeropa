package com.example.aeropa;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.aeropa.databinding.ActivityProfileBinding;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class ProfileActivity extends BaseActivity{
    private ActivityProfileBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        toSignInRegister();
        doLogOut();
        navbar();
    }

    private void navbar() {
        binding.chipNavMainCNB.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                Intent intent = null;
                if(i == R.id.nav_home){
                    intent = new Intent(ProfileActivity.this, MainActivity.class);
                }else if(i == R.id.nav_tickets){
//                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                }else if(i == R.id.nav_profile){
//                    intent = new Intent(MainActivity.this, ProfileActivity.class);
                }

                if (intent != null) {
                    startActivity(intent);
                }
            }
        });
    }

    private void doLogOut() {
        binding.logOutProfileBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mAuth.signOut();
                }
            });
    }

    private void toSignInRegister() {
        Intent intent = new Intent(ProfileActivity.this, RegisterActivity.class);
            startActivity(intent);
    }
}
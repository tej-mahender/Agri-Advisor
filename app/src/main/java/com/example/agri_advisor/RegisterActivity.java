package com.example.agri_advisor;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private EditText nameInput, areaInput, farmLandInput, emailInput, mobileInput, passwordInput;
    private Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.register), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        nameInput = findViewById(R.id.name);
        areaInput = findViewById(R.id.area);
        farmLandInput = findViewById(R.id.farmLand);
        emailInput = findViewById(R.id.email);
        mobileInput = findViewById(R.id.mobile);
        passwordInput = findViewById(R.id.password);
        registerBtn = findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String name = nameInput.getText().toString();
        String area = areaInput.getText().toString();
        String farmLand = farmLandInput.getText().toString();
        String email = emailInput.getText().toString();
        String mobile = mobileInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (name.isEmpty() || area.isEmpty() || farmLand.isEmpty() || email.isEmpty() || mobile.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Map<String, Object> farmer = new HashMap<>();
                            farmer.put("name", name);
                            farmer.put("area", area);
                            farmer.put("farm_land", farmLand);
                            farmer.put("email", email);
                            farmer.put("mobile", mobile);

                            db.collection("farmers").document(user.getUid()).set(farmer)
                                    .addOnSuccessListener(unused -> {
                                        Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                                        mAuth.signOut();

                                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                                        finish();
                                    })
                                    .addOnFailureListener(e -> Toast.makeText(RegisterActivity.this, "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
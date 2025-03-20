package com.example.agri_advisor;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class ProfileFragment extends Fragment {

    private TextView welcomeText, userDetails;
    private Button logoutBtn;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        welcomeText = view.findViewById(R.id.welcomeText);
        userDetails = view.findViewById(R.id.userDetails);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            fetchUserData(user.getUid());
        } else {
            userDetails.setText("No user logged in.");
        }

        logoutBtn.setOnClickListener(v -> {
            mAuth.signOut();
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        });

        return view;
    }

    private void fetchUserData(String userId) {
        DocumentReference docRef = db.collection("farmers").document(userId);
        docRef.get().addOnSuccessListener(documentSnapshot -> {
            if (documentSnapshot.exists()) {
                String name = documentSnapshot.getString("name");
                String email = documentSnapshot.getString("email");
                String area = documentSnapshot.getString("area");
                String mobile = documentSnapshot.getString("mobile");
                String farmLand = documentSnapshot.getString("farm_land");

                welcomeText.setText("Welcome, " + name + "!");
                userDetails.setText("📧 Email: " + email +
                        "\n📍 Area: " + area +
                        "\n📱 Mobile: " + mobile +
                        "\n🌾 Farm Land: " + farmLand);
            } else {
                userDetails.setText("User data not found.");
            }
        }).addOnFailureListener(e -> Toast.makeText(getActivity(), "Failed to load user data.", Toast.LENGTH_SHORT).show());
    }
}

package com.example.agri_advisor;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class YieldFragment extends Fragment {

    private RecyclerView recyclerView;
    private CropPagerAdapter adapter; // Correct Adapter
    private DatabaseReference databaseReference;
    private List<CropModel> cropList = new ArrayList<>();
    private SearchView searchView;
    private ProgressBar progressBar;
    private Button searchButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_yield, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        searchView = view.findViewById(R.id.searchView);
        progressBar = view.findViewById(R.id.progressBar);
        searchButton = view.findViewById(R.id.searchButton);

        searchView.setIconifiedByDefault(false);
        searchView.clearFocus(); // Prevents auto-focus

        // Initialize RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));
        adapter = new CropPagerAdapter(getContext(), cropList);
        recyclerView.setAdapter(adapter);

        databaseReference = FirebaseDatabase.getInstance().getReference("crops");

        loadCropsFromFirebase();

        // Search button
        searchButton.setOnClickListener(v -> filterCrops(searchView.getQuery().toString()));

        // SearchView Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterCrops(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterCrops(newText);
                return false;
            }
        });

        return view;
    }

    private void loadCropsFromFirebase() {
        progressBar.setVisibility(View.VISIBLE);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cropList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    CropModel crop = dataSnapshot.getValue(CropModel.class);
                    if (crop != null) {
                        Log.d("FirebaseData", "Loaded Crop: " + crop.getCropName());
                        cropList.add(crop);
                    }
                }

                adapter.updateList(cropList);
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(getContext(), "Failed to load crops: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void filterCrops(String query) {
        List<CropModel> filteredList = new ArrayList<>();
        for (CropModel crop : cropList) {
            if (crop.getCropName().toLowerCase().contains(query.toLowerCase())) {
                filteredList.add(crop);
            }
        }

        adapter.updateList(filteredList);
    }
}

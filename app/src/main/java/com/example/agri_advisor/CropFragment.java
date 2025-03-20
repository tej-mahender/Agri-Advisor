package com.example.agri_advisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.HashMap;
import java.util.Map;

public class CropFragment extends Fragment {
    private EditText inputN, inputP, inputK, inputTemp, inputHumidity, inputPh, inputRain;
    private TextView outputCrop;
    private Button btnPredict;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_crop, container, false);

        // Initialize views
        inputN = view.findViewById(R.id.input_nitrogen);
        inputP = view.findViewById(R.id.input_phosphorus);
        inputK = view.findViewById(R.id.input_potassium);
        inputTemp = view.findViewById(R.id.input_temperature);
        inputHumidity = view.findViewById(R.id.input_humidity);
        inputPh = view.findViewById(R.id.input_ph);
        inputRain = view.findViewById(R.id.input_rainfall);
        outputCrop = view.findViewById(R.id.output_crop);
        btnPredict = view.findViewById(R.id.btn_predict);

        btnPredict.setOnClickListener(view1 -> {
            try {
                // Get input values
                Map<String, Object> cropData = new HashMap<>();
                cropData.put("N", Double.parseDouble(inputN.getText().toString()));
                cropData.put("P", Double.parseDouble(inputP.getText().toString()));
                cropData.put("K", Double.parseDouble(inputK.getText().toString()));
                cropData.put("temperature", Double.parseDouble(inputTemp.getText().toString()));
                cropData.put("humidity", Double.parseDouble(inputHumidity.getText().toString()));
                cropData.put("ph", Double.parseDouble(inputPh.getText().toString()));
                cropData.put("rainfall", Double.parseDouble(inputRain.getText().toString()));

                // Call API
                APIHandler.predictCrop(requireContext(), cropData, result -> outputCrop.setText(result));
            } catch (NumberFormatException e) {
                Toast.makeText(requireContext(), "Error: Enter valid numbers in all fields.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}
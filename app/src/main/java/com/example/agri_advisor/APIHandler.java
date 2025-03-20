package com.example.agri_advisor;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.*;
import com.android.volley.toolbox.*;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.Map;

public class APIHandler {
    private static final String API_URL = "https://crop-api-nv49.onrender.com/predict"; // Change if using a real device

    public static void predictCrop(Context context, Map<String, Object> cropData, final VolleyCallback callback) {
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.POST, API_URL, new JSONObject(cropData),
                response -> {
                    try {
                        if (response.has("recommended_crop")) {
                            String recommendedCrop = response.getString("recommended_crop");
                            callback.onSuccess("Recommended Crop: " + recommendedCrop);
                        } else {
                            callback.onSuccess("Error: Invalid response from server.");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        callback.onSuccess("Error: JSON Parsing Error.");
                    }
                },
                error -> {
                    String errorMessage = "Request Failed! ";
                    if (error instanceof NetworkError) {
                        errorMessage += "Network error!";
                    } else if (error instanceof ServerError) {
                        errorMessage += "Server error!";
                    } else if (error instanceof TimeoutError) {
                        errorMessage += "Connection timed out!";
                    } else if (error.networkResponse != null) {
                        errorMessage += "Status Code: " + error.networkResponse.statusCode;
                    } else {
                        errorMessage += "No response from server.";
                    }
                    Log.e("APIHandler", "Error: " + errorMessage);
                    Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show();
                });

        queue.add(request);
    }

    public interface VolleyCallback {
        void onSuccess(String result);
    }
}

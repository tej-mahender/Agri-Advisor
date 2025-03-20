package com.example.agri_advisor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class WeatherFragment extends Fragment {

    private TextView weatherLocation, weatherCondition, weatherTemperature, weatherHumidity, weatherWind;
    private ImageView weatherIcon;
    private RequestQueue requestQueue;
    private static final String API_KEY = "3561cb39bb4d25786475ca9221819c4d\n"; // Replace with your OpenWeather API key
    private static final String CITY = "Vijayawada"; // Change this to your city
    private static final String API_URL = "https://api.openweathermap.org/data/2.5/weather?q=" + CITY + "&appid=" + API_KEY + "&units=metric";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);

        weatherLocation = view.findViewById(R.id.weatherLocation);
        weatherCondition = view.findViewById(R.id.weatherCondition);
        weatherTemperature = view.findViewById(R.id.weatherTemperature);
        weatherHumidity = view.findViewById(R.id.weatherHumidity);
        weatherWind = view.findViewById(R.id.weatherWind);
        weatherIcon = view.findViewById(R.id.weatherIcon);

        requestQueue = Volley.newRequestQueue(requireContext());

        fetchWeatherData();

        return view;
    }

    private void fetchWeatherData() {
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, API_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String city = response.getString("name");
                            JSONObject main = response.getJSONObject("main");
                            JSONObject wind = response.getJSONObject("wind");
                            JSONObject weather = response.getJSONArray("weather").getJSONObject(0);

                            double temp = main.getDouble("temp");
                            int humidity = main.getInt("humidity");
                            double windSpeed = wind.getDouble("speed");
                            String condition = weather.getString("main");
                            String icon = weather.getString("icon");

                            weatherLocation.setText(city);
                            weatherTemperature.setText("🌡 Temp: " + temp + "°C");
                            weatherHumidity.setText("💧 Humidity: " + humidity + "%");
                            weatherWind.setText("🌬 Wind: " + windSpeed + " km/h");
                            weatherCondition.setText(condition);

                            int iconRes = getWeatherIcon(icon);
                            weatherIcon.setImageResource(iconRes);

                        } catch (JSONException e) {
                            Toast.makeText(getActivity(), "Error parsing weather data", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity(), "Failed to fetch weather", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(request);
    }

    private int getWeatherIcon(String iconCode) {
        switch (iconCode) {
            case "01d": return R.drawable.sunny;
            case "01n": return R.drawable.night_clear;
            case "02d": case "02n": return R.drawable.clouds;
            case "03d": case "03n": return R.drawable.scattered_clouds;
            case "04d": case "04n": return R.drawable.broken_clouds;
            case "09d": case "09n": return R.drawable.shower_rain;
            case "10d": return R.drawable.rain_day;
            case "10n": return R.drawable.rain_night;
            case "11d": case "11n": return R.drawable.thunderstorm;
            case "13d": case "13n": return R.drawable.snow;
            case "50d": case "50n": return R.drawable.mist;
            default: return R.drawable.default_weather;
        }
    }
}

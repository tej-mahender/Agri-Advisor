
# 🌾 Agri-Advisor

Agri-Advisor is a smart mobile application designed to empower farmers with data-driven agricultural insights. Developed as part of an academic project, it integrates machine learning, weather APIs, and Firebase to enhance farming decisions and productivity.

## 📱 About the Project

Agri-Advisor helps farmers select the most suitable crops based on soil and environmental conditions. By inputting values like Nitrogen (N), Phosphorus (P), Potassium (K), temperature, humidity, pH, and rainfall, the app uses a machine learning model to recommend crops tailored to the user's local conditions.

Built on Android using Java, with Firebase for backend storage and authentication, and a Python Flask API for ML predictions.

**Documentation**: [Agri-Advisor.pdf](https://drive.google.com/file/d/1HbBSy-VANezsGS9hq2wRbtHmtwJHsAHx/view)


## 🌟 Features

- 🔐 **User Authentication & Profile Management**  
  Secure login and registration system with personalized profile storage.

- 🌦 **Real-time Weather Updates**  
  Integrated with OpenWeatherMap API to provide location-based climate data (temperature, humidity, wind speed).

- 🌱 **Machine Learning-based Crop Recommendation**  
  Suggests optimal crops using soil and climate inputs processed via a Flask-hosted ML model.

- 📚 **Crop Information Database**  
  Searchable and filterable crop descriptions including cultivation methods and benefits.

- 📊 **Interactive User Dashboard**  
  View weather conditions, profile details, and recommended crops in one place.

- 🔍 **Efficient Crop Search & Filtering**  
  Quickly search and filter crop details with responsive UI components.

- 🌍 **SDG Alignment**  
  Supports:
  - SDG 1 – No Poverty
  - SDG 2 – Zero Hunger
  - SDG 3 – Good Health and Well-being
  - SDG 15 – Life on Land


## 🧠 Tech Stack

| Layer           | Tools & Technologies                      |
|----------------|-------------------------------------------|
| Mobile App      | Java, Android SDK, XML UI                 |
| Backend         | Python Flask API                          |
| ML Integration  | Scikit-learn, Crop Recommendation Model   |
| Database        | Firebase (Realtime DB + Authentication)  |
| Weather Data    | OpenWeatherMap API                        |
| IDE             | Android Studio                            |
| Version Control | Git, GitHub                               |

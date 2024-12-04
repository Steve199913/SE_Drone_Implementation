package patterns;

import java.util.Random;

public class Weather {
    private float temperature;
    private float humidity;
    private String forecast;

    public Weather() {
        this.temperature = 0;
        this.humidity = 0;
        this.forecast = "";
    }

    public float getTemperature() {
        return temperature;
    }

    public float getHumidity() {
        return humidity;
    }

    public String getForecast() {
        return forecast;
    }

    public void checkWeather() {
        Random random = new Random();
        this.temperature = 10 + random.nextFloat() * 35;
        this.humidity = 10 + random.nextFloat() * 90;
        String[] forecasts = {"Sunny", "Rainy", "Cloudy", "Stormy", "Windy"};
        this.forecast = forecasts[random.nextInt(forecasts.length)];
    }
    public String alertFarmer() {
        StringBuilder alert = new StringBuilder();

        if (forecast.equalsIgnoreCase("Rainy")) {
            alert.append("Rain is expected. Protect your crops!\n");
        }
        if (temperature > 35) {
            alert.append("High temperature detected. Consider irrigation.\n");
        }
        if (humidity < 20) {
            alert.append("Low humidity levels. Soil might be dry.\n");
        }
        if (alert.length() == 0) {
            alert.append("Weather conditions are normal. No immediate action needed.");
        }

        return alert.toString();
    }
}

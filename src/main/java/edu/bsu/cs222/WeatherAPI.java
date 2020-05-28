package edu.bsu.cs222;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import org.junit.Assert;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

class WeatherAPI {

    private static String temperature;
    private static String chanceOfRain;
    private static String precipitationType;
    private static String precipitationIntensity = "none";

    void APIAccess() {
        URL url = null;
        try {
            url = new URL("http://dataservice.accuweather.com/forecasts/v1/hourly/1hour/348735?apikey=xSSRJLx8k07bFu9ynLzKDFU6f9WCnxrM");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        URLConnection connection = null;
        try {
            Assert.assertNotNull(url);
            connection = url.openConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        InputStream in = null;
        try {
            Assert.assertNotNull(connection);
            in = connection.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Assert.assertNotNull(in);
        Reader reader = new InputStreamReader(in);
        JsonParser jsonParser = new JsonParser();
        JsonElement rootElement = jsonParser.parse(reader);
        JsonArray jsonArray = (JsonArray) jsonParser.parse(String.valueOf(rootElement));
        Assert.assertNotNull(jsonArray);
        String jsonString = jsonArray.toString();
        stringParser(jsonString);
    }

    private void stringParser(String jsonString) {
        int tempIndex = jsonString.indexOf("Value");
        temperature = jsonString.substring(tempIndex + 7, tempIndex + 11);

        int precProbability = jsonString.indexOf("Probability");
        chanceOfRain = jsonString.substring(precProbability + 13, precProbability + 15) + " percent";

        int precType = jsonString.indexOf("tationType");
        String precipitationTypeStarter = jsonString.substring(precType + 13, precType + 15);
        switch (precipitationTypeStarter) {
            case "Ra":
                precipitationType = "Rain";
                break;
            case "Sl":
                precipitationType = "Sleet";
                break;
            case "Ha":
                precipitationType = "Hail";
                break;
            case "Sn":
                precipitationType = "Snow";
                break;
            default:
                precipitationType = "None";
                break;
        }

        int precIntensity = jsonString.indexOf("Intensity");
        String precipitationIntensityStarter = jsonString.substring(precIntensity + 12, precIntensity + 14);
        if (precipitationIntensityStarter.equals("Li")) {
            precipitationIntensity = "Light";
        }
        if (precipitationIntensityStarter.equals("He")) {
            precipitationIntensity = "Heavy";
        }
        if (precipitationIntensityStarter.equals("Me")) {
            precipitationIntensity = "Medium";
        }
    }

    String printTemp() {
        System.out.println(temperature);
        return "The temperature is " + temperature + " degrees fahrenheit";
    }

    String printChanceOfRain() {
        System.out.println(chanceOfRain);
        return "The chance of precipitation is: " + chanceOfRain;
    }

    String printPrecipitationType() {
        System.out.println(precipitationType);
        return "The type of precipitation is: " + precipitationType;
    }

    String printPrecipitationIntensity() {
        System.out.println(precipitationIntensity);
        return "The intensity of the precipitation is: " + precipitationIntensity;
    }
}
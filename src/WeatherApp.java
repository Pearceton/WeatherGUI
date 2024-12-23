import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

public class WeatherApp {

    public static String temperatureUnit = "fahrenheit";
    public static String windspeedUnit = "mph";

    //Get weather data for location given
    public static JSONObject getWeatherData(String locationName){
        //Get location via geolocation API
        JSONArray locationData = getLocationData(locationName);

        //Get latitude and longitude data
        JSONObject location = (JSONObject) locationData.get(0);
        double latitude = (Double) location.get("latitude");
        double longitude = (Double) location.get("longitude");

        String temperatureUnit = WeatherApp.temperatureUnit;


        //Build the API request using the location values
        String urlString = "https://api.open-meteo.com/v1/forecast?" +
                "latitude=" + latitude + "&longitude=" + longitude +
                "&hourly=temperature_2m,relative_humidity_2m,weather_code,wind_speed_10m&temperature_unit="
                + temperatureUnit + "&wind_speed_unit=" + windspeedUnit + "&precipitation_unit=inch";

        try {
            //Call the API for response
            HttpURLConnection conn = getAPIResponse(urlString);
            //Check the response status
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }
            //Store JSON data
            StringBuilder JsonResult = new StringBuilder();
            Scanner scnr = new Scanner(conn.getInputStream());
            while (scnr.hasNext()){
                //Read and store the data into the StringBuilder
                JsonResult.append(scnr.nextLine());
            }

            scnr.close();
            conn.disconnect();

            //Parse the data
            JSONParser parser = new JSONParser();
            JSONObject resultJsonObj = (JSONObject) parser.parse(String.valueOf(JsonResult));

            //Get hourly weather data
            JSONObject hourly = (JSONObject) resultJsonObj.get("hourly");
            //Get index of current hour
            JSONArray time = (JSONArray) hourly.get("time");
            int index = findIndexOfCurrentTime(time);

            //Get temperature
            JSONArray temperatureData = (JSONArray) hourly.get("temperature_2m");
            double temperature = (double) temperatureData.get(index);

            //Get the weather code
            JSONArray weather_code = (JSONArray) hourly.get("weather_code");
            String weatherCondition = convertWeatherCode((long) weather_code.get(index));

            //Get humidity
            JSONArray relativeHumidity = (JSONArray) hourly.get("relative_humidity_2m");
            long humidity = (long) relativeHumidity.get(index);

            //Get windspeed
            JSONArray windspeedData = (JSONArray) hourly.get("wind_speed_10m");
            double windspeed = (double) windspeedData.get(index);

            //Weather object that will be accessed in frontend
            JSONObject weatherData = new JSONObject();
            weatherData.put("temperature", temperature);
            weatherData.put("weather_condition", weatherCondition);
            weatherData.put("humidity", humidity);
            weatherData.put("windspeed", windspeed);

            return weatherData;

        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }//end getWeatherData method

    public static JSONArray getLocationData(String locationName){
        //Replaces spaces with "+" in order to follow the API's format
        locationName = locationName.replaceAll(" ", "+");

        //Construct url to call onto API depending on the user input
        String urlString = "https://geocoding-api.open-meteo.com/v1/search?name=" + locationName + "&count=10&language=en&format=json";

        try {
            //Call the API to get a response
            HttpURLConnection conn = getAPIResponse(urlString);

            //Check response status; If 200 is returned, connection was successful
            if(conn.getResponseCode() != 200){
                System.out.println("Error: Could not connect to API");
                return null;
            }else {
                //Store results from the API
                StringBuilder resultJson = new StringBuilder();
                Scanner scnr = new Scanner(conn.getInputStream());

                //Read and then store JSON data into string builder
                while(scnr.hasNext()){
                    resultJson.append(scnr.nextLine());
                }

                scnr.close();
                conn.disconnect();

                //Parse JSON string into an object
                JSONParser parser = new JSONParser();
                JSONObject resultsJsonObj = (JSONObject) parser.parse(String.valueOf(resultJson));

                //Get list of results of location data that the API generated from the location name given
                JSONArray locationData = (JSONArray) resultsJsonObj.get("results");
                return locationData;
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        //Location couldn't be found
        return null;
    }//end getLocationData method

    private static HttpURLConnection getAPIResponse(String urlString){
        try{
            //Attempt to create a connection with the API
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //Use get method to request
            conn.setRequestMethod("GET");
            //Connect to the API
            conn.connect();
            return conn;
        }catch(IOException e){
            e.printStackTrace();
        }

        //Returns null if connection could not be made
        return null;
    }//end getAPIResponse method

    private static int findIndexOfCurrentTime(JSONArray timeList){

        String currentTime = getCurrentTime();

        //Iterate through the time list from the API until you get the matched current time
        for(int i = 0; i < timeList.size(); i++){
            String time = (String) timeList.get(i);
            if(time.equalsIgnoreCase(currentTime)){
                return i;
            }
        }

        return 0;
    }//end findIndexOfCurrentTime method

    public static String getCurrentTime(){
        //Get current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();

        //Format date to what is needed from API
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH':00'");
        //Format and print the date and time
        String formattedDateTime = currentDateTime.format(formatter);
        return formattedDateTime;

    }

    //Convert the weather code to something readable
    private static String convertWeatherCode(long weathercode){
        String weatherCondition = "";
        if(weathercode == 0L){
            //Clear
            weatherCondition = "Clear";
        }else if(weathercode <= 3L && weathercode > 0L){
            //Cloudy
            weatherCondition = "Cloudy";
        }else if((weathercode >= 51L && weathercode <= 67L) ||  (weathercode >= 80L && weathercode <= 99L)){
            //Rain
            weatherCondition = "Rain";
        }else if(weathercode >= 71L && weathercode <= 77L){
            //Snow
            weatherCondition = "Snow";
        }

        return weatherCondition;
    }//end convertWeatherCode method



    // Set temperature unit globally (celsius or fahrenheit)
    public static void setTemperatureUnit(String temperatureUnit) {
        WeatherApp.temperatureUnit = temperatureUnit;
    }//end setTemperatureUnit method

    // Get the current temperature unit (celsius or fahrenheit)
    public static String getTemperatureUnit() {
        return WeatherApp.temperatureUnit;
    }//end getTemperatureUnit method

    //Set windspeed unit globally (kmh or mph)
    public static void setWindspeedUnit(String windspeedUnit) {
        WeatherApp.windspeedUnit = windspeedUnit;
    }//end setWindspeedUnit

    //Get current windspeed unit (kmh or mph)
    public static String getWindspeedUnit() {
        return WeatherApp.windspeedUnit;
    }//end getWindspeedUnit

}//end WeatherApp class

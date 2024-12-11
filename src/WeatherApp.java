import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class WeatherApp {

    //Get weather data for location given
    public static JSONObject getWeatherData(String locationName){
        //Get location via geolocation API
        JSONArray locationData = getLocationData(locationName);

    }//end getWeatherData method

    private static JSONArray getLocationData(String locationName){
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

        //Returns null if connection could not not be made
        return null;
    }//end getAPIResponse method
}//end WeatherApp class

import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherGUI extends JFrame{
    private JSONObject weatherData;
    WeatherApp weatherApp = new WeatherApp();

    public WeatherGUI(){
        //Set frame of the GUI
        super("Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 750);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);//Do not allow user to resize the GUI

        addGUIItems();
    }//end WeatherGUI class

    private void addGUIItems() {
        //Add components to the GUI

        //Add a menu bar

        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Settings");
        JMenu temperatureMenu = new JMenu("Temperature");
        JMenu windspeedMenu = new JMenu("Wind Speed");
        //Create menu items
        JMenuItem celsius = new JMenuItem("Celsius");
        JMenuItem fahrenheit = new JMenuItem("Fahrenheit");
        JMenuItem mph = new JMenuItem("MPH");
        JMenuItem kmh = new JMenuItem("KMH");
        JMenuItem exit =  new JMenuItem("Exit");
        //Add to file menu
        fileMenu.add(temperatureMenu);
        fileMenu.add(windspeedMenu);
        menuBar.add(fileMenu);
        //Add to edit menu
        temperatureMenu.add(celsius);
        temperatureMenu.add(fahrenheit);
        windspeedMenu.add(mph);
        windspeedMenu.add(kmh);
        fileMenu.add(exit);
        //Add action listeners
        exit.addActionListener(e -> System.exit(0));
        setVisible(true);
        setJMenuBar(menuBar);
        // Temperature Unit Listeners
        celsius.addActionListener(e -> {
            weatherApp.setTemperatureUnit("celsius");
            updateTemperatureDisplay();
        });

        fahrenheit.addActionListener(e -> {
            weatherApp.setTemperatureUnit("fahrenheit");
            updateTemperatureDisplay();
        });
        mph.addActionListener(e -> {
            weatherApp.setWindspeedUnit("mph");
            updateWindspeedDisplay();
        });
        kmh.addActionListener(e -> {
            weatherApp.setWindspeedUnit("kmh");
            updateWindspeedDisplay();
        });


        //Add search bar text field
        JTextField searchTextField = new JTextField();
        //Set visualization of text field
        searchTextField.setBounds(15, 15, 350, 40);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        //Add weather image
        JLabel weatherConditionLabel = new JLabel(loadImage("src/assets/cloudy.png"));//Set default image to cloudy
        weatherConditionLabel.setBounds(0, 125, 450, 210);
        add(weatherConditionLabel);

        //Add weather description
        JLabel weatherDescriptionText = new JLabel("Cloudy");
        weatherDescriptionText.setBounds(0, 225, 450, 210);

        //Add text for temperature
        JLabel temperatureText = new JLabel("60 F");//Set default for temperature to 60 degrees
        temperatureText.setBounds(0, 350, 450, 54);
        temperatureText.setFont(new Font("Dialog", Font.BOLD, 48));
        temperatureText.setHorizontalAlignment(SwingConstants.CENTER);//center the temperature text
        add(temperatureText);

        //Add description for weather condition
        JLabel weatherConditionDescription = new JLabel("Cloudy");//Default condition
        weatherConditionDescription.setBounds(0, 405, 450, 36);
        weatherConditionDescription.setFont(new Font("Dialog", Font.PLAIN, 32));
        weatherConditionDescription.setHorizontalAlignment(SwingConstants.CENTER);
        add(weatherConditionDescription);

        //Add image for humidity
        JLabel humidityImage = new JLabel(loadImage("src/assets/humidity.png"));
        humidityImage.setBounds(0, 490, 70, 75);
        add(humidityImage);

        //Add text for humidity
        JLabel humidityText = new JLabel("<html><b>Humidity</b> 100%</html>");//Set default for humidity %
        humidityText.setBounds(90, 500, 85 ,55);
        humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(humidityText);

        //Add image for windspeed
        JLabel windSpeedImage = new JLabel(loadImage("src/assets/windspeed.png"));
        windSpeedImage.setBounds(220, 500, 75, 60);
        add(windSpeedImage);

        //Add text for windspeed
        String windSpeedUnitText = weatherApp.getWindspeedUnit();
        JLabel windspeedHeader = new JLabel("Windspeed");
        windspeedHeader.setBounds(310, 490, 85 ,55);
        windspeedHeader.setFont(new Font("Dialog", Font.BOLD, 16));
        JLabel windSpeedText = new JLabel("15" + windSpeedUnitText);//Set default for wind speed mph
        windSpeedText.setBounds(310, 510, 85 ,55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windspeedHeader);
        add(windSpeedText);

        //Add image for precipitation
        JLabel precipitationImage = new JLabel(loadImage("src/assets/precipitation.png"));
        precipitationImage.setBounds(0, 575, 70, 75);
        add(precipitationImage);

        //Add text for precipitation
        JLabel precipitationText = new JLabel("<html><b>Precipitation</b> 100%</html>");
        precipitationText.setBounds(90, 575, 95 ,55);
        precipitationText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(precipitationText);

        //Add image for UV index
        JLabel uvIndexImage = new JLabel(loadImage("src/assets/uvindex.png"));
        uvIndexImage.setBounds(220, 575, 75, 75);
        add(uvIndexImage);

        //Add text for uv index
        JLabel uvIndexText = new JLabel("<html><b>UV Index\n</b> 1</html>");
        uvIndexText.setBounds(310, 575, 80 ,55);
        uvIndexText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(uvIndexText);

        //Add search icon
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//Change cursor to a hand when hovering over button
        searchButton.setBounds(375, 15, 40, 40);
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Get location from user
                String userInput = searchTextField.getText();
                //Validate user input to correctly take in input
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                weatherData = WeatherApp.getWeatherData(userInput);

                //Update GUI

                //Update image
                String weatherCondition = (String) weatherData.get("weather_condition");

                //Update image corresponding to condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionLabel.setIcon(loadImage("src/assets/clear.png"));
                        weatherDescriptionText.setText("Clear");
                        break;
                    case "Cloudy":
                        weatherConditionLabel.setIcon(loadImage("src/assets/cloudy.png"));
                        weatherDescriptionText.setText("Cloudy");
                        break;
                    case "Rain":
                        weatherConditionLabel.setIcon(loadImage("src/assets/rain.png"));
                        weatherDescriptionText.setText("Rain");
                        break;
                    case "Snow":
                        weatherConditionLabel.setIcon(loadImage("src/assets/snow.png"));
                        weatherDescriptionText.setText("Snow");
                        break;
                }

                //Update temperature text
                double temperature = (double) weatherData.get("temperature");
                if(weatherApp.getTemperatureUnit().equals("celsius")){
                    temperatureText.setText(temperature + "C");
                }
                else{
                    temperatureText.setText(temperature + "F");
                }


                //Update weather condition text
                weatherConditionDescription.setText(weatherCondition);

                //Update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                //Update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                if(weatherApp.getWindspeedUnit().equals("mph")){
                    windSpeedText.setText(windspeed + "mph");
                }
                else{
                    windSpeedText.setText(windspeed + "kmh");
                }

                //Update precipitation percentage
                long precipiation = (long) weatherData.get("precipitation_probability");
                precipitationText.setText("<html><b>Precipitation</b> " + precipiation + "%</html>");

                //Update UV index
                double uvIndex = (double) weatherData.get("uv_index");
                uvIndexText.setText("<html><b>UV Index\n</b>" + uvIndex + "</html>");
            }
        });
        add(searchButton);

    }//end addGUIItems method

    //Add images into GUI
    private ImageIcon loadImage(String resourcePath) {

        try{
            //Read the image file given, and return image to component
            BufferedImage image = ImageIO.read(new File(resourcePath));
            return new ImageIcon(image);
        }catch(IOException e) {
            e.printStackTrace();
        }//end catch
        System.out.println("Could not find resource.");
        return null;
    }//end loadImage method


    private void updateTemperatureDisplay() {
        if (weatherData != null) {
            // Update the temperature and weather condition based on the fetched data
            JLabel temperatureText = (JLabel) getComponentAt(0, 350); // getComponentAt will get the temperature label
            JLabel weatherConditionDescription = (JLabel) getComponentAt(0, 405);
            temperatureText.setText(weatherData.get("temperature").toString() + " " + weatherApp.getTemperatureUnit().toUpperCase());
            weatherConditionDescription.setText(weatherData.get("weather_condition").toString());
        }
    }//end updateTemperatureDisplay

    private void updateWindspeedDisplay() {
        if(weatherData != null) {
            JLabel windspeedText = (JLabel) getComponentAt(310, 500);
            JLabel weatherConditionDescription = (JLabel) getComponentAt(0, 405);
            windspeedText.setText(weatherData.get("windspeed").toString() + " " + weatherApp.getWindspeedUnit().toUpperCase());
            weatherConditionDescription.setText(weatherData.get("weather_condition").toString());
        }
    }//end updateWindspeedDisplay

    //private void

}//end WeatherGUI
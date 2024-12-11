import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class WeatherGUI extends JFrame{
    public WeatherGUI(){
        //Set frame of the GUI
        super("Weather App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(450, 650);
        setLocationRelativeTo(null);
        setLayout(null);
        setResizable(false);//Do not allow user to resize the GUI

        addGUIItems();
    }//end WeatherGUI class

    private void addGUIItems() {
        //Add components to the GUI

        //Add search bar text field
        JTextField searchTextField = new JTextField();
        //Set visualization of text field
        searchTextField.setBounds(15, 15, 350, 40);
        searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
        add(searchTextField);

        //Add search icon
        JButton searchButton = new JButton(loadImage("src/assets/search.png"));
        searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));//Change cursor to a hand when hovering over button
        searchButton.setBounds(375, 15, 40, 40);
        add(searchButton);

        //Add weather image
        JLabel weatherConditionLabel = new JLabel(loadImage("src/assets/cloudy.png"));//Set default image to cloudy
        weatherConditionLabel.setBounds(0, 125, 450, 210);
        add(weatherConditionLabel);

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
        JLabel windSpeedText = new JLabel("<html><b>Windspeed</b> 15mph</html>");//Set default for wind speed mph
        windSpeedText.setBounds(310, 500, 85 ,55);
        windSpeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
        add(windSpeedText);
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
}//end WeatherGUI class
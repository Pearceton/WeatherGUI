import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                //new WeatherGUI().setVisible(true);
                //System.out.println(WeatherApp.getLocationData("Tokyo"));
                //System.out.println(WeatherApp.getCurrentTime());
            }
        });

    }//end main method
}//end Main class

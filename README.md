<h1>Java Weather GUI</h1>
<p>This Java-based Weather GUI provides users real-time weather data for a given location. The user enters a location, and the program returns weather data back to the user with a GUI. </p>
<p align="center">
  <img src="https://github.com/Pearceton/WeatherGUI/blob/master/WeatherGUIScreenshot.png" align="center">
</p>
<h2>How It's Made</h2>
  <p>When program is launched, the user is prompted by a text field to enter in a location to get weather data for said location. A call to the location API is used to get the longitude and latitude coordinates
  temperature, humidity, and wind speed.</p>
<h3>Technologies Used</h3>
<ul>
  <li><a href="https://openjdk.org/projects/jdk/23/">Java 23</a>: Most recent version Java when program was written</li>
  <li><a href="https://code.google.com/archive/p/json-simple/">JSON Simple</a>: Parser for JSON data</li>
  <li><a href="https://docs.oracle.com/javase/8/docs/api/java/net/HttpURLConnection.html">HTTPURLConnection</a>: Makes HTTP requests to get data from APIs</li>
</ul>
<h2>Updates</h2>
<ul>
  <li><strong>v1.0.1</strong>- Added the option for the user to select between Fahrenheit and Celsius</li>
  <li><strong>v1.0.2.</strong>- Added the option for the user to select beween Mph and Kmh for windspeed</li>
</ul>

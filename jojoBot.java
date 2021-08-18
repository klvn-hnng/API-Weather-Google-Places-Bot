import com.google.gson.JsonObject;
import org.jibble.pircbot.PircBot;

import java.io.IOException;

public class jojoBot extends PircBot {
    public jojoBot() {
        this.setName("jojoBot");
    }
    
    private void sendMessage(String channel, String sender, String message) {
    	// Respond function.
        final int MAX_LINE_LEN = 200;
        if (message.length() > MAX_LINE_LEN) {
            for (int i = 0; i < message.length(); i += MAX_LINE_LEN) {
                if (i + MAX_LINE_LEN < message.length())
                    sendMessage(channel, sender + ": " + message.substring(i, MAX_LINE_LEN));
                else
                    sendMessage(channel, sender + ": " + message.substring(i));
            }
        } else {
            sendMessage(channel, sender + ": " + message);
        }
    }
    
    protected void onJoin(String channel, String sender, String login, String hostname) {
        // Sends a message when a user joins.
        if (!sender.equals(getName()))
            sendMessage(channel, sender, "Hello, my name is jojoBot. I was created by Speedwagon Foundation."
            					   + " My program has only two functions, which includes finding the weather"
            					   + " and finding places near UTD. How may I assist you? Please type jojobot"
            					   + " to continue...");
    }

    protected void onMessage(String channel, String sender, String login, String hostname, String message) {
        // User input calls the functions. 
    	String userMessage = message;
        if (userMessage.equals("jojoBot")) {
            instructionMessage(channel, sender);
        } else if  (userMessage.startsWith("jojoBot ")) {
            String[] tokens = userMessage.split(" ");

            if (tokens.length < 2)
                instructionMessage(channel, sender);
            else if (tokens[1].equals("weather"))
                weatherMessage(channel, sender, tokens);
            else if (tokens[1].equals("place"))
                googleSearchMessage(channel, sender, tokens);
            else
            	sendMessage(channel, sender, "Command not found.");
        }
    }
    
    private void instructionMessage(String channel, String sender) {
    	// Sends out instructions.
    	sendMessage(channel, sender, "**Instructions**"
        		+ " For weather: jojoBot weather <city name>."
        		+ " For places nearby: jojoBot place <name>."
        		+ " (examples of names: Chinese, McDonald's, Mexican, Chipotle, etc.)"
        		+ " Please input the exact name to work!");
    }

    private void weatherMessage(String channel, String sender, String[] tokens) {
    	// Displays secondary instructions.
        if (tokens.length < 3) {
        	sendMessage(channel, sender, "Please provide a city.");
        }
        try {
                getWeatherMessage(channel, sender, openWeatherApi.getWeatherAtCity(tokens[2]));

        } catch (IOException error) {
        	sendMessage(channel, sender, "Weather is unavailable right now, please try again later!");
            System.out.println(error.getMessage());
        }

    }

    private void getWeatherMessage(String channel, String sender, JsonObject weatherData) {
    	// Gets the information from the API request.
        String description = weatherData.getAsJsonArray("weather").get(0).getAsJsonObject()
                .get("description").getAsString();
        double temp = weatherData.getAsJsonObject("main").get("temp").getAsDouble();
        String cityName = weatherData.get("name").getAsString();

        sendMessage(channel, sender, "Weather is " + description + " and the temperature is " +
                temp + " C in " + cityName + ".");
    }
    
    private void googleSearchMessage(String channel, String sender, String[] tokens) {
    	// Displays secondary instructions
        if (tokens.length < 3) {
        	sendMessage(channel, sender, "Please provide a name.");
        }
        try {
                getGoogleSearchMessage(channel, sender, googleMapSearchApi.getMapSearchApi(tokens[2]));
        } catch (IOException error) {
        	sendMessage(channel, sender, "The place is unavailable right now, please try again later!");
            System.out.println(error.getMessage());
        }
    }

    private void getGoogleSearchMessage(String channel, String sender, JsonObject googleSearchData) {
    	// Gets the information from the API request.
    	String name = googleSearchData.getAsJsonArray("results").get(0).getAsJsonObject()
    			.get("name").getAsString();
    	String address = googleSearchData.getAsJsonArray("results").get(0).getAsJsonObject()
    			.get("vicinity").getAsString();
    	sendMessage(channel, sender, name + " near UTD is " + address + ".");
    }    

}
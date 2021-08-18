import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;

public class openWeatherApi {
		private static String myAPIurl = "http://api.openweathermap.org/data/2.5/weather?q=";
		private static String myApiToken = "&units=metric&APPID=4a6fd841bce84636c716392f775a4be2";
		
	    static JsonObject getWeatherAtCity(String city) throws IOException {
	        return ApiControl.getJson(new URL(myAPIurl + city.trim() + myApiToken));
	    }

}
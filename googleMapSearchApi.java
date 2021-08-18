import com.google.gson.JsonObject;
import java.io.IOException;
import java.net.URL;

public class googleMapSearchApi {
	private static String myAPIurl = "https://maps.googleapis.com/maps/api/place/nearbysearch/json";
	private static String location = "?location=32.983716,-96.752076";
	private static String radius = "&radius=5000";
	private static String nameOutline = "&name=";
	private static String myApiToken = "&key=AIzaSyCrnZ2wPKpB52mLFw9ZM6JtKouObS6Wy44";
	
    static JsonObject getMapSearchApi(String name) throws IOException {
        return ApiControl.getJson(new URL(myAPIurl + location + radius + nameOutline + name.trim() + myApiToken));
    }
}

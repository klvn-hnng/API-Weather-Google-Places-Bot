import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

class ApiControl {
	static JsonObject getJson(URL url) throws IOException{
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("Accept", "application/json");
        String res = ApiControl.bufferToString(new BufferedReader(new InputStreamReader(con.getInputStream())));
        return new JsonParser().parse(res).getAsJsonObject();		
	}
	private static String bufferToString(BufferedReader in) throws IOException{
        StringBuilder response = new StringBuilder();
        String responseSingle;
        while ((responseSingle = in.readLine()) != null) {
            response.append(responseSingle);
        }
        in.close();
        return response.toString();	
        }
}

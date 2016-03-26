package one.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONObject;


public class MyClient {

	public static void main(String[] args) {
		 try {
             URL url = new URL(" http://localhost:8282/two/data/person");
             System.out.println(url);
             URLConnection connection = url.openConnection();
             System.out.println(connection);
             connection.setDoOutput(true);
             connection.setRequestProperty("Content-Type", "application/json");
             connection.setConnectTimeout(5000);
             connection.setReadTimeout(5000);
             OutputStreamWriter out = new OutputStreamWriter(connection.getOutputStream());
           //  out.write(JSONObject.toString());
             out.close();

             BufferedReader in = new BufferedReader(new InputStreamReader(
                     connection.getInputStream()));

             while (in.readLine() != null) {
             }
             System.out.println("\nREST Service Invoked Successfully..");
             in.close();
         } catch (Exception e) {
             System.out.println("\nError while calling REST Service");
             System.out.println(e);
         }
        
         
      

	}

}

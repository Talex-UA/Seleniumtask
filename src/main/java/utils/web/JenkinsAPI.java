package utils.web;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class JenkinsAPI {

    BufferedReader reader;
    StringBuilder stringBuilder;
    String urlParameters;

    public void sendPostRequest(String serverURL) {

        try {
            URL url = new URL(serverURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setReadTimeout(5000);
            connection.getInputStream();

//            connection.setDoOutput(true);
//            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
////            wr.writeBytes(urlParameters);
//            wr.flush();
//            wr.close();
////
            int responseCode = connection.getResponseCode();
            System.out.println("\nSending 'POST' request to URL : " + url);
//////            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);
////
//            reader = new BufferedReader(
//                    new InputStreamReader(connection.getInputStream()));
//            String inputLine;
//
//            stringBuilder=new StringBuilder();
//
//            while ((inputLine = reader.readLine()) != null) {
//                stringBuilder.append(inputLine);
//            }
//            reader.close();
//
//            System.out.println(stringBuilder.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getHTTP(String serverURL) {
        URL url = null;
        BufferedReader reader;
        StringBuilder stringBuilder;

        try {
            url = new URL(serverURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(5000);
            connection.connect();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            stringBuilder = new StringBuilder();

            String line = null;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }

            System.out.println(stringBuilder.toString());

            reader.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

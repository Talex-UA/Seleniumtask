package utils.web;

import org.apache.commons.codec.binary.Base64;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import static utils.Generators.*;

public class JenkinsAPI{

    protected final Logger log = LogManager.getLogger(this);
    String myCredentials = getExistingUserName()+":"+getPassword();

    /**
     * @param projectName - just pass your current project name without any changes in letters
     * @param actionTrigger - pass respective trigger for each action:
     * <ul>
     *     <li> "build" to build project
     *     <li> "doDelete" to delete project
     *     <li> "disable" to disable project
     *     <li> "enable" to enable
     * <ul/>
     */
    public void sendPostRequest(String projectName, String actionTrigger) {

        String finalURL="";

        if (actionTrigger.equals("build")){
            finalURL = "http://seltr-kbp1-1.synapse.com:8080/job/"+projectName.replaceAll("\\s+","%20")+"/build?delay=0sec";
        } else if(actionTrigger.equals("doDelete")){
            finalURL = "http://seltr-kbp1-1.synapse.com:8080/job/"+projectName.replaceAll("\\s+","%20")+"/doDelete";
        } else if(actionTrigger.equals("disable")) {
            finalURL = "http://seltr-kbp1-1.synapse.com:8080/job/"+projectName.replaceAll("\\s+","%20")+"/disable";
        } else if(actionTrigger.equals("enable")) {
            finalURL = "http://seltr-kbp1-1.synapse.com:8080/job/"+projectName.replaceAll("\\s+","%20")+"/enable";
        } else {
            System.out.println("Wrong trigger: "+actionTrigger);
            return;
        }

        try {
            URL url = new URL(finalURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Basic " + new String(new Base64().encode(myCredentials.getBytes())));
            connection.setReadTimeout(5000);
            log.info("\nSending 'POST' request to URL : " + url);
            connection.getInputStream();

            int responseCode = connection.getResponseCode();
            log.info("Response Code : " + responseCode);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

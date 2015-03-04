package utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CommonMethods {

    private static WebDriver wd;

    public static void sendKeysToField(WebElement element,String input){
        if (input!=null){
            if (input.isEmpty()) {
                element.clear();
            } else {
                element.sendKeys(input);
            }
        }
    }

    private static List<String> pingGoogle(int numberTimes, int wait){
        List<String> pingOutput = new ArrayList<>();
        try {
            Process pingGoogle = Runtime.getRuntime().exec("ping 173.194.113.201" + " -w " + wait + " -n " + numberTimes);
            Scanner scanner = new Scanner(pingGoogle.getInputStream());
            while (scanner.hasNext()){
                pingOutput.add(scanner.nextLine());
            }
            pingGoogle.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return pingOutput;
    }

    public static String getIPfromPingGoogle(){
        String foundIP="";
        for (String current:pingGoogle(1,1)){
            if (current.matches(".+\\d+.+")){
                foundIP=current.substring(current.indexOf(" ")+1,current.indexOf(" ")+16);
                break;
            }
        }
        return foundIP;
    }
}

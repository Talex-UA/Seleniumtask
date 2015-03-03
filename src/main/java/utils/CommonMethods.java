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
        if (input==null){

        } else if (input.isEmpty()){
            element.clear();
        } else {
            if (!element.getText().isEmpty()){
                element.clear();
            }
            element.sendKeys(input);
        }
    }

    public static List<String> pingGoogle(int numberTimes, int wait){
        List<String> pingOutput = new ArrayList<>();
        try {
            Process pingGoogle = Runtime.getRuntime().exec("ping 173.194.113.201" + " -w " + wait + " -n " + numberTimes);
            Scanner scanner = new Scanner(pingGoogle.getInputStream()).useDelimiter("\\A");
            String scannerOutput = "";
            while (scanner.hasNext()){
                scannerOutput = scanner.next();
            }
            pingGoogle.waitFor();
            String [] scannerOutputToArray = scannerOutput.split("\\r?\\n");
            for (int i = 0; i < scannerOutputToArray.length; i++) {
                pingOutput.add(scannerOutputToArray[i]);
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return pingOutput;
    }
}

package utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Generators {

    public static String getExistingUserName(){
        return "OleksandrA";
    }
    public static String getPassword(){
        return "qwerty1234";
    }
    public static String getExistingUserFulName(){
        return "Alexander Agafonov";
    }
    public static String getExistingProjectName(){
        return "ExistingProject";
    }

    public static String generateNewUserName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-Name "+dateFormat.format(date);
    }
    public static String generateNewUserFullName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-FullName "+dateFormat.format(date);
    }
    public static String generateNewUserEmail(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        return "test_email"+dateFormat.format(date)+"@testemail.com";
    }
    public static String generateNewProjectName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-Project "+dateFormat.format(date);
    }

    public static String generateNewProjectDescription(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "This is my new project "+dateFormat.format(date);
    }

    public static String getBuildToken(){
        return "build_remotely";
    }

    public static String getBatchCommand(){
        return "ping 173.194.113.201 -w 1 -n 10";
    }

    public static String generateString(int length) {
        String alphabet = "qwertyuiopasdfghjklzxcvbnm";
        String result = "";
        for (int i = 0; i < length; i++) {
            Random random = new Random();
            result += alphabet.charAt(random.nextInt(alphabet.length()));
        }
        return result;
    }
}

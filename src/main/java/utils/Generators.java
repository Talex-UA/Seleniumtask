package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        return "Test-Name "+generateTimeAndDate();
    }
    public static String generateNewUserFullName(){
        return "Test-FullName "+generateTimeAndDate();
    }
    public static String generateNewUserEmail(){
        return "test_email"+generateTimeAndDate()+"@testemail.com";
    }
    public static String generateNewProjectName(){
        return "Test-Project "+generateTimeAndDate();
    }

    public static String generateNewProjectDescription(){
        return "This is my new project "+generateTimeAndDate();
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

    public static String generateTimeAndDate(){
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH-mm-ss");
        return LocalDateTime.now().format(dateTimeFormatter);
    }
}

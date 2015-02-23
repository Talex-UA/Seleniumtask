package JenkinsDashboard.Pages;

import java.text.SimpleDateFormat;
import java.util.Date;

public interface Generators {

    public default String getExistingUserName(){
        return "OleksandrA";
    }
    public default String getPassword(){
        return "qwerty1234";
    }
    public default String getExistingUserFulName(){
        return "Alexander Agafonov";
    }
    public default String getExistingProjectName(){
        return "ExistingProject";
    }

    public default String generateNewUserName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-Name "+dateFormat.format(date);
    }
    public default String generateNewUserFullName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-FullName "+dateFormat.format(date);
    }
    public default String generateNewUserEmail(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd:HH-mm-ss");
        return "test_email"+dateFormat.format(date)+"@testemail.com";
    }
    public default String generateNewProjectName(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "Test-Project "+dateFormat.format(date);
    }

    public default String generateNewProjectDescription(){
        Date date = new Date() ;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        return "This is my new project "+dateFormat.format(date);
    }
}

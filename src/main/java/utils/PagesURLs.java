package utils;

import static utils.Generators.*;

public class PagesURLs {

    public static final String HOME_PAGE = "http://seltr-kbp1-1.synapse.com:8080/";
    public static final String BUILDS_PAGE = "http://seltr-kbp1-1.synapse.com:8080/job/"+getExistingProjectName()+"/";
    public static final String FREESTYLE_PROPERTIES_PAGE= "http://seltr-kbp1-1.synapse.com:8080/job/" + getExistingProjectName() + "/configure";
    public static final String LOG_IN_PAGE = "http://seltr-kbp1-1.synapse.com:8080/login?from=%2F";
    public static final String NEW_ITEM_PAGE = "http://seltr-kbp1-1.synapse.com:8080/view/All/newJob";
    public static final String PEOPLE_PAGE = "http://seltr-kbp1-1.synapse.com:8080/asynchPeople/";
    public static final String PROJECT_PAGE = "http://seltr-kbp1-1.synapse.com:8080/job/" + getExistingProjectName() + "/";
    public static final String SIGN_UP_PAGE = "http://seltr-kbp1-1.synapse.com:8080/signup";
    public static final String USER_HOME_PAGE = "http://seltr-kbp1-1.synapse.com:8080/";
    public static final String USER_PAGE = "http://seltr-kbp1-1.synapse.com:8080/user/"+getExistingUserName()+"/";
    public static final String WRONG_LOGIN_PAGE = "http://seltr-kbp1-1.synapse.com:8080/loginError";
    public static final String WRONG_SIGNUP_PAGE = "http://seltr-kbp1-1.synapse.com:8080/securityRealm/createAccount";
}

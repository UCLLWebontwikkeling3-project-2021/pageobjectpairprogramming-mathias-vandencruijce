//////////////////////////////////////////
// Alexandre Vryghem - r0747249         //
// Mathias Van den Cruijce - r0785409   //
//////////////////////////////////////////

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.PageFactory;

import static org.junit.Assert.*;

public class LoginTest {

    private WebDriver driver;

    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\mathi\\Documents\\2e jaar informatica\\Web3\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @After
    public void clean() {
        //driver.quit();
    }

    @Test
    public void test_Login_LoginWithValidUseridAndMatchingPassword_LoginSuccessful() {
        RegisterPage registerPage = PageFactory.initElements(driver, RegisterPage.class);
        String userid = generateRandomUseridInOrderToRunTestMoreThanOnce("bananakin");
        registerPage.addValidPerson(userid,"Anakin", "Skywalker", "anakin.skywalker@hotmail.com", "t");

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid(userid);
        homePage.setPassword("t");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasWelcomeMessage("Welcome Anakin!"));
    }

    @Test
    public void test_Login_LoginWithEmptyUserid_LoginUnsuccessful() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid("");
        homePage.setPassword("t");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasErrorMessage("No valid userid/password"));
    }

    @Test
    public void test_Login_LoginWithEmptyPassword_LoginUnsuccessful() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid("ahsokaTrano");
        homePage.setPassword("");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasErrorMessage("No valid userid/password"));
    }

    @Test
    public void test_Login_LoginWithInvalidPassword_LoginUnsuccessful() {
        RegisterPage registerPage = PageFactory.initElements(driver, RegisterPage.class);
        String userid = generateRandomUseridInOrderToRunTestMoreThanOnce("BobaFat");
        registerPage.addValidPerson(userid,"Boba", "Fett", "boba.fett@hotmail.com", "t");

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid(userid);
        homePage.setPassword("1324");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasErrorMessage("No valid userid/password"));
    }

    @Test
    public void test_Login_LoginWithInvalidUserid_LoginUnsuccessful() {
        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid("nonStarWars@email.com");
        homePage.setPassword("t");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasErrorMessage("No valid userid/password"));
    }

    @Test
    public void test_Login_LoginWithValidUseridAndMatchingPasswordRegardlessOfUseridCasing_LoginSuccessful() {
        RegisterPage registerPage = PageFactory.initElements(driver, RegisterPage.class);
        String userid = generateRandomUseridInOrderToRunTestMoreThanOnce("JabbaDabbaDoo");
        registerPage.addValidPerson(userid, "Jabba", "The Hutt", "Jabba.TheHutt@hotmail.com", "t");

        HomePage homePage = PageFactory.initElements(driver, HomePage.class);
        homePage.setUserid(userid.toLowerCase());
        homePage.setPassword("t");
        homePage.submitLogIn();

        assertEquals("Home",homePage.getTitle());
        assertTrue(homePage.hasWelcomeMessage("Welcome Jabba!"));
    }

    private String generateRandomUseridInOrderToRunTestMoreThanOnce(String component) {
        int random = (int) (Math.random() * 1000 + 1);
        return random + component;
    }
}

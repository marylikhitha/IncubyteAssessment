package stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import java.util.concurrent.TimeUnit;

public class stepd {
    WebDriver driver;
    String username = "";
    String password = "";
    int navigate = 0;
    int logged = 0;

    @Given(": User navigate to gmail page")
    public void user_navigate_to_gmail_page() throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        // throw new io.cucumber.java.PendingException();
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver");
        driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.get("https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin");
        //driver.manage().window().maximize();
        try {
            Assert.assertEquals("Gmail", driver.getTitle());
            System.out.println("Successfully navigated to Gmail Signin");
            navigate = navigate + 1;
            // driver.close();
        } catch (Throwable pageNavigationError) {
            System.out.println("Didn't navigate to gmail signin");
            navigate = 0;
        }

    }

    @Given(": User log in using valid username as {string} and password as {string}")
    public void user_log_in_using_valid_username_as_and_password_as(String string, String string2) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        if (navigate == 1) {
            String username = string;
            String password = string2;
            // gmail login
            driver.findElement(By.name("identifier")).sendKeys(username);
            driver.findElement(By.id("identifierNext")).click();
            Thread.sleep(3000);
            driver.findElement(By.name("password")).sendKeys(password);
            Thread.sleep(2000);
            driver.findElement(By.id("passwordNext")).click();
            Thread.sleep(4000);
            try {
                Assert.assertEquals(driver.getCurrentUrl().contains("#inbox"), true);
                System.out.println("Successfully logged in to Gmail");
                logged = logged + 1;
            } catch (Exception e) {
                System.out.println("Not able to successfully login to Gmail");
                logged = 0;
            }

        }
        // System.out.println(logged);


    }
    @When(": User sends an email to the recipient as {string} with subject as {string} and body as {string}")
    public void user_sends_an_email_to_the_recipient_as_with_subject_as_and_body_as(String string, String string2, String string3) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        if (logged == 1) {
            Thread.sleep(2000);
            //System.out.println(username + password);
            String torecipient = string;
            String subj = string2;
            String body = string3;
            driver.findElement(By.xpath("//div[contains(text(),'Compose')]")).click();
            Thread.sleep(2000);
            //add to recepient
            driver.findElement(By.xpath("//textarea[@name='to']")).sendKeys(torecipient);

            Thread.sleep(2000);
            //add subject
            driver.findElement(By.xpath("//input[@name='subjectbox']")).sendKeys(subj);
            Thread.sleep(2000);
            //add body
            driver.findElement(By.xpath("//div[@aria-label='Message Body']")).sendKeys(body);
            Thread.sleep(3000);
            //click on send to send the email
            driver.findElement(By.xpath("//div[text()='Send']")).click();
            Thread.sleep(1000);
        }
    }

    @Then(": User should be able to see email in sent folder with subject as {string}")
    public void user_should_be_able_to_see_email_in_sent_folder_with_subject_as(String string) throws InterruptedException {
        // Write code here that turns the phrase above into concrete actions
        //throw new io.cucumber.java.PendingException();
        driver.findElement(By.xpath("//a[@aria-label='Sent']")).click();
        Thread.sleep(3000);
        driver.findElement(By.xpath("//span[@class='bog']//span[text()='" + string + "']")).click();
        if (driver.findElement(By.xpath("//span[@class='bog']//span[text()='" + string + "']")) != null) {
            System.out.println("Email sent sucessfully!!!");
        } else {
            System.out.println("Failed to send email !!!");
        }
        Thread.sleep(4000);
        driver.close();
        driver.quit();
    }
}

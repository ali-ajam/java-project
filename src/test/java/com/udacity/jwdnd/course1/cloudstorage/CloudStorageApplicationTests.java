package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	//private static WebDriver driver;
	String firstName = "Ali";
	String lastName = "Ajam";
	String username = "aliajam";
	String password = "12345";

	String filePath = "/cloudstorage/files/test_upload";

	String title = "Test";
	String description = "This is a test";

	String newTitle = "Test2";
	String newDescription = "This is a update test";

	String credentialUrl = "www.google.com";
	String credentialUsername = "testCredential";
	String credentialPassword = "1234";

	String newCredentialUrl = "www.google.com";
	String newCredentialUsername = "testCredential2";
	String newCredentialPassword = "12345";


	@LocalServerPort
	private int port;
	private static WebDriver driver;
	private WebDriverWait wait;
	private JavascriptExecutor js;


	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {

		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
		this.wait = new WebDriverWait(driver, 60);
		this.js = (JavascriptExecutor) driver;
	}

	@AfterAll
	public static  void afterAll() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
		driver = null;
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getSignupPage() {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Login", driver.getTitle());
	}

	@Test
	public void sendNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.sendNote(title,description);

	}

	@Test
	public void updateNote() throws InterruptedException {

		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.updateNote(newTitle, newDescription);
	}

	@Test
	public void deleteNote() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);
		homePage.sendNote(title,description);

		Thread.sleep(1000);
		homePage.deleteNote();
	}

	@Test
	public void sendCredentialTest() {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);

		homePage.sendCredential(credentialUrl, credentialUsername, credentialPassword);
	}

	@Test
	public void updateCredentialTest() {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);

		homePage.sendCredential(credentialUrl, credentialUsername, credentialPassword);

		homePage.updateCredential(newCredentialUrl, newCredentialUsername, newCredentialPassword);
	}

	@Test
	public void deleteCredentialTest() throws InterruptedException {
		driver.get("http://localhost:" + this.port + "/signup");

		SignupPage signupPage = new SignupPage(driver);
		signupPage.signup(firstName, lastName, username, password);

		Assertions.assertEquals("Sign Up", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/login");

		LoginPage loginPage = new LoginPage(driver);
		loginPage.login(username, password);

		Assertions.assertEquals("Home", driver.getTitle());

		driver.get("http://localhost:" + this.port + "/home");

		HomePage homePage = new HomePage(driver);

		homePage.sendCredential(credentialUrl, credentialUsername, credentialPassword);
		Thread.sleep(3000);

		homePage.deleteCredential();

	}
}

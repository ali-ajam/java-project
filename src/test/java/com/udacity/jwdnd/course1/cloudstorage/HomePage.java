package com.udacity.jwdnd.course1.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    private final JavascriptExecutor js;
    private final WebDriverWait wait;

    //Notes
    @FindBy(id = "nav-notes-tab")
    private WebElement navNote;

    @FindBy(id = "add-note")
    private WebElement addNote;

    @FindBy(id = "note-title")
    private WebElement noteTitle;

    @FindBy(id = "note-description")
    private WebElement noteDescription;

    @FindBy(id = "save-note")
    private WebElement saveNote;

    @FindBy(id = "edit-note")
    private WebElement editNote;

    @FindBy(id = "delete-note")
    private WebElement deleteNote;

    //Credentials
    @FindBy(id="nav-credentials")
    private WebElement navCredentials;

    @FindBy(id = "add-credential")
    private WebElement addCredential;

    @FindBy(id = "credential-url")
    private WebElement credentialUrl;

    @FindBy(id = "credential-username")
    private WebElement credentialUsername;

    @FindBy(id = "credential-password")
    private WebElement credentialPassword;

    @FindBy(id = "save-credential")
    private WebElement credentialSubmit;

    @FindBy(id = "edit-cred")
    private WebElement editCredential;

    @FindBy(id = "delete-credential")
    private WebElement deleteCredential;


    public HomePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        wait = new WebDriverWait(driver, 1);
        js = (JavascriptExecutor) driver;
    }

    //Notes
    public void sendNote(String title, String description) {
        js.executeScript("arguments[0].click();", addNote);
        js.executeScript("arguments[0].value='" + title + "';", noteTitle);
        js.executeScript("arguments[0].value='" + description + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void updateNote(String newTitle, String newDescription) {
        js.executeScript("arguments[0].click();", addNote);
        js.executeScript("arguments[0].value='" + newTitle + "';", noteTitle);
        js.executeScript("arguments[0].value='" + newDescription + "';", noteDescription);
        js.executeScript("arguments[0].click();", saveNote);
    }

    public void deleteNote() {
        WebElement noteTable = wait.until(webDriver -> webDriver.findElement(By.id("note-table")));
        js.executeScript("arguments[0].click();", deleteNote);
    }

    //Credential

    public void sendCredential(String credentialUrl, String credentialUsername, String credentialPassword) {
        js.executeScript("arguments[0].click();", navCredentials);
        js.executeScript("arguments[0].click();", addCredential);
        js.executeScript("arguments[0].value='" + credentialUrl + "';", credentialUrl);
        js.executeScript("arguments[0].value='" + credentialPassword + "';", credentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public void updateCredential(String newCredentialUrl, String newCredentialUsername, String newCredentialPassword) {
        js.executeScript("arguments[0].click();", navCredentials);
        js.executeScript("arguments[0].click();", addCredential);
        js.executeScript("arguments[0].value='" + newCredentialUrl + "';", newCredentialUrl);
        js.executeScript("arguments[0].value='" + newCredentialPassword + "';", newCredentialPassword);
        js.executeScript("arguments[0].click();", credentialSubmit);
    }

    public void deleteCredential() {
        WebElement deleteCredential = wait.until(webDriver -> webDriver.findElement(By.xpath("//a[@id='nav-credentials-tab']")));
        js.executeScript("arguments[0].click();", deleteCredential);
    }
}

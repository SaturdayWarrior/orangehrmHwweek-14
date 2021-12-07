package com.orangehrmlive.demo.testsuite;

import com.orangehrmlive.demo.pages.*;
import com.orangehrmlive.demo.testbase.TestBase;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class UsersTest extends TestBase {

    HomePage homePage;
    LoginPage loginPage;
    AddUserPage addUserPage;
    AdminPage adminPage;
    DashboardPage dashboardPage;
    ViewSystemUsersPage viewSystemUsersPage;

    @BeforeMethod//(groups = {"smoke","sanity" ,"regression"})
    public void inIt() {
        homePage = new HomePage();
        loginPage = new LoginPage();
        addUserPage = new AddUserPage();
        adminPage = new AdminPage();
        dashboardPage = new DashboardPage();
        viewSystemUsersPage = new ViewSystemUsersPage();
    }

    @BeforeMethod
    @Parameters({"username", "password"})
    public void loginSuccessfully(String uname, String pword) {
        loginPage.enterTheUsernameInTheUsernameField(uname);
        loginPage.enterThePasswordInThePasswordField(pword);
        loginPage.clickOnTheLoginButton();
    }

    @Test(priority = 1, groups = {"smoke", "regression"})
    @Parameters({"urole", "ufullname", "uusername", "ustatus", "upassword", "uconfirmPassword"})
    public void adminShouldAddUserSuccessfully(String role, String fullname, String username, String status, String password, String confirmPassword) {
        homePage.clickOnAdminTabFromHomePage();
        addUserPage.verifyTheSystemUsersWelcomeText();
        addUserPage.clickOnTheAddButton();
        addUserPage.verifyTheAddUserText();
        addUserPage.selectUserRole(role);
        addUserPage.putEmployeeNameInTheEmployeeNameField(fullname);
        addUserPage.putUserNameInTheUserNameField(username);
        addUserPage.selectUserStatus(status);
        addUserPage.enterPasswordInThePasswordField(password);
        addUserPage.enterConfirmationPassword(confirmPassword);
        addUserPage.clickOnTheSaveButton();
        addUserPage.verifyTheSuccessfullySavedMessage();
    }

    @Test(priority = 2, groups = {"sanity", "regression"})
    //@Test (groups = {"sanity", "regression"} )
    @Parameters({"uusername", "urole", "ustatus"})
    public void searchTheUserCreatedAndVerifyIt(String username, String role, String status) {
        homePage.clickOnAdminTabFromHomePage();
        viewSystemUsersPage.verifyTheSystemUsersWelcomeText();
        viewSystemUsersPage.putUserNameInTheUserNameField(username);
        viewSystemUsersPage.selectUserRole(role);
        viewSystemUsersPage.selectUserStatus(status);
        viewSystemUsersPage.clickOnTheSearchButton();

    }

    @Test(priority = 3, groups = {"sanity", "regression"})
    @Parameters({"uusername", "urole", "ustatus"})
    public void verifyThatAdminShouldDeleteTheUserSuccessFully(String username, String role, String status) {
        searchTheUserCreatedAndVerifyIt(username, role, status);
        viewSystemUsersPage.clickOnTheCheckBox();
        viewSystemUsersPage.clickOnTheDeleteButton();

    }

    @Test(priority = 4, groups = {"regression"})
    @Parameters({"uusername", "urole", "ustatus"})
    public void searchTheDeletedUserAndVerifyTheMessageNoRecordFound(String username, String role, String status) {
        searchTheUserCreatedAndVerifyIt(username, role, status);

    }
}

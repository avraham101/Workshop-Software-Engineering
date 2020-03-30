import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.*;

public class LogicManagerTest {

    private static LogicManager logicManager;

    @Before
    public void setUp() {
        logicManager = new LogicManager();
        logicManager.register("Admin","Admin");
    }

    @Test
    public void test() {
        testRegister();
        testLogin();
    }

    public void testRegister() {
        testAdmin();
        testRegisterSuccess();
        testRegisterFailWrongName();
    }

    //TODO MOVE THIS TO CLASS
    public void testAdmin() {
        //TODO
    }

    //TODO MOVE THIS TO CLASS
    public void testRegisterSuccess() {
        assertTrue(logicManager.register("Yuval","Sabag"));
    }

    //TODO MOVE THIS TO CLASS
    public void testRegisterFailWrongName() {
        assertFalse(logicManager.register("Admin","Admin2"));
    }

    public void testLogin() {
        testLoginFailWrongName();
        testLoginFailWrongPassword();
        testLoginSuccess();
        testLoginFailAlreadyUserLogged();
    }

    private void testLoginFailWrongName() {
        assertFalse(logicManager.login("Shlomi", "BAD TEACHER"));
    }

    private void testLoginFailWrongPassword() {
        assertFalse(logicManager.login("Yuval", "BAD TEACHER"));
    }

    private void testLoginSuccess() {
        assertTrue(logicManager.login("Yuval","Sabag"));
    }

    private void testLoginFailAlreadyUserLogged() {
        assertFalse(logicManager.login("Yuval","Sabag"));
    }


}
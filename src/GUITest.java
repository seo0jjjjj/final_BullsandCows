import ioManager.IOManager;
import user.User;
import window.LoginWindow;

public class GUITest {
    public static void main(String[] args) throws Exception {
        User u;
        LoginWindow lw = new LoginWindow(IOManager.getUserList());

    }
}

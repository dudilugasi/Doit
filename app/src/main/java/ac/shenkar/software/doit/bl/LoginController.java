package ac.shenkar.software.doit.bl;
import android.content.Context;
import ac.shenkar.software.doit.bl.LoginListener;
import ac.shenkar.software.doit.dal.DAO;
import ac.shenkar.software.doit.dal.IDataAccess;
import com.parse.ParseUser;

public class LoginController {
    IDataAccess dao;
    Context context;

    public LoginController(Context context) {
        dao = DAO.getInstance(context.getApplicationContext());
        this.context = context;
        dao.setLoginListener((LoginListener) context);
    }

    public static boolean isAdmin() {

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getBoolean("admin");
        } else {
            return false;
        }
    }

    public String getUserName() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            return currentUser.getString("username");
        } else {
            return "";
        }
    }

    public void login(String userName , String password) {
        dao.login(userName,password);
    }

    public boolean isLoggedIn() {
        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser != null) {
            return true;
        } else {
            return false;
        }
    }

    public static void logout() {
        ParseUser.logOut();
    }
}

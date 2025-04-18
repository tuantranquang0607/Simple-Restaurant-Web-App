package nbcc.restaurantteam6foxtrot.services;

import jakarta.servlet.http.HttpSession;
import nbcc.restaurantteam6foxtrot.entities.UserDetail;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService
{
    private static final String LOGIN_USER_ID = "LoginUserId";
    private static final String IS_USER_LOGGED_IN = "loggedIn";
    private final HttpSession session;
    private final UserService userService;

    public LoginServiceImpl(HttpSession session, UserService userService)
    {
        this.session = session;
        this.userService = userService;
    }

    @Override
    public boolean login(String username, String password)
    {
        if (userService.valid(username, password))
        {
            var user = userService.getUserByUsername(username);
            session.setAttribute(LOGIN_USER_ID, user.getId());
            session.setAttribute(IS_USER_LOGGED_IN, true);

            return true;
        }

        return false;
    }

    @Override
    public void logout()
    {
        session.removeAttribute(LOGIN_USER_ID);
        session.removeAttribute(IS_USER_LOGGED_IN);
    }

    @Override
    public boolean IsCurrenLoginValid()
    {
        var user = getLoggedInUser();

        return user != null;
    }

    public UserDetail getLoggedInUser()
    {
        Long userId = getLoggedInUserId();

        if (userId == null)
        {
            return null;
        }

        return userService.findUser(userId).orElse(null);
    }

    public Long getLoggedInUserId()
    {
        var userIdAttribute = session.getAttribute(LOGIN_USER_ID);

        if (userIdAttribute == null)
        {
            return null;
        }

        var userIdString = userIdAttribute.toString();

        try
        {
            return Long.parseLong(userIdString);
        }
        catch (NumberFormatException e)
        {
            return null;
        }
    }
}

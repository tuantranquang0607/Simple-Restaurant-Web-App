package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.UserDetail;

public interface LoginService
{
    boolean login(String username, String password);

    void logout();

    boolean IsCurrenLoginValid();

    UserDetail getLoggedInUser();

    Long getLoggedInUserId();
}

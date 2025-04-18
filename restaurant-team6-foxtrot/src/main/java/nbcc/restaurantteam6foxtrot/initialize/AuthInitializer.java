package nbcc.restaurantteam6foxtrot.initialize;

import nbcc.restaurantteam6foxtrot.config.environment.DefaultAdminConfig;
import nbcc.restaurantteam6foxtrot.services.UserService;
import org.springframework.stereotype.Component;

@Component
public class AuthInitializer
{
    private final UserService userService;

    private final DefaultAdminConfig defaultAdminConfig;

    public AuthInitializer(UserService userService, DefaultAdminConfig defaultAdminConfig)
    {
        this.userService = userService;
        this.defaultAdminConfig = defaultAdminConfig;
    }

    public void initializeAdminUser()
    {
        var adminUsername = defaultAdminConfig.getDefaultAdminUsername();
        if (adminUsername == null || adminUsername.isBlank())
        {
            return;
        }

        var exists = userService.usernameExists(adminUsername);
        if (exists)
        {
            return;
        }

        var adminPassword = defaultAdminConfig.getDefaultAdminPassword();
        var adminEmail = defaultAdminConfig.getDefaultAdminEmail();

        if (adminPassword == null || adminPassword.isBlank() || adminEmail == null || adminEmail.isBlank())
        {
            return;
        }

        if (userService.register(adminUsername, adminPassword, adminEmail) != null)
        {
            System.out.println("Admin user registered: " + adminUsername);
        }
        else
        {
            System.out.println("Admin user couldn't be registered: " + adminUsername);
        }
    }
}

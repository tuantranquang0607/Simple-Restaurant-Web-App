package nbcc.restaurantteam6foxtrot;

import nbcc.restaurantteam6foxtrot.initialize.AuthInitializer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class RestaurantTeam6FoxtrotApplication
{

    public static void main(String[] args)
    {
        var appContext = SpringApplication.run(RestaurantTeam6FoxtrotApplication.class, args);
        initialize(appContext);
    }

    public static void initialize(ApplicationContext applicationContext)
    {
        var authInitializer = applicationContext.getBean(AuthInitializer.class);

        try
        {
            authInitializer.initializeAdminUser();
        }
        catch (Exception e)
        {
            System.out.println("Failed to initialize admin user: " + e.getMessage());
        }
    }
}

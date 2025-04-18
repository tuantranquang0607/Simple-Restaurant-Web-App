package nbcc.restaurantteam6foxtrot.services;

import nbcc.restaurantteam6foxtrot.entities.UserDetail;
import nbcc.restaurantteam6foxtrot.repositories.UserDetailRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService
{
    private final UserDetailRepository userDetailRepository;
    private final HashingService hashingService;

    public UserService(UserDetailRepository userDetailRepository, HashingService hashingService)
    {
        this.userDetailRepository = userDetailRepository;
        this.hashingService = hashingService;
    }

    public UserDetail register(String username, String password, String email)
    {
        var exists =usernameExists(username);

        if (!exists)
        {
            var hashedPassword = hashingService.hash(password);

            var userDetail = new UserDetail(username, hashedPassword, email);

            return userDetailRepository.save(userDetail);
        }

        return null;
    }

    public boolean valid(String username, String password)
    {
        var user = userDetailRepository.getUserDetailByUsername(username);

        if (user == null)
        {
            return false;
        }

        return hashingService.valid(password, user.getPassword());
    }

    public UserDetail getUserByUsername(String username)
    {
        return userDetailRepository.getUserDetailByUsername(username);
    }

    public boolean usernameExists(String username)
    {
        return userDetailRepository.existsByUsername(username);
    }

    public Optional<UserDetail> findUser(Long userId)
    {
        if (userId == null)
        {
            return Optional.empty();
        }

        return userDetailRepository.findById(userId);
    }
}

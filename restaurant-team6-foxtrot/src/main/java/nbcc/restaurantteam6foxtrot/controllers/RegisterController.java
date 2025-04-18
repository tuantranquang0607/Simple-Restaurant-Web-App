package nbcc.restaurantteam6foxtrot.controllers;

import nbcc.restaurantteam6foxtrot.entities.UserDetail;
import nbcc.restaurantteam6foxtrot.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController
{
    private final UserService userService;

    public RegisterController(UserService userService)
    {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model)
    {
        model.addAttribute("userDetail", new UserDetail());
        return "register";
    }

    @PostMapping("/register")
    public String processRegister(@ModelAttribute("userDetail") UserDetail userDetail, Model model)
    {
        if (userService.usernameExists(userDetail.getUsername()))
        {
            model.addAttribute("error", "Username already exists");
            return "redirect:/register?error=exists";
        }

        UserDetail newUser = userService.register(
                userDetail.getUsername(),
                userDetail.getPassword(),
                userDetail.getEmail()
        );

        // Set a flag to indicate registration was successful
        model.addAttribute("registered", true);
        return "register";
    }
}

package nbcc.restaurantteam6foxtrot.controllers;

import nbcc.restaurantteam6foxtrot.services.LoginService;
import nbcc.restaurantteam6foxtrot.viewmodels.LoginViewModel;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class LoginController
{
    private final LoginService loginService;

    public LoginController(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @GetMapping("/login")
    public String login(Model model)
    {
        model.addAttribute("login", new LoginViewModel());

        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute("login") LoginViewModel loginViewModel)
    {
        if (loginService.login(loginViewModel.getUsername(), loginViewModel.getPassword()))
        {
            return "redirect:/"; // login successful
        }

        model.addAttribute("message", "Invalid login or password");

        return "login";
    }

    @PostMapping("/logout")
    public String logout()
    {
        loginService.logout();

        return "redirect:/";
    }
}

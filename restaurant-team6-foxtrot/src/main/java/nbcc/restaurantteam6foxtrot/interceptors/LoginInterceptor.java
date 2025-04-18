package nbcc.restaurantteam6foxtrot.interceptors;

import jakarta.annotation.Nonnull;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import nbcc.restaurantteam6foxtrot.services.LoginService;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor
{
    private final LoginService loginService;

    public LoginInterceptor(LoginService loginService)
    {
        this.loginService = loginService;
    }

    @Override
    public boolean preHandle(@Nonnull HttpServletRequest request, @Nonnull HttpServletResponse response, @Nonnull Object handler) throws Exception
    {
        if (loginService.IsCurrenLoginValid())
        {
            return true;
        }

        response.sendRedirect(request.getContextPath() + "/login");

        return false;
    }
}

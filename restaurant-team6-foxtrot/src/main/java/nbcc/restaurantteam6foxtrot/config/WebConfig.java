package nbcc.restaurantteam6foxtrot.config;

import nbcc.restaurantteam6foxtrot.interceptors.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer
{
    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor)
    {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(loginInterceptor).addPathPatterns("/menu/**");
        registry.addInterceptor(loginInterceptor).addPathPatterns("///**", "///**", "///**");
    }
}

package backend.Property_Sales_System.config;

import backend.Property_Sales_System.service.CustomUserDetailsService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import java.io.IOException;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        // Allow public access for feedback pages and API
                        .requestMatchers(
                                "/",
                                "/edit.html", "/feedback-list.html",
                                "/register/**", "/login", "/error", "/homePage/**",
                                "/css/**", "/js/**", "/images/**", "/favicon.ico"
                        ).permitAll()
                        .requestMatchers("/feedback/**", "/api/feedback/**").authenticated()
                        // everything else requires authentication
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("email")
                        .passwordParameter("password")
                        .successHandler(roleBasedSuccessHandler())
                        .permitAll()
                )
                .logout(l -> l
                        .logoutSuccessUrl("/")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                )
                .sessionManagement(session -> session
                        .maximumSessions(1)
                        .maxSessionsPreventsLogin(false)
                );

        return http.build();
    }

    /** ✅ Custom login redirect based on role */
    @Bean
    public SavedRequestAwareAuthenticationSuccessHandler roleBasedSuccessHandler() {
        return new SavedRequestAwareAuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request,
                                                HttpServletResponse response,
                                                Authentication authentication)
                    throws IOException, ServletException {
                String role = authentication.getAuthorities().iterator().next().getAuthority().toUpperCase();

                switch (role) {
                    case "TENANT", "ROLE_TENANT" -> setDefaultTargetUrl("/tenant/dashboard");
                    case "VENDOR", "ROLE_VENDOR" -> setDefaultTargetUrl("/vendor/dashboard");
                    case "BUYER", "ROLE_BUYER" -> setDefaultTargetUrl("/buyer/dashboard");
                    case "ADMIN", "ROLE_ADMIN" -> setDefaultTargetUrl("/admin/dashboard");
                    default -> setDefaultTargetUrl("/dashboard");
                }
                super.onAuthenticationSuccess(request, response, authentication);
            }
        };
    }
}
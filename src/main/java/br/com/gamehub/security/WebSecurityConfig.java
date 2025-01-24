package br.com.gamehub.security;

import br.com.gamehub.security.jwt.AuthEntryPointJWT;
import br.com.gamehub.security.jwt.AuthFilterToken;
import br.com.gamehub.security.jwt.JwtUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfig {

    private static final Logger logger = LoggerFactory.getLogger(WebSecurityConfig.class);

    private final AuthEntryPointJWT unauthorizedHandler;
    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    /**
     * Constructor for initializing WebSecurityConfig.
     *
     * @param unauthorizedHandler The handler for unauthorized access.
     * @param jwtUtils            The utility class for handling JWT tokens.
     * @param userDetailsService  The service that loads user details.
     */
    @Autowired
    public WebSecurityConfig(AuthEntryPointJWT unauthorizedHandler, JwtUtils jwtUtils,
            UserDetailsService userDetailsService) {
        this.unauthorizedHandler = unauthorizedHandler;
        this.jwtUtils = jwtUtils;
        this.userDetailsService = userDetailsService;
        logger.info("WebSecurityConfig initialized with AuthEntryPointJWT, JwtUtils, and UserDetailsService.");
    }

    /**
     * Bean definition for the PasswordEncoder.
     *
     * @return A new instance of BCryptPasswordEncoder to encode passwords.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        logger.debug("Initializing PasswordEncoder (BCryptPasswordEncoder).");
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean definition for the AuthenticationManager.
     *
     * @param authenticationConfiguration The configuration for authentication.
     * @return An AuthenticationManager to manage authentication.
     * @throws Exception If there is a problem creating the AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        logger.debug("Configuring AuthenticationManager.");
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Bean definition for the AuthFilterToken filter.
     *
     * @return A new instance of AuthFilterToken.
     */
    @Bean
    public AuthFilterToken authFilterToken() {
        logger.debug("Creating AuthFilterToken bean.");
        return new AuthFilterToken(jwtUtils, userDetailsService);
    }

    /**
     * Configures the SecurityFilterChain for the application.
     * Defines the request authorization rules, exception handling, and session
     * management.
     *
     * @param http The HttpSecurity instance used for configuring security settings.
     * @return The SecurityFilterChain to be applied to the HTTP requests.
     * @throws Exception If there is an issue with the security configuration.
     */
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        logger.info("Configuring SecurityFilterChain.");

        // Enable CORS with default settings
        http.cors(Customizer.withDefaults());

        // Disable CSRF protection as the app is stateless (using JWT)
        http.csrf(crsf -> crsf.disable())
                // Set up exception handling for unauthorized access
                .exceptionHandling(exption -> {
                    logger.info("Setting up exception handling with AuthEntryPointJWT.");
                    exption.authenticationEntryPoint(unauthorizedHandler);
                })
                // Configure session management to be stateless (using JWT)
                .sessionManagement(session -> {
                    logger.info("Configuring session management to be stateless.");
                    session.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                // Define authorization rules for different endpoints
                .authorizeHttpRequests(auth -> {
                    logger.info("Configuring authorization rules.");
                    auth.requestMatchers("/auth/**").permitAll() // Public routes
                            .requestMatchers("/usuarios/cadastrar").permitAll() // Public registration route
                            .requestMatchers("/admin/**").hasRole("ADMIN") // Only accessible by admins
                            .requestMatchers("/user/**").hasAnyRole("ADMIN", "COMMON") // Accessible by admins and
                                                                                       // common users
                            .requestMatchers("developers/**").permitAll() // Developers routes (testing)
                            .requestMatchers("games/**").permitAll() // Games routes (testing)
                            .requestMatchers("categories/**").permitAll() // Categories routes (testing)
                            .requestMatchers("platforms/**").permitAll() // Platforms routes (testing)
                            .requestMatchers("stores/**").permitAll() // Stores routes (testing)
                            .requestMatchers("store-games/**").permitAll() // Store games routes (testing)
                            .requestMatchers("discount-coupons/**").permitAll() // Discount coupon routes (testing)
                            .requestMatchers("active-discount-coupons/**").permitAll() // Active discount coupons routes
                                                                                       // (testing)
                            .requestMatchers("**").permitAll()
                            .anyRequest().authenticated(); // Any other request requires authentication
                });

        // Add the authentication filter (AuthFilterToken) to the filter chain
        logger.debug("Adding AuthFilterToken to the filter chain.");
        http.addFilterBefore(authFilterToken(), UsernamePasswordAuthenticationFilter.class);

        logger.info("SecurityFilterChain configured successfully.");
        return http.build();
    }
}

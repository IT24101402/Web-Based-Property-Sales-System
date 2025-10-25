package backend.Property_Sales_System.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbConnectionChecker implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    // Spring automatically injects the JdbcTemplate, which uses the configured DataSource
    @Autowired
    public DbConnectionChecker(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    /**
     * This method runs once the application context is loaded.
     */
    @Override
    public void run(String... args) {
        try {
            // Execute a simple query to test the database connection
            Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);

            if (result != null && result.equals(1)) {
                System.out.println(" **SUCCESS:** Database connection verified! MySQL is running and accessible.");
            } else {
                // This path is highly unlikely if the connection succeeded, but it's safe to include
                System.out.println("⚠ **WARNING:** Database connection succeeded, but the test query failed.");
            }
        } catch (Exception e) {
            // If the connection fails (wrong credentials, MySQL is down, etc.), an exception is thrown.
            System.err.println(" **FATAL ERROR:** Failed to connect to the database!");
            System.err.println("Error Details: " + e.getMessage());
            // Optional: Exit the application on a critical failure
            // System.exit(1);
        }
    }
}
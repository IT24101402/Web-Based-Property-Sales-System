package backend.Property_Sales_System.util;

import backend.Property_Sales_System.model.User;

/**
 * Utility class providing helper methods for User entity.
 */
public class UserUtil {

    /**
     * Returns formatted user info (Username + Email).
     */
    public static String getUserInfo(User user) {
        if (user == null) {
            return "No user data available";
        }

        return "User Info: [Username: " + user.getUsername() +
                ", Email: " + user.getEmail() + "]";
    }

    /**
     * Validates the user's email format (basic check).
     */
    public static boolean isValidEmail(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }
        return user.getEmail().contains("@");
    }

    /**
     * Returns a masked version of the user's password.
     */
    public static String maskPassword(User user) {
        if (user == null || user.getPassword() == null) {
            return "";
        }
        return "*".repeat(user.getPassword().length());
    }
}

package backend.Property_Sales_System.util;

import PropertyManagment.propertyease.backend.model.User;

public class UserUtil {

    // Example utility to get formatted user info
    public static String getUserInfo(User user) {
        if (user == null) {
            return "No user data available";
        }

        // ✅ Use getName() instead of getUsername()
        return "User Info: [Name: " + user.getName() +
                ", Email: " + user.getEmail() + "]";
    }

    // Example: validate user email format
    public static boolean isValidEmail(User user) {
        if (user == null || user.getEmail() == null) {
            return false;
        }
        return user.getEmail().contains("@");
    }

    // Example: mask user password (optional helper)
    public static String maskPassword(User user) {
        if (user == null || user.getPassword() == null) {
            return "";
        }
        return "*".repeat(user.getPassword().length());
    }
}

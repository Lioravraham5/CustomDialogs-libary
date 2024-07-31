package com.example.customdialogs.RegisterDialog;

import android.util.Patterns;

import java.util.regex.Pattern;

public class FormValidator {
    // Validate email
    public static boolean isValidEmail(String email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    // Validate password (at least 8 characters, one uppercase, one lowercase, one number)
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z]).{8,}$";
        Pattern pattern = Pattern.compile(passwordPattern);
        return pattern.matcher(password).matches();
    }

    // Validate phone number
    public static boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && Patterns.PHONE.matcher(phoneNumber).matches();
    }

    // Validate non-empty field
    public static boolean isNonEmpty(String text) {
        return text != null && !text.trim().isEmpty();
    }

    // Validate name (only letters, spaces, and hyphens)
    public static boolean isValidName(String name) {
        if (name == null) return false;
        String namePattern = "^[a-zA-Z\\s-]+$";
        Pattern pattern = Pattern.compile(namePattern);
        return pattern.matcher(name).matches();
    }

    // Validate address (allow only letters, hyphens, numbers, and spaces)
    public static boolean isValidAddress(String address) {
        if (address == null)
            return false;

        String addressPattern = "^[a-zA-Z0-9\\s\\-]+$";
        Pattern pattern = Pattern.compile(addressPattern);
        return pattern.matcher(address).matches();
    }
}

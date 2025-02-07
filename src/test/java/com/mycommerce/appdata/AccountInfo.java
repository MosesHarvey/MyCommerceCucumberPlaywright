package com.mycommerce.appdata;

public class AccountInfo {
    private static String name;
    private static String email;
    private static String password;
    public static void AccountInfo(String name, String email, String password) {
        AccountInfo.name = name;
        AccountInfo.email = email;
        AccountInfo.password = password;
    }
    public static String getName() {
        return name;
    }
    public static String getEmail() {
        return email;
    }
    public static String getPassword() {
        return password;
    }

    public static void setName(String name) {
        AccountInfo.name = name;
    }

    public static void setEmail(String email) {
        AccountInfo.email = email;
    }

    public static void setPassword(String password) {
        AccountInfo.password = password;
    }
}

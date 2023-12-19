package com.imshashwat745.cycfit;

import java.util.regex.*;

public class EmailValidator {
    private static final String THAPAR_EDU_PATTERN =
            "^([_A-Za-z0-9-+]+\\.?[_A-Za-z0-9-+]+@(thapar.edu))$";

    private static final Pattern pattern = Pattern.compile(THAPAR_EDU_PATTERN);

    public static boolean isThaparEmail(String email) {
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}

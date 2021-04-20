package com.test.view.statement.api.util;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class StatementDateFormatter {

    private Pattern STATEMENT_DATE_PATTERN = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");

    public boolean matches(String date) {
        return STATEMENT_DATE_PATTERN.matcher(date).matches();
    }
}

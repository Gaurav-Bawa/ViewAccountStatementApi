package com.test.view.statement.api.constant;

public class StatementConstants {

    private StatementConstants(){

    }
    public static final String ID = "id";
    public static final String FROM_DATE = "fromDate";
    public static final String TO_DATE = "toDate";
    public static final String FROM_AMOUNT = "fromAmount";
    public static final String TO_AMOUNT = "toAmount";
    public static final String MSG_NO_DATA_FOUND = "NO DATA FOUND";
    public static final String BAD_REQUEST = "BAD REQUEST : ";
    public static final String IS_BLANK = " IS BLANK";
    public static final String INVALID_DATE_FORMAT  = "INVALID_DATE_FORMAT : SUPPORTED FORMAT (yyyy-MM-dd)";
    public static final String ACCESS_DENIED = "UNAUTHORIZED : 401";
    public static final String QUERY_ATTRIBUTE = "QUERY ATTRIBUTE";
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ADMIN = "admin";
    public static final String ROLE_USER = "USER";
    public static final String USER = "user";
    public static final String LOG_STR = "ViewAccountStatementApi : ";
    public static final String HTML_LOGIN = "<table style=\"width: 2%;\" border=\"2\" cellpadding=\"2\">\n" +
            "<tbody>\n" +
            "<tr>\n" +
            "<td>Login Success</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>/account/{id}</td>\n" +
            "<td>Get request to get the statement for id for 3 months. Example : http://localhost:8080/account/3</td>\n" +
            "</tr>\n" +
            "<tr>\n" +
            "<td>/statement/parameter?id=<?>&fromDate=<?>&toDate=<?>&fromAmount=<?>&toAmount=<?></td>\n" +
            "<td>Get request to get the statement for range of amount and range of date for particular id Example : http://localhost:8080/statement/parameter?id=5&fromDate=2011-01-01&toDate=2012-12-31&fromAmount=100&toAmount=200</td>\n" +
            "</tr>\n" +
            "</tbody>\n" +
            "</table>";
}

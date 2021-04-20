package com.test.view.statement.api;

import com.test.view.statement.api.constant.StatementConstants;
import com.test.view.statement.api.model.ErrorResponse;
import com.test.view.statement.api.model.StatementResponse;
import com.test.view.statement.api.resource.ViewAccountStatementResource;
import com.test.view.statement.api.security.configurer.SecurityConfigurer;
import com.test.view.statement.api.service.ViewAccountStatementService;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@SpringBootTest
class ViewAccountStatementApiApplicationTests {

    @Autowired
    private ViewAccountStatementResource viewAccountStatementResource;

    @Autowired
    private SecurityConfigurer securityConfigurer;

    @Mock
    private ViewAccountStatementService service;

    @Test
    void getStatementByID() {
        //DB Test : test to fetch statement for last 3 months
        List<StatementResponse> responseList = (List<StatementResponse>) viewAccountStatementResource.getStatementByID(3L).getBody();
        assertTrue(!ObjectUtils.isEmpty(responseList) && !responseList.isEmpty());
    }

    @Test
    void getStatementByAmountRange() {
        //DB Test : test to fetch statement for range of amount
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_AMOUNT, "100");
        map.put(StatementConstants.TO_AMOUNT, "200");
        List<StatementResponse> responseList = (List<StatementResponse>) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(responseList) && !responseList.isEmpty());
    }

    @Test
    void getStatementByDateRange() {
        //DB Test : test to fetch statement for range of date
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_DATE, "2011-01-01");
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        List<StatementResponse> responseList = (List<StatementResponse>) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(responseList) && !responseList.isEmpty());
    }

    @Test
    void getStatementByDateAndAmountRange() {
        //DB Test : test to fetch statement for range of amount and date
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_AMOUNT, "100");
        map.put(StatementConstants.TO_AMOUNT, "200");
        map.put(StatementConstants.FROM_DATE, "2011-01-01");
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        List<StatementResponse> responseList = (List<StatementResponse>) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(responseList) && !responseList.isEmpty());
    }

    @Test
    void invalidDate() {
        //date validation : test to validate date format
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_AMOUNT, "100");
        map.put(StatementConstants.TO_AMOUNT, "200");
        map.put(StatementConstants.FROM_DATE, "2011/01/01"); // passing invalid date format
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        ErrorResponse errorResponse = (ErrorResponse) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(errorResponse) && errorResponse.getMessage().equals(StatementConstants.BAD_REQUEST + StatementConstants.INVALID_DATE_FORMAT));
    }

    @Test
    void invalidDateRange() {
        //date validation : test to validate date range -> fromDate is not passed
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_AMOUNT, "100");
        map.put(StatementConstants.TO_AMOUNT, "200");
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        ErrorResponse errorResponse = (ErrorResponse) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(errorResponse) && errorResponse.getMessage().equals(StatementConstants.BAD_REQUEST + StatementConstants.FROM_DATE + StatementConstants.IS_BLANK));
    }

    @Test
    void invalidAmountRange() {
        //amount validation : test to validate amount range -> fromAmount is not passed
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.TO_AMOUNT, "200");
        map.put(StatementConstants.FROM_DATE, "2011-01-01");
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        ErrorResponse errorResponse = (ErrorResponse) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(errorResponse) && errorResponse.getMessage().equals(StatementConstants.BAD_REQUEST + StatementConstants.FROM_AMOUNT + StatementConstants.IS_BLANK));
    }

    @Test
    void invalidID() {
        //date validation : test to validate date range -> fromDate is not passed
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "9");
        ErrorResponse errorResponse = (ErrorResponse) viewAccountStatementResource.getStatement(map).getBody();
        assertTrue(!ObjectUtils.isEmpty(errorResponse) && errorResponse.getMessage().equals(StatementConstants.MSG_NO_DATA_FOUND));
    }

    @Test
    void validateUsers() {
        //validate loaded user
        assertTrue(securityConfigurer.userDetailsService().loadUserByUsername(StatementConstants.ADMIN).getUsername().equals(StatementConstants.ADMIN) && securityConfigurer.userDetailsService().loadUserByUsername(StatementConstants.USER).getUsername().equals(StatementConstants.USER));
    }

}

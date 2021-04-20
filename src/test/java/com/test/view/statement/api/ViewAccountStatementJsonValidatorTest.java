package com.test.view.statement.api;

import com.test.view.statement.api.constant.StatementConstants;
import com.test.view.statement.api.model.StatementResponse;
import com.test.view.statement.api.resource.ViewAccountStatementResource;
import com.test.view.statement.api.service.ViewAccountStatementService;
import com.test.view.statement.api.util.StatementDateFormatter;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ViewAccountStatementResource.class)
public class ViewAccountStatementJsonValidatorTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    private ViewAccountStatementService service;

    @MockBean
    private ViewAccountStatementResource resource;

    @MockBean
    private StatementDateFormatter dateFormatter;

    @Test
    @WithMockUser(username = StatementConstants.ADMIN, roles = StatementConstants.ROLE_ADMIN)
    public void validateGetByIdJSON() throws Exception {
        Mockito.when(resource.getStatementByID(3L)).thenReturn(ResponseEntity.ok().body(Arrays.asList(new StatementResponse("3", "current", "24.01.2021", "600"))));

        mockMvc.perform(MockMvcRequestBuilders
                .get("/account/3")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", is("3")))
                .andExpect(jsonPath("$[0].accountType", is("current")))
                .andExpect(jsonPath("$[0].datefield", is("24.01.2021")))
                .andExpect(jsonPath("$[0].amount", is("600")));
    }


    @Test
    @WithMockUser(username = StatementConstants.ADMIN, roles = StatementConstants.ROLE_ADMIN)
    public void validateGetByParamJSON() throws Exception {
        Map<String, String> map = new HashMap();
        map.put(StatementConstants.ID, "5");
        map.put(StatementConstants.FROM_AMOUNT, "100");
        map.put(StatementConstants.TO_AMOUNT, "200");
        map.put(StatementConstants.FROM_DATE, "2011-01-01"); // passing invalid date format
        map.put(StatementConstants.TO_DATE, "2012-12-31");
        Mockito.when(resource.getStatement(map)).thenReturn(ResponseEntity.ok().body(Arrays.asList(new StatementResponse("5", "current", "24.01.2021", "600"))));


        mockMvc.perform(MockMvcRequestBuilders
                .get("/statement/parameter")
                .param(StatementConstants.ID, "5")
                .param(StatementConstants.FROM_AMOUNT, "100")
                .param(StatementConstants.TO_AMOUNT, "200")
                .param(StatementConstants.FROM_DATE, "2011-01-01")
                .param(StatementConstants.TO_DATE, "2012-12-31")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].accountNumber", is("5")))
                .andExpect(jsonPath("$[0].accountType", is("current")))
                .andExpect(jsonPath("$[0].datefield", is("24.01.2021")))
                .andExpect(jsonPath("$[0].amount", is("600")));
    }
}

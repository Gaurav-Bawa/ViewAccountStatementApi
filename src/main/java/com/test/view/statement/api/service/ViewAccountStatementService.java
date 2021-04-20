package com.test.view.statement.api.service;

import com.test.view.statement.api.constant.StatementConstants;
import com.test.view.statement.api.model.StatementResponse;
import com.test.view.statement.api.repository.AccountRepository;
import com.test.view.statement.api.util.StatementUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class ViewAccountStatementService {

    @Autowired
    private AccountRepository accountRepository;

    public List<StatementResponse> getStatementById(Long id, int months) {
        log.info(StatementConstants.LOG_STR + "Sending request to get statement for an id for 3 months");
        return StatementUtils.hashAccountID(StatementUtils.statementByLastNumberOfMonth(accountRepository.findByAccountId(id), months));
    }

    public List<StatementResponse> getStatementById(Long id) {
        log.info(StatementConstants.LOG_STR + "Sending request to get statement for an id for all months");
        return StatementUtils.hashAccountID(accountRepository.findByAccountId(id));
    }

    public List<StatementResponse> getStatementByAmount(Long id, String fromAmount, String toAmount) {
        log.info(StatementConstants.LOG_STR + "Sending request to get statement for range of amount for id " + id + " between " + fromAmount + " and " + toAmount);
        return StatementUtils.hashAccountID(accountRepository.findByAmount(id, fromAmount, toAmount));
    }

    public List<StatementResponse> getStatementByDate(List<StatementResponse> statementResponses, String fromDate, String toDate) {
        log.info(StatementConstants.LOG_STR + "Filtering statement for range of date between " + fromDate + " and " + toDate);
        return StatementUtils.hashAccountID(StatementUtils.statementByMonths(statementResponses, fromDate, toDate));
    }
}

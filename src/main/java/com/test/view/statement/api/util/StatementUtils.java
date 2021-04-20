package com.test.view.statement.api.util;

import com.test.view.statement.api.constant.StatementConstants;
import com.test.view.statement.api.model.ErrorResponse;
import com.test.view.statement.api.model.StatementResponse;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Log4j2
public class StatementUtils {

    @Autowired
    private StatementDateFormatter dateFormatter;

    public static List<StatementResponse> statementByLastNumberOfMonth(List<StatementResponse> statementResponses, long months) {
        return statementResponses.stream().filter(stmt -> LocalDate.of(Integer.parseInt(stmt.getDatefield().split("\\.")[2]), Integer.parseInt(stmt.getDatefield().split("\\.")[1]), Integer.parseInt(stmt.getDatefield().split("\\.")[0])).isAfter(LocalDate.now().minusMonths(months))).collect(Collectors.toList());
    }

    public static List<StatementResponse> statementByMonths(List<StatementResponse> statementResponses, String fromDate, String toDate) {
        return statementResponses.stream().filter(stmt ->
                (LocalDate.of(Integer.parseInt(stmt.getDatefield().split("\\.")[2]), Integer.parseInt(stmt.getDatefield().split("\\.")[1]), Integer.parseInt(stmt.getDatefield().split("\\.")[0])).isAfter(stringToDate(fromDate)))
                        && LocalDate.of(Integer.parseInt(stmt.getDatefield().split("\\.")[2]), Integer.parseInt(stmt.getDatefield().split("\\.")[1]), Integer.parseInt(stmt.getDatefield().split("\\.")[0])).isBefore(stringToDate(toDate))).collect(Collectors.toList());
    }

    public static ResponseEntity errorResponse(int httpStatus, Long id, String message) {
        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(new ErrorResponse(id, message));
    }

    public static ResponseEntity errorResponse(int httpStatus, String message) {
        return ResponseEntity.status(HttpStatus.valueOf(httpStatus)).body(new ErrorResponse(message));
    }

    public static List<String> validateRequest(Map<String, String> map) {
        if (!ObjectUtils.isEmpty(map.get(StatementConstants.ID))) {
            if (!ObjectUtils.isEmpty(map.get(StatementConstants.FROM_DATE)) && ObjectUtils.isEmpty(map.get(StatementConstants.TO_DATE))) {
                return Collections.singletonList(StatementConstants.TO_DATE);
            } else if (ObjectUtils.isEmpty(map.get(StatementConstants.FROM_DATE)) && !ObjectUtils.isEmpty(map.get(StatementConstants.TO_DATE))) {
                return Collections.singletonList(StatementConstants.FROM_DATE);
            } else if (!ObjectUtils.isEmpty(map.get(StatementConstants.FROM_AMOUNT)) && ObjectUtils.isEmpty(map.get(StatementConstants.TO_AMOUNT))) {
                return Collections.singletonList(StatementConstants.TO_AMOUNT);
            } else if (ObjectUtils.isEmpty(map.get(StatementConstants.FROM_AMOUNT)) && !ObjectUtils.isEmpty(map.get(StatementConstants.TO_AMOUNT))) {
                return Collections.singletonList(StatementConstants.FROM_AMOUNT);
            }
        } else {
            return Collections.singletonList(StatementConstants.ID);
        }
        return Collections.emptyList();
    }

    public static LocalDate stringToDate(String date) {
        return LocalDate.of(Integer.parseInt(date.split("-")[0]), Integer.parseInt(date.split("-")[1]), Integer.parseInt(date.split("-")[2]));
    }

    public static List<StatementResponse> hashAccountID(List<StatementResponse> responseList)
    {
        log.info(StatementConstants.LOG_STR + "Hashing account id");
        responseList.stream().forEach(
                str -> str.setAccountNumber(DigestUtils.sha256Hex(str.getAccountNumber()))
        );
        return responseList;
    }
}

package com.test.view.statement.api.resource;

import com.test.view.statement.api.model.StatementResponse;
import com.test.view.statement.api.service.ViewAccountStatementService;
import com.test.view.statement.api.util.StatementDateFormatter;
import com.test.view.statement.api.util.StatementUtils;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.test.view.statement.api.constant.StatementConstants.*;

@Log4j2
@RestController
public class ViewAccountStatementResource {

    @Autowired
    private ViewAccountStatementService viewAccountStatementService;

    @Autowired
    private StatementDateFormatter dateFormatter;

    @GetMapping("/account/{id}")
    public ResponseEntity getStatementByID(@PathVariable Long id) {
        List<StatementResponse> responseList = viewAccountStatementService.getStatementById(id, 3);
        if (!responseList.isEmpty()) {
            return ResponseEntity.ok(responseList);
        } else {
            log.error(LOG_STR + MSG_NO_DATA_FOUND + " FOR " +id);
            return StatementUtils.errorResponse(404, id, MSG_NO_DATA_FOUND);
        }
    }

    @GetMapping("/statement/parameter")
    public ResponseEntity getStatement(@RequestParam Map<String, String> requestParam) {
        List<StatementResponse> responseList = new ArrayList<>();
        try {
            if (requestParam.size() == 1 && !ObjectUtils.isEmpty(requestParam.get(ID))) {
                return getStatementByID(Long.parseLong(requestParam.get(ID)));
            }
            List<String> fieldList = StatementUtils.validateRequest(requestParam);
            if (fieldList.isEmpty()) {
                if (!ObjectUtils.isEmpty(requestParam.get(FROM_AMOUNT)) && !ObjectUtils.isEmpty(requestParam.get(TO_AMOUNT))) {
                    responseList = viewAccountStatementService.getStatementByAmount(Long.parseLong(requestParam.get(ID)), requestParam.get(FROM_AMOUNT), requestParam.get(TO_AMOUNT));
                }else
                {
                    responseList = viewAccountStatementService.getStatementById(Long.parseLong(requestParam.get(ID)));
                }
                if (!ObjectUtils.isEmpty(requestParam.get(FROM_DATE)) && !ObjectUtils.isEmpty(requestParam.get(TO_DATE))) {
                    if (dateFormatter.matches(requestParam.get(FROM_DATE)) && dateFormatter.matches(requestParam.get(TO_DATE))) {
                        responseList = viewAccountStatementService.getStatementByDate(responseList, requestParam.get(FROM_DATE), requestParam.get(TO_DATE));
                    } else {
                        log.error(LOG_STR + BAD_REQUEST + INVALID_DATE_FORMAT);
                        return StatementUtils.errorResponse(400, Long.parseLong(requestParam.get(ID)), BAD_REQUEST + INVALID_DATE_FORMAT);
                    }
                }
                if (!responseList.isEmpty()) {
                    return ResponseEntity.ok(responseList);
                } else {
                    log.error(LOG_STR + MSG_NO_DATA_FOUND);
                    return StatementUtils.errorResponse(404, Long.parseLong(requestParam.get(ID)), MSG_NO_DATA_FOUND);
                }
            } else {
                log.error(LOG_STR + BAD_REQUEST + fieldList.stream().findFirst().orElse(QUERY_ATTRIBUTE) + IS_BLANK);
                return StatementUtils.errorResponse(400, BAD_REQUEST + fieldList.stream().findFirst().orElse(QUERY_ATTRIBUTE) + IS_BLANK);
            }
        } catch (Exception e) {
            log.error(LOG_STR + BAD_REQUEST + e.getMessage());
            return StatementUtils.errorResponse(500, BAD_REQUEST + e.getMessage());
        }
    }

    @GetMapping("/success")
    public String loginSuccess()
    {
        return HTML_LOGIN;
    }

    @GetMapping("/accessDenied")
    public ResponseEntity accessDenied()
    {
        return StatementUtils.errorResponse(401, ACCESS_DENIED);
    }
}

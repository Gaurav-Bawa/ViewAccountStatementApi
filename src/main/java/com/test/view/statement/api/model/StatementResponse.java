package com.test.view.statement.api.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatementResponse {

    private String accountNumber;
    private String accountType;
    private String datefield;
    private String amount;
}

package com.test.view.statement.api.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "statement")
public class Statement {

    @Id
    @Column(name = "ID")
    private Long id;

    @Column(name = "account_id")
    private Long accountID;

    @Column(name = "datefield")
    private String datefield;

    @Column(name = "amount")
    private String amount;
}

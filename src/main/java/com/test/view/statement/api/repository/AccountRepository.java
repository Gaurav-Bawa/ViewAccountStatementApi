package com.test.view.statement.api.repository;

import com.test.view.statement.api.entity.Account;
import com.test.view.statement.api.model.StatementResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AccountRepository extends JpaRepository<Account, Long> {
    @Query("select new StatementResponse(acc.accountNumber, acc.accountType, stmt.datefield, stmt.amount) from Account acc JOIN Statement stmt on acc.id = stmt.accountID where acc.id = :id")
    public List<StatementResponse> findByAccountId(@Param("id") Long id);

    @Query("select new StatementResponse(acc.accountNumber, acc.accountType, stmt.datefield, stmt.amount) from Account acc JOIN Statement stmt on acc.id = stmt.accountID where acc.id = :id and val(stmt.amount) between val(:fromAmount) and val(:toAmount)")
    public List<StatementResponse> findByAmount(@Param("id") Long id, @Param("fromAmount") String fromAmount, @Param("toAmount") String toAmount);
}

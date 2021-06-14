package com.dsi.starter.flutter_p_o_c.repos;

import com.dsi.starter.flutter_p_o_c.domain.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    List<Expense> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
    Optional<Expense> findExpenseByDate(LocalDate date);
}

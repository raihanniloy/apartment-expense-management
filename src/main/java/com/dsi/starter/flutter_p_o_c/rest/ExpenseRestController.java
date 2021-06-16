package com.dsi.starter.flutter_p_o_c.rest;

import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseIncomeSummaryDTO;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;

import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/expenses", produces = MediaType.APPLICATION_JSON_VALUE)
public class ExpenseRestController {

    private final ExpenseService expenseService;

    public ExpenseRestController(final ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @GetMapping
    public ResponseEntity<List<ExpenseDTO>> getAllExpenses() {
        return ResponseEntity.ok(expenseService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExpenseDTO> getExpense(@PathVariable final Long id) {
        return ResponseEntity.ok(expenseService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createExpense(@RequestBody @Valid final ExpenseDTO expenseDTO) {
        return new ResponseEntity<>(expenseService.create(expenseDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateExpense(@PathVariable final Long id,
            @RequestBody @Valid final ExpenseDTO expenseDTO) {
        expenseService.update(id, expenseDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExpense(@PathVariable final Long id) {
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date")
    public ResponseEntity<List<ExpenseDTO>> getExpenseByDate(@RequestParam("startDate")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate")
                                                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(expenseService.findAllByDateRange(startDate, endDate));
    }

    @GetMapping("/summary/date")
    public ResponseEntity<ExpenseIncomeSummaryDTO> getExpenseIncomeSummaryByDate(@RequestParam("startDate")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate")
                                                             @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return ResponseEntity.ok(expenseService.getAnnualExpenseIncomeSummery(startDate, endDate));
    }

}

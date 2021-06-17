package com.dsi.starter.flutter_p_o_c.service;

import com.dsi.starter.flutter_p_o_c.Util.ExpensesUtil;
import com.dsi.starter.flutter_p_o_c.domain.Expense;
import com.dsi.starter.flutter_p_o_c.model.ExpenseIncomeSummaryDTO;
import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.repos.ExpenseRepository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ExpenseService {

    private final ExpenseRepository expenseRepository;

    @Autowired
    private ChargeService chargeService;

    @Autowired
    private ExpensesUtil expensesUtil;

    public ExpenseService(final ExpenseRepository expenseRepository) {
        this.expenseRepository = expenseRepository;
    }

    public List<ExpenseDTO> findAll() {
        List<ExpenseDTO> expenseDTOList = expenseRepository.findAll()
                .stream()
                .map(expense -> mapToDTO(expense, new ExpenseDTO()))
                .collect(Collectors.toList());
        expenseDTOList.stream().forEach(expenseDTO -> {
            List<ChargeDTO> chargeDTOS = chargeService.findAllByDate(expenseDTO.getDate(),expenseDTO.getDate());
            expenseDTO.setGrossIncome(expensesUtil.calculateGrossCharges(chargeDTOS));
        });
        return expenseDTOList;
    }

    public List<ExpenseDTO> findAllByDateRange(LocalDate startDate, LocalDate endDate) {
        List<ExpenseDTO> expenseDTOList = expenseRepository.findAllByDateBetween(startDate, endDate)
                .stream()
                .map(expense -> mapToDTO(expense, new ExpenseDTO()))
                .collect(Collectors.toList());
        expenseDTOList.stream().forEach(expenseDTO -> {
            List<ChargeDTO> chargeDTOS = chargeService.findAllByDate(expenseDTO.getDate(),expenseDTO.getDate());
            expenseDTO.setGrossIncome(expensesUtil.calculateGrossCharges(chargeDTOS));
        });
        return expenseDTOList;
    }

    public ExpenseIncomeSummaryDTO getAnnualExpenseIncomeSummery(LocalDate startDate, LocalDate endDate){
        List<ExpenseDTO> expenseDTOList = this.findAllByDateRange(startDate, endDate);
        return getExpenseIncomeSummary(expenseDTOList);
    }

    public ExpenseIncomeSummaryDTO getAnnualExpenseIncomeSummery(){
        List<ExpenseDTO> expenseDTOList = this.findAll();
        return getExpenseIncomeSummary(expenseDTOList);
    }

    public Map<String, Double> getExpenseIncomeSummaryMap(LocalDate startDate, LocalDate endDate){
        ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO = this.getAnnualExpenseIncomeSummery(startDate,endDate);
        return createExpenseIncomeSummaryMap(expenseIncomeSummaryDTO);
    }

    public Map<String, Double> getExpenseIncomeSummaryMap(){
        ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO = this.getAnnualExpenseIncomeSummery();
        return createExpenseIncomeSummaryMap(expenseIncomeSummaryDTO);
    }

    Map<String, Double> createExpenseIncomeSummaryMap(ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO){
        Map<String, Double> expenseIncomeSummaryMap = new HashMap<>();
        expenseIncomeSummaryMap.put("Electricity",expenseIncomeSummaryDTO.getElectricity());
        expenseIncomeSummaryMap.put("Gas",expenseIncomeSummaryDTO.getGas());
        expenseIncomeSummaryMap.put("Water",expenseIncomeSummaryDTO.getGas());
        expenseIncomeSummaryMap.put("Security",expenseIncomeSummaryDTO.getSecurity());
        expenseIncomeSummaryMap.put("Cleaner",expenseIncomeSummaryDTO.getCleaner());
        expenseIncomeSummaryMap.put("Lift",expenseIncomeSummaryDTO.getLift());
        expenseIncomeSummaryMap.put("Others",expenseIncomeSummaryDTO.getOthers());
        return expenseIncomeSummaryMap;
    }

    public ExpenseIncomeSummaryDTO getExpenseIncomeSummary(List<ExpenseDTO> expenseDTOList){
        ExpenseIncomeSummaryDTO expenseIncomeSummaryDTO = new ExpenseIncomeSummaryDTO();
        expenseDTOList.stream().forEach(expenseDTO -> {
            expenseIncomeSummaryDTO.setElectricity(expenseIncomeSummaryDTO.getElectricity() + expenseDTO.getElectricity());
            expenseIncomeSummaryDTO.setGas(expenseIncomeSummaryDTO.getGas() + expenseDTO.getGas());
            expenseIncomeSummaryDTO.setWater(expenseIncomeSummaryDTO.getWater() + expenseDTO.getWater());
            expenseIncomeSummaryDTO.setSecurity(expenseIncomeSummaryDTO.getSecurity() + expenseDTO.getSecurity());
            expenseIncomeSummaryDTO.setCleaner(expenseIncomeSummaryDTO.getCleaner() + expenseDTO.getCleaner());
            expenseIncomeSummaryDTO.setLift(expenseIncomeSummaryDTO.getLift() + expenseDTO.getLift());
            expenseIncomeSummaryDTO.setOthers(expenseIncomeSummaryDTO.getOthers() + expenseDTO.getOthers());
            expenseIncomeSummaryDTO.setTotalIncome(expenseIncomeSummaryDTO.getTotalIncome() + expenseDTO.getGrossIncome());
            expenseIncomeSummaryDTO.setTotalExpense(expenseIncomeSummaryDTO.getTotalExpense() + expenseDTO.getGrossExpense());
        });
        expenseIncomeSummaryDTO.setTotalRevenue(expenseIncomeSummaryDTO.getTotalIncome() - expenseIncomeSummaryDTO.getTotalExpense());
        return expenseIncomeSummaryDTO;
    }

    public ExpenseDTO get(final Long id) {
        return expenseRepository.findById(id)
                .map(expense -> mapToDTO(expense, new ExpenseDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ExpenseDTO expenseDTO) {
        final Expense expense = new Expense();
        mapToEntity(expenseDTO, expense);
        return expenseRepository.save(expense).getId();
    }

    public void update(final Long id, final ExpenseDTO expenseDTO) {
        final Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(expenseDTO, expense);
        expenseRepository.save(expense);
    }

    public void delete(final Long id) {
        expenseRepository.deleteById(id);
    }

    private ExpenseDTO mapToDTO(final Expense expense, final ExpenseDTO expenseDTO) {
        expenseDTO.setId(expense.getId());
        expenseDTO.setDate(expense.getDate());
        expenseDTO.setElectricity(expense.getElectricity());
        expenseDTO.setGas(expense.getGas());
        expenseDTO.setWater(expense.getWater());
        expenseDTO.setSecurity(expense.getSecurity());
        expenseDTO.setCleaner(expense.getCleaner());
        expenseDTO.setLift(expense.getLift());
        expenseDTO.setOthers(expense.getOthers());
        expenseDTO.setGrossExpense(expensesUtil.calculateGrossExpenses(expense));
        return expenseDTO;
    }

    private Expense mapToEntity(final ExpenseDTO expenseDTO, final Expense expense) {
        expense.setDate(expenseDTO.getDate());
        expense.setElectricity(expenseDTO.getElectricity());
        expense.setGas(expenseDTO.getGas());
        expense.setWater(expenseDTO.getWater());
        expense.setSecurity(expenseDTO.getSecurity());
        expense.setCleaner(expenseDTO.getCleaner());
        expense.setLift(expenseDTO.getLift());
        expense.setOthers(expenseDTO.getOthers());
        return expense;
    }

}

package com.dsi.starter.flutter_p_o_c.service;

import com.dsi.starter.flutter_p_o_c.domain.Charge;
import com.dsi.starter.flutter_p_o_c.domain.Expense;
import com.dsi.starter.flutter_p_o_c.domain.User;
import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.MonthlyChargesDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.repos.ChargeRepository;
import com.dsi.starter.flutter_p_o_c.repos.ExpenseRepository;
import com.dsi.starter.flutter_p_o_c.repos.UserRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


@Service
public class ChargeService {

    @Autowired
    private ChargeRepository chargeRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpenseRepository expenseRepository;

    public List<ChargeDTO> findAll() {
        return chargeRepository.findAll()
                .stream()
                .map(charge -> mapToDTO(charge, new ChargeDTO()))
                .collect(Collectors.toList());
    }

    public List<ChargeDTO> findAllByUserId(Long userId){
        return chargeRepository.findAllByUserChargesId(userId)
                .stream()
                .map(charge -> mapToDTO(charge, new ChargeDTO()))
                .collect(Collectors.toList());
    }

    public List<ChargeDTO> findAllByUserIdAndDate(Long userId, LocalDate startDate, LocalDate endDate){
        return chargeRepository.findAllByUserChargesIdAndDateBetween(userId, startDate, endDate)
                .stream()
                .map(charge -> mapToDTO(charge, new ChargeDTO()))
                .collect(Collectors.toList());
    }

    public List<ChargeDTO> findAllByDate(LocalDate startDate, LocalDate endDate){
        return chargeRepository.findAllByDateBetween(startDate,endDate)
                .stream()
                .map(charge -> mapToDTO(charge, new ChargeDTO()))
                .collect(Collectors.toList());
    }

    public List<MonthlyChargesDTO> findUserChargesByDate(LocalDate startDate, LocalDate endDate){
        List<MonthlyChargesDTO> monthlyChargesDTOList = new ArrayList<>();
        List<LocalDate> dateList = expenseRepository.findAllByDateBetween(startDate, endDate).stream().map(expense -> expense.getDate()).collect(Collectors.toList());
        for (LocalDate date: dateList) {
            List<Charge> chargeList = chargeRepository.findAllByDateBetween(date, date);
            List<ChargeDTO> chargeDTOList = chargeList.stream().map(charge -> mapToDTO(charge, new ChargeDTO())).collect(Collectors.toList());
            MonthlyChargesDTO monthlyChargesDTO = new MonthlyChargesDTO();
            List<User> userList = userRepository.findAll();
            List<Long> userIds = userList.stream().map(user -> user.getId()).collect(Collectors.toList());
            List<Long> chargedUsers = chargeList.stream().map(charge -> charge.getUserCharges().getId()).collect(Collectors.toList());
            userIds.removeAll(chargedUsers);
            userIds.stream().forEach(x -> {
                ChargeDTO chargeDTO = new ChargeDTO();
                User user = userRepository.findById(x).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
                chargeDTO.setDate(date);
                chargeDTO.setServiceCharge((double) 0);
                chargeDTO.setFlatNo(user.getFlatNo());
                chargeDTOList.add(chargeDTO);
            });
            monthlyChargesDTO.setDate(date);
            monthlyChargesDTO.setChargeDTOList(chargeDTOList);
            monthlyChargesDTOList.add(monthlyChargesDTO);
        }

        return monthlyChargesDTOList;
    }

    public ChargeDTO get(final Long id) {
        return chargeRepository.findById(id)
                .map(charge -> mapToDTO(charge, new ChargeDTO()))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    public Long create(final ChargeDTO chargeDTO) {
        final Charge charge = new Charge();
        mapToEntity(chargeDTO, charge);
        return chargeRepository.save(charge).getId();
    }

    public void update(final Long id, final ChargeDTO chargeDTO) {
        final Charge charge = chargeRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        mapToEntity(chargeDTO, charge);
        chargeRepository.save(charge);
    }

    public void delete(final Long id) {
        chargeRepository.deleteById(id);
    }

    private ChargeDTO mapToDTO(final Charge charge, final ChargeDTO chargeDTO) {
        chargeDTO.setId(charge.getId());
        chargeDTO.setDate(charge.getDate());
        chargeDTO.setServiceCharge(charge.getServiceCharge());
        chargeDTO.setUserCharges(charge.getUserCharges() == null ? null : charge.getUserCharges().getId());
        chargeDTO.setFlatNo(charge.getUserCharges() == null ? null : charge.getUserCharges().getFlatNo());
        return chargeDTO;
    }

    private Charge mapToEntity(final ChargeDTO chargeDTO, final Charge charge) {
        charge.setDate(chargeDTO.getDate());
        charge.setServiceCharge(chargeDTO.getServiceCharge());
        if (chargeDTO.getUserCharges() != null && (charge.getUserCharges() == null || !charge.getUserCharges().getId().equals(chargeDTO.getUserCharges()))) {
            final User userCharges = userRepository.findById(chargeDTO.getUserCharges())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "userCharges not found"));
            charge.setUserCharges(userCharges);
        }
        return charge;
    }

}

package com.dsi.starter.flutter_p_o_c.rest;

import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.MonthlyChargesDTO;
import com.dsi.starter.flutter_p_o_c.service.ChargeService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/charges", produces = MediaType.APPLICATION_JSON_VALUE)
public class ChargeController {

    private final ChargeService chargeService;

    public ChargeController(final ChargeService chargeService) {
        this.chargeService = chargeService;
    }

    @GetMapping
    public ResponseEntity<List<ChargeDTO>> getAllCharges() {
        return ResponseEntity.ok(chargeService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ChargeDTO> getCharge(@PathVariable final Long id) {
        return ResponseEntity.ok(chargeService.get(id));
    }

    @PostMapping
    public ResponseEntity<Long> createCharge(@RequestBody @Valid final ChargeDTO chargeDTO) {
        return new ResponseEntity<>(chargeService.create(chargeDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateCharge(@PathVariable final Long id,
            @RequestBody @Valid final ChargeDTO chargeDTO) {
        chargeService.update(id, chargeDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCharge(@PathVariable final Long id) {
        chargeService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/date")
    public ResponseEntity<List<MonthlyChargesDTO>> getMonthlyUserCharges(@RequestParam("startDate")
                                                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate ,@RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        return ResponseEntity.ok(chargeService.findUserChargesByDate(startDate, endDate));
    }

}

package com.dsi.starter.flutter_p_o_c.rest;

import com.dsi.starter.flutter_p_o_c.model.ChargeDTO;
import com.dsi.starter.flutter_p_o_c.model.ExpenseDTO;
import com.dsi.starter.flutter_p_o_c.model.UserDTO;
import com.dsi.starter.flutter_p_o_c.service.ChargeService;
import com.dsi.starter.flutter_p_o_c.service.ExpenseService;
import com.dsi.starter.flutter_p_o_c.service.UserService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(value = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private ChargeService chargeService;

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.findAll());
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDTO> getUser(@PathVariable final String username) {
        return ResponseEntity.ok(userService.getByUsername(username));
    }

    @PostMapping
    public ResponseEntity<Long> createUser(@RequestBody @Valid final UserDTO userDTO) {
        return new ResponseEntity<>(userService.create(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{username}")
    public ResponseEntity<Void> updateUser(@PathVariable final String username,
            @RequestBody @Valid final UserDTO userDTO) {
        userService.update(username, userDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable final String username) {
        userService.delete(username);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{username}/charges")
    public ResponseEntity<Long> createUserCharge(@PathVariable String username, @RequestBody @Valid final ChargeDTO chargeDTO){
       UserDTO userDTO = userService.getByUsername(username);
       chargeDTO.setUserCharges(userDTO.getId());
       chargeDTO.setFlatNo(userDTO.getFlatNo());
       return new ResponseEntity<>(chargeService.create(chargeDTO), HttpStatus.CREATED);
    }

    @GetMapping("/{username}/charges")
    public ResponseEntity<List<ChargeDTO>> getAllUserCharges(@PathVariable String username){
        Long userId = userService.getByUsername(username).getId();
        return ResponseEntity.ok(chargeService.findAllByUserId(userId));
    }

    @GetMapping("/{username}/charges/date/")
    public ResponseEntity<List<ChargeDTO>> getUserExpensesByDate(@PathVariable String username, @RequestParam("startDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate, @RequestParam("endDate")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate){
        Long userId = userService.getByUsername(username).getId();
        return ResponseEntity.ok(chargeService.findAllByUserIdAndDate(userId, startDate, endDate));
    }

}

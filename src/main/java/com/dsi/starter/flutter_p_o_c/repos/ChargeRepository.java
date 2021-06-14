package com.dsi.starter.flutter_p_o_c.repos;

import com.dsi.starter.flutter_p_o_c.domain.Charge;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;


public interface ChargeRepository extends JpaRepository<Charge, Long> {
    List<Charge> findAllByUserChargesId(Long userId);
    List<Charge> findAllByUserChargesIdAndDateBetween(Long userId, LocalDate starDate, LocalDate endDate);

    List<Charge> findAllByDateBetween(LocalDate startDate, LocalDate endDate);
}

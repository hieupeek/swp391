package com.ams.dao;

import com.ams.model.LiquidationMinute;
import java.util.List;
import java.util.Optional;

public interface LiquidationMinuteDAO {
    List<LiquidationMinute> findAll();

    Optional<LiquidationMinute> findById(int id);

    List<LiquidationMinute> findByStatus(String status);

    LiquidationMinute save(LiquidationMinute minute);

    boolean update(LiquidationMinute minute);

    boolean updateStatus(int minuteId, String status, Integer approvedBy);
}

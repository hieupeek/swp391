package com.ams.dao;

import com.ams.model.LiquidationDetail;
import java.util.List;

public interface LiquidationDetailDAO {
    List<LiquidationDetail> findByMinuteId(int minuteId);

    void save(LiquidationDetail detail);

    void deleteByMinuteId(int minuteId);
}

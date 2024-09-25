package com.kb.saving.mapper;

import com.kb.saving.dto.Saving;

import java.util.List;


public interface SavingMapper {
    List<Saving> getDepositList();
    List<Saving> getSavingList();

}

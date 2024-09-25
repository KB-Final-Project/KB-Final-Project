package com.kb.saving.service;

import com.kb.saving.dto.Saving;
import com.kb.saving.mapper.SavingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j
@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingMapper mapper;

    public List<Saving> getDepositList(){
        return mapper.getDepositList();
    }

    public List<Saving> getSavingList(){
        return mapper.getSavingList();
    }
}

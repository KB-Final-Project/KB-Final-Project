package com.kb.saving.service;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingInterestRate;
import com.kb.saving.dto.SavingListDTO;
import com.kb.saving.mapper.SavingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Log4j
@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingMapper mapper;

    public List<SavingListDTO> getProductList(int finCategoryId, int saveTerm){
        HashMap<String, Integer> map = new HashMap<>();
        map.put("finCategoryId", finCategoryId);
        map.put("saveTerm", saveTerm);
        return mapper.getProductList(map);
    }
    public Saving getDepositProduct(int savingId){
        return mapper.getDepositProduct(savingId);
    }

}

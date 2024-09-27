package com.kb.saving.service;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingInterestRate;
import com.kb.saving.dto.SavingListDTO;
import com.kb.saving.mapper.SavingMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Log4j
@Service
@RequiredArgsConstructor
public class SavingService {
    private final SavingMapper mapper;

    public List<SavingListDTO> getProductList(int finCategoryId, String search, String bankId, Integer saveTerm) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("finCategoryId", finCategoryId);
        map.put("search", search);
        map.put("bankId", bankId);
        map.put("saveTerm", saveTerm);
        return mapper.getProductList(map);
    }

    public Saving getProductDetail(int finCategoryId, int savingId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("finCategoryId", finCategoryId);
        map.put("savingId", savingId);
        Saving saving = mapper.getProductDetail(map);
        saving.setJoinWays(new ArrayList<>(Arrays.asList(saving.getJoinWay().split(","))));
        return saving;
    }

}

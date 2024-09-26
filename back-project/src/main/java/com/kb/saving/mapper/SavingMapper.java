package com.kb.saving.mapper;

import com.kb.saving.dto.Saving;
import com.kb.saving.dto.SavingInterestRate;
import com.kb.saving.dto.SavingListDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface SavingMapper {
    List<SavingListDTO> getProductList(Map<String, Integer> map);
    Saving getProductDetail(Map<String, Integer> map);

}

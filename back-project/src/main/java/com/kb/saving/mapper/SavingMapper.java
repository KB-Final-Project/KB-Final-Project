package com.kb.saving.mapper;

import com.kb.saving.dto.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface SavingMapper {
    List<SavingListDTO> getProductList(SavingParam savingParam);
    Saving getProductDetail(Map<String, Integer> map);

    int getTotalCount(SavingParam savingParam);

    List<SavingListDTO> getTopProductList();

    List<SavingListDTO> getTopSavingsProductList();
}

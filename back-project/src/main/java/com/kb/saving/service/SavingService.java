package com.kb.saving.service;

import com.kb.bank.mapper.BankMapper;
import com.kb.common.pagination.PageInfo;
import com.kb.saving.dto.*;
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
    private final BankMapper bankMapper;
    private final static int LIST_LIMIT = 9;
    private final static int PAGE_LIMIT = 5;

    public SavingListResponseDTO getProductList(SavingParam savingParam) {
        int totalSize = mapper.getTotalCount(savingParam);
        PageInfo pageInfo = new PageInfo(savingParam.getPage(), totalSize, LIST_LIMIT, PAGE_LIMIT);
        savingParam.setLimit(pageInfo.getListLimit());
        savingParam.setOffset(pageInfo.getStartList() - 1);

        if(savingParam.getSaveTerm() == null)
            savingParam.setSaveTerm(36);
        if(savingParam.getInterestRateType() == null)
            savingParam.setInterestRateType("단리");

        List<SavingListDTO> savingList = mapper.getProductList(savingParam);
        if (savingList == null || savingList.isEmpty()) {
            savingList = new ArrayList<>();
        }
        return new SavingListResponseDTO(savingList, savingParam, pageInfo, totalSize);
    }

    public Saving getProductDetail(int finCategoryId, int savingId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("finCategoryId", finCategoryId);
        map.put("savingId", savingId);
        Saving saving = mapper.getProductDetail(map);
        saving.setJoinWays(new ArrayList<>(Arrays.asList(saving.getJoinWay().split(","))));
        return saving;
    }

    public List<SavingListDTO> getTopProductList() {
        return mapper.getTopProductList();
    }
    public List<SavingListDTO> getTopSavingsProductList() {
        return mapper.getTopSavingsProductList();
    }

    public SavingCategory getCategoryList(){
        SavingCategory savingCategory = new SavingCategory();
        savingCategory.setBankList(bankMapper.getBankListByType(1));
        return savingCategory;
    }
}

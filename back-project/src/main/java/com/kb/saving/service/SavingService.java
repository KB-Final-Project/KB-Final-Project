package com.kb.saving.service;

import com.kb.board.dto.Board;
import com.kb.board.dto.BoardPageResult;
import com.kb.board.dto.BoardParam;
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
    private final static int LIST_LIMIT = 9;
    private final static int PAGE_LIMIT = 5;

    public SavingListResponseDTO getProductList(SavingParam savingParam) {
        int totalSize = mapper.getTotalCount(savingParam);
        PageInfo pageInfo = new PageInfo(savingParam.getPage(), totalSize, LIST_LIMIT, PAGE_LIMIT);
        savingParam.setLimit(pageInfo.getListLimit());
        savingParam.setOffset(pageInfo.getStartList() - 1);
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

}

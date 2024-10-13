package com.kb.gold.mapper;

import com.kb.gold.dto.GoldDataDto;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface GoldMapper {
    // 배치로 데이터를 삽입하는 메서드
    void insertGoldDataBatch(List<GoldDataDto> goldDataList);
}

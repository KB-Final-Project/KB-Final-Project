package com.kb.saving.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Saving {
    private long savingId;
    private String bankId;
    private String financialProductCode;
    private String savingName;
    private Date disclosureStartDay;
    private int joinDeny;
    private String joinMember;
    private int financialCategoryId;
    private String joinWay;
    private String maturityInterest;
    private String note;
    private double maxPrimeRate;

//    private BankDTO bank;
//    private List<SavingPrimeRate> primeRatesList;
}

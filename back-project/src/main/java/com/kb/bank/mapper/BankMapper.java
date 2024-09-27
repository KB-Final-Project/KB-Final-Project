package com.kb.bank.mapper;

import com.kb.bank.dto.Bank;

public interface BankMapper {
    Bank selectBankById(String bankId);
}

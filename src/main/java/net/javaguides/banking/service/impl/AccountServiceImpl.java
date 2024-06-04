package net.javaguides.banking.service.impl;

import net.javaguides.banking.dto.AccountDto;
import net.javaguides.banking.entity.Account;
import net.javaguides.banking.mapper.AccountMapper;
import net.javaguides.banking.repository.AccountRepo;
import net.javaguides.banking.service.AccountService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AccountServiceImpl implements AccountService {

    private AccountRepo accountRepo;

    public AccountServiceImpl(AccountRepo accountRepo) {
        this.accountRepo = accountRepo;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account savedAccount = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Does not exits."));
        return AccountMapper.mapToAccountDto(account);
    }

    @Override
    public AccountDto deposite(Long id, double amount) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Does not exits."));

        double total =account.getBalance() + amount;
        account.setBalance(total);
        Account savedAccount = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public AccountDto withdraw(Long id, double amount) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Does not exits."));

        if(account.getBalance() < amount) {
            throw new RuntimeException("Insufficient Balance..");
        }

        double total = account.getBalance() - amount;
        account.setBalance(total);
        Account savedAccount = accountRepo.save(account);
        return AccountMapper.mapToAccountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepo.findAll();
        return accounts.stream().map(AccountMapper::mapToAccountDto).collect(Collectors.toList());
    }

    @Override
    public void deleteAccount(Long id) {
        Account account = accountRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Account Does not exits."));

        accountRepo.deleteById(id);
    }
}

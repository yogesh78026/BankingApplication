package net.javaguides.banking.repository;

import net.javaguides.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {

}

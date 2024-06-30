package com.laundry.user_service.repository;

import com.laundry.user_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,String> {
    boolean existsByUsername(String username);
}

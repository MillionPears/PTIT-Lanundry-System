package com.laundry.user_service.repository;

import com.laundry.user_service.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account,String> {
    boolean existsByUsername(String username);
    @Query("SELECT a.role.id FROM Account a WHERE a.username = :username")
    Long findRoleIdByUsername(@Param("username") String username);
}

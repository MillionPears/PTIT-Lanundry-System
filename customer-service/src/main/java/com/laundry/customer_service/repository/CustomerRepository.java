package com.laundry.customer_service.repository;

import com.laundry.customer_service.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface CustomerRepository extends JpaRepository<Customer, Long> {
    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    // Lấy tất cả customers và sắp xếp theo tên
    List<Customer> findAllByOrderByNameAsc();

    // Lấy Customer theo rank
    @Query("SELECT c FROM Customer c WHERE c.ranking.rankId = :rankId")
    List<Customer> findAllByRankId(@Param("rankId") Long rankId);

    // Lấy tất cả customers và sắp xếp theo rankId
//    @Query("SELECT c FROM Customer c ORDER BY c.ranking.rankId ASC")
//    List<Customer> findAllCustomersSortedByRankId();


}

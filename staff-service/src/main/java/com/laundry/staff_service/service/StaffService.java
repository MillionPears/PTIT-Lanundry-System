package com.laundry.staff_service.service;

import com.laundry.staff_service.dto.StaffRequest;
import com.laundry.staff_service.dto.StaffResponse;

import java.util.List;

public interface StaffService {
    StaffResponse createStaff(StaffRequest staffRequest);
    StaffResponse getStaffById(Long id);
    StaffResponse updateStaff(StaffRequest staffRequest,Long id);
    List<StaffResponse> getAllStaff();
    StaffResponse getStaffByUserName(String username);
}

package com.laundry.staff_service.service.implement;

import com.laundry.staff_service.dto.StaffRequest;
import com.laundry.staff_service.dto.StaffResponse;
import com.laundry.staff_service.entity.Staff;
import com.laundry.staff_service.exception.EntityExitsException;
import com.laundry.staff_service.exception.GlobalCode;
import com.laundry.staff_service.repository.StaffRepository;
import com.laundry.staff_service.service.StaffService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MatchingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StaffServiceImpl implements StaffService {
    private final StaffRepository staffRepository;
    private final ModelMapper modelMapper;
    @Override
    public StaffResponse createStaff(StaffRequest staffRequest) {
        boolean checkEmail = staffRepository.existsByEmail(staffRequest.getEmail());
        if(checkEmail){
            throw new EntityExitsException("Email đã đuược đăng ký trước đó", GlobalCode.ERROR_ID_EXIST);
        }
        boolean checkPhoneNumber = staffRepository.existsByPhoneNumber(staffRequest.getPhoneNumber());

        if(checkPhoneNumber){
            throw new EntityExitsException("Số điện thoại đã đuược đăng ký trước đó", GlobalCode.ERROR_ID_EXIST);
        }


        Staff staff = modelMapper.map(staffRequest,Staff.class);
        staff = staffRepository.save(staff);

        StaffResponse staffResponse = modelMapper.map(staff,StaffResponse.class);
        staffResponse.setUsername(staff.getUserName());
        return staffResponse;
    }

    @Override
    public StaffResponse getStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow();
        StaffResponse staffResponse = modelMapper.map(staff,StaffResponse.class);
        staffResponse.setUsername(staff.getUserName());
        return staffResponse;
    }

    @Override
    public StaffResponse updateStaff(StaffRequest staffRequest, Long id) {

        Staff staff = staffRepository.findById(id).orElseThrow();
        modelMapper.map(staffRequest,staff);
        staff = staffRepository.save(staff);
        StaffResponse staffResponse = modelMapper.map(staff,StaffResponse.class);
        staffResponse.setUsername(staff.getUserName());
        return staffResponse;
    }

    @Override
    public List<StaffResponse> getAllStaff() {
        List<Staff> list = staffRepository.findAll();

        return list.stream()
                .map(staff -> {
                    StaffResponse staffResponse = modelMapper.map(staff,StaffResponse.class);
                    staffResponse.setUsername(staff.getUserName());
                    return staffResponse;
                })
                .collect(Collectors.toList());
    }

    @Override
    public StaffResponse getStaffByUserName(String username) {
        Staff staff = staffRepository.findByUserName(username);
        System.out.println("staff  "+ staff.getUserName());
        StaffResponse staffResponse = modelMapper.map(staff, StaffResponse.class);
        staffResponse.setUsername(staff.getUserName());
        return staffResponse;

    }
}

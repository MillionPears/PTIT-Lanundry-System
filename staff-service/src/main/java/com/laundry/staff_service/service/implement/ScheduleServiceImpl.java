package com.laundry.staff_service.service.implement;

import com.laundry.staff_service.dto.ScheduleRequest;
import com.laundry.staff_service.dto.ScheduleResponse;
import com.laundry.staff_service.dto.StaffResponse;
import com.laundry.staff_service.entity.Schedule;
import com.laundry.staff_service.entity.Staff;
import com.laundry.staff_service.repository.ScheduleRepository;
import com.laundry.staff_service.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {
    private final ModelMapper modelMapper;
    private final ScheduleRepository scheduleRepository;
    @Override
    public ScheduleResponse createSchedule(ScheduleRequest scheduleRequest) {
        Schedule schedule = modelMapper.map(scheduleRequest,Schedule.class);
        schedule = scheduleRepository.save(schedule);
        ScheduleResponse scheduleResponse = modelMapper.map(schedule, ScheduleResponse.class);
        return scheduleResponse;
    }

    @Override
    public ScheduleResponse updateSchedule(ScheduleRequest scheduleRequest, Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        modelMapper.map(scheduleRequest, schedule);
        schedule = scheduleRepository.save(schedule);
        ScheduleResponse scheduleResponse = modelMapper.map(schedule,ScheduleResponse.class);
        return scheduleResponse;
    }

    @Override
    public Long deleteSchedule(Long id) {
        Schedule schedule = scheduleRepository.findById(id).orElseThrow();
        schedule.setActive(false);
        schedule = scheduleRepository.save(schedule);
        return schedule.getScheduleId();
    }

    @Override
    public List<ScheduleResponse> getALlSchedule() {

        List<Schedule> list = scheduleRepository.findAll();

        return list.stream()
                .map(schedule -> {
                    ScheduleResponse scheduleResponse = modelMapper.map(schedule,ScheduleResponse.class);
                    return scheduleResponse;
                })
                .collect(Collectors.toList());
    }
}

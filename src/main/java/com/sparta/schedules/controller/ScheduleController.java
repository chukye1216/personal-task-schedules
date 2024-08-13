package com.sparta.schedules.controller;

import com.sparta.schedules.dto.ScheduleRequestDto;
import com.sparta.schedules.dto.ScheduleResponseDto;
import com.sparta.schedules.entity.Schedules;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final Map<Long, Schedules> scheduleList = new HashMap<>();

    @PostMapping("/todo")
    public ScheduleResponseDto createSchedules(@RequestBody ScheduleRequestDto requestDto) {
        Schedules schedules = new Schedules(requestDto);

        Long maxId = scheduleList.size() > 0 ? Collections.max(scheduleList.keySet()) + 1 : 1;
        schedules.setId(maxId);

        scheduleList.put(schedules.getId(), schedules);

        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedules);

        return scheduleResponseDto;

    }
//    전체조회
//    @GetMapping("/todo/{id}")
//    public List<ScheduleResponseDto> getSchedule(@PathVariable Long id) {
//        List<ScheduleResponseDto> responseList = scheduleList.values().stream().map(ScheduleResponseDto::new).toList();
//        return  responseList;
//    }

    @GetMapping("/todo/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        if (scheduleList.containsKey(id)) {
            Schedules schedules = scheduleList.get(id);
            return new ScheduleResponseDto(schedules);
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }
}

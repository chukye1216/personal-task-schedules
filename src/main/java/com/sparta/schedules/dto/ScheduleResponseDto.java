package com.sparta.schedules.dto;

import com.sparta.schedules.entity.Schedules;
import lombok.Getter;

@Getter
public class ScheduleResponseDto {
    private Long id;
    private String username;
    private String todo;
    private Integer password;
    private String date;

    public ScheduleResponseDto(Schedules schedules) {
        this.id = schedules.getId();
        this.username =schedules.getUsername();
        this.todo =schedules.getTodo();
        this.password = schedules.getPassword();
        this.date =schedules.getDate();
    }
}

package com.sparta.schedules.dto;

import lombok.Getter;
@Getter
public class ScheduleRequestDto {
    private Long id;
    private String username;
    private String todo;
    private Integer password;
    private String date;
}

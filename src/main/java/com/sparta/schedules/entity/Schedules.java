package com.sparta.schedules.entity;

import com.sparta.schedules.dto.ScheduleRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Schedules {
    private Long id;
    private String username;
    private String todo;
    private Integer password;
    private String date;

    public Schedules(ScheduleRequestDto requestDto) {
        this.username = requestDto.getUsername();
        this.todo = requestDto.getTodo();
        this.password = requestDto.getPassword();
        this.date = requestDto.getDate();
    }
}
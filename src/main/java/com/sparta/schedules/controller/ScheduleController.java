package com.sparta.schedules.controller;

import com.sparta.schedules.dto.ScheduleRequestDto;
import com.sparta.schedules.dto.ScheduleResponseDto;
import com.sparta.schedules.entity.Schedules;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.web.bind.annotation.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ScheduleController {
    private final JdbcTemplate jdbcTemplate;

    public ScheduleController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @PostMapping("/todo")
    public ScheduleResponseDto createSchedules(@RequestBody ScheduleRequestDto requestDto) {
        Schedules schedules = new Schedules(requestDto);

        // DB 저장
        KeyHolder keyHolder = new GeneratedKeyHolder(); // 기본 키를 반환받기 위한 객체

        String sql = "INSERT INTO schedule (todo, username, password, date) VALUES (?, ?, ?, ?)";

        jdbcTemplate.update(con -> {
                    PreparedStatement preparedStatement = con.prepareStatement(sql,
                            Statement.RETURN_GENERATED_KEYS);

                    preparedStatement.setString(1, schedules.getTodo());
                    preparedStatement.setString(2, schedules.getUsername());
                    preparedStatement.setString(3, schedules.getPassword());
                    preparedStatement.setString(4, schedules.getDate());
                    return preparedStatement;
                },
                keyHolder);

        // DB Insert 후 받아온 기본키 확인
        Long id = keyHolder.getKey().longValue();
        schedules.setId(id);

        // Entity -> ResponseDto
        ScheduleResponseDto scheduleResponseDto = new ScheduleResponseDto(schedules);

        return scheduleResponseDto;

    }


    @GetMapping("/todo/{id}")
    public ScheduleResponseDto getSchedule(@PathVariable Long id) {
        String sql = "SELECT * FROM schedule WHERE id = ?";
        Schedules sch = jdbcTemplate.query(sql, resultSet->{
            if (resultSet.next()){
                Schedules schedules = new Schedules();
                schedules.setId(resultSet.getLong("id"));
                schedules.setTodo(resultSet.getString("todo"));
                schedules.setUsername(resultSet.getString("username"));
                schedules.setPassword(resultSet.getString("password"));
                schedules.setDate(resultSet.getString("date"));
                return schedules;
            }else {
                return null;
            }
        }, id);
        return new ScheduleResponseDto(sch);
    }

    @GetMapping("/todo")
    public List<ScheduleResponseDto> getSchedules() {
        // DB 조회
        String sql = "SELECT * FROM schedule ORDER BY date DESC";

        return jdbcTemplate.query(sql, new RowMapper<ScheduleResponseDto>() {
            @Override
            public ScheduleResponseDto mapRow(ResultSet rs, int rowNum) throws SQLException {
                Long id = rs.getLong("id");
                String todo = rs.getString("todo");
                String username = rs.getString("username");
                String password = rs.getString("password");
                String date = rs.getString("date");
                return new ScheduleResponseDto(id, todo, username, password, date);
            }
        });
    }

    @PutMapping("/todo/{id}")
    public Long updateSchedule(@PathVariable Long id, @RequestBody ScheduleRequestDto requestDto) {
        Schedules schedules = findBYId(id);
        if (schedules != null) {
            String sql = "UPDATE schedules SET todo = ?, username = ?, password = ?, date =? WHERE id = ?";
            jdbcTemplate.update(sql, requestDto.getTodo(), requestDto.getUsername(), requestDto.getPassword(),
                    requestDto.getDate(), id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 일정이 존재하지 않습니다.");
        }
    }

    private Schedules findBYId(Long id) {
        String sql = "SELECT * FROM schedules WHERE id = ?";

        return jdbcTemplate.query(sql, resultSet -> {
            if (resultSet.next()) {
                Schedules schedules = new Schedules();
                schedules.setTodo(resultSet.getString("todo"));
                schedules.setUsername(resultSet.getString("username"));
                schedules.setPassword(resultSet.getString("password"));
                schedules.setDate(resultSet.getString("date"));
                return schedules;
            } else {
                return null;
            }
        }, id);
    }

}


package com.example.authservice.dtos;

import com.example.authservice.models.SessionStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ValidatetokenResponseDto {

    private UserDto userDto;
    private SessionStatus sessionStatus;
}

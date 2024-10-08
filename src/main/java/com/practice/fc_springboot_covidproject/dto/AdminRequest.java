package com.practice.fc_springboot_covidproject.dto;

public record AdminRequest(
        String email,
        String nickname,
        String password,
        String phoneNumber,
        String memo
) {
    public static AdminRequest of(
            String email,
            String nickname,
            String password,
            String phoneNumber,
            String memo
    ) {
        return new AdminRequest(email, nickname, password, phoneNumber, memo);
    }
}
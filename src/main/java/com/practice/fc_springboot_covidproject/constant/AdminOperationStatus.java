package com.practice.fc_springboot_covidproject.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AdminOperationStatus {
    CREATE("저장"),
    MODIFY("수정"),
    DELETE("삭제");

    private final String message;
}

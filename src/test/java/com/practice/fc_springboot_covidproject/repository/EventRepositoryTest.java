package com.practice.fc_springboot_covidproject.repository;

import com.practice.fc_springboot_covidproject.constant.EventStatus;
import com.practice.fc_springboot_covidproject.constant.PlaceType;
import com.practice.fc_springboot_covidproject.domain.Event;
import com.practice.fc_springboot_covidproject.domain.Place;
import com.practice.fc_springboot_covidproject.dto.EventViewResponse;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.List;

import static org.antlr.v4.runtime.tree.xpath.XPath.findAll;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("DB - 이벤트")
@DataJpaTest
class EventRepositoryTest {

    private final EventRepository eventRepository;

    public EventRepositoryTest(@Autowired EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @DisplayName("이벤트 뷰 데이터를 검색 파라미터와 함께 조회하면, 조건에 맞는 데이터를 페이징 처리하여 리턴한다.")
    @Test
    void givenSearchParams_whenFindingEventViewPage_thenReturnsEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = eventRepository.findEventViewPageBySearchParams(
            "배드민턴",
            "운동1",
            EventStatus.OPENED,
            LocalDateTime.of(2021, 1, 1, 0, 0, 0),
            LocalDateTime.of(2021, 1, 2, 0, 0, 0),
            PageRequest.of(0, 5)
        );

        // Then
        assertThat(eventPage.getTotalPages()).isEqualTo(1);
        assertThat(eventPage.getNumberOfElements()).isEqualTo(1);
        assertThat(eventPage.getTotalElements()).isEqualTo(1);
        assertThat(eventPage.getContent().get(0))
            .hasFieldOrPropertyWithValue("placeName", "서울 배드민턴장")
            .hasFieldOrPropertyWithValue("eventName", "운동1")
            .hasFieldOrPropertyWithValue("eventStatus", EventStatus.OPENED)
            .hasFieldOrPropertyWithValue("eventStartDatetime", LocalDateTime.of(2021, 1, 1, 9, 0, 0))
            .hasFieldOrPropertyWithValue("eventEndDatetime", LocalDateTime.of(2021, 1, 1, 12, 0, 0));
    }

    @DisplayName("이벤트 뷰 데이터 검색어에 따른 조회 결과가 없으면, 빈 데이터를 페이징 정보와 함께 리턴한다.")
    @Test
    void givenSearchParams_whenFindingNonexistentEventViewPage_thenReturnsEmptyEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = eventRepository.findEventViewPageBySearchParams(
            "없은 장소",
            "없는 이벤트",
            null,
            LocalDateTime.of(1000, 1, 1, 1, 1, 1),
            LocalDateTime.of(1000, 1, 1, 1, 1, 0),
            PageRequest.of(0, 5)
        );

        // Then
        assertThat(eventPage).hasSize(0);
    }

    @DisplayName("이벤트 뷰 데이터를 검색 파라미터 없이 페이징 값만 주고 조회하면, 전체 데이터를 페이징 처리하여 리턴한다.")
    @Test
    void givenPagingInfoOnly_whenFindingEventViewPage_thenReturnsEventViewResponsePage() {
        // Given

        // When
        Page<EventViewResponse> eventPage = eventRepository.findEventViewPageBySearchParams(
            null,
            null,
            null,
            null,
            null,
            PageRequest.of(0, 5)
        );

        // Then
        assertThat(eventPage).hasSize(5);
    }

    @DisplayName("이벤트 뷰 데이터를 페이징 정보 없이 조회하면, 에러를 리턴한다.")
    @Test
    void givenNothing_whenFindingEventViewPage_thenThrowsError() {
        // Given

        // When
        Throwable t = catchThrowable(() -> eventRepository.findEventViewPageBySearchParams(
            null,
            null,
            null,
            null,
            null,
            null
        ));

        // Then
        assertThat(t).isInstanceOf(InvalidDataAccessApiUsageException.class);
    }

}
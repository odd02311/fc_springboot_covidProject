package com.practice.fc_springboot_covidproject.controller;

import com.practice.fc_springboot_covidproject.constant.ErrorCode;
import com.practice.fc_springboot_covidproject.constant.EventStatus;
import com.practice.fc_springboot_covidproject.domain.Event;
import com.practice.fc_springboot_covidproject.dto.EventResponse;
import com.practice.fc_springboot_covidproject.dto.EventViewResponse;
import com.practice.fc_springboot_covidproject.exception.GeneralException;
import com.practice.fc_springboot_covidproject.service.EventService;
import com.querydsl.core.types.Predicate;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Validated
@RequestMapping("/events")
@Controller
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ModelAndView events(@QuerydslPredicate(root = Event.class) Predicate predicate) {
        Map<String, Object> map = new HashMap<>();
        List<EventResponse> events = eventService.getEvents(predicate)
                .stream()
                .map(EventResponse::from)
                .toList();

        map.put("/events", events);

        return new ModelAndView("event/index", map);
    }

    @GetMapping("/custom")
    public ModelAndView customEvents(
            @Size(min = 2) String placeName,
            @Size(min = 2) String eventName,
            EventStatus eventStatus,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventStartDatetime,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime eventEndDatetime,
            Pageable pageable
    ) {
        Map<String, Object> map = new HashMap<>();
        Page<EventViewResponse> events = eventService.getEventViewResponse(
                placeName,
                eventName,
                eventStatus,
                eventStartDatetime,
                eventEndDatetime,
                pageable
        );

        map.put("events", events);

        return new ModelAndView("event/index", map);
    }

    @GetMapping("/{eventId}")
    public ModelAndView eventDetail(@PathVariable("eventId") Long eventId) {
        Map<String, Object> map = new HashMap<>();
        EventResponse event = eventService.getEvent(eventId)
                .map(EventResponse::from)
                .orElseThrow(() -> new GeneralException(ErrorCode.NOT_FOUND));

        map.put("event", event);

        return new ModelAndView("event/detail", map);
    }

}
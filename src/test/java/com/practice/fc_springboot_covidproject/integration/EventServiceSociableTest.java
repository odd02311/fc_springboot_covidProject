package com.practice.fc_springboot_covidproject.integration;

import com.practice.fc_springboot_covidproject.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class EventServiceSociableTest {

    @Autowired private EventService sut;


//    @Disabled
//    @DisplayName("검색 조건 없이 이벤트 검색하면, 전체 결과를 출력하여 보여준다.")
//    @Test
//    void givenNothing_whenSearchingEvents_thenReturnEntireEventList(){
//        // Given
//
//        // When;
//        List<EventDTO> list = sut.getEvents(null, null, null, null, null);
//
//        // Then
//        assertThat(list).hasSize(0);
//    }
}

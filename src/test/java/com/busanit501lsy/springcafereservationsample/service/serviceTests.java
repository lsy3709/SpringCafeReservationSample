package com.busanit501lsy.springcafereservationsample.service;

import com.busanit501lsy.springcafereservationsample.repository.TimeSlotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class serviceTests {
    @Mock
    private TimeSlotRepository timeSlotRepository;

    @InjectMocks
    private TimeSlotService timeSlotService;

    @BeforeEach
    public void setUp() {
        // Mockito 어노테이션을 초기화
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testDeleteTimeSlotsByReservationItemId() {
        // Given: 예약 아이템 ID 설정
        Long reservationItemId = 14L;

        // When: 서비스 메서드 호출
        timeSlotService.deleteTimeSlotsByReservationItemId(reservationItemId);

        // Then: 레포지토리 메서드가 해당 ID로 호출되었는지 검증
        verify(timeSlotRepository, times(1)).deleteByReservationItemId(reservationItemId);
    }
}

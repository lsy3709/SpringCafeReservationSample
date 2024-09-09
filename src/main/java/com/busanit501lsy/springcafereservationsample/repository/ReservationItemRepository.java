package com.busanit501lsy.springcafereservationsample.repository;

import com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO;
import com.busanit501lsy.springcafereservationsample.entity.Item;
import com.busanit501lsy.springcafereservationsample.entity.ReservationItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReservationItemRepository extends JpaRepository<ReservationItem, Long> {
    // 예약 아이디에 대한 조회
    @Query("SELECT new com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO(r.reservationDate, r.reservationTime, "
            + "u.username, u.phone, u.address, i.name, i.price, i.description, i.itemRepImageId, "
            + "i.itemAdd1ImageId, i.itemAdd2ImageId, i.itemAdd3ImageId, i.itemAdd4ImageId) "
            + "FROM ReservationItem ri "
            + "JOIN ri.item i "
            + "JOIN ri.reservation r "
            + "JOIN r.user u "
            + "WHERE r.id = :reservationId")
    Page<ReservationItemDTO> findReservationItemsWithDetails(@Param("reservationId") Long reservationId, Pageable pageable);

    // 전체 조회
    @Query("SELECT new com.busanit501lsy.springcafereservationsample.dto.ReservationItemDTO(r.reservationDate, r.reservationTime, "
            + "u.username, u.phone, u.address, i.name, i.price, i.description, i.itemRepImageId, "
            + "i.itemAdd1ImageId, i.itemAdd2ImageId, i.itemAdd3ImageId, i.itemAdd4ImageId) "
            + "FROM ReservationItem ri "
            + "JOIN ri.item i "
            + "JOIN ri.reservation r "
            + "JOIN r.user u "
            )
    Page<ReservationItemDTO> findReservationItemsWithDetails2(Pageable pageable);

}


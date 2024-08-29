package com.busanit501lsy.springcafereservationsample.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
//@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String email;

    @Column(nullable = false)
    private String password;


    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<Reservation> reservations;
//    @JsonManagedReference: 부모 객체에 이 어노테이션을 사용.
//    Jackson은 이 어노테이션이 붙은 필드를 직렬화할 때 포함합니다.
//    자식 객체를 직렬화할 때 해당 필드는 직렬화되지만,
//    역참조되는 부모 객체는 직렬화하지 않습니다.

    // 프로필 이미지, 몽고디비에 업로드
    @Column(name = "profile_image_id")
    private String profileImageId;

    // 멤버를 조회시 roleSet 를 같이 조회를 하기.
    @ElementCollection(fetch = FetchType.LAZY)
    @Builder.Default
    private Set<MemberRole> roleSet = new HashSet<>();

    public void addRole(MemberRole memberRole) {
        this.roleSet.add(memberRole);

    }
    public void clearRole() {
        this.roleSet.clear();
    }


    public void changePassword(String password) {
        this.password = password;
    }

    // Reference to the image stored in MongoDB
}
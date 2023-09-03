package com.hgy.happybank.member.domain;

import com.hgy.happybank.member.type.Role;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String nickname;

    private LocalDateTime registerAt;
    private LocalDateTime updateAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "roles")
    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public Member addRoles(Role role) {
        roles.add(role);
        return this;
    }
}

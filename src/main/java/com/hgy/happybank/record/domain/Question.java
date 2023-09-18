package com.hgy.happybank.record.domain;

import com.hgy.happybank.member.domain.Member;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contents;
    private LocalDateTime registerAt;
    private LocalDateTime updateAt;

    @JoinColumn(name = "member_id")
    @ManyToOne(fetch = FetchType.EAGER)
    private Member member;

}

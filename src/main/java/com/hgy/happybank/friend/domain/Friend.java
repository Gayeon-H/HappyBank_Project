package com.hgy.happybank.friend.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = {
        @Index(name = "idx_friend_to_member_id", columnList = "to_member_id"),
        @Index(name = "idx_friend_from_member_id", columnList = "from_member_id")
})
public class Friend {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "to_member_id")
    private Long toMemberId;

    @Column(name = "from_member_id")
    private Long fromMemberId;

    private boolean areWeFriends;

    public void setAreWeFriends(boolean areWeFriends) {
        this.areWeFriends = areWeFriends;
    }

}

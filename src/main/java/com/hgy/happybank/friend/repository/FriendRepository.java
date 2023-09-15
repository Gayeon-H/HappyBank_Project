package com.hgy.happybank.friend.repository;

import com.hgy.happybank.friend.domain.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByToMemberIdAndFromMemberId(Long toMemberId, Long fromMemberId);

    Page<Friend> findByToMemberIdOrFromMemberIdAndAreWeFriends(Long toMemberId, Long fromMemberId, boolean areWeFriends);

    Page<Friend> findByToMemberIdAndAreWeFriends(Long memberId, boolean areWeFriends);

    Optional<Friend> findByToMemberIdAndFromMemberIdAndAreWeFriends(Long toMemberId, Long fromMemberId, boolean areWeFriends);

}

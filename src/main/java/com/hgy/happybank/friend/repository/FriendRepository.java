package com.hgy.happybank.friend.repository;

import com.hgy.happybank.friend.domain.Friend;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> {

    boolean existsByToMemberIdAndFromMemberId(Long toMemberId, Long fromMemberId);

    @Query("SELECT f FROM Friend f WHERE (f.toMemberId = :to OR f.fromMemberId = :from) " +
            "AND f.areWeFriends = :friend")
    Page<Friend> findByToMemberIdOrFromMemberIdAndAreWeFriends(@Param("to") Long toMemberId,
                                                               @Param("from") Long fromMemberId,
                                                               @Param("friend") boolean areWeFriends,
                                                               Pageable pageable);

    Page<Friend> findByToMemberIdAndAreWeFriends(Long memberId, boolean areWeFriends, Pageable pageable);

    Optional<Friend> findByToMemberIdAndFromMemberIdAndAreWeFriends(Long toMemberId, Long fromMemberId,
                                                                    boolean areWeFriends);

    boolean existsByToMemberIdAndFromMemberIdAndAreWeFriends(Long toMemberId, Long fromMemberId,
                                                             boolean areWeFriends);

}

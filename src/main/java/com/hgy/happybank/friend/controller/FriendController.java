package com.hgy.happybank.friend.controller;

import com.hgy.happybank.friend.domain.dto.BoardShareRequest;
import com.hgy.happybank.friend.domain.dto.BoardShareResponse;
import com.hgy.happybank.friend.service.BoardShareService;
import com.hgy.happybank.friend.service.FriendService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class FriendController {

    private final FriendService friendService;
    private final BoardShareService boardShareService;

    @PostMapping("/members/{memberId}/friend-request")
    public ResponseEntity<?> requestFriend(Authentication authentication,
                                           @PathVariable long memberId) {
        friendService.requestFriend(authentication.getName(), memberId);
        return ResponseEntity.ok().body("친구 신청이 완료되었습니다.");
    }

    @GetMapping("/friends/request-list")
    public ResponseEntity<?> getFriendRequestList(Authentication authentication) {

        return ResponseEntity.ok().body(friendService.getFriendRequestList(authentication.getName()));
    }

    @PutMapping("/friends/request-list/{memberId}")
    public ResponseEntity<?> updateFriendRequest(Authentication authentication,
                                                 @PathVariable long memberId) {
        friendService.acceptFriendRequest(authentication.getName(), memberId);
        return ResponseEntity.ok().body("친구 요청을 수락하였습니다.");
    }

    @GetMapping("/friends")
    public ResponseEntity<?> getFriendList(Authentication authentication) {

        return ResponseEntity.ok().body(friendService.getFriendList(authentication.getName()));
    }

    @PostMapping("/friends/{friendId}")
    public ResponseEntity<?> shareBoard(Authentication authentication,
                                        @PathVariable long friendId,
                                        @RequestBody BoardShareRequest boardShareRequest) {
        BoardShareResponse boardShareResponse = boardShareService.shareBoard(authentication.getName(), friendId, boardShareRequest);
        return ResponseEntity.ok().body(boardShareResponse.getFriendNickname() + "님에게 "
                + boardShareResponse.getBoardName() + " 저금통을 공유하였습니다.");
    }

}

package com.hgy.happybank.board.controller;

import com.hgy.happybank.board.domain.dto.BoardRegDTO;
import com.hgy.happybank.board.domain.dto.BoardUpdateDTO;
import com.hgy.happybank.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping("/register")
    public ResponseEntity<?> register(Authentication authentication,
                                      @RequestBody @Validated BoardRegDTO boardRegDTO) {

        boardService.register(boardRegDTO, authentication.getName());
        return ResponseEntity.ok().body("저금통이 생성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> getBoardList(Authentication authentication) {

        return ResponseEntity.ok().body(boardService.getBoardList(authentication.getName()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(Authentication authentication,
                                      @PathVariable Long id) {

        return ResponseEntity.ok().body(boardService.getBoard(id, authentication.getName()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(Authentication authentication,
                                         @PathVariable Long id,
                                         @RequestBody @Validated BoardUpdateDTO boardUpdateDTO) {

        boardService.updateBoard(id, authentication.getName(), boardUpdateDTO);
        return ResponseEntity.ok().body("저금통 이름이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(Authentication authentication,
                                         @PathVariable Long id) {

        boardService.deleteBoard(id, authentication.getName());
        return ResponseEntity.ok().body("저금통이 삭제되었습니다.");
    }
}

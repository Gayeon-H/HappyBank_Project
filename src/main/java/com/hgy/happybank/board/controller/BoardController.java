package com.hgy.happybank.board.controller;

import com.hgy.happybank.board.domain.dto.BoardRegDTO;
import com.hgy.happybank.board.domain.dto.BoardUpdateDTO;
import com.hgy.happybank.board.service.BoardService;
import com.hgy.happybank.util.JWTProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final JWTProvider jwtProvider;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestHeader("Authorization") String authorization,
                                      @RequestBody @Validated BoardRegDTO boardRegDTO) {
        String email = jwtProvider.getEmail(authorization.split(" ")[1]);
        boardService.register(boardRegDTO, email);

        return ResponseEntity.ok().body("저금통이 생성되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> getBoardList(@RequestHeader("Authorization") String authorization) {
        String email = jwtProvider.getEmail(authorization.split(" ")[1]);

        return ResponseEntity.ok().body(boardService.getBoardList(email));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBoard(@PathVariable Long id,
                                      @RequestHeader("Authorization") String authorization) {
        String email = jwtProvider.getEmail(authorization.split(" ")[1]);

        return ResponseEntity.ok().body(boardService.getBoard(id, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateBoard(@PathVariable Long id,
                                         @RequestHeader("Authorization") String authorization,
                                         @RequestBody @Validated BoardUpdateDTO boardUpdateDTO) {
        String email = jwtProvider.getEmail(authorization.split(" ")[1]);
        boardService.updateBoard(id, email, boardUpdateDTO);

        return ResponseEntity.ok().body("저금통 이름이 수정되었습니다.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBoard(@PathVariable Long id,
                                         @RequestHeader("Authorization") String authorization) {
        String email = jwtProvider.getEmail(authorization.split(" ")[1]);
        boardService.deleteBoard(id, email);

        return ResponseEntity.ok().body("저금통이 삭제되었습니다.");
    }
}

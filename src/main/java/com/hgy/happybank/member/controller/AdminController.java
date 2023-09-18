package com.hgy.happybank.member.controller;

import com.hgy.happybank.member.service.AdminService;
import com.hgy.happybank.record.domain.dto.QuestionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/question")
    public ResponseEntity<?> register(Authentication authentication,
                                      @RequestBody QuestionRequest questionRequest) {
        adminService.registerQuestion(authentication.getName(), questionRequest);
        return ResponseEntity.ok().body("질문 등록이 완료되었습니다.");
    }

    @GetMapping("/question-list")
    public ResponseEntity<?> getQuestionList() {
        return ResponseEntity.ok().body(adminService.getQuestionList());
    }

    @GetMapping("/question-list/{questionId}")
    public ResponseEntity<?> getQuestion(@PathVariable long questionId) {

        return ResponseEntity.ok().body(adminService.getQuestion(questionId));
    }

    @PutMapping("/question-list/{questionId}")
    public ResponseEntity<?> updateQuestion(Authentication authentication,
                                            @PathVariable long questionId,
                                            @RequestBody QuestionRequest questionRequest) {
        adminService.updateQuestion(authentication.getName(), questionId, questionRequest);
        return ResponseEntity.ok().body("질문 수정이 완료되었습니다.");
    }

    @DeleteMapping("/question-list/{questionId}")
    public ResponseEntity<?> deleteQuestion(Authentication authentication,
                                            @PathVariable long questionId) {
        adminService.deleteQuestion(authentication.getName(), questionId);
        return ResponseEntity.ok().body("질문 삭제가 완료되었습니다.");
    }

}

package com.hgy.happybank.record.controller;

import com.hgy.happybank.record.domain.dto.RecordRequest;
import com.hgy.happybank.record.service.RecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/boards/{boardId}/records")
@RequiredArgsConstructor
public class RecordController {

    private final RecordService recordService;

    @PostMapping
    public ResponseEntity<?> register(Authentication authentication,
                                      @PathVariable long boardId,
                                      @RequestBody RecordRequest request) {

        recordService.register(authentication.getName(), boardId, request);
        return ResponseEntity.ok().body("하루 기록이 완료되었습니다.");
    }

    @GetMapping
    public ResponseEntity<?> getRecordList(Authentication authentication,
                                           @PathVariable long boardId) {

        return ResponseEntity.ok().body(recordService.getRecordList(authentication.getName(), boardId));
    }

    @GetMapping("/{recordId}")
    public ResponseEntity<?> getRecord(Authentication authentication,
                                       @PathVariable long boardId,
                                       @PathVariable long recordId) {

        return ResponseEntity.ok().body(recordService.getRecord(authentication.getName(), boardId, recordId));
    }
}

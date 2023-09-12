package com.hgy.happybank.record.service;

import com.hgy.happybank.record.aws.AwsS3Service;
import com.hgy.happybank.board.domain.Board;
import com.hgy.happybank.board.repository.BoardRepository;
import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.record.domain.Record;
import com.hgy.happybank.record.domain.dto.RecordDTO;
import com.hgy.happybank.record.domain.dto.RecordRequest;
import com.hgy.happybank.record.domain.dto.RecordResponse;
import com.hgy.happybank.record.repository.RecordRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class RecordService {

    private final RecordRepository recordRepository;
    private final BoardRepository boardRepository;
    private final AwsS3Service awsS3Service;
    private final RedisDataService redisDataService;

    private final String key = "record:";

    public void register(String email, long boardId, RecordRequest request) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (!board.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD, "저금통 소유자만 기록 등록이 가능합니다.");
        }

        if (redisDataService.isExist(key + email)) {
            throw new BizException(ErrorCode.ALREADY_REGISTER_RECORD_TODAY);
        }

        try {
            String imgURL = awsS3Service.upload(request.getMultipartFile(), "upload").getPath();
            redisDataService.addToRedis(key + email, RecordDTO.builder()
                    .title(request.getTitle())
                    .contents(request.getContents())
                    .imgURL(imgURL)
                    .regDate(LocalDate.now())
                    .board(board)
                    .build());

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public Page<RecordResponse> getRecordList(String email, long boardId) {
        preVerify(email, boardId);

        return recordRepository.findByBoardId(boardId,
                        PageRequest.of(0, 10, Sort.Direction.ASC, "reg_date"))
                .map(record -> RecordResponse.builder()
                        .id(record.getId())
                        .title(record.getTitle())
                        .contents(record.getContents())
                        .imgURL(record.getImgURL())
                        .regDate(record.getRegDate())
                        .build());
    }

    public RecordResponse getRecord(String email, long boardId, long recordId) {
        preVerify(email, boardId);
        Record record = recordRepository.findByIdAndBoardId(recordId, boardId)
                .orElseThrow(() -> new BizException(ErrorCode.RECORD_NOT_FOUND));

        return RecordResponse.builder()
                .id(record.getId())
                .title(record.getTitle())
                .contents(record.getContents())
                .imgURL(record.getImgURL())
                .regDate(record.getRegDate())
                .build();
    }

    private void preVerify(String email, long boardId) {
        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new BizException(ErrorCode.BOARD_NOT_FOUND));

        if (board.getOpenDate().isAfter(LocalDate.now())) {
            throw new BizException(ErrorCode.NOT_POSSIBLE_TO_OPEN_BEFORE_OPENING_DATE);
        }

        if (!board.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_BOARD);
        }
    }

}

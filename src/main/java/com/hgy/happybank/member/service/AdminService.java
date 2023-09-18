package com.hgy.happybank.member.service;

import com.hgy.happybank.exception.BizException;
import com.hgy.happybank.exception.type.ErrorCode;
import com.hgy.happybank.member.domain.Member;
import com.hgy.happybank.member.repository.MemberRepository;
import com.hgy.happybank.record.domain.Question;
import com.hgy.happybank.record.domain.dto.QuestionRequest;
import com.hgy.happybank.record.domain.dto.QuestionResponse;
import com.hgy.happybank.record.repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final QuestionRepository questionRepository;
    private final MemberRepository memberRepository;

    public void registerQuestion(String email, QuestionRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        questionRepository.save(Question.builder()
                .contents(request.getContents())
                .registerAt(LocalDateTime.now())
                .member(member)
                .build());
    }

    public Page<QuestionResponse> getQuestionList() {
        return questionRepository.findAll(PageRequest.of(0, 10))
                .map(q -> QuestionResponse.builder()
                        .id(q.getId())
                        .contents(q.getContents())
                        .memberNickname(q.getMember().getNickname())
                        .registerAt(q.getRegisterAt())
                        .updateAt(q.getUpdateAt())
                        .build());
    }

    public QuestionResponse getQuestion(long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BizException(ErrorCode.QUESTION_NOT_FOUND));

        return QuestionResponse.builder()
                .id(questionId)
                .contents(question.getContents())
                .memberNickname(question.getMember().getNickname())
                .registerAt(question.getRegisterAt())
                .updateAt(question.getUpdateAt())
                .build();
    }

    public void updateQuestion(String email, long questionId, QuestionRequest request) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BizException(ErrorCode.MEMBER_NOT_FOUND));

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BizException(ErrorCode.QUESTION_NOT_FOUND));

        if (!question.getMember().getNickname().equals(member.getNickname())) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_QUESTION);
        }

        question.setContents(request.getContents());
        question.setUpdateAt(LocalDateTime.now());
        questionRepository.save(question);
    }

    public void deleteQuestion(String email, long questionId) {
        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new BizException(ErrorCode.QUESTION_NOT_FOUND));

        if (!question.getMember().getEmail().equals(email)) {
            throw new BizException(ErrorCode.NO_RIGHT_ABOUT_THIS_QUESTION, "등록자만 삭제가 가능합니다.");
        }

        questionRepository.delete(question);
    }

}

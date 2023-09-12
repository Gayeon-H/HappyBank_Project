package com.hgy.happybank.record.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecordRequest {

    private String title;
    private String contents;
    private MultipartFile multipartFile;

}

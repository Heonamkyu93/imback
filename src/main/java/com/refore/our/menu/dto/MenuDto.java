package com.refore.our.menu.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MenuDto {

    private Long buggerId;
    private String buggerName;
    private String buggerPrice;
    private String buggerInfo;
    private MultipartFile imageFile;





}

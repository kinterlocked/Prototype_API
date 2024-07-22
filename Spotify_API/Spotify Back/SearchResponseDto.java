package com.ssadolda.model.dto;

import lombok.Builder;

import lombok.Data;

@Data
@Builder
public class SearchResponseDto {
    private String artistName;
    private String title;
    private String albumName;
    private String imageUrl;
}
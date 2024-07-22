package com.ssadolda.mapper;

import com.ssadolda.model.dto.SearchResponseDto;
import org.springframework.stereotype.Component;

@Component
public class Mapper {
    public SearchResponseDto toSearchDto(String artistName, String title, String albumName, String imageUrl) {
        return SearchResponseDto.builder()
                .artistName(artistName)
                .title(title)
                .albumName(albumName)
                .imageUrl(imageUrl)
                .build();
    }
}
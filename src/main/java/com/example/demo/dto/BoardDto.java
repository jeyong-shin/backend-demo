package com.example.demo.dto;

import com.example.demo.entity.Board;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardDto {
    
    private Long id;
    
    @NotBlank(message = "제목은 필수 입력값입니다.")
    @Size(max = 100, message = "제목은 100자 이내로 작성해주세요.")
    private String title;
    
    @NotBlank(message = "내용은 필수 입력값입니다.")
    private String content;
    
    @NotBlank(message = "작성자는 필수 입력값입니다.")
    private String author;
    
    private LocalDateTime createdAt;
    
    // Entity to DTO
    public static BoardDto fromEntity(Board board) {
        return BoardDto.builder()
                .id(board.getId())
                .title(board.getTitle())
                .content(board.getContent())
                .author(board.getAuthor())
                .createdAt(board.getCreatedAt())
                .build();
    }
    
    // DTO to Entity
    public Board toEntity() {
        return Board.builder()
                .id(id)
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
} 
package com.example.demo.service;

import com.example.demo.dto.BoardDto;

import java.util.List;

public interface BoardService {
    
    List<BoardDto> getAllBoards();
    
    BoardDto getBoardById(Long id);
    
    BoardDto saveBoard(BoardDto boardDto);
    
    BoardDto updateBoard(Long id, BoardDto boardDto);
    
    void deleteBoard(Long id);
} 
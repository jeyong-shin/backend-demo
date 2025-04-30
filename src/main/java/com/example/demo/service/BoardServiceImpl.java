package com.example.demo.service;

import com.example.demo.dto.BoardDto;
import com.example.demo.entity.Board;
import com.example.demo.repository.BoardRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class BoardServiceImpl implements BoardService {
    
    private final BoardRepository boardRepository;
    
    @Override
    public List<BoardDto> getAllBoards() {
        return boardRepository.findAll().stream()
                .map(BoardDto::fromEntity)
                .collect(Collectors.toList());
    }
    
    @Override
    public BoardDto getBoardById(Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));
        return BoardDto.fromEntity(board);
    }
    
    @Override
    @Transactional
    public BoardDto saveBoard(BoardDto boardDto) {
        Board savedBoard = boardRepository.save(boardDto.toEntity());
        return BoardDto.fromEntity(savedBoard);
    }
    
    @Override
    @Transactional
    public BoardDto updateBoard(Long id, BoardDto boardDto) {
        Board existingBoard = boardRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id));
        
        existingBoard.setTitle(boardDto.getTitle());
        existingBoard.setContent(boardDto.getContent());
        existingBoard.setAuthor(boardDto.getAuthor());
        
        Board updatedBoard = boardRepository.save(existingBoard);
        return BoardDto.fromEntity(updatedBoard);
    }
    
    @Override
    @Transactional
    public void deleteBoard(Long id) {
        if (!boardRepository.existsById(id)) {
            throw new EntityNotFoundException("게시글을 찾을 수 없습니다. ID: " + id);
        }
        boardRepository.deleteById(id);
    }
} 
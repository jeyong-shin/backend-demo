package com.example.demo.controller.api;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardApiController {
    
    private final BoardService boardService;
    
    @GetMapping
    public ResponseEntity<List<BoardDto>> getAllBoards() {
        List<BoardDto> boards = boardService.getAllBoards();
        return ResponseEntity.ok(boards);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<BoardDto> getBoardById(@PathVariable(name = "id") Long id) {
        BoardDto board = boardService.getBoardById(id);
        return ResponseEntity.ok(board);
    }
    
    @PostMapping
    public ResponseEntity<BoardDto> createBoard(@Valid @RequestBody BoardDto boardDto) {
        BoardDto savedBoard = boardService.saveBoard(boardDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedBoard);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<BoardDto> updateBoard(
            @PathVariable(name = "id") Long id, 
            @Valid @RequestBody BoardDto boardDto) {
        BoardDto updatedBoard = boardService.updateBoard(id, boardDto);
        return ResponseEntity.ok(updatedBoard);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBoard(@PathVariable(name = "id") Long id) {
        boardService.deleteBoard(id);
        return ResponseEntity.noContent().build();
    }
} 
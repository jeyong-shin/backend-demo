package com.example.demo.controller;

import com.example.demo.dto.BoardDto;
import com.example.demo.service.BoardService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/boards")
@RequiredArgsConstructor
public class BoardController {
    
    private final BoardService boardService;
    
    @GetMapping
    public String listBoards(Model model) {
        model.addAttribute("boards", boardService.getAllBoards());
        return "boards/list";
    }
    
    @GetMapping("/{id}")
    public String viewBoard(@PathVariable Long id, Model model) {
        model.addAttribute("board", boardService.getBoardById(id));
        return "boards/view";
    }
    
    @GetMapping("/new")
    public String newBoardForm(Model model) {
        model.addAttribute("boardDto", new BoardDto());
        return "boards/form";
    }
    
    @PostMapping
    public String createBoard(@Valid @ModelAttribute("boardDto") BoardDto boardDto, 
                              BindingResult result) {
        if (result.hasErrors()) {
            return "boards/form";
        }
        
        BoardDto savedBoard = boardService.saveBoard(boardDto);
        return "redirect:/boards/" + savedBoard.getId();
    }
    
    @GetMapping("/{id}/edit")
    public String editBoardForm(@PathVariable Long id, Model model) {
        model.addAttribute("boardDto", boardService.getBoardById(id));
        return "boards/form";
    }
    
    @PostMapping("/{id}")
    public String updateBoard(@PathVariable Long id, 
                              @Valid @ModelAttribute("boardDto") BoardDto boardDto,
                              BindingResult result) {
        if (result.hasErrors()) {
            return "boards/form";
        }
        
        boardService.updateBoard(id, boardDto);
        return "redirect:/boards/" + id;
    }
    
    @GetMapping("/{id}/delete")
    public String deleteBoard(@PathVariable Long id) {
        boardService.deleteBoard(id);
        return "redirect:/boards";
    }
} 
package com.madayV2.blog.controller;

import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.madayV2.blog.service.PostService;
import com.madayV2.blog.service.SqlService;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class DashboardController {
    private final PostService postService;
    private final SqlService sqlService;

    @GetMapping("/")
    public String dashboard(Model model, @RequestParam(defaultValue = "0") int page) {
        int size = 8;
        model.addAttribute("posts", postService.getPage(page, size));
        model.addAttribute("totalPages", postService.getTotalPages(size));
        model.addAttribute("currentPage", page);
        return "dashboard";
    }

    @PostMapping("/sql/execute")
    @ResponseBody
    public Map<String, Object> execute(@RequestBody Map<String, String> body) {
        try {
            return sqlService.execute(body.get("sql"));
        } catch (Exception e) {
            return Map.of("type", "error", "message", e.getMessage());
        }
    }
}
package com.madayV2.blog.controller;

import java.util.List;
import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.madayV2.blog.dto.PostDto;
import com.madayV2.blog.service.PostService;

@Controller
@RequestMapping("/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	// 글 목록
	@GetMapping
	public String list(Model model) {
		List<PostDto> posts = postService.getAll();
		model.addAttribute("posts", posts);
		return "posts/list"; // templates/posts/list.html 로 이동
	}

	// 글 상세
	@GetMapping("/{id}")
	public String detail(@PathVariable Long id, Model model) {
		model.addAttribute("post", postService.getById(id));
		return "posts/detail";
	}
	
	@GetMapping("/{id}/data")
	@ResponseBody
	public PostDto data(@PathVariable Long id) {
	    return postService.getById(id);
	}

	// 글 작성 폼
	@GetMapping("/write")
	public String writeForm(Model model) {
		model.addAttribute("post", new PostDto());
		return "posts/write";
	}

	// 글 저장
	@PostMapping("/write")
	public String write(@ModelAttribute PostDto post, Principal principal) {
	    post.setAuthor(principal.getName());
	    postService.create(post);
		return "redirect:/posts";
	}

	// 글 수정 폼
	@GetMapping("/edit/{id}")
	public String editForm(@PathVariable Long id, Model model) {
		model.addAttribute("post", postService.getById(id));
		return "posts/edit";
	}

	// 글 수정 저장
	@PostMapping("/edit/{id}")
	public String edit(@PathVariable Long id, @ModelAttribute PostDto post) {
		post.setId(id);
		postService.modify(post);
		return "redirect:/posts";
	}

	// 글 삭제
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable Long id) {
		postService.remove(id);
		return "redirect:/posts";
	}
}
package com.madayV2.blog.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.madayV2.blog.dto.PostDto;
import com.madayV2.blog.mapper.PostMapper;
import lombok.RequiredArgsConstructor;

// @RequiredArgsConstructor: final로 선언된 필드의 생성자를 자동 생성 (스프링 권장 DI 방식)
@Service
@RequiredArgsConstructor
public class PostService {
	private final PostMapper postMapper;

	public List<PostDto> getAll() {
		return postMapper.findAll();
	}

	public PostDto getById(Long id) {
		return postMapper.findById(id);
	}

	public void create(PostDto post) {
		postMapper.insert(post);
	}

	public void modify(PostDto post) {
		postMapper.update(post);
	}

	public void remove(Long id) {
		postMapper.delete(id);
	}
	public List<PostDto> getPage(int page, int size) {
	    return postMapper.findPage(page * size, size);
	}

	public int getTotalPages(int size) {
	    int total = postMapper.count();
	    return (int) Math.ceil((double) total / size);
	}
}
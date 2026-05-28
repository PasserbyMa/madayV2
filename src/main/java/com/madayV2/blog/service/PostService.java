package com.madayV2.blog.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.madayV2.blog.dto.PostDto;
import com.madayV2.blog.mapper.PostMapper;

@Service
public class PostService {
	// Spring이 PostMapper 객체를 자동 삽임
	// @RequiredArgsConstructor 없이도 의존성 주입
	// @RequiredArgsConstructor: final로 선언된 변수의 생성자를 자동 생성
	// PostMapper를 직접 new 하지 않아도 Spring이 알아서 주입(의존성 주입)
	@Autowired
	private PostMapper postMapper;

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
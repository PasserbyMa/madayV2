package com.madayV2.blog.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.madayV2.blog.dto.PostDto;
import org.apache.ibatis.annotations.Param;
@Mapper
public interface PostMapper {
	List<PostDto> findAll();

	PostDto findById(Long id);

	void insert(PostDto post);

	void update(PostDto post);

	void delete(Long id);
	
	List<PostDto> findPage(@Param("offset") int offset, @Param("limit") int limit);
	int count();
}

/*
 * Mapper가 인터페이스인 이유 MyBatis가 XML의 SQL을 읽어서 구현체를 자동 생성 <- 개발자가 직접 구현 X
 * PostMapper 인터페이스 (메서드 선언만) MyBatis가 PostMapper.xml 읽어서 구현체 자동 생성 
 * Spring이 그 구현체를 @Autowired로 주입
 * 직접 구현하면 findAll()마다 JDBC 연결 열고, SQL 실행하고, 결과 매핑하고... 수십
 * 줄인데 MyBatis가 다 해줘요.
 */
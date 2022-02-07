package com.bribemap.blog;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.bribemap.common.entity.Blog;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
public class BlogRepositoryTest {

	@Autowired private BlogRepository repo;
	
	
	//test an entreprise profile existe in the database
	@Test 
	public void testListRecommand() {
	List<Blog> blogs = repo.findAllRecommand();
	blogs.forEach(blog->{
		System.out.println(blog.getContent()+"("+blog.isRecommend()+")");
	});
	}
}

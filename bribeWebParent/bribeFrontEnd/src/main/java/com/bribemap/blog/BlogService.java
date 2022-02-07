package com.bribemap.blog;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bribemap.common.entity.Blog;

@Service
public class BlogService {

	
	@Autowired private BlogRepository repo;
	
	public List<Blog> listBlog(){
		 List<Blog> listRecommd = repo.findAllRecommand();
		 return listRecommd;
	}
}

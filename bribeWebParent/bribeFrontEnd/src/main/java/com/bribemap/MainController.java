package com.bribemap;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.bribemap.blog.BlogService;
import com.bribemap.common.entity.Blog;

@Controller
public class MainController {

	@Autowired private BlogService blogService;
	
	
	@GetMapping("")
	public String viewHomePage(Model model) {
		 List<Blog> listBlog = blogService.listBlog();
		 model.addAttribute("listBlog",listBlog);
		
		return "index";
	}
}

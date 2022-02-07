package com.bribemap.admin.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bribemap.admin.user.UserNotFoundException;
import com.bribemap.admin.user.UserService;
import com.bribemap.common.entity.Role;
import com.bribemap.common.entity.User;

@Controller
public class UserController {

	@Autowired
	private UserService service;
	
	@GetMapping("/users")
	public String listFirstPage(Model model ) {
		//List<User>listUsers = service.listAll();
		//model.addAttribute("listUsers", listUsers);
		return listByPage(1,model,"firstName","asc",null); //entity class name not the column name in the database
	}
	
	
	@GetMapping("/users/page/{pageNum}")
	public String listByPage(@PathVariable(name="pageNum") int pageNum, Model model, 
			@Param ("sortField") String sortField, @Param("sortDir") String sortDir,
			@Param ("keyword") String keyword
			) {
		Page<User> page = service.listByPage(pageNum, sortField, sortDir, keyword);
		List<User> listUsers = page.getContent();
		long startCount = (pageNum-1)*UserService.USERS_PER_PAGES+1;
		long endCount = startCount + UserService.USERS_PER_PAGES -1;
		if (endCount> page.getTotalElements()) {
			endCount= page.getTotalElements();
		}
		
		String reverseSortDir = sortDir.equals("asc") ? "desc" : "asc";
		
		model.addAttribute("totalPages",page.getTotalPages());
		model.addAttribute("currentPage",pageNum);
		model.addAttribute("endCount",endCount);
		model.addAttribute("startCount",startCount);
		model.addAttribute("totalItems",page.getTotalElements());
		model.addAttribute("listUsers", listUsers);
		model.addAttribute("sortField", sortField);
		model.addAttribute("sortDir", sortDir);
		model.addAttribute("reverseSortDir", reverseSortDir);
		model.addAttribute("keyword",keyword); //to send a params to the view
		return"users/users";
	}
	
	
	
	@GetMapping("/users/new")
	public String newUser(Model model ) {
		List<Role>listRoles= service.listRoles();
		User user = new User();
		user.setEnabled(true);
		model.addAttribute("listRoles", listRoles);
		model.addAttribute("user", user);
		model.addAttribute("pageTitle","create new User");
		return"users/users_form";
	}
	
	
	@PostMapping("/users/save")
	public String saveUser(User user, RedirectAttributes redirectAttributes ) {
		System.out.println(user);
		service.save(user);
		
		redirectAttributes.addFlashAttribute("message","the user has been save successfully.");
		
		return"redirect:/users";
	}
	
	
	@GetMapping("/users/edit/{id}")
	public String editUser(@PathVariable (name = "id") Integer id, Model model,RedirectAttributes redirectAttributes  ) {
		
		try {
			User user = service.get(id);
			List<Role>listRoles= service.listRoles();
			
			//just remove Attribute if you dont want to see it in the views
			model.addAttribute("listRoles", listRoles);
			model.addAttribute("user",user);
			model.addAttribute("pageTitle","Edit User(ID: "+id+")");
			return"users_form";
		}catch(UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message",ex.getMessage());
			return"redirect:/users";
		}	
	}
	
	 
 	
	@GetMapping("/users/delete/{id}")
	public String deleteUser(@PathVariable (name = "id") Integer id, Model model,RedirectAttributes redirectAttributes  ) {
		
		try {
			service.delete(id);
			redirectAttributes.addFlashAttribute("message","The user ID"+id+"has been delete successfully");
		}catch(UserNotFoundException ex){
			redirectAttributes.addFlashAttribute("message",ex.getMessage());	
		}
		return"redirect:/users";
		
		
	}
	
}

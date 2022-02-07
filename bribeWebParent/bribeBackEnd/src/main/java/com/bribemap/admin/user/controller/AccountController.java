package com.bribemap.admin.user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bribemap.admin.security.BribeUserDetails;
import com.bribemap.admin.user.UserService;
import com.bribemap.common.entity.User;

@Controller
public class AccountController {
	
	@Autowired
	private UserService service;
	
	@GetMapping("/account")
	public String viewDetails(@AuthenticationPrincipal BribeUserDetails loggedUser, Model model) {
		String email = loggedUser.getUsername();
		User user = service.getByEmail(email);
		model.addAttribute("user",user);
		return "users/account_form";
	}
	
	//will be upload with new details for the photos later
	@PostMapping("/account/update")
	public String saveDetails(User user, RedirectAttributes redirectAttributes,
			@AuthenticationPrincipal BribeUserDetails loggedUser) {
		service.updateAccount(user);
		redirectAttributes.addFlashAttribute("message","Your account details have Been Updated.");
		
		
		loggedUser.setLastName(user.getLastName());
		loggedUser.setFirstName(user.getFirstName());
		
		
		return"redirect:/account";
	}
}

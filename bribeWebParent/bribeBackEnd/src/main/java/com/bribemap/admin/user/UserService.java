package com.bribemap.admin.user;

import java.util.List;
import java.util.NoSuchElementException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.bribemap.common.entity.Role;
import com.bribemap.common.entity.User;

@Service
@Transactional
public class UserService {
	 

	public static final int USERS_PER_PAGES = 4;
	
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private RoleRepository roleRepo;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	public Page<User> listByPage(int pageNum, String sortField, String sortDir, String keyword){
		Sort sort = Sort.by(sortField);
		sort =sortDir.equals("asc") ? sort.ascending() : sort.descending();
		
		Pageable pageable = PageRequest.of(pageNum-1, USERS_PER_PAGES, sort);
		
		if(keyword !=null) {
			return userRepo.findAll(keyword,pageable);
		}
		return userRepo.findAll(pageable);
		
		
	}
	
	public User getByEmail(String email) {
		return userRepo.getUserByEmail(email);
	}
	
	
	public List<User> listAll(){
		return (List<User>) userRepo.findAll();	
	}

	public List<Role> listRoles(){
		return (List<Role>) roleRepo.findAll();	
	}

	public void save(User user) {
		boolean isUpdatingUser =(user.getId()!=null);
		
		if (isUpdatingUser) {
			User existingUser = userRepo.findById(user.getId()).get();
			
			if(user.getPassword().isEmpty()) {
				user.setPassword(existingUser.getPassword());
			}else {
			encodePassword(user); //encode user pass before save it to DB 
		}
		}
		
		userRepo.save(user);
		
	}
	
	//update current user details in the setting option
	public User updateAccount(User userInForm) {
		User userInDB= userRepo.findById(userInForm.getId()).get();
		if(!userInForm.getPassword().isEmpty()) {
			userInDB.setPassword(userInForm.getPassword());
			encodePassword(userInDB);
		}
		
		if(userInForm.getPhotos() != null) {
			userInDB.setPhotos(userInForm.getPhotos());
		}		
		userInDB.setFirstName(userInForm.getFirstName());
		userInDB.setLastName(userInForm.getLastName());	
		return userRepo.save(userInDB);
	}
	
	private void encodePassword(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
	}
	
	
	//check if email is unique with id and email because we will you that method to also edit so you have to check base on them
	public boolean isEmailUnique(Integer id, String email) {
		User userByEmail = userRepo.getUserByEmail(email);
		if (userByEmail ==null) return true;
		boolean isCreatingNew = (id == null);
		if(isCreatingNew) {
			if(userByEmail!=null) return false;
		}else {
			if(userByEmail.getId() != id) {
				return false;
			}
		}
		return true;
		
	}

	public User get(Integer id) throws UserNotFoundException {
		try {
			return userRepo.findById(id).get();
		}catch (NoSuchElementException ex) {
		throw new UserNotFoundException("could not find any user with ID :"+id);
	}}

	
	
	public void delete(Integer id) throws UserNotFoundException {
		Long countById = userRepo.countById(id); //check if the user exist
		if(countById==null || countById==0) {
			throw new UserNotFoundException("could not find any user with ID :"+id);
		}
		userRepo.deleteById(id);
	}
	

	
	
	
	
}

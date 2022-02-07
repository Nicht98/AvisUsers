package com.bribemap.admin.user;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import com.bribemap.common.entity.Role;
import com.bribemap.common.entity.User;

@DataJpaTest
@AutoConfigureTestDatabase(replace= Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {

	@Autowired
	private UserRepository repo;
	
	@Autowired
	private TestEntityManager entityManager; //fetch specifics informations from the database
	
	@Test
	public void testcreateNewUserWithOneRole() {
		Role roleAdmin = entityManager.find(Role.class, 1);
		User userNamHm = new User("paulkam@gmail.com","quicdo237","paul","kwahm");
		userNamHm.addRole(roleAdmin);
		
		User savedUser = repo.save(userNamHm);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testcreateNewUserWithTwoRoles() {
		User userRavi = new User("ravi@quickdo.com","ravi201","ravi","kumar");
		Role roleEditor = new Role(2);
		Role roleAssistant = new Role(4);
		userRavi.addRole(roleAssistant);
		userRavi.addRole(roleEditor);
		
		User savedUser = repo.save(userRavi);
		assertThat(savedUser.getId()).isGreaterThan(0);
	}
	
	@Test
	public void testListAllUsers() {
		//List<User> listusers = (List<User>) repo.findAll();
		Iterable <User> listUsers = repo.findAll();
		listUsers.forEach(user-> System.out.println(user));
	 
	}
	
	@Test
	public void testGetUserById() {
		 User userNam = repo.findById(1).get();
		 System.out.println(userNam);
		 assertThat(userNam).isNotNull();	
		 }
	
	@Test
	public void testUpdateUserDetails() {
		 User userNam = repo.findById(1).get();
		 userNam.setEnabled(true);
		 userNam.setEmail("quick@do.com");
	
		 repo.save(userNam);
			
		 }
	
	@Test
	public void testUpdateUserRoles() {
		 User userRavi = repo.findById(5).get();
		 Role roleEditor = new Role(2);
		 Role roleAdmin = new Role(5);
		 
		 userRavi.getRoles().remove(roleEditor);
		 userRavi.addRole(roleAdmin);
		 repo.save(userRavi);
		 }
	
	@Test
	public void testDeleteUserDetails() {
		Integer userId = 5; 
		 repo.deleteById(userId);
			
		 }
	
	@Test
	public void testGetUserByEmail() {
		String email = "youngtheophile@gmail.com";
		User user =repo.getUserByEmail(email);
		assertThat(user).isNotNull();
	}
	
	@Test
	public void testCountByid() {
		Integer id =1;
		Long countById=repo.countById(id);
		assertThat(countById).isNotNull().isGreaterThan(0);
	}
	
	
	@Test void testListFirstPage() {
		int pageNumber = 0;
		int pageSize=4;
		
		Pageable pageable =PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		  
		assertThat(listUsers.size()).isEqualTo(pageSize);
		
	}
	
	
	@Test 
	public void testSearchUsers() {
		String keyword = "theo";
		int pageNumber = 0;
		int pageSize=4;
		
		Pageable pageable =PageRequest.of(pageNumber, pageSize);
		Page<User> page = repo.findAll(keyword,pageable);
		
		List<User> listUsers = page.getContent();
		listUsers.forEach(user -> System.out.println(user));
		assertThat(listUsers.size()).isGreaterThan(0);
	}
	
}

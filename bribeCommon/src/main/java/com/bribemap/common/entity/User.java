package com.bribemap.common.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


@Entity
@Table(name ="users")
public class User {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Column (length=128, nullable =false, unique=true)
	private String email;
	
	@Column (length=64, nullable =false)
	private String password;
	
	@Column (name ="first_name",length=45, nullable =false)
	private String firstName;
	
	@Column (name ="last_name",length=45, nullable =false)
	private String lastName;
	
	private Integer type;
	
	@Temporal(TemporalType.TIMESTAMP)
	    
	private Date createTime;
	    
	@Temporal(TemporalType.TIMESTAMP)
	private Date updateTime;
	
	
	@Column (length=64)
	private String photos;
	
	private boolean enabled;
	
	
	@OneToMany(mappedBy = "user")
    private List<Blog> blogs;
	
	//use to create an associate table between users and roles //fetch to match the role when cheking the login 
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "users_roles",
	joinColumns=@JoinColumn(name="user_id"),
	inverseJoinColumns = @JoinColumn(name ="role_id"))
	private Set<Role> roles = new HashSet<>();

	
	
	public User() {
	}
	
	public User(String email, String password, String firstName, String lastName) {
	
		this.email = email;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
	}



	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhotos() {
		return photos;
	}

	public void setPhotos(String photos) {
		this.photos = photos;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}
	
	
	public void addRole(Role role ) {
		this.roles.add(role);
	}

	 public Integer getType() {
	        return type;
	    }

	    public void setType(Integer type) {
	        this.type = type;
	    }

	    public Date getCreateTime() {
	        return createTime;
	    }

	    public void setCreateTime(Date createTime) {
	        this.createTime = createTime;
	    }

	    public Date getUpdateTime() {
	        return updateTime;
	    }

	    public void setUpdateTime(Date updateTime) {
	        this.updateTime = updateTime;
	    }

	    public List<Blog> getBlogs() {
	        return blogs;
	    }

	    public void setBlogs(List<Blog> blogs) {
	        this.blogs = blogs;
	    }

	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", type=" + type + ", createTime=" + createTime + ", updateTime="
				+ updateTime + ", photos=" + photos + ", enabled=" + enabled + ", blogs=" + blogs + ", roles=" + roles
				+ "]";
	}
	
	
	
	

}

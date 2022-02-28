package com.example.imageforme.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "image_users", uniqueConstraints = { 
								@UniqueConstraint(columnNames = "username"),
								@UniqueConstraint(columnNames = "email")})
public class ImageUser {

	@Id
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	@NotBlank
	@Size(min = 6)
	private String password;
	
//	@OneToMany(mappedBy = "imageUser", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//	private List<Image> images;
	
	public ImageUser() {
	}

	public ImageUser(String username, String email, String password) {
		this.username = username;
		this.email = email;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

//	public List<Image> getImages() {
//		return images;
//	}
//
//	public void setImages(List<Image> images) {
//		this.images = images;
//	}
	
	
}

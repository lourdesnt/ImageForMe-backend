package com.example.imageforme.controller;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.imageforme.model.ImageUser;
import com.example.imageforme.repository.ImageRepository;
import com.example.imageforme.repository.UserRepository;


@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600) //Es donde se va a abrir nuestro front
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private ImageRepository imageRepository;
	
	@GetMapping("/login")
	public ResponseEntity<ImageUser> login(@RequestParam String username, @RequestParam String password) {
		Optional<ImageUser> user = userRepository.findById(username);
		if (user.isPresent() && user.get().getPassword().equals(password)) {
			return new ResponseEntity<>(user.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PostMapping("/register")
	public ResponseEntity<ImageUser> register(@Valid @RequestBody ImageUser user){
		Optional<ImageUser> userToRegister = userRepository.findById(user.getUsername());
		if (userToRegister.isPresent()) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		} else {
			userRepository.save(user);
			return new ResponseEntity<>(user, HttpStatus.CREATED);
		}
	}
	
	@GetMapping("/user/{username}")
	public ResponseEntity<ImageUser> getUserByUsername(@PathVariable("username") String username) {
		Optional<ImageUser> actualUser = userRepository.findById(username);
		if (actualUser.isPresent()) {
			return new ResponseEntity<>(actualUser.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/user/{username}")
	public ResponseEntity<ImageUser> updateUser(@PathVariable("username") String username, @RequestBody ImageUser user) {
		Optional<ImageUser> actualUser = userRepository.findById(username);
		Optional<ImageUser> editedUser = userRepository.findById(user.getUsername());

		if (actualUser.isPresent()) {
			ImageUser userToEdit = actualUser.get();
			if(!editedUser.isPresent()) {
				//userToEdit.setUsername(user.getUsername());
				userToEdit.setEmail(user.getEmail());
				userToEdit.setPassword(user.getPassword());
				return new ResponseEntity<>(userRepository.save(userToEdit), HttpStatus.OK);
			} else {
				return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
			}
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@DeleteMapping("/user/{username}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("username") String username) {
		try {
			imageRepository.findByImageUser_Username(username).stream().forEach(i -> imageRepository.delete(i));
			userRepository.deleteById(username);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

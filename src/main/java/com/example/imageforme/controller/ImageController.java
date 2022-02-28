package com.example.imageforme.controller;

import java.util.List;
import java.util.Optional;

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
import org.springframework.web.bind.annotation.RestController;

import com.example.imageforme.model.Image;
import com.example.imageforme.repository.ImageRepository;
import com.example.imageforme.repository.UserRepository;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600) //Es donde se va a abrir nuestro front
@RestController
@RequestMapping("/api")
public class ImageController {

	@Autowired
	private ImageRepository imageRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@GetMapping("/images/{username}")
	public ResponseEntity<List<Image>> getAllImagesByUsername(@PathVariable("username") String username) {
		try {
			if (!userRepository.existsById(username)) {
					return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			    }
			
			List<Image> images = imageRepository.findByImageUser_Username(username);

			if (images.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(images, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/images/{username}/{id}")
	public ResponseEntity<Image> getImageById(@PathVariable("username") String username, @PathVariable("id") int id) {
		Optional<Image> imageData = imageRepository.findById((long) id);

		if (imageData.isPresent()) {
			return new ResponseEntity<>(imageData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/images/{username}/new")
	public ResponseEntity<Image> createImage(@PathVariable("username") String username, @RequestBody Image newImage) {
		try {
			Image image = userRepository.findById(username).map(user -> {
			      newImage.setImageUser(user); return imageRepository.save(newImage);}).get();
			
			return new ResponseEntity<>(image, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/images/{id}")
	public ResponseEntity<Image> updateImage(@PathVariable("id") long id, @RequestBody Image image) {
		Optional<Image> imagenData = imageRepository.findById(id);
		
		if (imagenData.isPresent()) {
			Image _image = imagenData.get();
			_image.setTitle(image.getTitle());
			_image.setLink(image.getLink());
			_image.setDescription(image.getDescription());
			return new ResponseEntity<>(imageRepository.save(_image), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@DeleteMapping("/images/{id}")
	public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id") long id) {
		try {
			imageRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}

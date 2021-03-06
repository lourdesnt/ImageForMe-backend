package com.example.imageforme.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.imageforme.model.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
	
	List<Image> findByImageUser_Username(String username);

}

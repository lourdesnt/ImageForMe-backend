package com.example.imageforme.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.imageforme.model.ImageUser;

@Repository
public interface UserRepository extends JpaRepository<ImageUser, String> {
	
	
}

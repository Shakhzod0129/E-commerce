package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProfileEntity;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity , Long> {


    Optional<ProfileEntity> findByEmail(String username);
}

package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.ProfileEntity;

import com.example.Ecommerce.enums.ProfileStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity , Long> {
    Optional<ProfileEntity> findByEmail(String username);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set password=?2 where id=?1")
    Integer updatePassword(Long profileId, String password);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String encode);

    @Transactional
    @Modifying
    @Query("Update ProfileEntity  set status =?2 where id = ?1")
    void updateStatus(Long id, ProfileStatus profileStatus);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set email=?2 where id=?1")
    void updateEmail(Long id, String email);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set attachId=?2 where id=?1")
    Integer updatePhoto(Long profileId, String id);
}

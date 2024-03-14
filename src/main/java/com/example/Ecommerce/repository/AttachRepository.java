package com.example.Ecommerce.repository;


import com.example.Ecommerce.entity.AttachEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Optional;

public interface AttachRepository extends CrudRepository<AttachEntity, String>,
        PagingAndSortingRepository<AttachEntity, String> {

    @NotNull
    Page<AttachEntity> findAll(@NotNull Pageable paging);

    @Query("from AttachEntity a where a.id=?1")
    Optional<AttachEntity> findAttachID(String attachId);

}
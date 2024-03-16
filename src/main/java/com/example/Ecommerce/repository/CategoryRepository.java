package com.example.Ecommerce.repository;

import com.example.Ecommerce.entity.CategoryEntity;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface CategoryRepository extends CrudRepository<CategoryEntity,Long> {

    @Modifying
    @Query("update CategoryEntity set visible=false where id=?1")
    void deleteById(@NotNull Long categoryId);


    @Query("from CategoryEntity where visible=true ")
    List<CategoryEntity> categoryList();

    @Query("from CategoryEntity where visible=true and parentId=?1 ")
    List<CategoryEntity> findByParentId(Long parentId);

//    @Query("from CategoryEntity where visible=true ")
//    List<CategoryEntity> findByNameContainingIgnoreCase(String query);

    @Query("from CategoryEntity where visible=true and lower(name) like lower(concat('%', :query, '%'))")
    List<CategoryEntity> findByNameContainingIgnoreCase(@Param("query") String query);

}

package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.category.CategoryDTO;
import com.example.Ecommerce.dto.category.CreateCategoryDTO;
import com.example.Ecommerce.entity.CategoryEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.enums.Status;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResourceBundleService resourceBundleService;
    public String create(CreateCategoryDTO dto, AppLanguage language) {
        CategoryEntity entity=new CategoryEntity();

        if(dto.getParentId()!=null){
            CategoryEntity categoryEntity = get(dto.getParentId(), language);
            entity.setParentId(categoryEntity.getId());
        }

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setStatus(Status.ACTIVE);
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setCreatedDate(LocalDateTime.now());

        categoryRepository.save(entity);
        return "Category has been created successfully.";
    }


    public String updateByCategoryId(Long categoryId, CreateCategoryDTO dto, AppLanguage language) {
        CategoryEntity entity = get(categoryId,language);
        if (dto.getParentId() != null) {
            CategoryEntity parentEntity = get(dto.getParentId(), language);
            entity.setParentId(parentEntity.getId());
        }

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setOrderNumber(dto.getOrderNumber());
        entity.setUpdatedDate(LocalDateTime.now());

        categoryRepository.save(entity);
        return "Category has been updated successfully.";
    }

    public String deleteByCategoryId(Long categoryId, AppLanguage language) {
        CategoryEntity categoryEntity = get(categoryId, language);

        categoryRepository.deleteById(categoryEntity.getId());
        return "Category has been deleted successfully.";
    }

    public List<CategoryDTO> getAllCategories() {
        List<CategoryEntity> categoryEntities = categoryRepository.categoryList();
        return categoryEntities.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public List<CategoryDTO> listByParentId(Long parentId) {
        List<CategoryEntity> categories = categoryRepository.findByParentId(parentId);
        return categories.stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

//    public List<CategoryDTO> searchCategories(String query, AppLanguage language) {
//        List<CategoryEntity> categories = categoryRepository.findByNameContainingIgnoreCase(query);
//        return categories.stream()
//                .map(this::mapToDTO)
//                .collect(Collectors.toList());
//    }

    public CategoryDTO getByLangById(Long categoryId, AppLanguage language){
        Optional<CategoryEntity> optional=categoryRepository.getCategoryEntityById(categoryId);
        if (optional.isEmpty()){
            log.warn("Category not found");
            throw new AppBadException(resourceBundleService.getMessage("category.not.found",language));
        }

        CategoryEntity categoryEntity = optional.get();
        CategoryDTO dto=new CategoryDTO();

        dto.setId(categoryEntity.getId());
        switch (language){
            case UZ -> dto.setName(categoryEntity.getNameUz());
            case RU -> dto.setName(categoryEntity.getNameRu());
            case EN -> dto.setName(categoryEntity.getNameEn());
        }
        return dto;

    }

    public List<CategoryDTO> getByLang(AppLanguage language) {
        List<CategoryEntity> categoryEntityList = categoryRepository.getCategoryEntitiesByOrderNumber();

        List<CategoryDTO> dtoList=new ArrayList<>();

        for (CategoryEntity categoryEntity : categoryEntityList) {
            CategoryDTO dto=new CategoryDTO();
            switch (language){
                case UZ -> dto.setName(categoryEntity.getNameUz());
                case RU -> dto.setName(categoryEntity.getNameRu());
                case EN -> dto.setName(categoryEntity.getNameEn());
            }
            dtoList.add(dto);
        }

        return dtoList;
    }



    private CategoryDTO mapToDTO(CategoryEntity categoryEntity) {
        CategoryDTO dto = new CategoryDTO();
        dto.setId(categoryEntity.getId());
        dto.setNameUz(categoryEntity.getNameUz());
        dto.setNameRu(categoryEntity.getNameRu());
        dto.setNameEn(categoryEntity.getNameEn());
        dto.setParentId(categoryEntity.getParentId());
        dto.setCreatedDate(categoryEntity.getCreatedDate());
        return dto;
    }
    private CategoryDTO toDTO(CategoryEntity categoryEntity) {
        CategoryDTO dto = new CategoryDTO();
//        dto.setId(categoryEntity.getId());
        dto.setNameUz(categoryEntity.getNameUz());
        dto.setNameRu(categoryEntity.getNameRu());
        dto.setNameEn(categoryEntity.getNameEn());
//        dto.setCreatedDate(categoryEntity.getCreatedDate());
        return dto;
    }

    public CategoryEntity get(Long categoryId, AppLanguage language){
        return categoryRepository.findById(categoryId).orElseThrow(() -> {
            log.warn("Category not found{}", categoryId);
            return new AppBadException(resourceBundleService.getMessage("category.not.found", language));
        });
    }
}

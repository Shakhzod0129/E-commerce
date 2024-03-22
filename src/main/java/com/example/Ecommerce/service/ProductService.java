package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.dto.product.CreateProductDTO;
import com.example.Ecommerce.dto.product.ProductDTO;
import com.example.Ecommerce.dto.product.ProductInfoMapper;
import com.example.Ecommerce.dto.product.UpdateProductDTO;
import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.dto.store.StoreInfoMapper;
import com.example.Ecommerce.entity.AttachEntity;
import com.example.Ecommerce.entity.ProductEntity;
import com.example.Ecommerce.entity.StoreEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.ProductRepository;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductAndAttachService productAndAttachService;
    @Autowired
    private ProductAndCategoryService productAndCategoryService;

    @Autowired
    private StoreService storeService;
    @Autowired
    private ResourceBundleService resourceBundleService;
    @Autowired
    private AttachService attachService;

    @Autowired
    private ProductAndTagNameService productAndTagNameService;


    public String create(Long profileId, CreateProductDTO dto, AppLanguage language) {

        ProductEntity entity = new ProductEntity();
        StoreEntity store = storeService.get(dto.getStoreId(), language);

        check(profileId, store.getProfileId(), language);

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setStoreId(store.getId());
        entity.setPrice(dto.getPrice());
        productRepository.save(entity);

        productAndCategoryService.create(entity.getId(), dto.getCategoryId(), language);
        productAndAttachService.create(entity.getId(), dto.getAttachId());
        productAndTagNameService.create(entity.getId(), dto.getTagName());



        return "Product has created";
    }

    public String update(Long productId, Long profileId, UpdateProductDTO dto, AppLanguage language) {

        ProductEntity entity = get(productId, language);
        Long profile = productRepository.findProfile(entity.getStoreId());

        check(profileId, profile, language);

        entity.setNameUz(dto.getNameUz());
        entity.setNameRu(dto.getNameRu());
        entity.setNameEn(dto.getNameEn());
        entity.setDescriptionUz(dto.getDescriptionUz());
        entity.setDescriptionRu(dto.getDescriptionRu());
        entity.setDescriptionEn(dto.getDescriptionEn());
        entity.setPrice(dto.getPrice());
        productRepository.save(entity);

        productAndCategoryService.merge(entity.getId(), dto.getCategoryId());
        productAndAttachService.merge(entity.getId(), dto.getAttachId());

        return "Product has edited successfully!!!";

    }

    public String delete(Long productId, Long profileId, AppLanguage language) {

        ProductEntity entity = get(productId, language);
        Long profile = productRepository.findProfile(entity.getStoreId());

        check(profileId, profile, language);


        productRepository.deleteById(entity.getId());
        return "Product has deleted successfully!!!";

    }

    public PageImpl<ProductDTO> pagination(Integer page, Integer size, AppLanguage language) {

        Pageable pageable = PageRequest.of(page - 1, size);

        Page<ProductInfoMapper> productInfo = productRepository.getProductInfo(pageable);

        List<ProductDTO> dtoList = new ArrayList<>();
        for (ProductInfoMapper productInfoMapper : productInfo) {
            dtoList.add(toProductDTO(productInfoMapper, language));
        }

        return new PageImpl<>(dtoList, pageable, productInfo.getTotalElements());
    }

    public PageImpl<ProductDTO> paginationByCategoryId(Integer page, Integer size, Long categoryId, AppLanguage language) {
        Pageable pageable = PageRequest.of(page - 1, size);

        Page<ProductInfoMapper> byCategoryId = productRepository.findByCategoryId(categoryId, pageable);

        List<ProductDTO> dtoList = new ArrayList<>();

        for (ProductInfoMapper productEntity : byCategoryId) {
            dtoList.add(toDTO(productEntity, language));
        }

        return new PageImpl<>(dtoList, pageable, byCategoryId.getTotalElements());
    }


    public ProductDTO toDTO(ProductInfoMapper mapper, AppLanguage language) {
        ProductDTO dto = new ProductDTO();
        dto.setId(mapper.getProductId());

        switch (language) {
            case UZ -> {
                dto.setDescription(mapper.getProductDescriptionUz());
                dto.setName(mapper.getProductNameUz());
            }
            case RU -> {
                dto.setDescription(mapper.getProductDescriptionRu());
                dto.setName(mapper.getProductNameRu());
            }
            case EN -> {
                dto.setDescription(mapper.getProductDescriptionEn());
                dto.setName(mapper.getProductNameEn());
            }
        }

        dto.setCountComments(mapper.getProductCommentCount());
        dto.setCountOrders(mapper.getProductOrderCount());
        dto.setPrice(mapper.getProductPrice());
        dto.setRate(mapper.getProductRate());
        dto.setQuantity(mapper.getProductQuantity());

        StoreEntity storeEntity = storeService.get(mapper.getProductStoreId(), language);
        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(storeEntity.getId());
        storeDTO.setName(storeEntity.getName());

        dto.setStore(storeDTO);

        return dto;
    }


    public ProductDTO toProductDTO(ProductInfoMapper productInfo, AppLanguage language) {
        ProductDTO dto = new ProductDTO();
        dto.setId(productInfo.getProductId());


        dto.setDescriptionUz(productInfo.getProductDescriptionUz());
        dto.setDescriptionRu(productInfo.getProductDescriptionRu());
        dto.setDescriptionEn(productInfo.getProductDescriptionEn());
        dto.setNameUz(productInfo.getProductNameUz());
        dto.setNameRu(productInfo.getProductNameRu());
        dto.setNameEn(productInfo.getProductNameEn());
        dto.setCountComments(productInfo.getProductCommentCount());
        dto.setCountOrders(productInfo.getProductOrderCount());
        dto.setPrice(productInfo.getProductPrice());
        dto.setRate(productInfo.getProductRate());
        dto.setQuantity(productInfo.getProductQuantity());

        String productAttachJson = productInfo.getProductAttachJson();
        if (productAttachJson != null && !productAttachJson.isEmpty()) {
            dto.setProductAttachJson(productAttachJson);
        }
        String productAttributeJson = productInfo.getProductAttributeJson();
        if (productAttributeJson != null && !productAttributeJson.isEmpty()) {
            dto.setProductAttributeJson(productAttributeJson);
        }
        String productCategoryJson = productInfo.getProductCategoryJson();
        if (productCategoryJson != null && !productCategoryJson.isEmpty()) {
            dto.setProductCategoryJson(productCategoryJson);
        }
        StoreEntity storeEntity = storeService.get(productInfo.getProductStoreId(), language);

        StoreDTO storeDTO = new StoreDTO();
        storeDTO.setId(storeEntity.getId());
        storeDTO.setName(storeEntity.getName());

        AttachEntity attachEntity = attachService.get(storeEntity.getAttachId());
        AttachDTO attachDTO = new AttachDTO();

        attachDTO.setId(attachEntity.getId());
        attachDTO.setUrl(attachEntity.getUrl());
        storeDTO.setAttach(attachDTO);

        dto.setStore(storeDTO);
        return dto;
    }


    private void check(Long profileId, Long storeOwner, AppLanguage language) {

        if (!profileId.equals(storeOwner)) {
            log.warn("You don't have permission");
            throw new AppBadException(resourceBundleService.getMessage("store.not.allowed", language));
        }
    }

    public ProductEntity get(Long productId, AppLanguage language) {
        return productRepository.findById(productId).orElseThrow(() -> {
            log.warn("Store not found{}", productId);
            return new AppBadException(resourceBundleService.getMessage("product.not.found", language));
        });
    }
}

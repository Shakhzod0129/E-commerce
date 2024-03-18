package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.ProfileDTO;
import com.example.Ecommerce.dto.extre.AttachDTO;
import com.example.Ecommerce.dto.store.CreateStoreDTO;
import com.example.Ecommerce.dto.store.StoreDTO;
import com.example.Ecommerce.dto.store.StoreInfoMapper;
import com.example.Ecommerce.dto.store.UpdateStoreDTO;
import com.example.Ecommerce.entity.AttachEntity;
import com.example.Ecommerce.entity.ProfileEntity;
import com.example.Ecommerce.entity.StoreEntity;
import com.example.Ecommerce.enums.AppLanguage;
import com.example.Ecommerce.enums.ProfileRole;
import com.example.Ecommerce.exp.AppBadException;
import com.example.Ecommerce.repository.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private ProfileService profileService;

    @Autowired
    private AttachService attachService;

    @Autowired
    private ResourceBundleService resourceBundleService;

    public String create(CreateStoreDTO dto, AppLanguage language) {
        StoreEntity entity = new StoreEntity();
        ProfileEntity profileEntity = profileService.get(dto.getProfileId(), language);
        AttachEntity attachEntity = attachService.get(dto.getAttachId());

        entity.setName(dto.getName());
        entity.setProfileId(profileEntity.getId());
        entity.setDescription(dto.getDescription());
        entity.setAttachId(attachEntity.getId());
        storeRepository.save(entity);


        return "Category has been created successfully.";
    }


    public String updateStoreByOwner(Long storeId, Long profileId, UpdateStoreDTO dto, AppLanguage language) {
        Optional<StoreEntity> optional = storeRepository.findByIdAndProfileId(storeId, profileId);

        if (optional.isEmpty()) {
            log.warn("Store not found");
            throw new AppBadException(resourceBundleService.getMessage("store.not.allowed", language));
        }

        StoreEntity entity = optional.get();

        // Ma'lumotlarni yangilash
        if (dto.getName() != null) {
            entity.setName(dto.getName());
        }
        if (dto.getDescription() != null) {
            entity.setDescription(dto.getDescription());
        }
        if (dto.getAttachId() != null) {
            AttachEntity attachEntity = attachService.get(dto.getAttachId());
            entity.setAttachId(attachEntity.getId());
        }

        // Updated date ni yangilash
        entity.setUpdatedDate(LocalDateTime.now());

        // StoreEntity ni saqlash
        storeRepository.save(entity);

        return "Store has been updated successfully.";
    }

    public List<StoreDTO> getList(AppLanguage language){
        Iterable<StoreEntity> all = storeRepository.findAll();
        List<StoreDTO> dtoList=new ArrayList<>();

        for (StoreEntity entity : all) {
            dtoList.add(toDTO(entity,language));
        }

        return dtoList;
     }

    public PageImpl<StoreDTO> pagination(Integer page, Integer size, AppLanguage language) {
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<StoreInfoMapper> pageResult = storeRepository.getStoreInfo(pageable);

        List<StoreDTO> storeDTOs = pageResult.getContent().stream()
                .map(storeInfoMapper -> convertToDTO(storeInfoMapper, language))
                .collect(Collectors.toList());

        return new PageImpl<>(storeDTOs, pageable, pageResult.getTotalElements());
    }

    private StoreDTO toDTO(StoreEntity entity,AppLanguage language){
        StoreDTO dto=new StoreDTO();

        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setAttachId(entity.getAttachId());
        dto.setStatus(entity.getStatus());
        dto.setProfileId(entity.getProfileId());
        dto.setCountOfComment(entity.getCommentCount());
        dto.setCreatedDate(entity.getCreatedDate());
        dto.setQuantityOfOrder(entity.getQuantityOfOrder());
        dto.setQuantityOfProduct(entity.getQuantityOfProduct());
        dto.setRateOfProducts(entity.getRete());
        return dto;
    }
    public List<StoreDTO> getStoresByProfileId(Long profileId, AppLanguage language) {
        ProfileEntity profileEntity = profileService.get(profileId, language);
        List<StoreEntity> storeEntities = storeRepository.findByProfileId(profileEntity.getId());
        return storeEntities.stream()
                .map(storeEntity -> toDTO(storeEntity, language))
                .collect(Collectors.toList());
    }

    private StoreDTO convertToDTO(StoreInfoMapper storeInfoMapper, AppLanguage language) {
        StoreDTO storeDTO = new StoreDTO();


        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setId(storeInfoMapper.getProfileId());
        profileDTO.setName(storeInfoMapper.getProfileName());
        profileDTO.setSurname(storeInfoMapper.getProfileSurname());
        profileDTO.setGender(storeInfoMapper.getProfileGender());
        profileDTO.setPhoneNumber(storeInfoMapper.getProfilePhoneNumber());

        if (storeInfoMapper.getProfileAttachId() != null) {
            AttachEntity attachEntity = attachService.get(storeInfoMapper.getProfileAttachId());
            AttachDTO attachDTO = new AttachDTO();
            attachDTO.setId(attachEntity.getId());
            attachDTO.setUrl(attachEntity.getUrl());
            profileDTO.setAttach(attachDTO);
        }


        storeDTO.setId(storeInfoMapper.getStoreId());
        storeDTO.setName(storeInfoMapper.getStoreName());
        storeDTO.setProfile(profileDTO);
        if(storeInfoMapper.getStoreAttachId()!=null){

            AttachEntity attachEntity = attachService.get(storeInfoMapper.getStoreAttachId());
            AttachDTO attachDTO=new AttachDTO();
            attachDTO.setId(attachEntity.getId());
            attachDTO.setUrl(attachEntity.getUrl());
            storeDTO.setAttach(attachDTO);
        }
        storeDTO.setDescription(storeInfoMapper.getStoreDescription());
        storeDTO.setCountOfComment(storeInfoMapper.getStoreCommentCount());
        storeDTO.setStatus(storeInfoMapper.getStoreStatus());
        storeDTO.setQuantityOfProduct(storeInfoMapper.getStoreQuantityOfProduct());
        storeDTO.setQuantityOfOrder(storeInfoMapper.getStoreQuantityOfOrder());
        storeDTO.setCreatedDate(storeInfoMapper.getStoreCreatedDate());

        // Set other properties as needed
        return storeDTO;
    }

    public StoreEntity get(Long storeId, AppLanguage language) {
        return storeRepository.findById(storeId).orElseThrow(() -> {
            log.warn("Store not found{}", storeId);
            return new AppBadException(resourceBundleService.getMessage("store.not.found", language));
        });
    }
}

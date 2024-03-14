package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.AttributeDTO;
import com.example.Ecommerce.entity.AttributeEntity;
import com.example.Ecommerce.repository.AttributeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AttributeService {

    @Autowired
    private AttributeRepository attributeRepository;

    public String  create(AttributeDTO attributeDTO) {
        AttributeEntity entity=new AttributeEntity();

        entity.setAttributeName(attributeDTO.getName());

        attributeRepository.save(entity);

        return "Attribute has created";

    }


    public List<AttributeDTO> getList(){
        Iterable<AttributeEntity> all = attributeRepository.findAll();

        List<AttributeDTO> dtos=new ArrayList<>();

        for (AttributeEntity attributeEntity : all) {
            AttributeDTO dto=new AttributeDTO();

            dto.setId(attributeEntity.getId());
            dto.setName(attributeEntity.getAttributeName());

            dtos.add(dto);
        }

        return dtos;
    }
}

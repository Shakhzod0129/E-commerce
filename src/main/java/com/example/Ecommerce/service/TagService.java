package com.example.Ecommerce.service;

import com.example.Ecommerce.dto.tag.TagDTO;
import com.example.Ecommerce.entity.TagEntity;
import com.example.Ecommerce.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TagService {

    @Autowired
    private TagRepository tagRepository;

    public String create(TagDTO dto){
        TagEntity entity=new TagEntity();

        entity.setName(dto.getTagName());
        entity.setCreatedDate(LocalDateTime.now());

        tagRepository.save(entity);
        return "Tag has created successfully";
    }
}

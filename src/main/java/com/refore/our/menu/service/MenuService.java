package com.refore.our.menu.service;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.entity.MenuEntity;
import com.refore.our.menu.mapper.MenuTransMapper;
import com.refore.our.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;


    public void menuIndex(MenuDto menuDto) {


    }
    @Transactional
    public void menuInsert(MenuDto menuDto) {
        MenuEntity menuEntity = MenuTransMapper.INSTANCE.dtoToEntity(menuDto);
        menuRepository.save(menuEntity);
    }
    @Transactional
    public void menuDelete(MenuDto menuDto) {
        MenuEntity menuEntity = MenuTransMapper.INSTANCE.dtoToEntity(menuDto);
        try {
            menuRepository.delete(menuEntity);
        } catch (EmptyResultDataAccessException ex) {

        }


    }
}

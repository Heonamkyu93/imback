package com.refore.our.menu.service;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.entity.MenuEntity;
import com.refore.our.menu.exception.MenuNotFoundException;
import com.refore.our.menu.mapper.MenuTransMapper;
import com.refore.our.menu.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MenuService {

    private final MenuRepository menuRepository;


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
        } catch (MenuNotFoundException exception) {
            throw  exception;
        }


    }
    @Transactional
    public void menuUpdate(MenuDto menuDto) {
        try {
            MenuEntity menuEntity = menuRepository.findById(menuDto.getBuggerId())
                    .orElseThrow(() -> new MenuNotFoundException());

            menuEntity.setBuggerName(menuDto.getBuggerName());
            menuEntity.setBuggerInfo(menuDto.getBuggerInfo());
            menuEntity.setBuggerPrice(menuDto.getBuggerPrice());

            // 변경된 메뉴를 저장
            menuRepository.save(menuEntity);
        } catch (MenuNotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw new RuntimeException("메뉴 업데이트 중에 오류가 발생했습니다.", e);
        }
    }

    public void menuIndex() {


    }
}

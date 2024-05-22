package com.refore.our.menu.service;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.entity.MenuEntity;
import com.refore.our.menu.exception.MenuNotFoundException;
import com.refore.our.menu.mapper.MenuTransMapper;
import com.refore.our.menu.repository.MenuRepository;
import com.refore.our.menu.util.FileSave;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileSave fileSave;

    @Transactional
    public void menuInsert(MenuDto menuDto) {
        String serverName=fileSave.imgSave(menuDto.getImageFile());
        MenuEntity menuEntity = MenuTransMapper.INSTANCE.dtoToEntity(menuDto);
        menuEntity.setImgRealName(menuDto.getImageFile().getOriginalFilename());
        menuEntity.setImgServerName(serverName);
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

    public Page<MenuDto> menuIndex(Pageable pageable) {
        Page<MenuDto> menuDtoPage = menuRepository.findAll(pageable).map(MenuTransMapper.INSTANCE::entityToDto);
        menuDtoPage.getContent();

        return menuDtoPage;
    }
}

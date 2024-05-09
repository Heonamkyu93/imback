package com.refore.our.menu.mapper;

import com.refore.our.menu.dto.MenuDto;
import com.refore.our.menu.entity.MenuEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MenuTransMapper {


    MenuTransMapper INSTANCE = Mappers.getMapper(MenuTransMapper.class);


    MenuDto entityToDto(MenuEntity menuEntity);

    MenuEntity dtoToEntity(MenuDto menuDto);


}

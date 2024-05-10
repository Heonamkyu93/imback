package com.refore.our.menu.exception;

public class MenuNotFoundException extends RuntimeException{
    public MenuNotFoundException(){
        super("없는 메뉴이거나 이미 삭제된 메뉴입니다.");
    }
}

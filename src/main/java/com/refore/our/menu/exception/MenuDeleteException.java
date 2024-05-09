package com.refore.our.menu.exception;

public class MenuDeleteException extends RuntimeException{
    public MenuDeleteException(){
        super("없는 메뉴이거나 이미 삭제된 메뉴입니다.");
    }
}

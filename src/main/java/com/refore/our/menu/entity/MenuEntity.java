package com.refore.our.menu.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class MenuEntity {



    @Id
    private Long buggerId;


    private String buggerName;
    private String buggerPrice;
    private String buggerInfo;
}

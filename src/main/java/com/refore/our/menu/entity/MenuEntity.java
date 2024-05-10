package com.refore.our.menu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "bugger")
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MenuEntity {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long buggerId;
    private String buggerName;
    private String buggerPrice;
    private String buggerInfo;
}

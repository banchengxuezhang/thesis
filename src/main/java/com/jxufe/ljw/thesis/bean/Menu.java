package com.jxufe.ljw.thesis.bean;

import lombok.Data;

/**
 * @Classname Menu
 * @Author: LeJunWen
 * @Date: 2020/2/27 13:19
 */
@Data
public class Menu {
    private String menuId;

    private String menuText;

    private String menuBelong;

    private String menuStatus;

    private String menuIcon;

    private String menuUrl;
    private double menuWeight;
}

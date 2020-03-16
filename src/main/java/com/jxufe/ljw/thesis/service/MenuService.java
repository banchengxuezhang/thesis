package com.jxufe.ljw.thesis.service;

import com.baomidou.mybatisplus.service.IService;
import com.jxufe.ljw.thesis.bean.Menu;


import java.util.List;
import java.util.Map;

public interface MenuService {
    List<Menu> getMenyBymenuBelong(String menuBelong);
    List<Menu> getMenuByStatus(int menuStatus);

    Map<String, Object> selectAllMenu(Menu menu, int page, int rows);

    int updateMenuByMenuId(Menu menu);
    Menu getMenu(String menuId);
}

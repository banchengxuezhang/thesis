package com.jxufe.ljw.thesis.service;

import com.baomidou.mybatisplus.service.IService;
import com.jxufe.ljw.thesis.bean.Menu;


import java.util.List;
import java.util.Map;

public interface MenuService extends IService<Menu> {
    List<Menu> getMenyBymenuBelong(String menuBelong);

    Map<String, Object> selectAllMenu(Menu menu, int page, int rows);
}

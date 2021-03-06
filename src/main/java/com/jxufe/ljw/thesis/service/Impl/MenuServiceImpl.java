package com.jxufe.ljw.thesis.service.Impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.google.common.collect.Maps;
import com.jxufe.ljw.thesis.bean.Menu;
import com.jxufe.ljw.thesis.dao.MenuDao;
import com.jxufe.ljw.thesis.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: 84644
 * @Date: 2019/4/7 23:38
 * @Description:
 **/
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> getMenyBymenuBelong(String menuBelong) {
        return menuDao.getMenyBymenuBelong(menuBelong);
    }

    @Override
    public List<Menu> getMenuByStatus(int menuStatus) {
        return menuDao.getMenuByStatus(menuStatus);
    }

    @Override
    public Map<String, Object> selectAllMenu(Menu menu, int page, int rows) {
        Map<String, Object> map = Maps.newHashMap();
        Page<Menu> pageRecord = new Page<>(page, rows);
        List<Menu> menuList = menuDao.selectAllMenu(menu, pageRecord);
        map.put("total", pageRecord.getTotal());
        map.put("rows", menuList);
        return map;
    }

    @Override
    public int updateMenuByMenuId(Menu menu) {
        return menuDao.updateMenuByMenuId(menu);
    }

    @Override
    public Menu getMenu(String menuId) {
        return menuDao.getMenu(menuId);
    }

}

package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface MenuDao {
    List<Menu> getMenyBymenuBelong(String menuBelong);
    List<Menu> getMenuByStatus(int menuStatus);

    List<Menu> selectAllMenu(Menu menu, Pagination page);

    int updateMenuByMenuId(Menu menu);

}

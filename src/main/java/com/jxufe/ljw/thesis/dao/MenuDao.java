package com.jxufe.ljw.thesis.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.jxufe.ljw.thesis.bean.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MenuDao extends BaseMapper<Menu> {
    List<Menu> getMenyBymenuBelong(String menuBelong);

    List<Menu> selectAllMenu(Menu menu, Pagination page);

}

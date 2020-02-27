package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.Menu;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.MenuService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Author: 84644
 * @Date: 2019/4/7 23:28
 * @Description:
 **/
@Controller
@RequestMapping("/menu")
public class MenuController {
    private static final Logger LOG = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    @RequestMapping("/getMenuByMenuBelong")
    @ResponseBody
    public Object getMenu(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        LOG.info("==================开始查询菜单列表==================");
        try {
            List<Menu> menuList = menuService.getMenyBymenuBelong(String.valueOf(user.getUserType()));
            LOG.info("==================查询菜单列表结束，菜单列表：{}==================", menuList);
            return ResultUtil.success(menuList);
        } catch (Exception e) {
            LOG.error("==================查询菜单列表异常，e：{}==================", e);
            return ResultUtil.error("查询菜单列表异常");
        }
    }

    @RequestMapping("/list")
    @ResponseBody
    public Object list(Menu menu, int page, int rows) {
        LOG.info("==================开始查询菜单列表==================");
        try {
            Map<String, Object> res = menuService.selectAllMenu(menu, page, rows);
            LOG.info("==================查询菜单列表完成，列表：{}==================", res);
            return ResultUtil.success(res);
        } catch (Exception e) {
            LOG.error("==================查询菜单列表异常==================");
        }
        return ResultUtil.dataGridEmptyResult();
    }

    @RequestMapping("updateMenuStatus")
    @ResponseBody
    public Object updateMenuStatus(Menu menu) {
        LOG.info("==================开始更改菜单状态，菜单信息：{}==================", menu);
        try {
            menuService.updateById(menu);
            LOG.info("==================更改菜单状态完成==================");
            return ResultUtil.success("更改菜单状态成功");
        } catch (Exception e) {
            LOG.error("==================更改菜单状态异常,e={}==================", e);
            return ResultUtil.error("更改菜单状态异常");
        }
    }
}

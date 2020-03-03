package com.jxufe.ljw.thesis.controller;

import com.jxufe.ljw.thesis.bean.Menu;
import com.jxufe.ljw.thesis.bean.User;
import com.jxufe.ljw.thesis.service.MenuService;
import com.jxufe.ljw.thesis.vo.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @Classname MenuController
 * @Author: LeJunWen
 * @Date: 2020/2/29 18:31
 */
@RestController
@RequestMapping("/menu")
public class MenuController {
    private static final Logger logger = LoggerFactory.getLogger(MenuController.class);

    @Autowired
    private MenuService menuService;

    /**
     * 根据角色获取功能权限
     *
     * @param request 请求
     * @return
     */
    @GetMapping("/getMenuByMenuBelong")
    public Object getMenu(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        try {
            List<Menu> menuList = menuService.getMenyBymenuBelong(String.valueOf(user.getUserType()));
            return ResultUtil.success(menuList);
        } catch (Exception e) {
            return ResultUtil.error("查询菜单列表异常");
        }
    }

    /**
     * 管理员获取所有角色功能详情
     *
     * @param menu 菜单（选择条件）
     * @param page 页码
     * @param rows 条数
     * @return
     */
    @GetMapping("/list")
    public Object list(Menu menu, int page, int rows) {
        try {
            Map<String, Object> res = menuService.selectAllMenu(menu, page, rows);
            logger.info("查看menuId" + res);
            return ResultUtil.success(res);
        } catch (Exception e) {
            return ResultUtil.dataGridEmptyResult();
        }

    }

    /**
     * 管理员控制角色功能
     *
     * @param menu 菜单
     * @return
     */
    @Transactional
    @PostMapping("/updateMenuStatus")
    public Object updateMenuStatus(Menu menu) {
        try {
            logger.info("查看Menu信息" + menu);
            menuService.updateMenuByMenuId(menu);
            return ResultUtil.success("更改菜单状态成功");
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return ResultUtil.error("更改菜单状态异常");
        }
    }
}

package com.jxufe.ljw.thesis.util;

import com.jxufe.ljw.thesis.bean.TeacherInfo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * @Classname RandomGroup
 * @Author: LeJunWen
 * @Date: 2020/3/13 20:42
 */
@Component
public class RandomGroup {
    /**
     * 获取随机数
     */
    public static int getRandom(int i) {
        Random r = new Random();
        return r.nextInt(i);
    }

    /**
     * 进行分组 els 需要进行分组的成员 groups 需要分成几组
     */
    public static List<List<TeacherInfo>> getGroup(List<TeacherInfo> els, int groups) {
        // 判断验证
        if (els.size() < (groups * 2)) {
            System.out.println("分组数过多! 最多只能分" + (els.size() / 2) + "组");
            return null;
        }
        if (groups == 1) {
            System.out.println("分组数不能为1组");
            return null;
        }
        // 数据源的list
        List<TeacherInfo> list = new ArrayList<TeacherInfo>();
        // 作为结果返回的list
        List<List<TeacherInfo>> groupsList = new ArrayList<List<TeacherInfo>>();
        // List<Map<String, String>> groupsList = new ArrayList<List<String>>();
        // 往数据源里面添加数据
        for (int i = 0; i < els.size(); i++) {
            list.add(els.get(i));
        }
        // 随机打乱一下顺序
        Collections.shuffle(list);
        // 计算一下每组多少人
        int peoples = els.size() / groups;
        // 分组开始
        for (int i = 0; i < groups; i++) {
            List<TeacherInfo> group = new ArrayList<TeacherInfo>();
            for (int j = 0; j < peoples; j++) {
                int random = getRandom(list.size());
                group.add(list.get(random));
                list.remove(random);
            }
            groupsList.add(group);
        }
        // 最后剩下的人再重新分配一遍
        for (int i = 0; i < list.size(); i++) {
            groupsList.get(i).add(list.get(i));
        }
        return groupsList;
    }
}

package com.hdb.hrsguard.util;


import com.hdb.hrsguard.entity.admin.Menu;

import java.util.ArrayList;
import java.util.List;

public class MenuUtil {
    /**
     * 获取所有的顶级菜单类
     * @param menus
     * @return
     */
    public static List<Menu> getTopMenus(List<Menu> menus){
        List<Menu> topMenus = new ArrayList<Menu>();
        for(Menu menu : menus){
            if(menu.getParent() == null){
                topMenus.add(menu);
            }
        }
        return topMenus;
    }
    /**
     * 获取二级菜单分类
     * @param menus
     * @return
     */
    public static List<Menu> getSecondMenus(List<Menu> menus){
        List<Menu> secondMenus = new ArrayList<Menu>();
        for(Menu menu : menus){
            if(menu.getParent() != null && menu.getParent().getParent() == null){
                secondMenus.add(menu);
            }
        }
        return secondMenus;
    }
    /**
     * 获取三级菜单分类
     * @param menus
     * @return
     */
    public static List<Menu> getThirdMenus(List<Menu> menus){
        List<Menu> thirdMenus = new ArrayList<Menu>();
        for(Menu menu : menus){
            if(menu.getParent() != null && menu.getParent().getParent() != null){
                thirdMenus.add(menu);
            }
        }
        return thirdMenus;
    }

}

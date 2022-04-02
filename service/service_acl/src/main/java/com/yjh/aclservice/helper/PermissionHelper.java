package com.yjh.aclservice.helper;

import com.yjh.aclservice.entity.Permission;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 根据权限数据构建菜单数据
 * </p>
 *
 * @author qy
 * @since 2019-11-11
 */
public class PermissionHelper {

    /**
     * 使用递归方法建菜单
     * @param treeNodes
     * @return
     */
    public static List<Permission> bulid(List<Permission> treeNodes) {
        List<Permission> trees = new ArrayList<>();
        for (Permission treeNode : treeNodes) {
            if ("0".equals(treeNode.getPid())) {
                treeNode.setLevel(1);
                trees.add(findChildren(treeNode,treeNodes));
            }
        }
        return trees;
    }

    /**
     * 递归查找子节点
     * @param treeNodes
     * @return
     */
    public static Permission findChildren(Permission treeNode,List<Permission> treeNodes) {
        //因为向一层菜单里面放二层菜单，二层里面还要放三层，把对象初始化
        treeNode.setChildren(new ArrayList<Permission>());

        //遍历所有菜单list集合，进行判断比较，比较id和pid值是否相同
        for (Permission it : treeNodes) {
            // 判断id和pid值是否相同
            if(treeNode.getId().equals(it.getPid())) {
                // 把父菜单的level值+1
                int level = treeNode.getLevel() + 1;
                it.setLevel(level);
                //如果children为空，进行初始化操作
                if (treeNode.getChildren() == null) {
                    treeNode.setChildren(new ArrayList<>());
                }
                //把查询出来的子菜单放到父菜单里面
                treeNode.getChildren().add(findChildren(it,treeNodes));
            }
        }
        return treeNode;
    }
}

package com.anju.yyk.common.impl;
/**
 * 
 * @author wcs
 * 
 * @Package com.leo.common.impl
 * 
 * @Description Toolbar监听
 * 
 * @Date 2019/4/29 15:32
 * 
 * @modify:
 */
public interface ToolbarListener {
    /**
     * 左边按钮监听
     */
    void leftTvListener();

    /**
     * 右边按钮监听
     */
    void rightTvListener();

    /**
     * 左侧图片监听
     */
    void leftIvListener();

    /**
     * 右侧图片监听
     */
    void rightIvListener();
}

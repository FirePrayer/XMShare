package com.merpyzf.filemanager.widget;

import android.view.View;

import com.merpyzf.filemanager.widget.bean.Label;

/**
 * Created by merpyzf on 2018/3/17.
 */

public interface IFileSelectorIndicator {

    /**
     * 添加一个目录
     * @param path 目录的绝对路径
     */
    void add(Label path);

    /**
     * 移除一个目录
     * @param path 目录的绝对路径
     */
    void remove(Label path);


    /**
     * 设置内部/外部存储设备的根目录的路径
     * @param path 路径
     */
    void setBaseStoragePath(Label path);


    /**
     * 创建一个指示器中要显示的View
     * @return
     */
    View createIndicatorView(Label dirName);



}

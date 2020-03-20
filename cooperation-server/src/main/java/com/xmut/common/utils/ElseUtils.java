package com.xmut.common.utils;

import com.xmut.modules.sys.entity.SysUserEntity;

import java.util.List;

/**
 * 一些Controller里可能用到的工具类
 */
public class ElseUtils {

    /*
     * ReportDetailController的list方法用到，主要是把
     * 多个用户的用户名拼接成 “admin、test” 这种形式的字符串
     */
    public static String formatExecutors(List<SysUserEntity> executors) {
        if (executors.size() == 1) {
            return executors.get(0).getUsername();
        }

        String executorsStr = "";
        for (int i=0; i<executors.size(); i++) {
            if (i == executors.size() - 1) {
                executorsStr = executorsStr + executors.get(i).getUsername();
            } else {
                executorsStr = executorsStr + executors.get(i).getUsername() + "、";
            }
        }

        return executorsStr;
    }
}

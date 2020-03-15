package com.xmut.modules.coordination.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

/**
 * @author whf
 */
@Data
@TableName("tb_report")
public class ReportEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 报告id
     */
    @TableId
    private String id;

    /**
     * 报告名称
     */
    private String name;

    /**
     * 报告简介
     */
    private String reportInfo;

    /**
     * 报告负责人
     */
    private String leader;

    /**
     * 报告类型，预留字段
     */
    private String type;

    /**
     * 报告创建时间（开始时间）
     */
    private String createTime;

    /**
     * 报告预计完成时间
     */
    private String predictTime;

    /**
     * 报告完成时间
     */
    private String finishTime;

    /**
     * 报告状态，进行中完成01
     */
    private String status;
}

package com.xmut.modules.coordination.entity.workbenchEntity;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作台 - 参与过的报告
 */
@Data
public class WorkbenchFinishReportEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reportName;

	private String reportInfo;
}

package com.xmut.modules.coordination.entity.workbenchEntity;

import lombok.Data;

import java.io.Serializable;

/**
 * 工作台 - 进行中的指标
 */
@Data
public class WorkbenchCurrentReportIndxEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String reportName;

	private String indxName;
}

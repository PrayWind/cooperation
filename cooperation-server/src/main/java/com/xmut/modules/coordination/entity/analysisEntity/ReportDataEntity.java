package com.xmut.modules.coordination.entity.analysisEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 报告分析 - 报告部分
 */
@Data
public class ReportDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer totalReportNum;

	private Integer currentReportNum;

	private List<HashMap<String, String>> rows;
}

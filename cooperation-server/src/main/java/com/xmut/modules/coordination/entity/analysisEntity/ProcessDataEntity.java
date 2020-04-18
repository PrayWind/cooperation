package com.xmut.modules.coordination.entity.analysisEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 报告分析 - 完成进度
 */
@Data
public class ProcessDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private String currentProcess;

	private String totalProcess;
}

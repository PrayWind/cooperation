package com.xmut.modules.coordination.entity.analysisEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 报告分析 - 各月份报告指标部分
 */
@Data
public class TotalDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

    private List<HashMap<String, String>> rows;
}

package com.xmut.modules.coordination.entity.analysisEntity;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

/**
 * 报告分析 - 指标部分
 */
@Data
public class IndxDataEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer totalIndxNum;

	private Integer currentIndxNum;

	private List<HashMap<String, String>> rows;
}

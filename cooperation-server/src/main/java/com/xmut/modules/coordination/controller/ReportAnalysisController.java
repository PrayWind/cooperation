package com.xmut.modules.coordination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.DateUtils;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.ReportEntity;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.entity.analysisEntity.IndxDataEntity;
import com.xmut.modules.coordination.entity.analysisEntity.ProcessDataEntity;
import com.xmut.modules.coordination.entity.analysisEntity.ReportDataEntity;
import com.xmut.modules.coordination.entity.analysisEntity.TotalDataEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 报告分析部分Controller
 */
@RestController
@RequestMapping("/reportAnalysis")
public class ReportAnalysisController extends AbstractController {

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportIndxService reportIndxService;

    @GetMapping("/list")
    public R list() {
        List<ReportEntity> reportList = reportService.list();

        // 获取各月份 报告数量
        ReportDataEntity reportData = new ReportDataEntity();
        reportData.setRows(new ArrayList<HashMap<String, String>>());
        for (int i = 1; i < 13; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("month", i + "月");
            item.put("reportNum", "0");
            reportData.getRows().add(item);
        }
        reportData = getReportNum(reportList, reportData);


        // 获取各月份 指标数量
        IndxDataEntity indxData = new IndxDataEntity();
        indxData.setRows(new ArrayList<HashMap<String, String>>());
        for (int i = 1; i < 13; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            item.put("month", i + "月");
            item.put("indxNum", "0");
            indxData.getRows().add(item);
        }
        indxData = getIndxNum(reportList, indxData);


        // 获取各月份的 报告和指标数量
        TotalDataEntity totalData = new TotalDataEntity();
        totalData.setRows(new ArrayList<HashMap<String, String>>());
        for (int i = 1; i < 13; i++) {
            HashMap<String, String> item = new HashMap<String, String>();
            totalData.getRows().add(item);
        }
        List<HashMap<String, String>> totalDataRows = totalData.getRows();
        List<HashMap<String, String>> reportDataRows = reportData.getRows();
        List<HashMap<String, String>> indxDataRows = indxData.getRows();
        for (int i = 1; i < 13; i++) {
            totalDataRows.get(i - 1).putAll(reportDataRows.get(i - 1));
            totalDataRows.get(i - 1).putAll(indxDataRows.get(i - 1));
        }
        totalData.setRows(totalDataRows);


        // 获取完成进度
        ProcessDataEntity processData = new ProcessDataEntity();
        processData = getProcess(reportList, processData);

        return R.ok()
                .put("reportData", reportData)
                .put("indxData", indxData)
                .put("totalData", totalData)
                .put("processData", processData);
    }

    // 计算完成的进度
    private ProcessDataEntity getProcess(List<ReportEntity> reportList, ProcessDataEntity processData) {
        int totalReportNum = reportList.size();
        int totalFinishReportNum = 0;
        int currentReportNum = 0;
        int currentFinishReportNum = 0;
        String currentProcess = "";
        String totalProcess = "";
        for (ReportEntity temp : reportList) {
            String month = temp.getCreateTime().split("\\-")[1].replaceFirst("^0*", "");
            if ("1".equals(temp.getStatus())) {
                totalFinishReportNum = totalFinishReportNum + 1;
            }
            if (DateUtils.getCurrentMonth().equals(month)) {
                currentReportNum = currentReportNum + 1;
                if ("1".equals(temp.getStatus())) {
                    currentFinishReportNum = currentFinishReportNum + 1;
                }
            }
        }

        // 创建一个数值格式化对象
        NumberFormat nf = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        nf.setMaximumFractionDigits(2);
        if (currentReportNum == 0) {
            currentProcess = "0";
        } else {
            currentProcess = nf.format((double)currentFinishReportNum / (double)currentReportNum * 100);
        }
        totalProcess = nf.format((double)totalFinishReportNum / (double)totalReportNum * 100);
        processData.setCurrentProcess(currentProcess);
        processData.setTotalProcess(totalProcess);

        return processData;
    }

    // 计算各月份的 指标数量
    private IndxDataEntity getIndxNum(List<ReportEntity> reportList, IndxDataEntity indxData) {
        int totalIndxNum = 0;
        List<HashMap<String, String>> rows = indxData.getRows();

        for (ReportEntity temp : reportList) {
            List<ReportIndxEntity> reportIndxList = reportIndxService.list(new QueryWrapper<ReportIndxEntity>()
                    .lambda().eq(ReportIndxEntity::getReportId, temp.getId()));
            totalIndxNum = totalIndxNum + reportIndxList.size();
            // 获取月份并去掉前面的0
            String month = temp.getCreateTime().split("\\-")[1].replaceFirst("^0*", "");
            HashMap<String, String> row = rows.get(Integer.parseInt(month) - 1);
            row.put("indxNum", Integer.parseInt(row.get("indxNum")) + reportIndxList.size() + "");
        }

        String currentMonth = DateUtils.getCurrentMonth();
        indxData.setCurrentIndxNum(Integer.valueOf(rows.get(Integer.parseInt(currentMonth) - 1).get("indxNum")));
        indxData.setTotalIndxNum(totalIndxNum);
        indxData.setRows(rows);

        return indxData;
    }

    // 计算各月份的 报告数量
    private ReportDataEntity getReportNum(List<ReportEntity> reportList, ReportDataEntity reportData) {
        int totalReportNum = 0;
        List<HashMap<String, String>> rows = reportData.getRows();

        for (ReportEntity temp : reportList) {
            totalReportNum = totalReportNum + 1;
            // 获取月份并去掉前面的0
            String month = temp.getCreateTime().split("\\-")[1].replaceFirst("^0*", "");
            HashMap<String, String> row = rows.get(Integer.parseInt(month) - 1);
            row.put("reportNum", Integer.parseInt(row.get("reportNum")) + 1 + "");
        }

        String currentMonth = DateUtils.getCurrentMonth();
        reportData.setCurrentReportNum(Integer.valueOf(rows.get(Integer.parseInt(currentMonth) - 1).get("reportNum")));
        reportData.setTotalReportNum(totalReportNum);
        reportData.setRows(rows);

        return reportData;
    }
}

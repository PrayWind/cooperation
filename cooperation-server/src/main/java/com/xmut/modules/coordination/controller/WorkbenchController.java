package com.xmut.modules.coordination.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.IndxEntity;
import com.xmut.modules.coordination.entity.ReportEntity;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.entity.ReportIndxUserEntity;
import com.xmut.modules.coordination.entity.workbenchEntity.WorkbenchCurrentReportEntity;
import com.xmut.modules.coordination.entity.workbenchEntity.WorkbenchCurrentReportIndxEntity;
import com.xmut.modules.coordination.entity.workbenchEntity.WorkbenchFinishReportEntity;
import com.xmut.modules.coordination.entity.workbenchEntity.WorkbenchFinishReportIndxEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * 工作台部分Controller
 */

@RestController
@RequestMapping("/workbench")
public class WorkbenchController extends AbstractController {
    @Autowired
    private IndxService indxService;

    @Autowired
    private ReportService reportService;

    @Autowired
    private ReportIndxService reportIndxService;

    @Autowired
    private ReportIndxUserService reportIndxUserService;

    @GetMapping("/list")
    public R list() {
        List<ReportIndxUserEntity> reportIndxUserList =  reportIndxUserService.list(new QueryWrapper<ReportIndxUserEntity>()
                .lambda().eq(ReportIndxUserEntity::getUserId, this.getUserId()));

        List<WorkbenchCurrentReportEntity> currentReport = new ArrayList<WorkbenchCurrentReportEntity>();
        List<WorkbenchCurrentReportIndxEntity> currentReportIndx = new ArrayList<WorkbenchCurrentReportIndxEntity>();
        List<WorkbenchFinishReportEntity> finishReport = new ArrayList<WorkbenchFinishReportEntity>();
        List<WorkbenchFinishReportIndxEntity> finishReportIndx = new ArrayList<WorkbenchFinishReportIndxEntity>();
        for (ReportIndxUserEntity temp : reportIndxUserList) {
            ReportEntity reportTemp = reportService.getById(temp.getReportId());
            ReportIndxEntity reportIndxTemp = reportIndxService.getOne(new QueryWrapper<ReportIndxEntity>().lambda()
                    .eq(ReportIndxEntity::getReportId, temp.getReportId())
                    .eq(ReportIndxEntity::getIndxId, temp.getIndxId()));
            IndxEntity indxTemp = indxService.getById(reportIndxTemp.getIndxId());
            if ("2".equals(reportIndxTemp.getStatus())) {
                // 完成过的指标
                WorkbenchFinishReportIndxEntity finishReportIndxTemp = new WorkbenchFinishReportIndxEntity();
                finishReportIndxTemp.setReportName(reportTemp.getName());
                finishReportIndxTemp.setIndxName(indxTemp.getName());
                finishReportIndx.add(finishReportIndxTemp);
            } else {
                // 进行中的指标
                WorkbenchCurrentReportIndxEntity currentReportIndxTemp = new WorkbenchCurrentReportIndxEntity();
                currentReportIndxTemp.setReportName(reportTemp.getName());
                currentReportIndxTemp.setIndxName(indxTemp.getName());
                currentReportIndx.add(currentReportIndxTemp);
            }
            if ("1".equals(reportTemp.getStatus())) {
                // 完成过的报告
                WorkbenchFinishReportEntity finishReportTemp = new WorkbenchFinishReportEntity();
                finishReportTemp.setReportName(reportTemp.getName());
                finishReportTemp.setReportInfo(reportTemp.getReportInfo());
                finishReport.add(finishReportTemp);
            } else {
                // 进行中的报告
                WorkbenchCurrentReportEntity currentReportTemp = new WorkbenchCurrentReportEntity();
                currentReportTemp.setReportName(reportTemp.getName());
                currentReportTemp.setReportInfo(reportTemp.getReportInfo());
                currentReport.add(currentReportTemp);
            }
        }

        return R.ok()
                .put("currentReport", currentReport)
                .put("currentReportIndx", currentReportIndx)
                .put("finishReport", finishReport)
                .put("finishReportIndx", finishReportIndx);
    }
}

package com.xmut.modules.coordination.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.*;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @description: 报告协同的报告部分Controller
 * @author: whf
 * @date: 2019-10-11 08:50:11
 */

@RestController
@RequestMapping("/report")
public class ReportController extends AbstractController {
    @Autowired
    private ReportService reportService;

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private IndxService indxService;

    @Autowired
    private ReportIndxService reportIndxService;

    @Autowired
    private ReportIndxUserService reportIndxUserService;

    @Autowired
    private ReportUserService reportUserService;

    /*
     * 获取所有报告
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = reportService.queryPage(params);
        List<ReportEntity> reportList = (List<ReportEntity>) page.getList();
        if (!ObjectUtils.isEmpty(reportList)) {
            for (ReportEntity temp : reportList) {
                SysUserEntity user = sysUserService.getOne(new QueryWrapper<SysUserEntity>().lambda()
                        .eq(SysUserEntity::getUserId, temp.getLeader()));
                temp.setLeaderStr(user.getUsername());
            }
        }
        page.setList(reportList);

        return R.ok().put("page", page);
    }

    /*
     * 获取报告信息
     */
    @GetMapping("/info/{reportId}")
    public R info(@PathVariable("reportId") String reportId) {
        ReportEntity report = reportService.getById(reportId);

        return R.ok().put("report", report);
    }

    /*
     * 获取系统所有用户
     */
    @GetMapping("/users")
    public R users() {
        List<SysUserEntity> users = sysUserService.list();

        return R.ok().put("users", users);
    }

    /*
     * 获取报告下的所有指标，只是有用到
     */
    @GetMapping("/indxs")
    public R indxs(@RequestParam Map<String, Object> params) {
        String reportId = (String) params.get("reportId");
        List<ReportIndxEntity> reportIndxList = reportIndxService.list(new QueryWrapper<ReportIndxEntity>()
                .lambda().eq(ReportIndxEntity::getReportId, reportId));
        if (ObjectUtils.isEmpty(reportIndxList)) {
            return R.ok();
        }

        List<Long> indxIds = reportIndxList.stream().map(ReportIndxEntity::getIndxId).collect(Collectors.toList());
        List<IndxEntity> indxs = (List<IndxEntity>) indxService.listByIds(indxIds);

        return R.ok().put("indxs", indxs);
    }

    /*
     * 添加或修改报告时 获取报告下的指标
     */
    @GetMapping("/indx")
    public R indx(@RequestParam Map<String, Object> params) {
        String reportId = (String) params.get("reportId");

        PageUtils page = indxService.queryPage(params);

        // reportId==0说明是获取新增报告时的指标，reportId!=0说明是获取修改报告时的指标
        List<ReportIndxEntity> reportIndxList = null;
        List<Long> indxIds = null;
        if (!"0".equals(reportId)) {
            reportIndxList = reportIndxService.list(new QueryWrapper<ReportIndxEntity>().lambda()
                    .eq(ReportIndxEntity::getReportId, reportId));
            if (ObjectUtils.isEmpty(reportIndxList)) {
                return R.ok().put("page", page);
            }
            indxIds = reportIndxList.stream().map(ReportIndxEntity::getIndxId).collect(Collectors.toList());

            return R.ok().put("page", page).put("indxIds", indxIds);
        }


        return R.ok().put("page", page);
    }

    /*
     * 创建报告
     */
    @Transactional
    @PostMapping("/save")
    public R save(@RequestBody JSONObject data) {
        JSONObject object = data.getJSONObject("dataForm");
        ReportEntity report = JSONObject.toJavaObject(object, ReportEntity.class);
        // 插入数据后，已插入数据的主键直接注入到report对象
        reportService.insertReport(report);

        JSONArray array = data.getJSONArray("selectedIndxList");
        if (!ObjectUtils.isEmpty(array)) {
            List<IndxEntity> selectedIndxList = JSONArray.parseArray(array.toJSONString(), IndxEntity.class);
            List<Long> indxIds = selectedIndxList.stream().map(IndxEntity::getId).collect(Collectors.toList());
            for (Long temp : indxIds) {
                ReportIndxEntity temp1 = new ReportIndxEntity();
                temp1.setIndxId(temp);
                temp1.setReportId(report.getId());
                reportIndxService.save(temp1);
            }
        }

        return R.ok();
    }

    /*
     * 修改报告
     */
    @Transactional
    @PostMapping("/update")
    public R update(@RequestBody JSONObject data) {
        JSONObject object = data.getJSONObject("dataForm");
        ReportEntity report = JSONObject.toJavaObject(object, ReportEntity.class);
        reportService.updateById(report);

        reportIndxService.remove(new QueryWrapper<ReportIndxEntity>().lambda()
                .eq(ReportIndxEntity::getReportId, report.getId()));
        JSONArray array = data.getJSONArray("selectedIndxList");
        if (!ObjectUtils.isEmpty(array)) {
            List<IndxEntity> selectedIndxList = JSONArray.parseArray(array.toJSONString(), IndxEntity.class);
            List<Long> indxIds = selectedIndxList.stream().map(IndxEntity::getId).collect(Collectors.toList());
            for (Long temp : indxIds) {
                ReportIndxEntity temp1 = new ReportIndxEntity();
                temp1.setIndxId(temp);
                temp1.setReportId(report.getId());
                reportIndxService.save(temp1);
            }
        }

        return R.ok();
    }

    /*
     * 删除报告
     */
    @Transactional
    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> reportIds) {
        // 要删除与报告有关的所有数据
        for (Long temp : reportIds) {
            reportIndxUserService.remove(new QueryWrapper<ReportIndxUserEntity>().lambda()
                    .eq(ReportIndxUserEntity::getReportId, temp));
            reportIndxService.remove(new QueryWrapper<ReportIndxEntity>().lambda()
                    .eq(ReportIndxEntity::getReportId, temp));
            reportUserService.remove(new QueryWrapper<ReportUserEntity>().lambda()
                    .eq(ReportUserEntity::getReportId, temp));
        }

        reportService.removeByIds(reportIds);


        return R.ok();
    }
}

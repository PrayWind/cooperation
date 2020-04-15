package com.xmut.modules.coordination.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.DateUtils;
import com.xmut.common.utils.ElseUtils;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.IndxEntity;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.entity.ReportIndxUserEntity;
import com.xmut.modules.coordination.entity.ReportUserEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 报告详情页Controller
 */

@RestController
@RequestMapping("/detail")
public class ReportDetailController extends AbstractController {
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
     * 获取报告下的所有指标，这个方法与ReportController里的不同
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        String reportId = (String) params.get("reportId");
        String search = (String) params.get("search");
        Integer pageIndex = Integer.parseInt((String) params.get("page"));
        Integer pageSize = Integer.parseInt((String) params.get("limit"));
        Integer start = (pageIndex-1) * pageSize;

        List<IndxEntity> indxs = indxService.getIndxsHavePagination(reportId, search, start, pageSize);
        Integer totalCount = indxService.getIndxsCountHavePagination(reportId, search, start, pageSize);

        // 获取 执行者（executors） 和 指标状态、完成时间
        for (IndxEntity temp : indxs) {
            // 获取执行者
            List<ReportIndxUserEntity> reportIndxUserList = reportIndxUserService.list(new QueryWrapper<ReportIndxUserEntity>().lambda()
                    .eq(ReportIndxUserEntity::getReportId, reportId).eq(ReportIndxUserEntity::getIndxId, temp.getId()));
            if (!ObjectUtils.isEmpty(reportIndxUserList)) {
                List<Long> userIds = reportIndxUserList.stream().map(ReportIndxUserEntity::getUserId).collect(Collectors.toList());
                List<SysUserEntity> executors = (List<SysUserEntity>) sysUserService.listByIds(userIds);
                temp.setExecutors(executors);
                temp.setExecutorsStr(ElseUtils.formatExecutors(executors));
            }
            // 获取指标状态、完成时间
            ReportIndxEntity reportIndxEntity = reportIndxService.getOne(new QueryWrapper<ReportIndxEntity>().lambda()
                    .eq(ReportIndxEntity::getReportId, reportId).eq(ReportIndxEntity::getIndxId, temp.getId()));
            temp.setStatus(reportIndxEntity.getStatus());
            temp.setFinishTime(reportIndxEntity.getFinishTime());
        }

        PageUtils page = new PageUtils(indxs, totalCount, pageSize, pageIndex);

        return R.ok().put("page", page);
    }

    /*
     * 获取系统和报告的成员
     */
    @Transactional
    @GetMapping("/reportUsers/{reportId}")
    public R reportUsers(@PathVariable("reportId") String reportId) {
        List<SysUserEntity> userList = sysUserService.list();
        List<ReportUserEntity> reportUserList = reportUserService.list(new QueryWrapper<ReportUserEntity>()
                .lambda().eq(ReportUserEntity::getReportId, reportId));
        if (ObjectUtils.isEmpty(reportUserList)) {
            return R.ok().put("userList", userList);
        }

        List<Long> selectedUserIds = reportUserList.stream().map(ReportUserEntity::getUserId).collect(Collectors.toList());

        return R.ok().put("userList", userList).put("selectedUserIds", selectedUserIds);
    }

    /**
     * 保存报告成员
     */
    @Transactional
    @RequestMapping("/saveReportUsers")
    public R saveReportUsers(@RequestBody JSONObject data) {
        String reportId = data.getString("reportId");
        reportUserService.remove(new QueryWrapper<ReportUserEntity>().lambda()
                .eq(ReportUserEntity::getReportId, reportId));

        JSONArray array = data.getJSONArray("selectedUserIds");
        if (!ObjectUtils.isEmpty(array)) {
            List<Long> selectedUserIds = JSONArray.parseArray(array.toJSONString(), Long.class);
            for (Long temp : selectedUserIds) {
                ReportUserEntity temp1 = new ReportUserEntity();
                temp1.setReportId(Long.valueOf(reportId));
                temp1.setUserId(temp);
                reportUserService.save(temp1);
            }
        }

        return R.ok();
    }

    /*
     * 获取报告和指派的成员
     */
    @Transactional
    @GetMapping("/sentUsers")
    public R sentUsers(@RequestParam Map<String, Object> params) {
        String reportId = (String) params.get("reportId");
        String indxId = (String) params.get("indxId");
        List<ReportUserEntity> reportUserList = reportUserService.list(new QueryWrapper<ReportUserEntity>()
                .lambda().eq(ReportUserEntity::getReportId, reportId));
        if (ObjectUtils.isEmpty(reportUserList)) {
            return R.error("请先添加报告成员！");
        }
        List<Long> reportUserIds = reportUserList.stream().map(ReportUserEntity::getUserId).collect(Collectors.toList());
        List<SysUserEntity> userList = (List<SysUserEntity>) sysUserService.listByIds(reportUserIds);

        List<ReportIndxUserEntity> reportIndxUserList = reportIndxUserService.list(new QueryWrapper<ReportIndxUserEntity>().lambda()
                .eq(ReportIndxUserEntity::getReportId, reportId).eq(ReportIndxUserEntity::getIndxId, indxId));
        if (ObjectUtils.isEmpty(reportIndxUserList)) {
            return R.ok().put("userList", userList);
        }
        List<Long> sentUserIds = reportIndxUserList.stream().map(ReportIndxUserEntity::getUserId).collect(Collectors.toList());

        return R.ok().put("userList", userList).put("sentUserIds", sentUserIds);
    }

    /**
     * 保存指派成员
     */
    @Transactional
    @RequestMapping("/saveSentUsers")
    public R saveSentUsers(@RequestBody JSONObject data) {
        String reportId = data.getString("reportId");
        String indxId = data.getString("indxId");
        reportIndxUserService.remove(new QueryWrapper<ReportIndxUserEntity>().lambda()
                .eq(ReportIndxUserEntity::getReportId, reportId)
                .eq(ReportIndxUserEntity::getIndxId, indxId));

        JSONArray array = data.getJSONArray("sentUserIds");
        if (!ObjectUtils.isEmpty(array)) {
            List<Long> sentUserIds = JSONArray.parseArray(array.toJSONString(), Long.class);
            for (Long temp : sentUserIds) {
                ReportIndxUserEntity temp1 = new ReportIndxUserEntity();
                temp1.setReportId(Long.valueOf(reportId));
                temp1.setIndxId(Long.valueOf(indxId));
                temp1.setUserId(temp);
                reportIndxUserService.save(temp1);
            }
            // 指派完后得把tb_report_indx的STATUS字段改为1，说明已分配（状态1表示进行中==已分配）
            ReportIndxEntity reportIndx = reportIndxService.getOne(new QueryWrapper<ReportIndxEntity>().lambda()
                    .eq(ReportIndxEntity::getReportId, reportId).eq(ReportIndxEntity::getIndxId, indxId));
            reportIndx.setStatus("1");
            reportIndx.setFinishTime("");
            reportIndxService.updateById(reportIndx);

            return R.ok();
        }

        // 如果执行这部分，说明指标还没指派，所以得把tb_report_indx的STATUS字段改为0，表示未分配
        ReportIndxEntity reportIndx = reportIndxService.getOne(new QueryWrapper<ReportIndxEntity>().lambda()
                .eq(ReportIndxEntity::getReportId, reportId).eq(ReportIndxEntity::getIndxId, indxId));
        reportIndx.setStatus("0");
        reportIndx.setFinishTime("");
        reportIndxService.updateById(reportIndx);

        return R.ok();
    }

    /**
     * 报告详情页点击完成，网页和office插件均适用
     */
    @Transactional
    @RequestMapping("/statusChange")
    public R statusChange(@RequestBody JSONObject data) {
        String reportId = data.getString("reportId");
        String indxId = data.getString("indxId");
        ReportIndxEntity reportIndx = reportIndxService.getOne(new QueryWrapper<ReportIndxEntity>().lambda()
                .eq(ReportIndxEntity::getReportId, reportId).eq(ReportIndxEntity::getIndxId, indxId));
        if ("0".equals(reportIndx.getStatus())) {
            return R.error("该指标还未分配！");
        }
        if ("2".equals(reportIndx.getStatus())) {
            return R.error("该指标已完成！");
        }
        reportIndx.setStatus("2");
        reportIndx.setFinishTime(DateUtils.format(new Date()));
        reportIndxService.updateById(reportIndx);

        return R.ok();
    }
}

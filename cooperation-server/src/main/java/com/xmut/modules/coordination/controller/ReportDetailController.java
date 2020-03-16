package com.xmut.modules.coordination.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.IndxEntity;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.entity.ReportUserEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysRoleService;
import com.xmut.modules.sys.service.SysUserRoleService;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description: 报告协同的报告详情页Controller
 * @author: whf
 * @date: 2019-10-11 11:52:53
 */

@RestController
@RequestMapping("/detail")
public class ReportDetailController extends AbstractController {
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
        PageUtils page = new PageUtils(indxs, totalCount, pageSize, pageIndex);

        return R.ok().put("page", page);
    }

    /*
     * 获取报告成员
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
            List<Long> userIds = JSONArray.parseArray(array.toJSONString(), Long.class);
            for (Long temp : userIds) {
                ReportUserEntity temp1 = new ReportUserEntity();
                temp1.setReportId(Long.valueOf(reportId));
                temp1.setUserId(temp);
                reportUserService.save(temp1);
            }
        }

        return R.ok();
    }

    /**
     * 指派模板给user
     */
    @Transactional
    @RequestMapping("/sendToUser")
    public R sendToUser(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        JSONArray userIds = data.getJSONArray("userIds");
        Templet templet = data.getObject("templet", Templet.class);
        List<String> ids = JSONArray.parseArray(userIds.toJSONString(), String.class);

        if (StringUtils.isEmpty(projectId)) {
            return ResponseUtil.deletedArgumentValue("项目");
        }

        *//**
         * 思路：先删除所有分配的用户；
         *      判断前端选择的用户列表（userIds）是否为空，
         *      空则结束；不空则插入userIds。
         *//*
        // 删除所有用户
        projectIndxUserService.remove(new QueryWrapper<ProjectIndxUser>().lambda()
                .eq(ProjectIndxUser::getProjectId, projectId)
                .eq(ProjectIndxUser::getIndxTreeId, templet.getIndxTreeId())
                .eq(ProjectIndxUser::getTempletId, templet.getId()));

        if (ObjectUtils.isNullOrEmpty(ids)) {
            // 删除操作
            // 修改TB_PROJECT_USER表TEMPLET_FLAG字段为0，表示未分配
            ProjectIndx projectIndx = new ProjectIndx();
            projectIndx.setTempletFlag("0");
            reportIndxService.update(projectIndx, new QueryWrapper<ProjectIndx>().lambda()
                    .eq(ProjectIndx::getProjectId, projectId)
                    .eq(ProjectIndx::getIndxTreeId, templet.getIndxTreeId())
                    .eq(ProjectIndx::getTempletId, templet.getId()));

            return ResponseUtil.ok();
        }

        // 修改TB_PROJECT_USER表TEMPLET_FLAG字段为1，表示已分配
        ProjectIndx projectIndx = new ProjectIndx();
        projectIndx.setTempletFlag("1");
        reportIndxService.update(projectIndx, new QueryWrapper<ProjectIndx>().lambda()
                .eq(ProjectIndx::getProjectId, projectId)
                .eq(ProjectIndx::getIndxTreeId, templet.getIndxTreeId())
                .eq(ProjectIndx::getTempletId, templet.getId()));

        // 插入用户到数据库
        List<User> userList = (List<User>) userService.listByIds(ids);
        ProjectIndxUser saveData = new ProjectIndxUser();
        saveData.setProjectId(projectId);
        saveData.setIndxTreeId(templet.getIndxTreeId());
        saveData.setTempletId(templet.getId());
        for (User temp : userList) {
            // 插入操作
            try {
                saveData.setId("");
                saveData.setUserId(temp.getId());
                projectIndxUserService.save(saveData);
            } catch (Exception e) {
                return ResponseUtil.serverProblem(new Exception("数据插入失败"));
            }
        }*/

        return R.ok();
    }

    /*
     * 获取指标树
     */
    @RequestMapping("/indxTree")
    public R indxTree() {
        /*List<IndxTree> indxTrees = null;
        List<IndxTreeHelper.IndxTreeNode> indxTreeNodes = null;

        // 获取指标树
        try {
            indxTrees = indxTreeService.list(new QueryWrapper<IndxTree>().lambda().orderByAsc(IndxTree::getSort));
            indxTreeNodes = IndxTreeHelper.create().sort(indxTrees, "list", false, Constant.TOP_PRAENT_ID);
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("指标树加载错误"));
        }*/

        return R.ok();
    }

    /*
     * 获取可选择的模板（除去已经选择的模板）
     */
    @RequestMapping("/selectTemplet")
    public R selectTemplet(@RequestBody JSONObject data) {
        /*String indxTreeId = data.getString("indxTreeId");
        String projectId = data.getString("projectId");
        Templet templet = null;
        List<Templet> templetList = new ArrayList<Templet>();
        List<Templet> selectedTempletList = new ArrayList<Templet>();
        List<IndxTemplet> indxTempletList = null;
        ProjectIndx projectIndx = null;

        // 获取指标下的模板
        indxTempletList = indxTempletService.list(new QueryWrapper<IndxTemplet>().lambda().eq(IndxTemplet::getIndxTreeId, indxTreeId));
        for (IndxTemplet temp : indxTempletList) {
            projectIndx = reportIndxService.getOne(new QueryWrapper<ProjectIndx>().lambda()
                    .eq(ProjectIndx::getProjectId, projectId)
                    .eq(ProjectIndx::getIndxTreeId, indxTreeId)
                    .eq(ProjectIndx::getTempletId, temp.getTempletId()));
            try {
                templet = templetService.getById(temp.getTempletId());
                templet.setIndxTreeId(temp.getIndxTreeId());
            } catch (Exception e) {
                ResponseUtil.deletedArgumentValue("模板");
            }
            if (ObjectUtils.isNullOrEmpty(projectIndx)) {
                templetList.add(templet);
            } else {
                selectedTempletList.add(templet);
            }
        }*/

//        JSONObject response = new JSONObject();
//        response.put("templetList", templetList);
//        response.put("selectedTempletList", selectedTempletList);
        return R.ok();
    }

    /*
     * 保存已选择的模板
     */
    @RequestMapping("/saveSelectedTemplet")
    public R saveSelectedTemplet(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        JSONArray templetList = data.getJSONArray("templetList");
        List<Templet> selectTempletList = JSONArray.parseArray(templetList.toJSONString(), Templet.class);
        ProjectIndx projectIndx = new ProjectIndx();
        projectIndx.setProjectId(projectId);

        if (StringUtils.isEmpty(projectId)) {
            return ResponseUtil.deletedArgumentValue("项目");
        }

        for (Templet temp : selectTempletList) {
            projectIndx.setId("");
            projectIndx.setIndxTreeId(temp.getIndxTreeId());
            projectIndx.setTempletId(temp.getId());
            try {
                reportIndxService.save(projectIndx);
            } catch (Exception e) {
                return ResponseUtil.serverProblem(new Exception("保存失败"));
            }
        }*/

        return R.ok();
    }

    /*
     * 在Office365中点击 模板完成
     */
    @RequestMapping("/finishTemplet")
    public R finishTemplet(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        JSONObject object = data.getJSONObject("templet");
        String templetId = object.getString("id");
        String indxTreeId = object.getString("indxTreeId");

        ProjectIndx projectIndx = reportIndxService.getOne(new QueryWrapper<ProjectIndx>().lambda()
                .eq(ProjectIndx::getProjectId, projectId)
                .eq(ProjectIndx::getTempletId, templetId)
                .eq(ProjectIndx::getIndxTreeId, indxTreeId));
        projectIndx.setTempletStatus("1");
        reportIndxService.updateById(projectIndx);*/

        return R.ok();
    }
}

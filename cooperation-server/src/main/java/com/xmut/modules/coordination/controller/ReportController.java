package com.xmut.modules.coordination.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xmut.common.utils.PageUtils;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.IndxEntity;
import com.xmut.modules.coordination.entity.ReportEntity;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Map;
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
    private ReportUserService reportUserService;

    /*
     * 获取所有报告
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        String userId = getUserId() + "";

        PageUtils page = reportService.queryPage(params);

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
     * 获取 添加或修改报告下的指标
     */
    @GetMapping("/indx")
    public R indx(@RequestParam Map<String, Object> params) {
        String reportId = (String) params.get("reportId");

        PageUtils page = indxService.queryPage(params);

        // reportId==0说明是获取新增报告时的指标，reportId!=0说明是获取修改报告时的指标
        List<ReportIndxEntity> reportIndxList = null;
        List<String> indxIds = null;
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
     * 获取指标下的所有模板
     */
    @Transactional
    @RequestMapping("/allIndxTemplet")
    public R allIndxTemplet(@RequestBody JSONObject data) {
        /*String indxTreeId = data.getString("indxTreeId");
        Templet templet = null;
        List<Templet> templetList = new ArrayList<Templet>();
        List<IndxTemplet> indxTempletList = null;

        if (StringUtils.isEmpty(indxTreeId)) {
            return ResponseUtil.deletedArgumentValue("指标");
        }

        // 获取指标下的模板
        indxTempletList = indxTempletService.list(new QueryWrapper<IndxTemplet>().lambda().eq(IndxTemplet::getIndxTreeId, indxTreeId));
        for (IndxTemplet temp : indxTempletList) {
            try {
                templet = templetService.getById(temp.getTempletId());
                templet.setIndxTreeId(temp.getIndxTreeId());
            } catch (Exception e) {
                return ResponseUtil.deletedArgumentValue("模板");
            }
            templetList.add(templet);
        }*/

        return R.ok();
    }

    /*
     * 创建报告
     */
    @Transactional
    @RequestMapping("/saveReport")
    public R saveReport(@RequestBody JSONObject data) {
        /*JSONArray templetList = data.getJSONArray("templetList");
        List<Templet> selectTempletList = JSONArray.parseArray(templetList.toJSONString(), Templet.class);
        JSONObject formData = data.getJSONObject("formData");

        ProjectIndx projectIndx = new ProjectIndx();
        Project project = new Project();
        String projectId = com.cn.uddata.core.utils.StringUtils.randomUUID();
        project.setId(projectId);
        project.setName(formData.getString("name"));
        project.setLeader(formData.getString("leader"));
        project.setProjectInfo(formData.getString("projectInfo"));
        project.setType(formData.getString("type"));
        project.setCreateTime(formData.getString("createTime"));
        project.setPredictTime(formData.getString("predictTime"));

        // 保存项目信息和对应模板关联信息
        try {
            projectService.save(project);
            for (Templet temp : selectTempletList) {
                projectIndx.setId("");
                projectIndx.setProjectId(projectId);
                projectIndx.setIndxTreeId(temp.getIndxTreeId());
                projectIndx.setTempletId(temp.getId());
                reportIndxService.save(projectIndx);
            }
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("保存失败"));
        }*/

        return R.ok();
    }

    /*
     * @Description: 获取所有用户，或者获取所有用户和与项目有关用户
     * @Author: whf
     * @Date: 2019/10/28
     */
    @RequestMapping("/userList")
    public R userList(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");

         *//**
         * 思路：不管什么情况，都必须用到所有用户，所以先获取所有用户
         *      然后开始判断，如果projectId为空，则直接返回所有用户；
         *      如果不为空，则返回所有用户和与项目有关用户。
         *//*

        List<User> userList = null;
        try {
            userList = userService.list();
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("所有用户获取失败"));
        }

        if (StringUtils.isEmpty(projectId)) {
            return ResponseUtil.ok(userList);
        }

        // 获取与项目有关用户
        List<String> userIds = null;
        List<ProjectUser> projectUserList = null;
        try {
            projectUserList = projectUserService.list(new QueryWrapper<ProjectUser>().lambda()
                    .eq(ProjectUser::getProjectId, projectId));
            userIds = projectUserList.stream().map(ProjectUser::getUserId).collect(Collectors.toList());
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("与项目有关用户获取失败"));
        }

        JSONObject response = new JSONObject();
        response.put("userList", userList);
        response.put("userIds", userIds);*/

        return R.ok();
    }

    /*
     * 为报告添加用户
     */
    @Transactional
    @RequestMapping("/addUser")
    public R addUser(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        JSONArray userList = data.getJSONArray("userList");
        List<String> userIds = JSONArray.parseArray(userList.toJSONString(), String.class);
        ProjectUser projectUser = new ProjectUser();
        projectUser.setProjectId(projectId);

        if (StringUtils.isEmpty(projectId)) {
            return ResponseUtil.deletedArgumentValue("项目");
        }

        *//**
         * 思路：先删除项目下的所有用户；
         *      判断前端选择的用户列表（userIds）是否为空，
         *      空则结束；不空则插入userIds。
         *//*

        try {
            projectUserService.remove(new QueryWrapper<ProjectUser>().lambda()
                    .eq(ProjectUser::getProjectId, projectId));
            if (ObjectUtils.isNullOrEmpty(userIds)) {
                return ResponseUtil.ok();
            }
            for (String temp : userIds) {
                projectUser.setId("");
                projectUser.setUserId(temp);
                projectUserService.save(projectUser);
            }
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("保存失败"));
        }*/

        return R.ok();
    }

    /*
     * 删除报告
     */
    @Transactional
    @RequestMapping("/deleteReports")
    public R deleteReports(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");

        if (StringUtils.isEmpty(projectId)) {
            return ResponseUtil.deletedArgumentValue("项目");
        }

        try {
            projectIndxUserService.remove(new QueryWrapper<ProjectIndxUser>().lambda()
                    .eq(ProjectIndxUser::getProjectId, projectId));
            projectUserService.remove(new QueryWrapper<ProjectUser>().lambda()
                    .eq(ProjectUser::getProjectId, projectId));
            reportIndxService.remove(new QueryWrapper<ProjectIndx>().lambda()
                    .eq(ProjectIndx::getProjectId, projectId));
            projectService.removeById(projectId);
        } catch (Exception e) {
            return ResponseUtil.serverProblem(new Exception("删除失败"));
        }*/

        return R.ok();
    }
}

package com.xmut.modules.coordination.controller;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSONObject;
import com.xmut.common.utils.R;
import com.xmut.modules.coordination.entity.ReportIndxEntity;
import com.xmut.modules.coordination.entity.ReportIndxUserEntity;
import com.xmut.modules.coordination.service.*;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysRoleService;
import com.xmut.modules.sys.service.SysUserRoleService;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 报告协同的报告详情页Controller
 * @author: whf
 * @date: 2019-10-11 11:52:53
 */

@RestController
@RequestMapping("/reportDetail")
public class ReportDetailController extends AbstractController {

    @Autowired
    private ReportIndxUserService projectIndxUserService;

    @Autowired
    private ReportIndxService reportIndxService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private ReportUserService projectUserService;

    @Autowired
    private SysUserRoleService userRoleService;

    /**
     * 根据用户角色获取模板
     */
    @RequestMapping("/templetList")
    public R templetList(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        String search = data.getString("search");
        String userId = getUserId() + "";

        List<TempletEntity> templetList = new ArrayList<TempletEntity>();
        TempletEntity templet = null;
        List<ReportIndxEntity> projectIndxList = null;
        ReportIndxEntity projectIndx = null;
        List<SysUserEntity> executors = null;
        List<String> ids = null;
        List<ReportIndxUserEntity> projectIndxUserList = null;

        if (StringUtils.isEmpty(userId)) {
            return R.error("未登录");
        }
        if (StringUtils.isEmpty(projectId)) {
            return R.error("报告已删除或不存在");
        }
*/
        // 判断是否是管理员"coordinationAdmin"
        /*Role role1 = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getCode, "coordinationAdmin"));
        if (ObjectUtils.isNullOrEmpty(role1)) {
            return ResponseUtil.deletedArgumentValue("角色");
        }
        UserRole userRole1 = userRoleService.getOne(new QueryWrapper<UserRole>().lambda()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, role1.getId()));

        // 判断是否是普通员工"coordinationUser"
        Role role2 = roleService.getOne(new QueryWrapper<Role>().lambda().eq(Role::getCode, "coordinationUser"));
        if (ObjectUtils.isNullOrEmpty(role2)) {
            return ResponseUtil.deletedArgumentValue("角色");
        }
        UserRole userRole2 = userRoleService.getOne(new QueryWrapper<UserRole>().lambda()
                .eq(UserRole::getUserId, userId)
                .eq(UserRole::getRoleId, role2.getId()));

        // 查询模板
        Boolean searchIsEmpty = StringUtils.isEmpty(search);
        if (!ObjectUtils.isNullOrEmpty(userRole1)) {
            // 按管理员查询，看到报告已添加的所有模板
            // 1) 根据PROJECT_ID，获取TB_PROJECT_INDX表数据，需要里面的优先级和状态
            projectIndxList = reportIndxService.list(new QueryWrapper<ProjectIndx>().lambda().eq(ProjectIndx::getProjectId, projectId));
            // 2) 获取模板等数据
            for (int i = 0; i < projectIndxList.size(); i++) {
                ProjectIndx temp = projectIndxList.get(i);
                // 根据IndxTreeId和TempletId（这两个才能确定模板）获取模板
                templet = templetService.getTempletByIndxTreeIdAndTempletId(temp.getIndxTreeId(), temp.getTempletId());
                if (searchIsEmpty || (!searchIsEmpty && com.cn.uddata.core.utils.StringUtils.isContain(templet.getName(), search))) {
                    // 在TB_PROJECT_INDX_USER根据ProjectId、IndxTreeId和TempletId（这三个才能确定user）获取USER_ID列表
                    // TB_PROJECT_INDX的FLAG字段表示该模板是否已被分配，分配1未分配0
                    if ("1".equals(temp.getTempletFlag())) {
                        projectIndxUserList = projectIndxUserService.list(new QueryWrapper<ProjectIndxUser>().lambda()
                                .eq(ProjectIndxUser::getProjectId, projectId)
                                .eq(ProjectIndxUser::getIndxTreeId, temp.getIndxTreeId())
                                .eq(ProjectIndxUser::getTempletId, temp.getTempletId()));
                        ids = projectIndxUserList.stream().map(ProjectIndxUser::getUserId).collect(Collectors.toList());
                        executors = (List<User>) userService.listByIds(ids);
                    } else {
                        executors = new ArrayList<User>();
                    }
                    templet.setExecutors(executors);
                    templet.setExecutorName(UserUtils.getUserListName(executors));
                    templet.setIndxTreeId(temp.getIndxTreeId());
                    templet.setTempletLevel(temp.getTempletLevel());
                    templet.setTempletStatus(temp.getTempletStatus());
                    templet.setRowId(i + 1 + "");
                    templetList.add(templet);
                }
            }

        } else if (!ObjectUtils.isNullOrEmpty(userRole2)) {
            // 按普通员工查询，看到与自己有关的模板
            projectIndxUserList = projectIndxUserService.list(new QueryWrapper<ProjectIndxUser>().lambda()
                    .eq(ProjectIndxUser::getProjectId, projectId)
                    .eq(ProjectIndxUser::getUserId, userId));
            ids = new ArrayList<String>();
            ids.add(userId);
            executors = (List<User>) userService.listByIds(ids);
            if (!ObjectUtils.isNullOrEmpty(projectIndxUserList)) {
                for (int i = 0; i < projectIndxList.size(); i++) {
                    ProjectIndx temp = projectIndxList.get(i);
                    templet = templetService.getTempletByIndxTreeIdAndTempletId(temp.getIndxTreeId(), temp.getTempletId());
                    if (searchIsEmpty || (!searchIsEmpty && com.cn.uddata.core.utils.StringUtils.isContain(templet.getName(), search))) {
                        projectIndx = reportIndxService.getOne(new QueryWrapper<ProjectIndx>().lambda()
                                .eq(ProjectIndx::getProjectId, projectId)
                                .eq(ProjectIndx::getIndxTreeId, temp.getIndxTreeId())
                                .eq(ProjectIndx::getTempletId, temp.getTempletId()));
                        templet.setExecutors(executors);
                        templet.setExecutorName(UserUtils.getUserListName(executors));
                        templet.setIndxTreeId(temp.getIndxTreeId());
                        templet.setTempletStatus(projectIndx.getTempletStatus());
                        templet.setTempletLevel(projectIndx.getTempletLevel());
                        templet.setRowId(i + 1 + "");
                        templetList.add(templet);
                    }
                }
            }
        } else {
            return ResponseUtil.serverProblem(new Exception("没有权限"));
        }*/

        return R.ok();
    }

    /**
     * 获取项目成员和该模板已经指派的成员
     */
    @Transactional
    @RequestMapping("/projectUserList")
    public R projectUserList(@RequestBody JSONObject data) {
        /*String projectId = data.getString("projectId");
        Templet templet = data.getObject("templet", Templet.class);

        // 获取项目成员
        List<ProjectUser> projectUserList = projectUserService.list(new QueryWrapper<ProjectUser>().lambda()
                .eq(ProjectUser::getProjectId, projectId));
        if (ObjectUtils.isNullOrEmpty(projectUserList)) {
            // 此时项目没有成员，但这不是错误
            return ResponseUtil.ok(new ArrayList<User>());
        }
        List<String> ids = projectUserList.stream().map(ProjectUser::getUserId).collect(Collectors.toList());
        List<User> userList = (List<User>) userService.listByIds(ids);

        // 获取该模板已经指派的成员
        List<ProjectIndxUser> projectIndxUserList = projectIndxUserService.list(new QueryWrapper<ProjectIndxUser>()
                .lambda().eq(ProjectIndxUser::getProjectId, projectId)
                .eq(ProjectIndxUser::getIndxTreeId, templet.getIndxTreeId())
                .eq(ProjectIndxUser::getTempletId, templet.getId()));
        List<String> userIds = projectIndxUserList.stream().map(ProjectIndxUser::getUserId).collect(Collectors.toList());

        JSONObject response = new JSONObject();
        response.put("userList", userList);
        response.put("userIds", userIds);*/
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

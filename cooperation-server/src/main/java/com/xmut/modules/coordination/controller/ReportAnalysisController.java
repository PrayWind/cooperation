package com.xmut.modules.coordination.controller;

import com.xmut.modules.coordination.service.ReportService;
import com.xmut.modules.coordination.service.ReportUserService;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.service.SysRoleService;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description: 报告协同的报告分析部分Controller
 * @author: whf
 * @date: 2019-10-11 08:51:25
 */

@RestController
@RequestMapping("/reportAnalysis")
public class ReportAnalysisController extends AbstractController {
    @Autowired
    private ReportService projectService;

//    @Autowired
//    private IDepartmentService departmentService;

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysRoleService roleService;

    @Autowired
    private ReportUserService projectUserService;
}

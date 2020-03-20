package com.xmut.modules.coordination.controller;

import com.xmut.modules.coordination.service.ReportUserService;
import com.xmut.modules.sys.controller.AbstractController;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 报告分析部分Controller
 */

@RestController
@RequestMapping("/reportAnalysis")
public class ReportAnalysisController extends AbstractController {

    @Autowired
    private SysUserService sysUserService;

    @Autowired
    private ReportUserService reportUserService;
}

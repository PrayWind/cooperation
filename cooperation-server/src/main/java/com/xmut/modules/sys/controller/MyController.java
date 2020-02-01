package com.xmut.modules.sys.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

/**
 * @description: 个人信息页面
 * @author: whf
 * @date: 2020-1-28 11:42:43
 */

@RestController
@RequestMapping("/my")
public class MyController {

    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IDepartmentService departmentService;

    // 当前处于项目根路径(uddata-service)，./ 代表当前路径，注意：最后要加/，不然保存的路径和avatar同级
//    private static final String AVATAR = "src/main/resources/static/avatar/";

    // 服务器的路径
    private static final String AVATAR = "../webapps/uddata/WEB-INF/images/avatar/";

    /**
     * @Description: 获取用户信息
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [userId]
     * @Return: java.lang.Object
     */
    @RequestMapping("/getUserInfo")
    public Object getUserInfo(@LoginAdmin String userId) {

        User user = null;

        try {
            user = userService.getById(userId);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }

        // 获取用户角色名
        String roleName = "";
        List<Role> roleList = null;
        try {
            roleList = roleService.findListByUserId(userId);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }
        for (Role temp : roleList) {
            roleName = roleName + " " + temp.getName();
        }
        roleName = roleName.trim();

        // 获取用户部门名
        String deptName = "";
        Department department = null;
        try {
            department = departmentService.findDepartmentByUserId(userId);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }
        deptName = department.getDeptName();

        user.setRoleName(roleName);
        user.setDeptName(deptName);

        return ResponseUtil.ok(user);
    }

    /**
     * @Description: 修改用户信息
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [userId, data]
     * @Return: java.lang.Object
     */
    @RequestMapping("/saveUserInfo")
    public Object saveUserInfo(@LoginAdmin String userId, @RequestBody JSONObject data) {

        /**
         * boolean saveOrUpdate(T entity);
         * 如果存在id字段，则更新该记录，
         * 不存在，则插入一条记录。
         */

        JSONObject userInfo = data.getJSONObject("info");
        User user = null;
        try {
            user = userService.getById(userId);
            user.setName(userInfo.getString("name"));
            user.setTele(userInfo.getString("tele"));
            user.setEmail(userInfo.getString("email"));
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }

        try {
            userService.updateById(user);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }

        return ResponseUtil.ok("修改成功");
    }

    /**
     * @Description: 修改密码
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [data]
     * @Return: java.lang.Object
     */
    @RequestMapping("/savePassword")
    public Object savePassword(@RequestBody JSONObject data) {

        JSONObject form = data.getJSONObject("form");
        String userId = form.getString("id");
        String origin = form.getString("origin");
        String password = form.getString("pass");

        User user = null;
        try {
            user = userService.getById(userId);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }

        if (origin.equals(user.getPassword())) {
            user.setPassword(password);
            try {
                userService.updateById(user);
            } catch (Exception e) {
                return ResponseUtil.deletedArgumentValue("用户");
            }
        } else {
            return ResponseUtil.passwordFail();
        }

        return ResponseUtil.ok("密码修改成功");
    }

    /**
     * @Description: 上传图片
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [map, file]
     * @Return: java.lang.Object
     */
    @RequestMapping("/uploadAvatar")
    public Object uploadAvatar(@ModelAttribute MultipartFile data, @LoginAdmin String userId) {

        String filename = "";
        if (null != data) {
            filename = data.getOriginalFilename();
        } else {
            return ResponseUtil.uploadFail();
        }

        User user = null;
        try {
            user = userService.getById(userId);
        } catch (Exception e) {
            return ResponseUtil.deletedArgumentValue("用户");
        }

        // 如果上传一样的图片会覆盖，所以这边用uuid拼接
        String uuid = UUID.randomUUID().toString().replace("-", "");
        String[] split = filename.split("\\.");
        filename = uuid + "." + split[1];

        // 在路径里加上文件名
        String path = AVATAR + filename;
        FileOutputStream fos = null;
        try {
            // 把文件名保存到数据库
            user.setAvatar(filename);
            try {
                userService.updateById(user);
            } catch (Exception e) {
                return ResponseUtil.deletedArgumentValue("用户");
            }
            // 写入文件
            fos = new FileOutputStream(path);
            fos.write(data.getBytes());
        } catch (Exception e) {
            return ResponseUtil.uploadFail();
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                return ResponseUtil.uploadFail();
            }
        }
        // 图片上传成功把图片的名字返回前端，便于刷新页面
        return ResponseUtil.ok("图片上传成功", filename);
    }

    /**
     * @Description: 加载图片（没用到）
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [logo, response]
     * @Return: java.lang.Object
     */
    @RequestMapping("/loadAvatar")
    public Object loadAvatar(String filename, HttpServletResponse response) { // public Object loadAvatar(@RequestBody JSONObject data, HttpServletResponse response) {

//        String filename = data.getString("filename");
        String filepath = "";
        if (!StringUtils.isEmpty(filename)) {
            filepath = AVATAR + filename;
//            filepath = (new File(AVATAR).getAbsolutePath() + "\\" + filename);

            try {
                FileUtil.loadPhoto(response, filepath);
            } catch (IOException e) {
                return ResponseUtil.downloadFail();
            }
        } else {
            return ResponseUtil.downloadFail();
        }
        return ResponseUtil.ok("图片加载成功");

        /*if (!StringUtils.isEmpty(filename)) {
            File file = new File(new File(AVATAR).getAbsolutePath() + "/" + filename);
            if (null != file) {
                FileInputStream in = null;
                OutputStream out = null;
                try {
                    // 如果in为空，说明文件不存在，但数据库的值还在
                    in = new FileInputStream(file);
                    out = response.getOutputStream();
                    byte[] buff = new byte[100];
                    int rc = 0;
                    while ((rc = in.read(buff, 0, 100)) > 0) {
                        out.write(buff, 0, rc);
                    }

                } catch (Exception e) {
                    return ResponseUtil.fail();
                } finally {
                    try {
                        in.close();
                    } catch (Exception e) {
                        return ResponseUtil.fail();
                    }
                    try {
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }*/
    }

}

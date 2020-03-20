package com.xmut.modules.sys.controller;

import com.xmut.common.utils.R;
import com.xmut.modules.sys.entity.SysUserEntity;
import com.xmut.modules.sys.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.util.UUID;

/**
 * 个人信息页面
 */

@RestController
@RequestMapping("/my")
public class MyController extends AbstractController {

    // 当前处于项目根路径(cooperation-service)，./ 代表当前路径，注意：最后要加/，不然保存的路径和avatar同级
    private static final String AVATAR = "src/main/resources/static/avatar/";

    @Autowired
    private SysUserService sysUserService;

    /**
     * 修改用户信息
     */
    @PostMapping("/update")
    public R update(@RequestBody SysUserEntity user) {

        SysUserEntity baseUser = getUser();
        baseUser.setName(user.getName());
        baseUser.setMobile(user.getMobile());
        baseUser.setEmail(user.getEmail());

        sysUserService.updateById(baseUser);

        return R.ok();
    }

    /**
     * 上传图片
     */
    @RequestMapping("/uploadAvatar")
    public R uploadAvatar(@RequestBody MultipartFile data) {

        // 使用 data.getOriginalFilename() 可以获取图片名字
        String filename = data.getOriginalFilename();

        SysUserEntity user = getUser();

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
            sysUserService.updateById(user);
            // 写入文件
            fos = new FileOutputStream(path);
            fos.write(data.getBytes());
        } catch (Exception e) {
            return R.error(501, "服务器错误，上传失败！");
        } finally {
            try {
                fos.close();
            } catch (Exception e) {
                return R.error(501, "服务器错误，上传失败！");
            }
        }
        // 图片上传成功把图片的路径返回前端，便于刷新页面
        return R.ok().put("avatar", user.getAvatar());
    }

}

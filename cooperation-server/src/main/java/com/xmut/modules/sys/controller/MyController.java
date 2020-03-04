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
 * @description: 个人信息页面
 * @author: whf
 * @date: 2020-1-28 11:42:43
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
    public R update(@RequestBody SysUserEntity user){

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

    /**
     * @Description: 加载图片（没用到）
     * @Author: whf
     * @Date: 2019/7/31
     * @Param: [logo, response]
     * @Return: java.lang.Object
     */
    /*@RequestMapping("/loadAvatar")
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
        return ResponseUtil.ok("图片加载成功");*/

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
        }
    }*/

}

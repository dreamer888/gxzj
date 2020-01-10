package com.dlc.modules.sys.controller;

import com.dlc.common.utils.FileUploadUtils;
import com.dlc.common.utils.R;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuexin
 * @version 1.0
 * @date 2018-05-18 16:05
 */

@RestController("sysUploadController")
@RequestMapping("/sys")
public class FileUploadController {
    private final Logger log = LoggerFactory.getLogger(FileUploadController.class);

    private static final String IMG_PATH = "/statics/images";

    /**
     * @param files
     * @param request
     * @return
     * @throws IOException
     * @api {POST} /fileUpload/upload  上传图片接口
     * @apiGroup common
     * @apiPermission Chenyuexin
     * @apiParam {File[]} files 图片数组,注意input文件框的name属性必须为 files,否则上传失败
     * @apiSuccessExample {json} 成功的响应
     * HTTP/1.1 200 OK
     * {
     *      "code":"1",
     *      "msg":"成功",
     *      "data":{
     *      "imgPath": [
     *      "/statics/images/xxxxxxx.png"
     *          ]
     *     }
     *  }
     */
    @RequestMapping(value = "/upload")
    public R upload(MultipartFile[] files, HttpServletRequest request) throws IOException {

        JSONObject obj = new JSONObject();
        List<String> imgPath = new ArrayList<>();

        String filePath = request.getServletContext().getRealPath(IMG_PATH);

        if (null != files  && files.length > 0) {
            //循环获取file数组中得文件
            for (int i = 0; i < files.length; i++) {
                String fileName = String.valueOf(System.currentTimeMillis());
                MultipartFile file = files[i];
                //保存文件
                fileName = FileUploadUtils.fileUp(file, filePath, fileName);
                if(fileName != null && fileName != ""){
                    String imagePath = request.getContextPath() + IMG_PATH+ "/" + fileName;
                    log.info(imagePath);
                    imgPath.add(imagePath);
                }
            }

        }
        obj.put("imgPath", imgPath);
        return R.reOk(obj);
    }

    @RequestMapping(value = "/image/del")
    public R deleteImg(HttpServletRequest request){
        //删除原来的图片
        String oldImgUrl = request.getParameter("oldImgUrl");
        String filePath = request.getServletContext().getRealPath("");
        if(StringUtils.isNotBlank(oldImgUrl)){
            File oldFile = new File(filePath,oldImgUrl);
            if(null!= oldFile && true == oldFile.isFile()){
                oldFile.delete();
            }
        }
        return R.reOk();
    }
}

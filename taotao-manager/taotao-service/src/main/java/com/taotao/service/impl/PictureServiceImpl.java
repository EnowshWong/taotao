package com.taotao.service.impl;

import com.taotao.service.PictureService;
import com.taotao.utils.IDUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/13 15:33
 *   *
 **/
@Service
public class PictureServiceImpl implements PictureService {

    @Value("${PIC_STORAGE}")
    private String PIC_STORAGE;

    @Value("${PIC_BASE_URL}")
    private String PIC_BASE_URL;

    //图片上传
    @Override
    public Map pictureUpload(MultipartFile uploadFile) throws IOException {
        //旧文件名
        String oldName = uploadFile.getOriginalFilename();
        //使用id生成工具类创建新文件名
        String newName = IDUtils.genImageName() + oldName.substring(oldName.lastIndexOf("."));
        //存储路径,物理路径
        String pic_path = PIC_STORAGE;
        //将图片写入图片文件
        File newFile = new File(pic_path + newName);
        uploadFile.transferTo(newFile);
        Map resultMap = new HashMap();
        if (newFile != null) {
            resultMap.put("error", 0);
            resultMap.put("url", PIC_BASE_URL + newName);
            return resultMap;
        } else {
            resultMap.put("error", 1);
            resultMap.put("message", "文件上传失败");
            return resultMap;
        }
    }
}
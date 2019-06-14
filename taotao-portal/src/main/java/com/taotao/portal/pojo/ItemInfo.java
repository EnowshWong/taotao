package com.taotao.portal.pojo;

import com.taotao.pojo.TbItem;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.pojo
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/26 10:20
 *   *
 **/
public class ItemInfo extends TbItem {
    public String[] getImages() {
        String image = getImage();
        if (image != null && image.length() > 0) {
            String[] images = image.split(",");
            return images;
        }
        return null;
    }
}

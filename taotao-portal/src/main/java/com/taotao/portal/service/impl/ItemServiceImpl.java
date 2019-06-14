package com.taotao.portal.service.impl;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;
import com.taotao.utils.HttpClientUtil;
import com.taotao.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.portal.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/24 15:27
 *   *
 **/
@Service
public class ItemServiceImpl implements ItemService {

    @Value("${REST_BASE_URL}")
    private String REST_BASE_URL;

    @Value("${REST_ITEM_INFO_URL}")
    private String REST_ITEM_INFO_URL;

    @Value("${REST_ITEM_DESC_URL}")
    private String REST_ITEM_DESC_URL;

    @Value("${REST_ITEM_PARAM_URL}")
    private String REST_ITEM_PARAM_URL;

    @Override
    public ItemInfo getItemById(Long id) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_INFO_URL + "/" + id);
            if (!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
                if (taotaoResult.getStatus() == 200)
                    return (ItemInfo) taotaoResult.getData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemDescById(Long id) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_DESC_URL + "/" + id);
            if (!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemDesc.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemDesc desc = (TbItemDesc) taotaoResult.getData();
                    return desc.getItemDesc();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getItemParamById(Long id) {
        try {
            String json = HttpClientUtil.doGet(REST_BASE_URL + REST_ITEM_PARAM_URL + "/" + id);
            if (!StringUtils.isBlank(json)) {
                TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, TbItemParamItem.class);
                if (taotaoResult.getStatus() == 200) {
                    TbItemParamItem tbItemParamItem = (TbItemParamItem) taotaoResult.getData();
                    String paramData = tbItemParamItem.getParamData();
                    //将json数据转化为以map为元素的list
                    List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
                    //拼凑html
                    StringBuffer sb = new StringBuffer();
                    sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
                    sb.append("    <tbody>\n");
                    for (Map paramMap : paramList) {
                        sb.append("    <tr>\n");
                        sb.append("        <th class=\"tdTitle\" colspan=\"2\">" + paramMap.get("group") + "</th>\n");
                        sb.append("    </tr>\n");
                        List<Map> list1 = (List<Map>) paramMap.get("params");
                        for (Map map1 : list1) {
                            sb.append("    <tr>\n");
                            sb.append("        <td class=\"tdTitle\">" + map1.get("k") + "</td>\n");
                            sb.append("        <td>" + map1.get("v") + "</td>\n");
                            sb.append("    </tr>\n");
                        }
                    }
                    sb.append("    </tbody>\n");
                    sb.append("</table>");
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}

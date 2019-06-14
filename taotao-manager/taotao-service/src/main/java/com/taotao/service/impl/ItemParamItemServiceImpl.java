package com.taotao.service.impl;

import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamItemService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

import static com.taotao.utils.JsonUtils.jsonToList;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.service.impl
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/15 18:32
 *   *
 **/
@Service
public class ItemParamItemServiceImpl implements ItemParamItemService {
    @Autowired
    private TbItemParamItemMapper itemParamItemMapper;

    @Override
    public String findItemParamById(Long itemId) {
        TbItemParamItemExample example = new TbItemParamItemExample();
        TbItemParamItemExample.Criteria criteria = example.createCriteria();
        criteria.andItemIdEqualTo(itemId);
        List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);
        if (list == null || list.size() == 0) {
            return "";
        }
        /*
            该paramData是json数据
            [
                {
                "group": "主体",
                "params": [
                           {
                            "k": "品牌",
                            "v": "苹果（Apple）"
                           },
                           {
                            "k": "型号",
                            "v": "iPhone 6 A1589"
                            },
                            {
                            "k": "智能机",
                            "v": "是 "
                            }
                            ]
                }
            ]
         */
        String paramData = list.get(0).getParamData();
        //将json数据转化为以map为元素的list
        List<Map> paramList = JsonUtils.jsonToList(paramData, Map.class);
        //拼凑html
        StringBuffer sb = new StringBuffer();
        sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
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

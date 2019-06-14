package com.taotao.rest.controller;

import com.taotao.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;
import com.taotao.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 *     
 *   * @ProjectName:    taotao
 *   * @Package:        com.taotao.rest.controller
 *   * @ClassName:      ${TYPE_NAME}
 *   * @Description:    
 *   * @Author:         Michoel
 *   * @CreateDate:     2017/11/16 15:00
 *   *
 **/
@Controller
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;

    //    /*
//        方法一，该方法使用字符串拼接portal的jsonp请求，返回了json数据
//     */
//    //这里的produces属性指定了返回的是json数据，并且加上utf-8编码，解决乱码问题
//    @RequestMapping(value = "/itemcat/list",produces = MediaType.APPLICATION_JSON_VALUE+";charset=utf-8")
//    @ResponseBody
//    //传入的参数是：回调方法名
//    public String getItemCatList(String callback){
//        CatResult catResult=itemCatService.getItemCatList();
//        //将pojo转为json串
//        String json= JsonUtils.objectToJson(catResult);
//        String result=callback+"("+json+");";
//        return result;
//    }
    /*
        方法二，使用springmvc提供的新类
     */
    @RequestMapping("/itemcat/list")
    @ResponseBody
    public Object getItemCatList(String callback) {
        CatResult catResult = itemCatService.getItemCatList();
        //把json对象作为参数新建构造方法
        MappingJacksonValue jacksonValue = new MappingJacksonValue(catResult);
        jacksonValue.setJsonpFunction(callback);
        return jacksonValue;
    }
}

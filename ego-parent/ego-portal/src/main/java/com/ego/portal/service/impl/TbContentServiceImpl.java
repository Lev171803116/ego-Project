package com.ego.portal.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.fastjson.JSON;
import com.ego.TbContent;
import com.ego.dubboservice.service.TbContentDubboService;
import com.ego.portal.service.TbContentService;
import com.ego.redis.dao.JedisDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@PropertySource("redis.properties")
public class TbContentServiceImpl implements TbContentService {
    @Reference
    private TbContentDubboService tbContentDubboServiceImpl;

    @Resource
    private JedisDao jedisDaoImpl;

    @Value("${redis.bigpic.key}")
    private String key;
    @Override
    public String showBigPic() {
        //先判断是否在redis中存在
        if(jedisDaoImpl.exists(key)){
            String value = jedisDaoImpl.get(key);
            if(value != null && !value.equals("")){
                return value;
            }
        }

        //如果不存在在mysql中取，取完后放入redis中
        List<TbContent> list = tbContentDubboServiceImpl.selByCount(6, true);
        List<Map<String,Object>> listResult=new ArrayList<>();
        for (TbContent tbContent : list) {
            Map<String, Object> map = new HashMap<>();
            map.put("srcB",tbContent.getPic2());
            map.put("height",240);
            map.put("alt","对不起，加载图片失败");
            map.put("width",670);
            map.put("src",tbContent.getPic());
            map.put("widthB",550);
            map.put("href",tbContent.getUrl());
            map.put("heightB",240);
            listResult.add(map);
        }

        String listJson = JSON.toJSONString(listResult);
        //把数据放入redis中
        jedisDaoImpl.set(key,listJson);
        return listJson;
    }
}

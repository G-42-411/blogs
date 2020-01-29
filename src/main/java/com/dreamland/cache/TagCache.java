package com.dreamland.cache;

import com.dreamland.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class TagCache {
    public static List<TagDTO> get(){
        List<TagDTO> tagDTOs = new ArrayList<>();

        TagDTO tagDTO = new TagDTO();
        tagDTO.setCategoryName("开发语言");
        tagDTO.setTags(Arrays.asList("java","vb","c","html","node","python","php"));

        TagDTO framework = new TagDTO();
        framework.setCategoryName("平台框架");
        framework.setTags(Arrays.asList("spring","springboot","vue","django","struts"));

        TagDTO server = new TagDTO();
        server.setCategoryName("服务器");
        server.setTags(Arrays.asList("linux","nginx","docker","ubuntu","unix","负载均衡"));

        TagDTO db = new TagDTO();
        db.setCategoryName("数据库");
        db.setTags(Arrays.asList("mysql","redis","sql","oracle","sqlserver","nosql","sqlite"));

        tagDTOs.add(tagDTO);
        tagDTOs.add(framework);
        tagDTOs.add(server);
        tagDTOs.add(db);
        return tagDTOs;
    }

    public static String filterIsValid(String tags){
        String[] split = StringUtils.split(tags,",");
        List<TagDTO> tagDTOS = get();

        List<String>  tagList =  (List<String>)tagDTOS.stream().flatMap(tag -> tag.getTags().stream()).collect(Collectors.toList());
        String invalid =  Arrays.stream(split).filter(t -> !tagList.contains(t)).collect(Collectors.joining(","));
        return invalid;
    }
}

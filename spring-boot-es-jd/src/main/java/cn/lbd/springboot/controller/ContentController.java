package cn.lbd.springboot.controller;

import cn.lbd.springboot.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class ContentController {
    @Autowired
    ContentService service;

    @GetMapping("/parse/{keyword}")
    public Boolean parse(@PathVariable String keyword) throws Exception {
        return service.parseContent(keyword);
    }

    @GetMapping("/search/{keyword}/{pageNum}/{size}")
    public List<Map<String,Object>> searchPage(@PathVariable("keyword") String keyword,
                                               @PathVariable("pageNum")int pageNum,
                                               @PathVariable("size")int size) throws IOException {
        return service.searchPageHighLight(keyword, pageNum, size);
    }
}

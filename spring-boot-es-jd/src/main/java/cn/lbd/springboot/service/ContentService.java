package cn.lbd.springboot.service;

import cn.lbd.springboot.pojo.Content;
import cn.lbd.springboot.utils.HtmlParseUtil;
import com.alibaba.fastjson.JSON;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class ContentService {
    @Autowired
    HtmlParseUtil htmlParseUtil;

    @Qualifier("restHighLevelClient")
    @Autowired
    RestHighLevelClient client;

    //将解析完的数据放入ES索引中
    public Boolean parseContent(String keyword) throws Exception {
        List<Content> contents = htmlParseUtil.parseJD(keyword);
        BulkRequest request=new BulkRequest();
        request.timeout("10s");
        for(int i=0;i<contents.size();i++){
            request.add(new IndexRequest("j_goods").source(JSON.toJSONString(contents.get(i)), XContentType.JSON));
        }
        BulkResponse bulk = client.bulk(request, RequestOptions.DEFAULT);
        return !bulk.hasFailures();
    }

    public List<Map<String,Object>> searchPage(String keyword,int pageNum,int size) throws IOException {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(pageNum<1){
            pageNum=1;
        }
        SearchRequest request=new SearchRequest("j_goods");
        SearchSourceBuilder builder=new SearchSourceBuilder();
        builder.from(pageNum);
        builder.size(size);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);

        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            list.add(sourceAsMap);
        }
        return list;
    }

    public List<Map<String,Object>> searchPageHighLight(String keyword,int pageNum,int size) throws IOException {
        List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
        if(pageNum<1){
            pageNum=1;
        }
        SearchRequest request=new SearchRequest("j_goods");
        SearchSourceBuilder builder=new SearchSourceBuilder();
        HighlightBuilder highlightBuilder=new HighlightBuilder();
        highlightBuilder.field("title");
        highlightBuilder.requireFieldMatch(false);
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        builder.highlighter(highlightBuilder);
        builder.from(pageNum);
        builder.size(size);
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("title", keyword);
        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(10, TimeUnit.MINUTES));
        request.source(builder);
        SearchResponse search = client.search(request, RequestOptions.DEFAULT);
        //用高亮显示替换原来的内容
        for (SearchHit hit : search.getHits().getHits()) {
            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
            HighlightField title = highlightFields.get("title");
            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
            if(title!=null){
                Text[] fragments = title.fragments();
                String n_title="";
                for (Text fragment : fragments) {
                    n_title+=fragment;
                }
                sourceAsMap.put("title",n_title);
            }
            list.add(sourceAsMap);
        }
        return list;
    }
}

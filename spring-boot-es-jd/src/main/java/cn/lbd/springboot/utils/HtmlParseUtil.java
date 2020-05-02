package cn.lbd.springboot.utils;

import cn.lbd.springboot.pojo.Content;
import org.elasticsearch.common.recycler.Recycler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class HtmlParseUtil {
    //测试
    /*public static void main(String[] args) throws Exception {
        List<Content> contents = new HtmlParseUtil().parseJD("惠普 暗影精灵");
        for (Content content : contents) {
            System.out.println(content);
        }
    }*/

    public List<Content> parseJD(String keyword) throws Exception {
        //要爬取的url,加上enc=utf-8可以识别中文,keyword即我们要在搜索框中搜素的内容
        String url="https://search.jd.com/Search?keyword="+keyword+"&enc=utf-8";
        //解析网页。返回的这个这个Document即浏览器的Document对象
        Document document = Jsoup.parse(new URL(url), 30000);
        //获取id为J_goodsList的div
        Element element = document.getElementById("J_goodsList");
        //获取J_goodsList下的所有li标签
        Elements elements = element.getElementsByTag("li");
        List<Content> list=new ArrayList<Content>();
        //获取元素中的内容，每一个el就是一个li标签
        for (Element el : elements) {
            //京东用的是懒加载，追求的加载的速度
            //网页一开始放的是默认的图片，等网页加载好才把真正的商品图片放上来，故我们应该去拿source-data-lazy-img而不是src
            String image = el.getElementsByTag("img").eq(0).attr("source-data-lazy-img");
            String price = el.getElementsByClass("p-price").eq(0).text();
            String title = el.getElementsByClass("p-name").eq(0).text();
            //将爬取的数据全部封装到Content对象，我们就可以随心所欲使用
            Content content=new Content();
            content.setImage(image);
            content.setPrice(price);
            content.setTitle(title);
            list.add(content);
        }
        return list;
    }
}

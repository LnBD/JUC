package cn.lbd.springboot;

import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class SpringBootEsJdApplicationTests {
    @Qualifier("restHighLevelClient")
    @Autowired
    RestHighLevelClient client;

    @Test
    void contextLoads() throws IOException {
        CreateIndexRequest request=new CreateIndexRequest("j_goods");
        CreateIndexResponse createIndexResponse = client.indices().create(request, RequestOptions.DEFAULT);
        String index = createIndexResponse.index();
        System.out.println(index);
    }

}

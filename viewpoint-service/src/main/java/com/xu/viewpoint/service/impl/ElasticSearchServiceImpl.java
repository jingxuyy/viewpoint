package com.xu.viewpoint.service.impl;

//import com.xu.viewpoint.dao.domain.UserInfo;
//import com.xu.viewpoint.dao.domain.Video;
//import com.xu.viewpoint.dao.repository.UserInfoRepository;
//import com.xu.viewpoint.dao.repository.VideoRepository;
//import com.xu.viewpoint.service.ElasticSearchService;
//import org.elasticsearch.action.search.SearchRequest;
//import org.elasticsearch.action.search.SearchResponse;
//import org.elasticsearch.client.RequestOptions;
//import org.elasticsearch.client.RestHighLevelClient;
//import org.elasticsearch.common.text.Text;
//import org.elasticsearch.core.TimeValue;
//import org.elasticsearch.index.query.MultiMatchQueryBuilder;
//import org.elasticsearch.index.query.QueryBuilders;
//import org.elasticsearch.search.SearchHit;
//import org.elasticsearch.search.builder.SearchSourceBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
//import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.elasticsearch.client.ClientConfiguration;
//import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.TimeUnit;
//
///**
// * @author: xuJing
// * @date: 2024/12/11 12:25
// */
//@Service
//public class ElasticSearchServiceImpl implements ElasticSearchService {
//
//    @Autowired
//    private VideoRepository videoRepository;
//
//    @Autowired
//    private UserInfoRepository userInfoRepository;
//
//    @Autowired
//    private RestHighLevelClient restHighLevelClient;
//
//    /**
//     * 向es添加Video
//     *
//     * @param video
//     */
//    @Override
//    public void addVideo(Video video) {
//        videoRepository.save(video);
//    }
//
//
//    /**
//     * 根据关键字从标题查询Video
//     *
//     * @param keyword
//     */
//    @Override
//    public Video getVideoByTitle(String keyword) {
//        Video video = videoRepository.findByTitleLike(keyword);
//        return video;
//    }
//
//
//    /**
//     * 在es中删除所有video
//     */
//    @Override
//    public void deleteAllVideos() {
//        videoRepository.deleteAll();
//    }
//
//
//    /**
//     * 在es中添加UserInfo
//     *
//     * @param userInfo
//     */
//    @Override
//    public void addUserInfo(UserInfo userInfo) {
//        userInfoRepository.save(userInfo);
//    }
//
//
//    /**
//     * 根据keyword进行多索引库分页查询
//     *
//     * @param keyword
//     * @param pageNo
//     * @param pageSize
//     */
//    @Override
//    public List<Map<String, Object>> getContents(String keyword, Integer pageNo, Integer pageSize) throws IOException {
//
//        // 1. 列举需要查询的索引库名称
//        String[] indices = {"videos", "user-infos"};
//
//        // 2. 创建对应的请求对象
//        SearchRequest searchRequest = new SearchRequest(indices);
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//
//        // 3. 设置分页参数
//        searchSourceBuilder.from(pageNo-1);
//        searchSourceBuilder.size(pageSize);
//
//        // 4. 构建多匹配Builder并设置，需要匹配的属性和keyword
//        MultiMatchQueryBuilder matchQueryBuilder = QueryBuilders.multiMatchQuery(keyword, "title", "description", "nick");
//        searchSourceBuilder.query(matchQueryBuilder);
//
//        // 可以设置本次查询超时时间
//        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
//
//        // 5. 构建成完整请求
//        searchRequest.source(searchSourceBuilder);
//
//        // 6. 高亮显示
//        String[] array = {"title", "description", "nick"};
//        HighlightBuilder builder = new HighlightBuilder();
//        for (String key : array) {
//            builder.fields().add(new HighlightBuilder.Field(key));
//        }
//        // 多个字段都需要高亮，则下面设置为false，本身默认为true
//        builder.requireFieldMatch(false);
//        // 高亮起始与结束设置
//        builder.preTags("<span style=\"color:red\">");
//        builder.postTags("</span>");
//
//        // 7. 将高亮添加到请求中
//        searchSourceBuilder.highlighter(builder);
//
//        SearchResponse response = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
//        List<Map<String, Object>> arrayList = new ArrayList<>();
//
//        // 解析结果
//        for (SearchHit hit : response.getHits()) {
//            // 处理高亮
//            Map<String, HighlightField> highlightFields = hit.getHighlightFields();
//            Map<String, Object> sourceAsMap = hit.getSourceAsMap();
//            for (String key : array) {
//                HighlightField field = highlightFields.get(key);
//                if(field != null){
//                    Text[] fragments = field.fragments();
//                    String str = Arrays.toString(fragments);
//                    str = str.substring(1, str.length() -1);
//                    sourceAsMap.put(key, str);
//                }
//            }
//            arrayList.add(sourceAsMap);
//        }
//
//
//        return arrayList;
//    }
//}

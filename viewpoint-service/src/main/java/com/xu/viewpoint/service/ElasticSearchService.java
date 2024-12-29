//package com.xu.viewpoint.service;
//
//import com.xu.viewpoint.dao.domain.UserInfo;
//import com.xu.viewpoint.dao.domain.Video;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Map;
//
///**
// * @author: xuJing
// * @date: 2024/12/11 12:25
// */
//
//public interface ElasticSearchService {
//
//    /**
//     * 向es添加Video
//     * @param video
//     */
//    void addVideo(Video video);
//
//    /**
//     * 根据关键字从标题查询Video
//     * @param keyword
//     */
//    Video getVideoByTitle(String keyword);
//
//    /**
//     * 在es中删除所有video
//     */
//    void deleteAllVideos();
//
//    /**
//     * 在es中添加UserInfo
//     * @param userInfo
//     */
//    void addUserInfo(UserInfo userInfo);
//
//    /**
//     * 根据keyword进行多索引库分页查询
//     * @param keyword
//     * @param pageNo
//     * @param pageSize
//     */
//    List<Map<String, Object>> getContents(String keyword,
//                                          Integer pageNo,
//                                          Integer pageSize) throws IOException;
//}

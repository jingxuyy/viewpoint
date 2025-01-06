package com.xu.viewpoint.service.v2.impl;

import com.alibaba.fastjson.JSONObject;
import com.mysql.cj.util.StringUtils;
import com.xu.viewpoint.dao.domain.Content;
import com.xu.viewpoint.dao.domain.UserMoment;
import com.xu.viewpoint.dao.domain.Video;
import com.xu.viewpoint.dao.domain.VideoTag;
import com.xu.viewpoint.dao.domain.constant.UserMomentsConstant;
import com.xu.viewpoint.dao.domain.constant.VideoConstant;
import com.xu.viewpoint.dao.domain.exception.ConditionException;
import com.xu.viewpoint.dao.v2.VideoDao;
import com.xu.viewpoint.service.v2.IContentService;
import com.xu.viewpoint.service.v2.IUserMomentsService;
import com.xu.viewpoint.service.v2.IVideoService;
import com.xu.viewpoint.service.v2.IVideoTagService;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: xuJing
 * @date: 2025/1/6 15:04
 */
@Service
public class VideoService implements IVideoService {

    @Autowired
    private VideoDao videoDao;

    @Autowired
    private IVideoTagService videoTagService;

    @Autowired
    private IUserMomentsService userMomentsService;

    @Autowired
    private IContentService contentService;


    /**
     * 视频投稿
     *
     * @param video
     */
    @Transactional
    @Override
    public void addVideos(Video video) {
        // userId url thumbnail title type duration area description createTime
        // videoTagList

        // 1. 参数校验
        // 1.1 校验视频类型 type字段， 只允许 0 原创 1转载
        // 1.2 视频投稿的分区 area字段，目前支持 0 鬼畜 1 音乐 2 电影
        String type = video.getType();
        String area = video.getArea();
        if(StringUtils.isNullOrEmpty(type) || StringUtils.isNullOrEmpty(area)){
            throw new ConditionException("上传出错！");
        }
        if(!VideoConstant.VIDEO_TYPE.contains(type)){
            throw new ConditionException("不支持此视频类型！");
        }
        if(!VideoConstant.VIDEO_AREA.contains(area)){
            throw new ConditionException("不支持此视频分区！");
        }

        // 2. 视频上传 还会涉及 用户给视频设置的标签，是一个集合
        // 为了方便，在Video类中增加 List<VideoTag> videoTagList属性，用来传递视频标签

        // 3. 给video设置创建时间，之后存入数据库t_video中
        Date now = new Date();
        video.setCreateTime(new Date());

        videoDao.addVideos(video);


        // 4. 获取上一步存入到数据库的视频id,为每一个视频标签设置创建时间和关联的视频id
        Long videoId = video.getId();
        List<VideoTag> tagList = video.getVideoTagList();
        tagList.forEach(item -> {
            item.setCreateTime(now);
            item.setVideoId(videoId);
        });

        // 批量存入到数据库t_video_tag中
        videoTagService.batchAddVideoTags(tagList);

        //自动发布动态
        this.send(video);


    }


//    -------------------------------private-----------------------------

    /**
     * 存储动态具体内容
     * @param video
     */
    private Map<String, Object> saveContent(Video video){
        Content content = new Content();
        content.setContentDetail(JSONObject.parseObject(JSONObject.toJSONString(video)));
        contentService.addContent(content);
        HashMap<String, Object> map = new HashMap<>();
        map.put("contentId", content.getId());
        map.put("videoUserId", video.getUserId());
        return map;
    }

    /**
     * 发布动态
     * @param map
     */
    private void sendMoments(Map<String, Object> map) throws Exception {
        UserMoment moment = new UserMoment();
        moment.setType(UserMomentsConstant.TYPE_VIDEO);
        moment.setContentId((Long) map.get("contentId"));
        moment.setUserId((Long) map.get("videoUserId"));
        userMomentsService.addUserMoments(moment);
    }

    @Async
    protected void send(Video video){
        try {
            this.sendMoments(this.saveContent(video));
        } catch (Exception e) {
            throw new ConditionException("动态发布失败");
        }

    }
}

package com.xu.viewpoint.dao.repository;

import com.xu.viewpoint.dao.domain.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoRepository extends ElasticsearchRepository<Video, Long> {


    Video findByTitleLike(String keyword);

}

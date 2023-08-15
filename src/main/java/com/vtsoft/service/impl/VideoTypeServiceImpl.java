package com.vtsoft.service.impl;

import com.vtsoft.dao.VideoTypeRepository;
import com.vtsoft.service.VideoTypeService;
import org.springframework.stereotype.Service;

/**
 * description
 *
 * @author Garden
 * @version 1.0 create 2023/3/12 18:02
 */
@Service
public class VideoTypeServiceImpl implements VideoTypeService {

    /**
     * 视频分类数据
     */
    private final VideoTypeRepository videoTypeRepository;

    public VideoTypeServiceImpl(VideoTypeRepository videoTypeRepository) {
        this.videoTypeRepository = videoTypeRepository;
    }



}

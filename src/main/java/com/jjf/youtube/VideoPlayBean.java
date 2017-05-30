package com.jjf.youtube;

/**
 * Created by jjf_lenovo on 2017/5/15.
 */
import java.util.ArrayList;
import java.util.List;

public class VideoPlayBean {
    /**
     * 视频id
     */
    public String vid;

    /**
     * 视频的播放流
     */
    public List<FmtStreamMap> currentMaps = new ArrayList<FmtStreamMap>();
}
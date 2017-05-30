package com.jjf.youtube;

/**
 * Created by jjf_lenovo on 2017/5/15.
 */
import java.io.Serializable;

public class MediaFileInfo implements Serializable {

    private static final long serialVersionUID = 401314654472890540L;

    public enum FileCategory implements Serializable {
        Music, Video, Picture, Doc, Apk, Other, Albums, PicBack
    }

    public String folderPath = "";

    public String filePath = "";

    public String fileName = "";

    public long fileSize;

    public int Count;

    public long ModifiedDate;

    public boolean Selected;

    public long duration;

    public boolean canRead;

    public boolean canWrite;

    public boolean isHidden;

    public FileCategory fileType;// 1 app 2 video 3 picture 4 music 5 document

    public long dbId;

}
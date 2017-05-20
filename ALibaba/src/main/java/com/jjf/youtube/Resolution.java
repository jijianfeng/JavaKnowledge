package com.jjf.youtube;

/**
 * Created by jjf_lenovo on 2017/5/15.
 */
public class Resolution {
    public Resolution(String _id, String _resolution, String _format, String _type, ResolutionNote _notes) {
        id = _id;
        resolution = _resolution;
        format = _format;
        type = _type;
        notes = _notes;
    }

    public String id;
    public String resolution;
    public String format;
    public String type;
    public ResolutionNote notes;
}
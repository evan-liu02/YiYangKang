package com.anju.yyk.common.entity.response;

import com.anju.yyk.common.base.BaseResponse;
import com.chad.library.adapter.base.entity.MultiItemEntity;

public class TipsResponse extends BaseResponse implements MultiItemEntity {

    public static final int NORMAL_TYPE = 0;

    private String date;
    private String audioUrl;
    private String workerName;
    private String recordTime;
    private String readWorker;
    private int readed;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAudioUrl() {
        return audioUrl;
    }

    public void setAudioUrl(String audioUrl) {
        this.audioUrl = audioUrl;
    }

    public String getWorkerName() {
        return workerName;
    }

    public void setWorkerName(String workerName) {
        this.workerName = workerName;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getReadWorker() {
        return readWorker;
    }

    public void setReadWorker(String readWorker) {
        this.readWorker = readWorker;
    }

    public int getReaded() {
        return readed;
    }

    public void setReaded(int readed) {
        this.readed = readed;
    }

    @Override
    public int getItemType() {
        return NORMAL_TYPE;
    }
}

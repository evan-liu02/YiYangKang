package com.anju.yyk.main.ui.frg.recordlist;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.RecordResponse;

import io.reactivex.Observable;

public interface IRecordListContract {

    interface IRecordListModel extends IBaseModel{
        /**
         * 护理
         * @param id
         * @param startTime
         * @param endTime
         * @return
         */
        Observable<RecordResponse> requestCareRecordList(String id, String startTime, String endTime);

        /**
         * 查房
         * @param id
         * @param startTime
         * @param endTime
         * @return
         */
        Observable<RecordResponse> requestCheckRoomRecordList(String id, String startTime, String endTime);

        /**
         * 事故
         * @param id
         * @param startTime
         * @param endTime
         * @return
         */
        Observable<RecordResponse> requestAccidentRecordList(String id, String startTime, String endTime);
    }

    interface IRecordListView extends IBaseView{
        void getCareRecordSucc(RecordResponse response);

        void getCheckRoomSucc(RecordResponse response);

        void getaccidentSucc(RecordResponse response);
    }

    abstract class RecordListPresenter extends BasePresenter<IRecordListModel, IRecordListView>{
        public abstract void getCareRecord(String id, String startTime, String endTime);

        public abstract void getCheckRoom(String id, String startTime, String endTime);

        public abstract void getAccident(String id, String startTime, String endTime);
    }

}

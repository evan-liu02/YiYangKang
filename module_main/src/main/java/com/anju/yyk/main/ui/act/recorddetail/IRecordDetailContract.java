package com.anju.yyk.main.ui.act.recorddetail;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.AccidentDetailResponse;
import com.anju.yyk.common.entity.response.CareDetailResponse;
import com.anju.yyk.common.entity.response.CheckRoomDetailResponse;

import io.reactivex.Observable;

public interface IRecordDetailContract {
    interface IRecordDetailModel extends IBaseModel{
        /**
         * 查房详情
         * @param chafang_id
         * @return
         */
        Observable<CheckRoomDetailResponse> requestCheckRoomDetail(String chafang_id);

        /**
         * 护理详情
         * @param laoren_id
         * @param time
         * @return
         */
        Observable<CareDetailResponse> requestCareDetail(String laoren_id, String time);

        /**
         * 事故详情
         * @param shigu_id
         * @return
         */
        Observable<AccidentDetailResponse> requestAccidentDetail(String shigu_id);
    }

    interface IRecordDetailView extends IBaseView{
        void checkRoomDetailSucc(CheckRoomDetailResponse response);

        void careDetailSucc(CareDetailResponse response);

        void accidentDetailSucc(AccidentDetailResponse response);
    }

    abstract class RecordDetailPresenter extends BasePresenter<IRecordDetailModel, IRecordDetailView> {
        public abstract void checkRoomDetail(String chafang_id);

        public abstract void careDetail(String laoren_id, String time);

        public abstract void accidentDetail(String shigu_id);
    }
}

package com.anju.yyk.main.ui.act.infodetail;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.DiseaseResponse;
import com.anju.yyk.common.entity.response.HealthResposne;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.entity.response.OperationResponse;

import io.reactivex.Observable;

public interface IInfoDetailContract {

    interface IInfoDetailModel extends IBaseModel{
        /**
         * 家属信息
         * @param id 老人id
         * @return
         */
        Observable<JisShuResponse> requestJisShu(String id);

        /**
         * 健康信息
         * @param id 老人id
         * @return
         */
        Observable<HealthResposne> requestHealthInfo(String id);

        /**
         * 疾病史
         * @param id 老人id
         * @return
         */
        Observable<DiseaseResponse> requestDiseaseInfo(String id);

        /**
         * 手术史
         * @param id 老人id
         * @return
         */
        Observable<OperationResponse> requestOperationInfo(String id);
    }

    interface IInfoDetailView extends IBaseView{
        /**
         * 家属信息获取成功
         * @param info
         */
        void jisShuInfoSucc(JisShuResponse info);

        /**
         * 健康情况
         * @param info
         */
        void healthInfoSucc(HealthResposne info);

        /**
         * 疾病史
         * @param info
         */
        void diseaseInfoSucc(DiseaseResponse info);

        /**
         * 手术史
         * @param info
         */
        void operationInfoSucc(OperationResponse info);
    }

    abstract class IInfoDetailPresenter extends BasePresenter<IInfoDetailModel, IInfoDetailView>{
        /**
         * 获取家属信息
         * @param id 老人id
         */
        public abstract void getJisShuInfo(String id);

        /**
         * 获取健康信息
         * @param id 老人id
         */
        public abstract void getHealthInfo(String id);

        /**
         * 获得老人疾病史
         * @param id 老人id
         */
        public abstract void getDiseaseInfo(String id);

        /**
         * 获得手术史
         * @param id
         */
        public abstract void getOperationInfo(String id);
    }

}

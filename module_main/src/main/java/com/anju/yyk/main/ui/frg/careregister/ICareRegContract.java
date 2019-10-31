package com.anju.yyk.main.ui.frg.careregister;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.HuLiXiangmu0Response;
import com.anju.yyk.common.entity.response.HuLiXiangmu1Response;

import io.reactivex.Observable;

public interface ICareRegContract {

    interface ICareRegModel extends IBaseModel{
        /**
         * 护理项目提交项列表
         * @param id 老人id
         * @return
         */
        Observable<HuLiXiangmu0Response> requestHuli0(String id);

        /**
         * 护理项目已完成项列表
         * @param id 老人id
         * @return
         */
        Observable<HuLiXiangmu1Response> requestHuli1(String id);

        /**
         * 护理项提交
         * @param id 项目id
         * @param jifen 积分
         * @param title 项目名
         * @param laoren_id 老人id
         * @param hugong_id 护工id
         * @return
         */
        Observable<BaseResponse> requestComplete(String id, String jifen, String title, String laoren_id
                            , String hugong_id);
    }

    interface ICareRegView extends IBaseView{

        /**
         * 获得护理提交项成功
         * @param response
         */
        void getHuli0Succ(HuLiXiangmu0Response response);

        /**
         * 获得已完成护理项成功
         * @param response
         */
        void getHuli1Succ(HuLiXiangmu1Response response);


        /**
         * 提交护理项
         * @param response
         */
        void commitHuliSucc(BaseResponse response);

    }

    abstract class ICareRegPresenter extends BasePresenter<ICareRegModel, ICareRegView>{
        /**
         * 加载护理提交项
         * @param id 老人id
         */
        public abstract void huli0(String id);

        /**
         * 加载已完成护理项
         * @param id 老人id
         */
        public abstract void huli1(String id);

        /**
         * 护理项提交
         * @param id 项目id
         * @param jifen 积分
         * @param title 项目名
         * @param laoren_id 老人id
         * @param hugong_id 护工id
         * @return
         */
        public abstract void commitHuli(String id, String jifen, String title, String laoren_id
                , String hugong_id);
    }

}

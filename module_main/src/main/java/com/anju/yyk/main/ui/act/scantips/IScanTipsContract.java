package com.anju.yyk.main.ui.act.scantips;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.NewTipsListResponse;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;

public interface IScanTipsContract {

    interface IScanTipsModel extends IBaseModel{
        Observable<NewTipsListResponse> requestTips();

        Observable<NewTipsListResponse> requestPersonalTips(String laoren_id);

        Observable<ResponseBody> requestDownLoadAudio(String audioUrl);

        Observable<BaseResponse> requestReaded(String zhuyi_id);
    }

    interface IScanTipsView extends IBaseView{
        void getTipsSucc(List<NewTipsListResponse.ListBean> listBeans);

        void getPersonalTipsSucc(List<NewTipsListResponse.ListBean> listBeans);

        void downLoadSucc();

        void readedSucc();
    }

    abstract class ScanTipsPresenter extends BasePresenter<IScanTipsModel, IScanTipsView>{
        public abstract void loadTips();

        public abstract void loadPersonalTips(String laoren_id);

        public abstract void downAudio(String audioUrl);

        public abstract void read(String zhuyi_id);
    }

}

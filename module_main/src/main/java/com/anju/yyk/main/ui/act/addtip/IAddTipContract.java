package com.anju.yyk.main.ui.act.addtip;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.UploadFileResponse;

import io.reactivex.Observable;

public interface IAddTipContract {
    interface IAddTipModel extends IBaseModel{
        Observable<BaseResponse> addTip(String id, String userId, String content, String filePath);
        Observable<UploadFileResponse> uploadAudio(String filePath);
    }

    interface IAddTipView extends IBaseView{
        void uploadSucc(BaseResponse response);
    }

    abstract class AddTipPresenter extends BasePresenter<IAddTipModel, IAddTipView>{
        public abstract void addTip(String id, String userId, String content, String filePath);
    }
}

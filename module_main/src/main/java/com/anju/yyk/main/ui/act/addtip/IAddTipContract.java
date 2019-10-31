package com.anju.yyk.main.ui.act.addtip;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IAddTipContract {
    interface IAddTipModel extends IBaseModel{
        Observable<BaseResponse> addTip(RequestBody des, MultipartBody.Part file);
    }

    interface IAddTipView extends IBaseView{
        void uploadSucc(BaseResponse response);
    }

    abstract class AddTipPresenter extends BasePresenter<IAddTipModel, IAddTipView>{
        public abstract void uploadAudio(String content, String filePath);
    }
}

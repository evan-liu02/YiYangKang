package com.anju.yyk.main.ui.frg.accidentregister;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IAccidentRegContract {
    interface IAccidentRegModel extends IBaseModel{
        Observable<BaseResponse> addTip(RequestBody des, MultipartBody.Part file);
        Observable<BaseResponse> requestUploadPhoto(MultipartBody.Part imageFile);
    }

    interface IAccidentRegView extends IBaseView{
        void uploadSucc();
        void uploadSucc(BaseResponse response);
    }

    abstract class AccidentReg extends BasePresenter<IAccidentRegModel, IAccidentRegView>{
        public abstract void uploadPhoto(String imgPath);
        public abstract void uploadAudio(String content, String filePath);
    }
}

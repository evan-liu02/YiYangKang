package com.anju.yyk.main.ui.frg.accidentregister;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.UploadFileResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public interface IAccidentRegContract {
    interface IAccidentRegModel extends IBaseModel{
        Observable<BaseResponse> addTip(RequestBody des, MultipartBody.Part file);
        Observable<BaseResponse> requestUploadPhoto(MultipartBody.Part imageFile);
        Observable<UploadFileResponse> uploadFile(String name, MultipartBody.Part file);
        Observable<UploadFileResponse> uploadImage(String name, String type, int isThumb, int isWater, MultipartBody.Part file);
        Observable<BaseResponse> addAccident(String id, String userId, String content, String audioPath, String imagePath);
    }

    interface IAccidentRegView extends IBaseView{
        void uploadSucc();
        void uploadSucc(String filePath); // 如果上传成功的话，filePath是服务器返回的文件路径
        void uploadSucc(BaseResponse response);
        void uploadFailed(String filePath);
        void addAccidentSuccess();
        void addAccidentFailed();
    }

    abstract class AccidentReg extends BasePresenter<IAccidentRegModel, IAccidentRegView>{
        public abstract void uploadPhoto(String imgPath);
        public abstract void uploadAudio(String content, String filePath);
        public abstract void uploadFile(String filePath);
        public abstract void uploadImage(String filePath, String type, int isThumb, int isWater);
        public abstract void addAccident(String id, String userId, String content, String audioPath, String imagePath);
    }
}

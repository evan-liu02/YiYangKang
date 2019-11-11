package com.anju.yyk.main.ui.frg.register;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;
import com.anju.yyk.common.entity.response.UploadImageResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface IRegisterContract {

    interface IRegisterModel extends IBaseModel{
        Observable<CheckRoomListResponse> requestCheckRoomList();

        /**
         *
         * @param hugong_id
         * @param txtContent
         * @param isCheck 1选  0未选
         * @return
         */
        Observable<BaseResponse> requestCheckRoomCommit(String hugong_id, String txtContent
                , int isCheck, String laoren_id, Map<String, String> map, String imagePath);
        Observable<UploadImageResponse> uploadImage(String name, MultipartBody.Part file);
    }

    interface IRegisterView extends IBaseView{
        void checkRoomListSucc(CheckRoomListResponse response);
        void checkRoomCommitSucc();
        void uploadSuccess(String filePath); // 如果上传成功的话，filePath是服务器返回的文件路径
        void uploadFailed(String filePath);
    }

    abstract class RegisterPresenter extends BasePresenter<IRegisterModel, IRegisterView>{

        public abstract void getCheckRoomList();

        public abstract void checkRoomCommit(String hugong_id, String txtContent
                , boolean isCheck, String laoren_id, Map<String, String> map, String imagePath);
        public abstract void uploadImage(String filePath);

    }

}

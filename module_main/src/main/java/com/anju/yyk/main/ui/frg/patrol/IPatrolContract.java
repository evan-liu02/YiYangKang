package com.anju.yyk.main.ui.frg.patrol;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.PatrolResponse;
import com.anju.yyk.common.entity.response.UploadImageResponse;

import io.reactivex.Observable;
import okhttp3.MultipartBody;

public interface IPatrolContract {
    interface IPatrolModel extends IBaseModel{
        /**
         * 安全巡查项目
         * @return
         */
        Observable<PatrolResponse> requestPatrol();
        Observable<UploadImageResponse> uploadImage(String name, MultipartBody.Part file);
    }

    interface IPatrolView extends IBaseView{
        void patrolSucc(PatrolResponse response);
        void uploadSuccess(String filePath); // 如果上传成功的话，filePath是服务器返回的文件路径
        void uploadFailed(String filePath);
    }

    abstract class PatrolPresenter extends BasePresenter<IPatrolModel, IPatrolView>{
        public abstract void getPatrol();
        public abstract void uploadImage(String filePath);
    }
}

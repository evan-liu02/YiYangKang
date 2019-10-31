package com.anju.yyk.main.ui.frg.register;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.CheckRoomListResponse;

import java.util.Map;

import io.reactivex.Observable;

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
                , int isCheck, String laoren_id, Map<String, String> map);
    }

    interface IRegisterView extends IBaseView{
        void checkRoomListSucc(CheckRoomListResponse response);
        void checkRoomCommitSucc();
    }

    abstract class RegisterPresenter extends BasePresenter<IRegisterModel, IRegisterView>{

        public abstract void getCheckRoomList();

        public abstract void checkRoomCommit(String hugong_id, String txtContent
                , boolean isCheck, String laoren_id, Map<String, String> map);

    }

}

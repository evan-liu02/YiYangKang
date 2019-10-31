package com.anju.yyk.main.ui.frg.home;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.AttentionCountResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;

import java.util.List;

import io.reactivex.Observable;

public interface IHomeContract {

    interface IHomeModel extends IBaseModel{

        Observable<AttentionCountResponse> requestAttentionCount();

        Observable<PersonListResponse> requestPersonTop();

        Observable<PersonListResponse> requestPersonList();

        Observable<BaseResponse> requestChangeTopStatus(String personId, int status);
    }

    interface IHomeView extends IBaseView{

        void getAttentionCountSucc(AttentionCountResponse response);

        void getPersonTop(List<PersonListResponse.ListBean> list);

        void getPersonListSucc(List<PersonListResponse.ListBean> list);

        void changeTopStatusSucc();

        void noTop();

        void refreshComplete();
    }

    abstract class HomePresenter extends BasePresenter<IHomeModel, IHomeView>{

        public abstract void getAttentionCount();

        public abstract void getPersonTop();

        public abstract void getPersonList();

        public abstract void changeTopStatus(String personId, int status);

    }
}

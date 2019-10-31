package com.anju.yyk.main.ui.frg.patrol;

import com.anju.yyk.common.base.BasePresenter;
import com.anju.yyk.common.base.IBaseModel;
import com.anju.yyk.common.base.IBaseView;
import com.anju.yyk.common.entity.response.PatrolResponse;

import io.reactivex.Observable;

public interface IPatrolContract {
    interface IPatrolModel extends IBaseModel{
        /**
         * 安全巡查项目
         * @return
         */
        Observable<PatrolResponse> requestPatrol();
    }

    interface IPatrolView extends IBaseView{
        void patrolSucc(PatrolResponse response);
    }

    abstract class PatrolPresenter extends BasePresenter<IPatrolModel, IPatrolView>{
        public abstract void getPatrol();
    }
}

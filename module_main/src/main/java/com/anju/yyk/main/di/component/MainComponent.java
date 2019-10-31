package com.anju.yyk.main.di.component;

import com.anju.yyk.common.di.component.AppComponent;
import com.anju.yyk.common.di.scope.ActivityScope;
import com.anju.yyk.main.adapter.PatrolAdapter;
import com.anju.yyk.main.adapter.TakePhotoAdapter;
import com.anju.yyk.main.ui.act.addtip.AddTipModel;
import com.anju.yyk.main.ui.act.addtip.AddTipPresenter;
import com.anju.yyk.main.ui.act.homepage.HomePageAct;
import com.anju.yyk.main.ui.act.infodetail.InfoDetailModel;
import com.anju.yyk.main.ui.act.infodetail.InfoDetailPresenter;
import com.anju.yyk.main.ui.act.login.LoginAct;
import com.anju.yyk.main.ui.act.login.LoginModel;
import com.anju.yyk.main.ui.act.login.LoginPresenter;
import com.anju.yyk.main.ui.act.recorddetail.RecordDetailModel;
import com.anju.yyk.main.ui.act.recorddetail.RecordDetailPresenter;
import com.anju.yyk.main.ui.act.scantips.ScanTipsModel;
import com.anju.yyk.main.ui.act.scantips.ScanTipsPresenter;
import com.anju.yyk.main.ui.act.web.WebAct;
import com.anju.yyk.main.ui.frg.accidentregister.AccidentRegModel;
import com.anju.yyk.main.ui.frg.accidentregister.AccidentRegPresenter;
import com.anju.yyk.main.ui.frg.careregister.CareRegModel;
import com.anju.yyk.main.ui.frg.careregister.CareRegPresenter;
import com.anju.yyk.main.ui.frg.home.HomeFrg;
import com.anju.yyk.main.ui.frg.home.HomeModel;
import com.anju.yyk.main.ui.frg.home.HomePresenter;
import com.anju.yyk.main.ui.frg.patrol.PatrolModel;
import com.anju.yyk.main.ui.frg.patrol.PatrolPresenter;
import com.anju.yyk.main.ui.frg.patrol.PatrolWebFrg;
import com.anju.yyk.main.ui.frg.recordlist.CheckRoomRecordListFrg;
import com.anju.yyk.main.ui.frg.recordlist.RecordListModel;
import com.anju.yyk.main.ui.frg.recordlist.RecordPresenter;
import com.anju.yyk.main.ui.frg.register.RegisterFrg;
import com.anju.yyk.main.ui.frg.register.RegisterModel;
import com.anju.yyk.main.ui.frg.register.RegisterPresenter;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface MainComponent {

    void inject(PatrolAdapter adapter);

    void inject(LoginModel model);
    void inject(LoginPresenter presenter);
    void inject(LoginAct act);

    void inject(HomeFrg frg);
    void inject(HomeModel model);
    void inject(HomePresenter presenter);

    void inject(WebAct act);

    void inject(ScanTipsModel model);
    void inject(ScanTipsPresenter presenter);

    void inject(InfoDetailModel model);
    void inject(InfoDetailPresenter presenter);

    void inject(RecordListModel model);
    void inject(RecordPresenter presenter);

    void inject(CheckRoomRecordListFrg frg);

    void inject(CareRegModel model);
    void inject(CareRegPresenter presenter);

    void inject(RegisterModel model);
    void inject(RegisterPresenter presenter);

    void inject(RecordDetailModel model);
    void inject(RecordDetailPresenter presenter);

    void inject(HomePageAct act);

    void inject(PatrolModel model);
    void inject(PatrolPresenter presenter);

    void inject(PatrolWebFrg frg);

    void inject(AddTipModel model);
    void inject(AddTipPresenter presenter);

    void inject(RegisterFrg frg);

    void inject(TakePhotoAdapter adapter);

    void inject(AccidentRegModel model);
    void inject(AccidentRegPresenter presenter);

}

package com.anju.yyk.di.component;

import com.anju.yyk.LoadingAct;
import com.anju.yyk.YYKApp;
import com.anju.yyk.common.di.component.AppComponent;
import com.anju.yyk.common.di.scope.ActivityScope;

import dagger.Component;

@ActivityScope
@Component(dependencies = AppComponent.class)
public interface YYKComponent {

    void inject(YYKApp app);

    void inject(LoadingAct act);
}

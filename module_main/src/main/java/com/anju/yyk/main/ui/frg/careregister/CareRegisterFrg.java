package com.anju.yyk.main.ui.frg.careregister;

import android.widget.TextView;

import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.app.sp.AppSP;
import com.anju.yyk.common.base.BaseMvpFragment;
import com.anju.yyk.common.base.BaseResponse;
import com.anju.yyk.common.entity.response.HuLiXiangmu0Response;
import com.anju.yyk.common.entity.response.HuLiXiangmu1Response;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.common.widget.LabelsView;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 *
 * @author LeoWang
 *
 * @Package com.anju.yyk.main.ui.frg.careregister
 *
 * @Description 护理登记
 *
 * @Date 2019/10/16 17:28
 *
 * @modify:
 */
public class CareRegisterFrg extends BaseMvpFragment<CareRegPresenter, CareRegModel> implements ICareRegContract.ICareRegView{

    @BindView(R2.id.un_complete_lv)
    LabelsView mUnCompleteLv;

    @BindView(R2.id.complete_lv)
    LabelsView mCompleteLv;

    private PersonListResponse.ListBean mPersonInfo;

    private ArrayList<HuLiXiangmu0Response.ListBean> mLabel0 = new ArrayList<>();
    private ArrayList<HuLiXiangmu1Response.ListBean> mLabel1 = new ArrayList<>();

    private AppSP mAppSP;

    @Override
    public int getLayoutId() {
        return R.layout.home_frg_careregister;
    }

    @Override
    public void init() {
        mAppSP = new AppSP(mActivity);
        if (getArguments() != null){
            mPersonInfo = (PersonListResponse.ListBean) getArguments().getSerializable(RouterKey.BUNDLE_TAG);
        }
    }

    @Override
    public void initListener() {
        mUnCompleteLv.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                HuLiXiangmu0Response.ListBean bean = (HuLiXiangmu0Response.ListBean) data;
//                showToast("点击的id：" + bean.getId());
                // TODO 是否需要加入提示对话框
                mPresenter.commitHuli(bean.getId(), bean.getJifen(), bean.getTitle(), mPersonInfo.getId()
                                    , mAppSP.getUserId());
            }
        });
    }

    @Override
    protected void initData() {
        mPresenter.huli0(mPersonInfo.getId());
    }

    @Override
    protected void setupFragmentComponent() {

    }

    @Override
    protected boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void getHuli0Succ(HuLiXiangmu0Response response) {
        List<HuLiXiangmu0Response.ListBean> listBeans = response.getList();
        if (listBeans != null && listBeans.size() > 0){
            mLabel0.clear();
            mLabel0.addAll(listBeans);
            /*for (HuLiXiangmu0Response.ListBean bean : listBeans){
                mLabel0.add(bean);
            }*/
            mUnCompleteLv.setLabels(mLabel0, (label1, position, data) -> data.getTitle());
        }
        mPresenter.huli1(mPersonInfo.getId());
    }

    @Override
    public void getHuli1Succ(HuLiXiangmu1Response response) {
        List<HuLiXiangmu1Response.ListBean> listBeans = response.getList();
        if (listBeans != null && listBeans.size() > 0){
            mLabel1.clear();
            mLabel1.addAll(listBeans);
            /*for (HuLiXiangmu1Response.ListBean bean : listBeans){
                mLabel1.add(bean);
            }*/
            mCompleteLv.setLabels(mLabel1, (label1, position, data) -> data.getTitle() + "x" + data.getCishu());
        }
    }

    @Override
    public void commitHuliSucc(BaseResponse response) {
        mPresenter.huli1(mPersonInfo.getId());
    }
}

package com.anju.yyk.main.ui.act.infodetail;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.anju.yyk.common.app.arouter.RouterConstants;
import com.anju.yyk.common.app.arouter.RouterKey;
import com.anju.yyk.common.base.BaseMvpActivity;
import com.anju.yyk.common.entity.response.DiseaseResponse;
import com.anju.yyk.common.entity.response.HealthResposne;
import com.anju.yyk.common.entity.response.JisShuResponse;
import com.anju.yyk.common.entity.response.OperationResponse;
import com.anju.yyk.common.entity.response.PersonListResponse;
import com.anju.yyk.main.R;
import com.anju.yyk.main.R2;

import java.util.List;

import butterknife.BindView;

/**
 * 
 * @author LeoWang
 * 
 * @Package com.anju.yyk.main.ui.act.infodetail
 * 
 * @Description 详细信息
 * 
 * @Date 2019/10/18 11:18
 * 
 * @modify:
 */
@Route(path = RouterConstants.ACT_URL_INFO_DETAIL)
public class InfoDetailAct extends BaseMvpActivity<InfoDetailPresenter,InfoDetailModel> implements IInfoDetailContract.IInfoDetailView{

    @BindView(R2.id.iv_sex)
    ImageView mSexIv;

    @BindView(R2.id.tv_age)
    TextView mAgeTv;

    @BindView(R2.id.tv_name)
    TextView mNameTv;

    @BindView(R2.id.tv_bedid)
    TextView mBedIdTv;

    @BindView(R2.id.tv_tag)
    TextView mTagTv;

    @BindView(R2.id.tv_detail_info)
    TextView mDetailInfoTv;

    @BindView(R2.id.tv_name1)
    TextView mNameTv1;

    @BindView(R2.id.tv_name2)
    TextView mNameTv2;

    @BindView(R2.id.tv_phone1)
    TextView mPhoneTv1;

    @BindView(R2.id.tv_phone2)
    TextView mPhoneTv2;

    @BindView(R2.id.tv_relation1)
    TextView mRelationTv1;

    @BindView(R2.id.tv_relation2)
    TextView mRelationTv2;

    @BindView(R2.id.tv_allergy_history)
    TextView mAllergyTv;

    @BindView(R2.id.tv_disease_history)
    TextView mDiseaseTv;

    @BindView(R2.id.tv_operation_history)
    TextView mOperationTv;

    @BindView(R2.id.tv_pulse)
    TextView mPulseTv;

    @Autowired(name = RouterKey.BUNDLE_TAG)
    public PersonListResponse.ListBean mPersonInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.home_act_infodetail;
    }

    @Override
    protected void init() {
        ARouter.getInstance().inject(this);
        setToolbarTopic(R.string.common_topic_detail_info);
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        if (mPersonInfo.getSex() == 2){
            mSexIv.setImageResource(R.mipmap.home_ic_famale);
        }else {
            mSexIv.setImageResource(R.mipmap.home_ic_male);
        }
        mAgeTv.setText(mPersonInfo.getNianling());
        mNameTv.setText(mPersonInfo.getName());
        mBedIdTv.setText(mPersonInfo.getChuangwei() + "号床");
        mTagTv.setText(mPersonInfo.getHulijibie());

        mPresenter.getJisShuInfo(mPersonInfo.getId());
    }

    @Override
    protected void setupActivityComponent() {

    }

    @Override
    public boolean isRegisterEventBus() {
        return false;
    }

    @Override
    public void jisShuInfoSucc(JisShuResponse info) {
        if (info.getStatus() == 0){
            List<JisShuResponse.ListBean> listBeans = info.getList();
            if (listBeans != null && listBeans.size() > 0){
                for (int i = 0; i < listBeans.size(); i++){
                    if (i == 0){
                        mNameTv1.setText(listBeans.get(i).getName());
                        mPhoneTv1.setText(listBeans.get(i).getPhone());
                        mRelationTv1.setText(listBeans.get(i).getGuanxi());
                    }else if (i == 1){
                        mNameTv2.setVisibility(View.VISIBLE);
                        mPhoneTv2.setVisibility(View.VISIBLE);
                        mRelationTv2.setVisibility(View.VISIBLE);
                        mNameTv2.setText(listBeans.get(i).getName());
                        mPhoneTv2.setText(listBeans.get(i).getPhone());
                        mRelationTv2.setText(listBeans.get(i).getGuanxi());
                    }
                }
            }
        }
        mPresenter.getHealthInfo(mPersonInfo.getId());
    }

    @Override
    public void healthInfoSucc(HealthResposne info) {
        if (info.getStatus() == 0){
            mAllergyTv.setText(info.getGuominshi());
            mPulseTv.setText("脉率：" + info.getMailv() + "        " + "体温：" + info.getTiwen());
        } else {
            mAllergyTv.setText("无");
        }
        mPresenter.getDiseaseInfo(mPersonInfo.getId());
    }

    @Override
    public void diseaseInfoSucc(DiseaseResponse info) {
        if (info.getStatus() == 0){
            List<DiseaseResponse.ListBean> listBeans = info.getList();
            if (listBeans != null && listBeans.size() > 0){
                StringBuffer sb = new StringBuffer();
                for (DiseaseResponse.ListBean bean : listBeans){
                    sb.append(bean.getName() + "    ");
                }
                mDiseaseTv.setText(sb.toString());
            }
        } else {
            mDiseaseTv.setText("无");
        }

        mPresenter.getOperationInfo(mPersonInfo.getId());
    }

    @Override
    public void operationInfoSucc(OperationResponse info) {
        if (info.getStatus() == 0){
            List<OperationResponse.ListBean> listBeans = info.getList();
            if (listBeans != null && listBeans.size() > 0){
                StringBuffer sb = new StringBuffer();
                for (OperationResponse.ListBean bean : listBeans){
                    sb.append(bean.getName() + "    ");
                }
                mOperationTv.setText(sb.toString());
            }
        } else {
            mOperationTv.setText("无");
        }
    }
}

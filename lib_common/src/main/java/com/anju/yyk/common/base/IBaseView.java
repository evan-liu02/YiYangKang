package com.anju.yyk.common.base;

public interface IBaseView {
    /**
     * 提示
     * @param msg 文本
     */
    void showToast(String msg);

    /**
     * 提示
     * @param strId 文字资源id
     */
    void showToast(int strId);

    /**
     * 显示加载框
     * @param isCancel 是否可以取消
     */
    void showLoading(boolean isCancel);

    /**
     * 隐藏加载框
     */
    void hideLoading();

    /**
     * 服务器提示错误
     * @param response 返回数据
     */
    void serverTipError(BaseResponse response);

//    /**
//     * 处理网络错误
//     * @param throwable {@link Throwable}
//     */
//    void handleThrowable(Throwable throwable);

    /**
     * 显示空数据页面
     */
    void showEmptyView();
}

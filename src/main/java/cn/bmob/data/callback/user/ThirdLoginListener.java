package cn.bmob.data.callback.user;

import cn.bmob.data.callback.base.BmobCallback;

public abstract class ThirdLoginListener extends BmobCallback {

    /**
     * 关联授权成功
     */
    public abstract void onSuccess();
}

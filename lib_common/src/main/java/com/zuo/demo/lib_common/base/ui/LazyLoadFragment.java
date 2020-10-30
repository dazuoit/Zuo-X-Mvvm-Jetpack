package com.zuo.demo.lib_common.base.ui;

import androidx.fragment.app.Fragment;

/**
 * @author zuo
 * @filename: LazyLoadFragment
 * @date: 2020/10/23
 * @description: 懒加载
 * @version: V1.0
 */
public abstract class LazyLoadFragment extends Fragment {
    private boolean visibleToUser;

    @Override
    public void onResume() {
        super.onResume();
        if (!visibleToUser) {
            visibleToUser = true;
            lazyLoad();
        }
    }

    /**
     * 懒加载，只有在Fragment第一次创建且第一次对用户可见
     */
    protected abstract void lazyLoad();
}
package com.zuo.demo.lib_common.net;


import com.blankj.utilcode.util.LogUtils;
import com.zuo.demo.lib_common.model.ZuoGobal;


import org.json.JSONException;
import org.json.JSONObject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * FileName: MJsonUtils
 * Author: zuo
 * Date: 2018/6/6
 * Description:  穷举所有字段,统一规划
 * Version: 1.0
 */

public class MJsonUtils {
	private JSONObject root;

	public MJsonUtils() {
		if (root == null) {
			root = new JSONObject();
		}
	}

	public MJsonUtils set_token() {
		return set_object("token", ZuoGobal.token);

	}

	/**
	 * @param name
	 * @param value 可以为空
	 * @return
	 */
	public MJsonUtils set_object(@NonNull String name, @Nullable Object value) {
		try {
			getRoot().put(name, value);
		} catch (JSONException e) {
			LogUtils.e("set_object error:" + name);
		}
		return this;
	}

	public MJsonUtils set_value(@NonNull String name, @Nullable String value) {
		try {
			if (value != null) {
				getRoot().put(name, value);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return this;
	}

	public JSONObject getRoot() {
		if (root == null) {
			root = new JSONObject();
		}
		return root;
	}

	public RequestBody builder() {
		return RequestBody.create(MediaType.parse("application/json; charset=utf-8"), root.toString());
	}

	public void releaseMJson() {
		if (root != null) {
			root = null;
		}
	}
}

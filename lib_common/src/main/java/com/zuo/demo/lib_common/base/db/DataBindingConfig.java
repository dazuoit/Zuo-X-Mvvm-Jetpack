/*
 *
 *  * Copyright 2018-2020 KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.zuo.demo.lib_common.base.db;

import android.util.SparseArray;

import androidx.lifecycle.ViewModel;

/**
 * @author zuo
 * @filename: DataBindingConfig
 * @date: 2020/5/2
 * @description:  取自 KunMinX ，稍加改造 详见 https://xiaozhuanlan.com/topic/9816742350 和 https://xiaozhuanlan.com/topic/2356748910
 * @version: 1.0
 *
 */
public class DataBindingConfig {

	private int layout;
	private int vmVariableId;
	private ViewModel stateViewModel;
	private SparseArray bindingParams = new SparseArray();


	public DataBindingConfig(int layout, int vmVariableId) {
		this.layout = layout;
		this.vmVariableId = vmVariableId;
	}

	public void setStateViewModel(ViewModel stateViewModel) {
		this.stateViewModel = stateViewModel;
	}

	public int getLayout() {
		return layout;
	}

	public int getVmVariableId() {
		return vmVariableId;
	}

	public ViewModel getStateViewModel() {
		return stateViewModel;
	}

	public SparseArray getBindingParams() {
		return bindingParams;
	}

	public DataBindingConfig addBindingParam(int variableId, Object object) {
		if (bindingParams.get(variableId) == null) {
			bindingParams.put(variableId, object);
		}
		return this;
	}
}

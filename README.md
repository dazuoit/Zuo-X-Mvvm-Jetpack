# ZuoXMvvmJetpack (2020-11-02)  
***
本项目为 MVVM + Jetpack + AndroidX + Fragmnet2 + Glide + rxjava3 + okhttp + retrofit + rxlifecycle + rxpermissions + SmartRefreshLayout +  autosize(头条) + BaseRecyclerViewAdapterHelper 的基类封装与实现 
  
# 总结: 
****
对于Jetpack + mvvm  相关文章与教程,网上**资源繁多**,对于层次分明的**系统教程**很难找到,对接触不久的人十分**不友好**,大多数的转载与人云亦云,很容易给人造成**误解与错误使用**,
对于精品文章少之又少,寥寥数语对于解构如隔靴搔痒,希望用我对谷歌Jetpack 与 Mvvm 的理解, 通过最简单的 Demo 与 清晰的**架构层次**,与众多开发者一起从谷歌的**设计初衷与理念**开始,抽丝剥茧的来处理每个模块的**职能边界**,在**高度封装与拓展性**力求平衡

# 架构说明:  
***
- ## UI 层  [BaseActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseActivity.java) [BaseFragment](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseFragment.java)
	- ### 包含内容： 
 		- 1 ，权限的处理  2 ，eventbus 相关（默认关）  3 ，ViewModel的相关处理  4，rootview 的封装与统一处理  5，相关弹窗与通用逻辑的处理与展示   6 , SmartRefreshLayout的封装与逻辑处理（默认关）
	- ### 设计理念：
		- 1，再参考了众多项目后，绝大多数 Mvvm + Jetpack 只做了前3步，因为做为基类，整个项目的**拓展性与可实现性**是足够重要的，但是当下大多 App 作为一个**完整的系统**，**通用性布局**是很多的，如相似的头布局  等，子类**重复构写**不是开发者愿意看到的，因为根布局的引用，为整个项目更多相同部件**不用重复**提供了可能与解决方案；  
  2，ViewDataBinding 作为 **数据驱动UI的可靠实现**，谷歌**设计理念**中，我大胆揣测，通过寻找到指定View来操作，非最佳实现,故在项目中，基本**弱化**了它的存在；
	- ### 子类用法参考 [MainActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/main/MainActivity.java)：
		- 1 , //泛型必须  一个子布局需要绑定一个 
		```Java
		public class MainActivity extends BaseActivity<MainActivityViewModel> {}  
		``` 
		 
		- 2 , //绑定视图与点击事件相关配置 
		```Java
		protected DataBindingConfig getDataBindingConfig() {
			return new DataBindingConfig(R.layout.activity_main, BR.viewModel)
			.addBindingParam(BR.click, new ClickProxy());
		}
		```
	  
		- 3 , //注册eventbus
		```Java
		protected void initActivity() {
			super.initActivity();
			setEventTag(this);
		}
		```
		  
		- 4 , //设置标题
		```Java
		protected void setTitle(TitleViewModel titleViewModel) {
			super.setTitle(titleViewModel);
			titleViewModel.setLeftVisable(false)
				.setTitle("Main")
				.setRightVisable(true)
				.setRightTvDes("列表");
		} 
		```
		  
		- 5 ,  //标题栏右侧点击事件 
		```Java
		protected void onRightPressEvent(TitleViewModel titleViewModel) {
			ActivityUtils.startActivity(ListActivity.class);
		}
		```
	   
		- 6 , //获取权限
		```Java
		rxPermissions(ZuoGobal.XPERMINSSION_TIP_CAMERA_AND_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);
		```
		  
		- 7 ,//权限获取成功 
		```Java
		protected void rxPermissionsSuccess(String tip, String... permissions) {
			ToastUtils.showShort("成功");
		}
		```
	  
		- 8 ,//点击事件
		```Java
		public class ClickProxy {} 
		```
  
		- 9, //发送消息
		```Java
		postEventBus("tag","收到信息"); 
		```
	  <br>	  
- ## UI 层  [BaseRecycleViewActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseRecycleViewActivity.java)  [BaseRecycleViewFragment](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseRecycleViewFragment.java)	
	- ### 包含内容：
 		- 1 ，集成了BaseRecyclerViewAdapterHelper,子类布局都为**同一种**,只包含一个RecycleView,通过头布局脚布局等可以实现绝大多数复杂布局,如不可以,重写getDataBindingConfig()
		- 2 , 将SmartRefreshLayout功能默认打开,如不需要,重写关闭
		- 3 , item的点击事件与item的点击事件抽取出来
		- 4 , 是否需要检验网络(默认关),没有网络的情况在BaseActivity处理
		- 5 , 分页情况已page与时间戳2种,自行选择与拓写
	- ### 设计理念：
		- 1 , 在通用的处理情况下,完全统一布局,adapter 向子类暴露,通过重写方法来**控制各类开关**,
		- 2 , 网络处理情况统一,如有个例,子类复写,当观察子类方法时,会发现**代码极少,功能齐全**;
	- ### 子类用法参考 [ListActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/list/ListActivity.java)：
		- 1 ,//2个泛型必存在
		```Java
		public class ListActivity extends BaseRecycleViewActivity<ListDemoViewModel, DemoAdapter> {) 
		```
		  
	 	- 2 ,//当泛型存在时,基类已经获取到,做这一步的目的是方便基类做更多的通用拓展
		```Java
		protected DemoAdapter createAdapter() {
			return new DemoAdapter();
		} 
		```
		  
		- 3 ,//去处理数据的统一入口
		```Java
		protected void loadData(boolean isLoadMore) {
			showLoadView(null);
			DataRepository.getInstance().getNews(mViewModel.getNewsData(), mViewModel.getPage());
		} 
		```
		  
		- 4 ,//点击事件
		```Java
		public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
			ToastUtils.showShort(mAdapter.getData().get(position));
		} 
		```
		  
		- 5 ,//拿到数据后处理结果
		```Java
		mViewModel.getNewsData().observe(this, newsDetail -> {
			hideLoadView();
			finishLoadData(mViewModel.getIsLoadMore().getValue());
		}); 
		```
		 <br>  
- ## Adapter 层:
	- ### 引用：[CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
	
 	- ### 基础封装 [BaseRecycleAdaper](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/adapter/BaseRecycleAdaper.java) ：
		- 1 , // 沿袭原有的泛型
		```Java
		public abstract class BaseRecycleAdaper<T, K extends ViewDataBinding> extends BaseQuickAdapter<T, BaseDataBindingHolder<K>> {} 
		```
		  
		- 2 ,  // 抽取通用部分,便于未来拓展
		```Java
		protected  void convert(@NotNull BaseDataBindingHolder<K> holder, T t){K binding = holder.getDataBinding();
			if (binding != null){
				bindingData(holder,t,binding);
				binding.executePendingBindings();
			}
		}
		```
		  
		- 3 , // 对bindingdata 做统一处理
		```Java
		protected abstract void bindingData(@NotNull BaseDataBindingHolder<K> holder, T t,K k) {}
		``` 
	- ### 子类参考 [DemoAdapter](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/list/DemoAdapter.java) ：
	<br>  
- ## VM 层 [BaseViewModel](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/model/BaseViewModel.java)   [BaseRecycleViewViewModel](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/model/BaseRecycleViewViewModel.java):
	- ### 对：[goldze/MVVMHabit](https://github.com/goldze/MVVMHabit/blob/master/mvvmhabit/src/main/java/me/goldze/mvvmhabit/base/BaseViewModel.java) 进行边界职能进行重写定义,对其拓展性进行开放
	  <br>  
- ## [DataRepository](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/net/DataRepository.java) 层:
	- ### 网络求情数据: 职能边界,仅用于请求指定数据并绑定到VM层,不做任何多余操作  
	  <br>
- ## [网络层封装](https://github.com/dazuoit/ZuoXMvvmJetpack/tree/master/lib_common/src/main/java/com/zuo/demo/lib_common/net) 层:
	- ### 参考见 [DataRepository](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/net/DataRepository.java) 中的请求
  	  <br>
# 特别说明 : 
*** 
经过参考众多方案,如[KunMinX/Jetpack-MVVM-Best-Practice](https://github.com/KunMinX/Jetpack-MVVM-Best-Practice) , [goldze/MVVMHabit](https://github.com/goldze/MVVMHabit) 等等的开源项目,试着理解这些开源的大体思想与理念,并尝试在自己的思想中取其精华与自己认为一些更优,更符合实际开发需求的方案,希望能用最简单的 Demo 传达一些思想与框架层面 封装、继承多态、拓展与统一等一些想法,框架经过简单的改写与配置,方便开发者使用,不足与错误之处,欢迎讨论,个人 QQ : 1391084310
	
			
		
		
		  
		
		
	
	
	
  
  
  









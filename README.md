# ZuoXMvvmJetpack (2020-11-02)  
***
本项目为 MVVVM + Jetpack + AndroidX + Fragmnet2 + Glide + rxjava3 + okhttp + retrofit + rxlifecycle + rxpermissions + SmartRefreshLayout +  autosize(头条) + BaseRecyclerViewAdapterHelper 的基类封装与实现 
  
# 总结: 
****
对于Jetpack + mvvm  相关文章与教程,网上**资源繁多**,对于层次分明的**系统教程**很难找到,对接触不久的人十分**不友好**,大多数的转载与人云亦云,很容易给人造成**误解与错误使用**,
对于为数不多的精品文章少之又少,寥寥数语对于解构如隔靴搔痒,希望用我对谷歌Jetpack 与 Mvvm 的理解, 通过最简单的 Demo 与 清晰的**架构层次**,与众多开发者一起从谷歌的**设计初衷与理念**开始,抽丝剥茧的来处理每个模块的**职能边界**,在**高度封装与拓展性**力求平衡

# 架构说明:  
***
- ## UI 层  [BaseActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseActivity.java) [BaseFragment](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/ui/BaseFragment.java)
	- ### 包含内容： 
 		- 1 ，权限的处理  2 ，eventbus 相关（默认关）  3 ，ViewModel的相关处理  4，rootview 的封装与统一处理  5，相关弹窗与通用逻辑的处理与展示   6 , SmartRefreshLayout的封装与逻辑处理（默认关）
	- ### 设计理念：
		- 1，再参考了众多项目后，绝大多数 Mvvm + Jetpack 只做了前3步，因为做为基类，整个项目的**拓展性与可实现性**是足够重要的，但是当下大多 App 作为一个**完整的系统**，**通用性布局**是很多的，如相似的头布局  等，子类**重复构写**不是开发者愿意看到的，因为根布局的引用，为整个项目更多相同部件**不用重复**提供了可能与解决方案；  
  2，ViewDataBinding 作为 **数据驱动UI的可靠实现**，谷歌**设计理念**中，我大胆揣测，通过寻找到指定View来操作，非最佳实现,故在项目中，基本**弱化**了它的存在；
	- ### 子类用法参考 [MainActivity](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/main/MainActivity.java)：
		- 1 , ```public class MainActivity extends BaseActivity<MainActivityViewModel> {}  //泛型必须  一个子布局需要绑定一个 ``` 
		 
		- 2 , ``` protected DataBindingConfig getDataBindingConfig() {
		return new DataBindingConfig(R.layout.activity_main, BR.viewModel)
		.addBindingParam(BR.click, new ClickProxy());
	} //绑定视图与点击事件相关配置 ```
	  
		- 3 , ``` protected void initActivity() {
		super.initActivity();
		setEventTag(this);//注册eventbus}```
		  
		- 4 , ```protected void setTitle(TitleViewModel titleViewModel) {
		super.setTitle(titleViewModel);
		titleViewModel.setLeftVisable(false);
		titleViewModel.setTitle("Main");
		titleViewModel.setRightVisable(true);
		titleViewModel.setRightTvDes("列表");
	} //设置标题```
		  
		- 5 , ```protected void onRightPressEvent(TitleViewModel titleViewModel) {
		ActivityUtils.startActivity(ListActivity.class);
	} //标题栏右侧点击事件 ```
	   
		- 6 , ```rxPermissions(ZuoGobal.XPERMINSSION_TIP_CAMERA_AND_WRITE_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA);//获取权限```
		  
		- 7 ,``` protected void rxPermissionsSuccess(String tip, String... permissions) {
		ToastUtils.showShort("成功");
	}//权限获取成功 ```
	  
		- 8 ,```public class ClickProxy {} //点击事件```
  
		- 9, ```postEventBus("tag","收到信息"); //发送消息```
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
		- 1 ,```public class ListActivity extends BaseRecycleViewActivity<ListDemoViewModel, DemoAdapter> {) //2个泛型必存在```
		  
	 	- 2 ,```protected DemoAdapter createAdapter() {
		return new DemoAdapter();
	} //当泛型存在时,基类已经获取到,做这一步的目的是方便基类做更多的通用拓展```
		  
		- 3 ,```protected void loadData(boolean isLoadMore) {
		showLoadView(null);
		DataRepository.getInstance().getNews(mViewModel.getNewsData(), mViewModel.getPage());
	} //去处理数据的统一入口```
		  
		- 4 ,```public void onItemClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
		ToastUtils.showShort(mAdapter.getData().get(position));
	} //点击事件```
		  
		- 5 ,```mViewModel.getNewsData().observe(this, newsDetail -> {
			hideLoadView();
			finishLoadData(mViewModel.getIsLoadMore().getValue());
		}); //拿到数据后处理结果```
		  
- ## Adapter 层:
	- ### 引用：[CymChad/BaseRecyclerViewAdapterHelper](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
	
 	- ### 基础封装 [BaseRecycleAdaper](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/adapter/BaseRecycleAdaper.java) ：
		- 1 , ```public abstract class BaseRecycleAdaper<T, K extends ViewDataBinding> extends BaseQuickAdapter<T, BaseDataBindingHolder<K>> {} // 沿袭原有的泛型```
		  
		- 2 , ```protected  void convert(@NotNull BaseDataBindingHolder<K> holder, T t){K binding = holder.getDataBinding();
		if (binding != null){
			bindingData(holder,t,binding);
			binding.executePendingBindings();
		}} // 抽取通用部分,便于未来拓展```
		  
		- 3 , ```protected abstract void bindingData(@NotNull BaseDataBindingHolder<K> holder, T t,K k) // 对bindingdata 做统一处理```
		  
	- ### 子类参考 [DemoAdapter](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/list/DemoAdapter.java) ：
- ## VM 层 [BaseViewModel](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/model/BaseViewModel.java)   [BaseRecycleViewViewModel](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/lib_common/src/main/java/com/zuo/demo/lib_common/base/model/BaseRecycleViewViewModel.java):
	- ### 对：[goldze/MVVMHabit](https://github.com/goldze/MVVMHabit/blob/master/mvvmhabit/src/main/java/me/goldze/mvvmhabit/base/BaseViewModel.java) 进行边界职能进行重写定义,对其拓展性进行开放
	  
- ## [DataRepository](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/net/DataRepository.java) 层:
	- ### 网络求情数据: 职能边界,仅用于请求指定数据并绑定到VM层,不做任何多余操作
  
- ## [网络层封装](https://github.com/dazuoit/ZuoXMvvmJetpack/tree/master/lib_common/src/main/java/com/zuo/demo/lib_common/net) 层:

	- ### 参考见 [DataRepository](https://github.com/dazuoit/ZuoXMvvmJetpack/blob/master/app/src/main/java/com/zuo/xmvvm/net/DataRepository.java) 中的请求：
	
			
		
		
		  
		
		
	
	
	
  
  
  









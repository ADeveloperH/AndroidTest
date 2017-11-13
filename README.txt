普及常识（git max branch：https://www.zhihu.com/question/20731260）


分支简介：

master（下边所有分支都基于master）
	1、加入butterknife依赖
	2、错误日志收集

activity_task_test
	Activity栈测试
	出现并解决问题：
		360加固后首次启动。进入其他页面后点击home键退到桌面。再次点击桌面图标，会重新到SplashActivity。
		此时!isTaskRoot() == true，杀掉进程后，再次重复相同的操作不会出现该问题(不加固也不会有此问题)
		
customer_scheme
	1、DiskLruCache基本功能使用的Demo,okhttp同步异步请求测试
	2、H5通过自定义scheme唤起客户端某个页面并实现传参功能及兼容性测试
	
glideloader_test
	加入Glide依赖，可以进行Glide的API使用测试	

gson
	加入Gson依赖，Gson解析对比JsonObject，可进行Gson相关API测试		
	
NavigationBar
	获取真实的导航栏高度。适配不同手机
	
okhttp
	添加okhttp和disklrucache依赖,DiskLruCache基本功能使用的Demo,okhttp同步异步请求测试
	
PullableRefresh_webview
	下拉刷新嵌套WebView解决冲突
		
pageloadstatedemo
	封装请求失败页面状态管理，使用Builder模式，完美集成TabLayout使用Demo，完美适用Activity、Fragment等

permission
	用来测试权限适配相关代码，暂未加入代码

service_process
	Service使用及多进程通信使用测试代码

splash_navi
	启动页完美适配虚拟键盘遮挡问题

timestamp
	用来实现同服务端时间同步的方案，监听系统时间改变的广播等

traffic
	适配Android N统计应用使用流量Demo
	
viewpager_fragment
	Viewpager使用FragmentPagerAdapter动态删除增加Fragment功能

webviewdownload
	实现webview内部下载pdf等文件功能。	
	
webviewtest
	Android WebView控件使用测试，实现监听开始滑动和滑动停止状态功能

windowmanager
	WindowManager使用时权限问题解决测试代码，Decorview Dialog 对比显示功能完成
	
	

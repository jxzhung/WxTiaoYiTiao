# 微信跳一跳助手

手机端微信跳一跳辅助，单个APK，无需电脑，过微信刷分检测。

## 预览
<img src="https://github.com/jxzhung/WxTiaoYiTiao/raw/master/images/preview.png" width=500 />
<img src="https://github.com/jxzhung/WxTiaoYiTiao/raw/master/images/score.png" width=500 />


## 注意

1 手机需要ROOT，并给予授权 <br />
2 给本应用开启悬浮窗口权限 <br />

## 使用方法
apk文件在/apk/文件夹下，下载最新版本 <br />
1 开启悬浮窗服务 <br />
2 打开微信，下拉调出跳一跳 <br />
3 点击中间区域起跳点和落下点 <br />
4 确定跳一下 <br />
5 通过力量两个按钮调整跳的远近，找到一个合适的值。 <br />
目前小米6已测试完毕，其他机型适当修改即可。 <br />

## 问题和解决方法
1 点击确定后跳转到分享界面 <br />
可以开启开发者选项里面的显示“指针位置”查看模拟触摸的位置。 <br />
在com/jzhung/tiaoyitiaohelper/service/FloatViewService.java的onCreate中有一行mTouchPointLeftMargin = DensityUtil.dip2px(getApplicationContext(), 50);  <br />
可以把后面的50改大成合适的位置就行了。

## 更新

2018.01.04 v1.3<br />
1 加入崩溃日志，保存在sdcard/JLog/目录下<br />
2 点选单个点时加入提示<br />

2018.01.04 v1.1<br />
1 新增启动应用时申请su权限<br />
2 优化说明文字<br />
3 优化模拟触摸位置改为顶部<br />

2018.01.03 v1.0<br />
1 初版发布<br />

## 建议大家以技术研究的目的运行本程序，毕竟，游戏的快乐无价！


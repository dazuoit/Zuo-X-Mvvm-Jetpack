package com.zuo.demo.lib_common.model;

import android.os.Environment;

/**
 * @author zuo
 * @filename: ZuoGobal
 * @date: 2020/4/13
 * @description: 描述
 * @version: 版本号
 */
public class ZuoGobal {
	public final static boolean IS_DEBUG = true;
	public final static boolean bIsOpenLog = IS_DEBUG;
	public  static String token = "" ;
	public static final String URL_ROOT_HOST = "http://api.bing.com/";//这个要替换成自己的 baseUr;
	public final static int CONNECT_TIME_OUT = 20 * 1000;//超时时间设置
	public final static float AUTO_SIZE_IN_DP = 667; //适配
	public final static long GLIDE_MEMORY_CACHE = 1024 * 1024 * 20; // 20mb
	public final static long GLIDE_DISK_CACHE = 1024 * 1024 * 100;  //100 MB
	public final static String ZUO_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/zuo/cache/";//需要权限
	public final static String ROOT_PATH = ZuoApplication.getmContext().getCacheDir().getPath() + "/zuo/";
	public final static String GLIDE_CACHE_DIR = ROOT_PATH + "/image"; //glide图片管理
	public final static String IMAGE_DOWN_PATH = "ZUO";  // 保存的文件夹名称，会在Picture目录进行文件夹的新建。比如："BigImageView"，会在Picture目录新建BigImageView文件夹)
	public final static String MATISSE_FILE_PATH =  ZuoApplication.getmContext().getPackageName()+".fileprovider"; //glide图片管理

	public final static String XPERMINSSION_TIP_CAMERA_AND_WRITE_EXTERNAL_STORAGE = "相机和读写";
	public final static String XPERMINSSION_TIP_WRITE_EXTERNAL_STORAGE = "读写";
}

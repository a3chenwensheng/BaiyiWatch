package com.baiyi.watch.aqgs2;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Debug;

import com.baiyi.watch.dao.DeviceDao;
import com.baiyi.watch.dao.DeviceDaoInface;
import com.baiyi.watch.dao.GroupDao;
import com.baiyi.watch.dao.GroupDaoInface;
import com.baiyi.watch.dao.MemberDao;
import com.baiyi.watch.dao.MemberDaoInface;
import com.baiyi.watch.dao.PersonDao;
import com.baiyi.watch.dao.PersonDaoInface;
import com.baiyi.watch.dao.PostureDataDao;
import com.baiyi.watch.dao.PostureDataInface;
import com.baiyi.watch.model.TsModel;
import com.baiyi.watch.utils.Constant;
import com.baiyi.watch.utils.Sputil;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import java.io.File;
import java.util.List;

public class MyApplication extends Application {
	
	private static MyApplication myApplication = null;
	private Sputil sputil;
	public TsModel tsModel;
	
	private PersonDaoInface personDaoInface;
	private GroupDaoInface groupDaoInface;
	private DeviceDaoInface deviceDaoInface;
	private MemberDaoInface memberDaoInface;
	private PostureDataInface postureDataInface;

	public static MyApplication getInstance() {
		return myApplication;
	}

	@Override
	public void onCreate() {
		super.onCreate();
		// CrashHandler crashHandler=CrashHandler.getInstance();
		// crashHandler.init(getApplicationContext());
		Debug.stopMethodTracing();

		// 由于Application类本身已经单例，所以直接按以下处理即可。
		myApplication = this;
		personDaoInface = new PersonDao(getApplicationContext());
		groupDaoInface = new GroupDao(getApplicationContext());
		deviceDaoInface = new DeviceDao(getApplicationContext());
		memberDaoInface = new MemberDao(getApplicationContext());
		postureDataInface = new PostureDataDao(getApplicationContext());
		
		sputil = new Sputil(getApplicationContext(), Constant.PRE_NAME);
		
		initImageLoader();
		
	}
	
	@Override
	public void onTerminate() {
		// TODO Auto-generated method stub
		super.onTerminate();
		//整体摧毁的时候调用这个方法
	}

	/**
	 * 初始化imageLoader
	 */
	public void initImageLoader() {
		File cacheDir = StorageUtils.getCacheDirectory(getApplicationContext());
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(getApplicationContext())
				.memoryCache(new LruMemoryCache(5 * 1024 * 1024)).memoryCacheSize(10 * 1024 * 1024)
				.discCache(new UnlimitedDiscCache(cacheDir))
				.discCacheFileNameGenerator(new HashCodeFileNameGenerator()).build();
		ImageLoader.getInstance().init(config);
	}

	public DisplayImageOptions getOptions(int drawableId) {
		return new DisplayImageOptions.Builder().showImageOnLoading(drawableId).showImageForEmptyUri(drawableId)
				.showImageOnFail(drawableId).resetViewBeforeLoading(true).cacheInMemory(true).cacheOnDisc(true)
				.imageScaleType(ImageScaleType.EXACTLY).bitmapConfig(Bitmap.Config.RGB_565).build();
	}

	public PersonDaoInface getPersonDaoInface() {
		return personDaoInface;
	}

	public GroupDaoInface getGroupDaoInface() {
		return groupDaoInface;
	}
	
	public DeviceDaoInface getDeviceDaoInface() {
		return deviceDaoInface;
	}

	public MemberDaoInface getMemberDaoInface() {
		return memberDaoInface;
	}

	public PostureDataInface getPostureDataInface() {
		return postureDataInface;
	}
	
	public Sputil getSputil() {
		if(null == sputil){
			sputil = new Sputil(getApplicationContext(), Constant.PRE_NAME);
		}
		return sputil;
	}
	
	public TsModel getTsModel() {
		return tsModel;
	}
	public void setTsModel(TsModel tsModel) {
		this.tsModel = tsModel;
	}
	
	public boolean isMainProcess() {
		ActivityManager am = ((ActivityManager) getSystemService(Context.ACTIVITY_SERVICE));
		List<RunningAppProcessInfo> processInfos = am.getRunningAppProcesses();
		String mainProcessName = getPackageName();
		int myPid = android.os.Process.myPid();
		for (RunningAppProcessInfo info : processInfos) {
			if (info.pid == myPid && mainProcessName.equals(info.processName)) {
				return true;
			}
		}
		return false;
	}

}

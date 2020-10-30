package com.zuo.demo.lib_common.glide;

import android.content.Context;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.module.AppGlideModule;
import com.zuo.demo.lib_common.model.ZuoGobal;

import java.io.InputStream;

import androidx.annotation.NonNull;
import cc.shinichi.library.glide.progress.ProgressManager;
/**
 * @author zuo
 * @filename: ZuoGlideModule
 * @date: 2020/4/15
 * @description: GlideModule
 * @version: 1.0
 */
@GlideModule
public class ZuoGlideModule extends AppGlideModule {

	@Override
	public void applyOptions(@NonNull Context context, @NonNull GlideBuilder builder) {
		builder.setMemoryCache(new LruResourceCache(ZuoGobal.GLIDE_MEMORY_CACHE))
				.setDiskCache(new DiskLruCacheFactory(ZuoGobal.GLIDE_CACHE_DIR, ZuoGobal.GLIDE_DISK_CACHE));
	}

	@Override
	public boolean isManifestParsingEnabled() {
		return false;
	}

	@Override
	public void registerComponents(Context context, Glide glide,  Registry registry) {
		super.registerComponents(context, glide, registry);
		//图片查看适配进度下载
		registry.replace(GlideUrl.class, InputStream.class, new OkHttpUrlLoader.Factory(ProgressManager.getOkHttpClient()));
	}
}
package com.zenchn.electrombile.service;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.zenchn.electrombile.BuildConf;
import com.zenchn.electrombile.R;
import com.zenchn.electrombile.api.AppUpdateApi;
import com.zenchn.electrombile.api.DownloadApi;
import com.zenchn.electrombile.api.callback.ApiCallback;
import com.zenchn.electrombile.api.callback.DownloadCallback;
import com.zenchn.electrombile.base.BaseService;
import com.zenchn.electrombile.entity.AppVersionInfo;
import com.zenchn.electrombile.kit.ApplicationKit;
import com.zenchn.electrombile.utils.CommonUtils;
import com.zenchn.electrombile.widget.Dialog.CommonDialog;
import com.zenchn.electrombile.widget.NumberProgressbar.NumberProgressBar;
import com.zenchn.mlibrary.utils.ApkUtils;
import com.zenchn.mlibrary.utils.EncryptUtils;
import com.zenchn.mlibrary.utils.FileUtils;
import com.zenchn.mlibrary.utils.ToastUtils;

import java.io.File;

/**
 * 作    者：wangr on 2017/3/15 11:36
 * 描    述：
 * 修订记录：
 */
public class UpdateService extends BaseService implements ApiCallback<AppVersionInfo>, DownloadCallback {

    private NumberProgressBar mProgress;

    private String destFileDirPath = BuildConf.APP_UPDATE.DEST_FILE_DIR;//文件保存路径
    private String destFileName = BuildConf.APP_UPDATE.DEST_FILE_NAME;//文件名
    private String systemType = BuildConf.APP_UPDATE.SYSTEM_TYPE;//系统版本

    private AppVersionInfo appVersionInfo;
    private File targetFile;
    private CommonDialog downloadDialog;

    @Override
    protected void handler(Message msg) {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        AppUpdateApi
                .getInstance()
                .getApkVersion(systemType, this);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onSuccess(AppVersionInfo appVersionInfo) {
        checkVersion(appVersionInfo);// 判断版本
    }

    @Override
    public void onResponseError(String msg) {
        askIsMustUpdate();
    }

    @Override
    public void onFailure() {
        askIsMustUpdate();
    }

    /**
     * 检查版本信息
     *
     * @param appVersionInfo
     */
    private void checkVersion(AppVersionInfo appVersionInfo) {
        this.appVersionInfo = appVersionInfo;
        // 版本判断
        if (appVersionInfo != null) {

            String versionName = ApkUtils.getVersionName(this);
            int oldVersion = Integer.parseInt(versionName.replace(".", ""));
            int newVersion = Integer.parseInt(appVersionInfo.getVersionCode().replace(".", ""));
            if (oldVersion < newVersion) {
                showRemindDialog(getString(R.string.update_new_app), appVersionInfo);
            } else {
                stopSelf();
            }
        }
    }

    /**
     * 是否强制更新
     */
    private void askIsMustUpdate() {
        if (appVersionInfo != null) {
            boolean isMust = BuildConf.APP_UPDATE.MUST_UPDATE == appVersionInfo.getIsMust();
            if (isMust) {
                ApplicationKit.getInstance().exitApp();
            } else {
                showResMessage(R.string.update_fail);
            }
        }
    }

    /**
     * 提示框
     *
     * @param title
     * @param appVersionInfo
     */
    public void showRemindDialog(String title, AppVersionInfo appVersionInfo) {
        Activity topActivity = ApplicationKit.getInstance().getTopActivity();
        CommonDialog.Builder builder = new CommonDialog.Builder(topActivity);

        if (appVersionInfo != null) {
            this.appVersionInfo = appVersionInfo;

            String versionCode = appVersionInfo.getVersionCode();
            String updateDesc = appVersionInfo.getUpdateDesc();
            if (CommonUtils.isEmpty(updateDesc))
                updateDesc = getString(R.string.update_new_app_default_desc);

            long fileLength = appVersionInfo.getFileSize();
            String fileSize = FileUtils.formatFileSize(fileLength);

            String content = String.format(getString(R.string.update_new_app_desc_format), versionCode, fileSize, updateDesc);
            builder.setMessage(content);

            builder.setTitle(CommonUtils.isEmpty(title) ? "提示" : title);
            builder.setCancelable(false);
            builder.setCanceledOnTouchOutside(false);
            builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {

                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    String fileMd5 = UpdateService.this.appVersionInfo.getFileMd5();
                    createLocalFile();
                    if (CommonUtils.isNonNull(fileMd5))
                        checkLocalFileMd5(fileMd5);
                }

            });

            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if (dialog != null) {
                        dialog.dismiss();
                        askIsMustUpdate();
                    }
                }
            });
            builder.create().show();
        }
    }

    /**
     * 创建文件夹
     */
    private void createLocalFile() {
        File folder = FileUtils.createFolder(destFileDirPath);
        targetFile = new File(folder, destFileName);
    }

    /**
     * 检查本地是否有缓存apk
     *
     * @param onlineApkMd5
     */
    private void checkLocalFileMd5(String onlineApkMd5) {

        if (targetFile.exists()) {
            String md5ByFile = EncryptUtils.getMd5ByFile(targetFile);
            if (onlineApkMd5.equalsIgnoreCase(md5ByFile)) {
                installApk(targetFile);
            } else {
                startDownloadTask(targetFile);
            }
        } else {
            startDownloadTask(targetFile);
        }
    }

    /**
     * 开启下载任务
     *
     * @param targetFile
     */
    private void startDownloadTask(File targetFile) {
        FileUtils.deleteFolder(targetFile.getAbsolutePath());

        DownloadApi
                .download(appVersionInfo.getDownloadUrl(), targetFile, this);
//        AppUpdateApi
//                .getInstance()
//                .downloadApp(appVersionInfo.getDownloadUrl(), targetFile, this);
        showDownloadDialog();
    }

    /**
     * 安装APK文件
     */
    private void installApk(File apkFile) {
        if (FileUtils.isFileExist(apkFile)) {
            String apkName = apkFile.getName();
            String fileType = apkName.substring(apkName.lastIndexOf(".") + 1);
            if ("apk".equals(fileType)) {
                installApp(apkFile);
            } else {
                showResMessage(R.string.update_apk_breakdown);
                askIsMustUpdate();
            }
        }
    }

    /**
     * toast消息
     *
     * @param msg
     */
    private void showMessage(String msg) {
        ToastUtils.showDefaultMessage(getApplicationContext(), msg);
    }

    /**
     * toast消息
     *
     * @param resId
     */
    private void showResMessage(int resId) {
        showMessage(getApplication().getString(resId));
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog() {
        // 构造软件下载对话框
        Activity topActivity = ApplicationKit.getInstance().getTopActivity();
        CommonDialog.Builder builder = new CommonDialog.Builder(topActivity);
        builder.setTitle("正在更新");
        builder.setCancelable(false);

        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(topActivity);
        View view = inflater.inflate(R.layout.app_update_progress, null);
        mProgress = (NumberProgressBar) view.findViewById(R.id.update_progress);
        mProgress.setMax(100);
        builder.setContentView(view);

        builder.setPositiveButton("后台更新", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(UpdateService.this, getString(R.string.update_background), Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        // 取消更新
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                // 设置取消状态
                askIsMustUpdate();
            }
        });
        downloadDialog = builder.create();
        downloadDialog.show();
    }

    /**
     * 安装一个apk文件
     */
    public void installApp(File uriFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(uriFile), "application/vnd.android.package-archive");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected boolean useEventBus() {
        return false;
    }

    @Override
    public void onGrantRefuse() {

    }

    @Override
    public void onDownloadSuccess(File file) {
        if (file != null) {
            installApk(file);
        } else {
            askIsMustUpdate();
        }
    }

    @Override
    public void onDownloadFailure() {

    }

    @Override
    public void onDownloadCompleted() {
        downloadDialog.dismiss();
    }

    @Override
    public void onResponseProgress(long bytesRead, long contentLength, boolean done) {
        int progress = (int) (bytesRead * 100 / contentLength);
        mProgress.setProgress(progress);
//        LogKit.success("正在下载", "当前进度：" + progress + "%");
    }
}

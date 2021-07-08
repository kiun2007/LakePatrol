package com.kssoft.lake.base.presenter.imp;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.Gravity;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.kssoft.lake.BR;
import com.kssoft.lake.R;
import com.kssoft.lake.base.presenter.VersionUpdate;
import com.kssoft.lake.data.model.vo.DatVersion;
import com.kssoft.lake.data.model.vo.UpdateVersion;
import com.kssoft.lake.net.services.VersionService;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import kiun.com.bvroutine.interfaces.callers.SetCaller;
import kiun.com.bvroutine.net.ServiceGenerator;
import kiun.com.bvroutine.utils.AgileThread;
import kiun.com.bvroutine.utils.AppUtil;
import kiun.com.bvroutine.utils.RetrofitUtil;
import kiun.com.bvroutine.utils.SharedUtil;
import kiun.com.bvroutine.views.dialog.MCDialogManager;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class VersionUpdatePresenter implements VersionUpdate {

    public final static String APP_ROOT_PATH = Environment.getExternalStorageDirectory() + "/apk";

    Context context;
    DatVersion datVersion;
    File dir;
    boolean isPackageNotFund = false;

    public VersionUpdatePresenter(Context context){
        this.context = context;
        dir = new File(APP_ROOT_PATH);
        if (!dir.exists()){
            dir.mkdir();
        }
    }

    public DatVersion checkVersion(String packageName){
        return checkVersion(packageName, Build.VERSION.SDK_INT, null);
    }

    public DatVersion checkVersion(String packageName, int sdk, Integer versionCode){

        PackageInfo packageInfo = null;
        try {
            packageInfo = AppUtil.getPackageInfo(packageName, context);
        } catch (PackageManager.NameNotFoundException e) {
            isPackageNotFund = true;
        }

        PackageInfo finalPackageInfo = packageInfo;
        int build = versionCode != null ? versionCode : (finalPackageInfo == null ? 0 : finalPackageInfo.versionCode);

        try {
            datVersion = RetrofitUtil.callServiceData(VersionService.class, s -> s.getNewVersion(packageName, sdk, build));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return datVersion;
    }

    public DatVersion checkVersion(){
        return checkVersion(context.getPackageName());
    }

    public DatVersion getVersion() {
        return datVersion;
    }

    private void startShare(){

        if (datVersion == null){
            Toast.makeText(context, "没有可供分享的版本", Toast.LENGTH_LONG).show();
            return;
        }

        String url = String.format("%s/system/datVersionInfo/download/%s", ServiceGenerator.getBasePrefix(), datVersion.getId());

        Intent share_intent = new Intent();
        share_intent.setAction(Intent.ACTION_SEND);
        share_intent.setType("text/plain");
        share_intent.putExtra(Intent.EXTRA_SUBJECT, "湖泛巡查分享");
        share_intent.putExtra(Intent.EXTRA_TEXT, String.format("%s\n最新版本%s\n下载地址:\n%s", datVersion.getName(), datVersion.getVersion(), url));
        share_intent = Intent.createChooser(share_intent, "分享");
        context.startActivity(share_intent);
    }

    @Override
    public void share() {
        new AgileThread(context, (thread)->{
            if (datVersion == null){
                datVersion = checkVersion(context.getPackageName(), Integer.MAX_VALUE - 5000, 0);
            }

            thread.into(this::startShare);
        }).start();
    }

    /**
     * 断点下载APK文件.
     * @param callBack
     * @throws IOException
     */
    public boolean download(SetCaller<Long> callBack) throws IOException {

        if (datVersion == null){
            return false;
        }

        String id = datVersion.getId();


        VersionService versionService = ServiceGenerator.createService(VersionService.class);

        String bpName = String.format("%s.bp", id);
        int breakPoint = SharedUtil.getValue(bpName, 0);
        File apkFile = new File(dir, String.format("%s.apk", id));
        if (apkFile.exists() && breakPoint == 0){
            return true;
        }

        Response<ResponseBody> response = versionService.downloadVersion(datVersion.getId(), String.format("bytes=%d", breakPoint)).execute();
        ResponseBody body = response.body();

        long len = 0, total = breakPoint;
        byte[] buf = new byte[20480];
        InputStream inputStream = body.byteStream();
        RandomAccessFile randomAccessFile = new RandomAccessFile(apkFile, "rwd");

        if (breakPoint == 0){
            randomAccessFile.setLength(datVersion.getFileSize().longValue());
        }
        randomAccessFile.seek(breakPoint);

        while ((len = inputStream.read(buf)) != -1) {
            randomAccessFile.write(buf, 0, (int) len);
            total += len;
            SharedUtil.saveValue(bpName, total);

            if (callBack != null){
                callBack.call(total);
            }
        }

        SharedUtil.delete(bpName);
        return true;
    }

    public boolean installApp() {

        if (datVersion != null) {
            File targetFile = new File(dir, String.format("%s.apk", datVersion.getId()));
            if (targetFile.exists()) {

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {

                    Uri contentUri = FileProvider.getUriForFile(context, "com.szxgm.gusustreet.provider", targetFile);
                    intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                    intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
                } else {
                    intent.addCategory(Intent.CATEGORY_DEFAULT);
                    intent.setDataAndType(Uri.fromFile(targetFile), "application/vnd.android.package-archive");
                }

                context.startActivity(intent);
                return true;
            }else {
                Toast.makeText(context, "apk 文件不存在",Toast.LENGTH_SHORT).show();
            }
        }
        return false;
    }

    private void startLaunch(String packageName, String activityName){

        if (activityName != null){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            /**知道要跳转应用的包命与目标Activity*/
            ComponentName componentName = new ComponentName(packageName, activityName);
            intent.setComponent(componentName);
            context.startActivity(intent);
        }else{
            Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
            if (intent != null){
                context.startActivity(intent);
            }
        }
    }

    @Override
    public boolean compound(String packageName) {

        new AgileThread(context, (thread)->{

            DatVersion version = checkVersion(packageName);

            if (version == null){
                //无新版本
                if (!context.getPackageName().equals(packageName)){
                    //非本APP启动.
                    thread.into(()->startLaunch(packageName, null));
                }else{
                    thread.into(()->Toast.makeText(context, "已经是最新版本了", Toast.LENGTH_LONG).show());
                }
                return;
            }

            Integer isUpdate = thread.intoWait(false, ()->{
                String msg = String.format("发现新版本%s是否更新?", version.getVersion());
                if (isPackageNotFund){
                    msg = String.format("检测到手机未安装\"%s\", 是否安装版本%s?", version.getName(), version.getVersion());
                }
                new AlertDialog.Builder(context)
                        .setTitle(version.getName() + (isPackageNotFund ? "-安装" : "-更新"))
                        .setMessage(msg)
                        .setPositiveButton(isPackageNotFund?"安装":"更新", (dialog, which) ->thread.notify(0))
                        .setNegativeButton(isPackageNotFund?"稍后":"不更新",(dialog, which) ->thread.notify(1))
                        .setOnCancelListener(dialog -> thread.notify(1)).show();
            });

            if (isUpdate == 0){

                UpdateVersion updateVersion = new UpdateVersion();
                updateVersion.setCount(version.getFileSize().longValue());
                updateVersion.setAppName(version.getName());
                updateVersion.setVersionDesc(version.getVersionDesc());
                updateVersion.setVersionName(version.getVersion());

                MCDialogManager dialogManager = MCDialogManager.create(context, R.layout.dialog_download_apk, updateVersion, BR.version, BR.dialog)
                                                                .setCancelable(false).setGravity(Gravity.BOTTOM);
                thread.into(dialogManager::show);

                try {
                    download(progress->{
                        thread.into(()->updateVersion.setCurrent(progress));
                    });
                    updateVersion.setState(UpdateVersion.State.Downloaded);
                } catch (IOException e) {
                    e.printStackTrace();
                    updateVersion.setState(UpdateVersion.State.DownloadError);
                    return;
                }

                updateVersion.setState(UpdateVersion.State.Installing);
                installApp();
                updateVersion.setState(UpdateVersion.State.Complete);
                thread.into(dialogManager::dismiss);
            }
        }).start();
        return true;
    }

    @Override
    public boolean compound() {
        return compound(context.getPackageName());
    }
}

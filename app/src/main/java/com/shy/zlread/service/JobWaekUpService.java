package com.shy.zlread.service;

import android.app.ActivityManager;
import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.util.Log;

import java.util.List;

/**
 * Created by zhangli on 2019/7/11.
 */

public class JobWaekUpService extends JobService {

    private int jobId = 1;
    private String TAG = JobWaekUpService.class.getSimpleName();

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        JobScheduler jobScheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);

        JobInfo.Builder builder = new JobInfo.Builder(jobId, new ComponentName(this, JobWaekUpService.class));

//        builder.setPeriodic(2000);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        builder.setMinimumLatency(2000);
        jobScheduler.schedule(builder.build());
        return START_STICKY;
    }

    @Override
    public boolean onStartJob(JobParameters params) {

        boolean isAlive = serviceAlive(AwakenService.class.getName());
        Log.e(TAG, TAG + "定时任务...");
        if (!isAlive) {
            startService(new Intent(this, AwakenService.class));
        }
        scheduleRefresh(AwakenService.class.getName());
        jobFinished(params, true);
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        jobFinished(params, true);
        return false;
    }

    /**
     * 判断某个服务是否正在运行的方法
     *
     * @param serviceName 是包名+服务的类名（例如：net.loonggg.testbackstage.TestService）
     * @return true代表正在运行，false代表服务没有正在运行
     */
    private boolean serviceAlive(String serviceName) {
        boolean isWork = false;
        ActivityManager myAM = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> myList = myAM.getRunningServices(100);
        if (myList.size() <= 0) {
            return false;
        }
        for (int i = 0; i < myList.size(); i++) {
            String mName = myList.get(i).service.getClassName().toString();
            if (mName.equals(serviceName)) {
                isWork = true;
                break;
            }
        }
        return isWork;
    }

    private void scheduleRefresh(String serviceName) {
        JobScheduler mJobScheduler = (JobScheduler) getApplicationContext()
                .getSystemService(JOB_SCHEDULER_SERVICE);
        //jobId可根据实际情况设定
        JobInfo.Builder mJobBuilder =
                new JobInfo.Builder(0,
                        new ComponentName(getPackageName(),
                                JobWaekUpService.class.getName()));

        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mJobBuilder.setMinimumLatency(2 * 60 * 1000).setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
            PersistableBundle persiBundle = new PersistableBundle();
            persiBundle.putString("servicename", serviceName);
            mJobBuilder.setExtras(persiBundle);
        }

        if (mJobScheduler != null && mJobScheduler.schedule(mJobBuilder.build())
                <= JobScheduler.RESULT_FAILURE) {
            //Scheduled Failed/LOG or run fail safe measures
            Log.d("JobSchedulerService", "7.0 Unable to schedule the service FAILURE!");
        } else {
            Log.d("JobSchedulerService", "7.0 schedule the service SUCCESS!");
        }
    }

}

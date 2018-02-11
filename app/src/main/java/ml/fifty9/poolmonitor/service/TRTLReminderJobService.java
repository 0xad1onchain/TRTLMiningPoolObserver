package ml.fifty9.poolmonitor.service;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.AsyncTask;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by HemantJ on 11/02/18.
 */

public class TRTLReminderJobService extends JobService {
    private AsyncTask backgroundTask;

    @SuppressLint("StaticFieldLeak")
    @Override
    public boolean onStartJob(JobParameters job) {
        backgroundTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = TRTLReminderJobService.this;
                ReminderTasks.executeTasks(context, ReminderTasks.ACTION_TURTLE);
                return null;
            }

            @Override
            protected void onPostExecute(Object o) {
                jobFinished(job,false);
            }
        };
        backgroundTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        if(backgroundTask != null)
            backgroundTask.cancel(true);
        return true;
    }
}

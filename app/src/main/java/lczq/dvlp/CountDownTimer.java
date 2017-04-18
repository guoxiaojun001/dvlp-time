package lczq.dvlp;

import android.widget.TextView;

/**
 * Created by chujun on 17/3/14.
 */
public abstract class CountDownTimer {
    public long remainTime;
    public TextView tv_hour,tv_minute,tv_second,textView;

    public abstract void onTimeChange(int hour, int minute, int second, long remainTime);

}


package lczq.dvlp;

import android.os.Handler;
import android.os.Message;

import java.util.ArrayList;

/**
 * 倒计时管理器，所有需要倒计时的页面，都需要添加监听接口，避免每个模块单独起线程，浪费时间。
 * 在退出模块时 必须调用unRegisterCountDownTimer方法，否则会造成内存泄露。
 * Created by chujun on 17/3/14.
 */
public class CountdownManager implements Runnable {
    private ArrayList<CountDownTimer> timers = new ArrayList<>();

    private static CountdownManager ourInstance = new CountdownManager();

    public static CountdownManager getInstance() {
        return ourInstance;
    }

    private CountdownManager() {

    }

    public void registerCountDownTimer(CountDownTimer countDownTimer) {
        if (countDownTimer.remainTime > 0) {
            timers.add(countDownTimer);
        }

        if (timers.size() == 1) {
            new Thread(this).start();
        }

    }

    public void unRegisterCountDownTimer(CountDownTimer countDownTimer) {
        if (countDownTimer.remainTime>1)
            countDownTimer.remainTime=1;

    }

    @Override
    public void run() {
        while (timers.size() > 0) {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    ArrayList<CountDownTimer> completeTimes = new ArrayList<>();
    Handler handler;

    {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {


                super.handleMessage(msg);
                synchronized (this) {

                    for (CountDownTimer timer : timers) {
                        timer.remainTime--;

                        int second = (int) (timer.remainTime % 60);
                        int minute = (int) (timer.remainTime / 60 % 60);
                        int hour = (int) (timer.remainTime / 60 / 60);
                        timer.onTimeChange(hour, minute, second, timer.remainTime);
                        if (timer.remainTime <= 0) {
                            completeTimes.add(timer);
                        }

                    }
                    timers.removeAll(completeTimes);
                    completeTimes.clear();
                }

            }
        };
    }
}

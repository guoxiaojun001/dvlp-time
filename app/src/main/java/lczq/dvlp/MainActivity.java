package lczq.dvlp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * Created by chujun on 17/3/14.
 */

public class MainActivity extends Activity {

    @InjectView(R.id.time)
    TextView time_tv;

    Timer timer;

    @InjectView(R.id.btn)
    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        findViewById(R.id.btn2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
    }

    

    @OnClick(R.id.btn)
    public void onClicktime() {
        timer = new Timer();
        timer.remainTime = 3700;
        time_tv.setEnabled(false);
        CountdownManager.getInstance().registerCountDownTimer(timer);
        Log.v("registerCountDownTimer ", timer + "注册时间");
    }

    class Timer extends CountDownTimer {

        @Override
        public void onTimeChange(int hour, int minute, int second, long remainTime) {
            if (remainTime <= 0) {

                time_tv.setText("重新发送验证码");
                time_tv.setEnabled(true);
                if (timer != null) {
                    CountdownManager.getInstance().unRegisterCountDownTimer(timer);
                    timer = null;
                }
                Log.v("registerCountDownTimer ", hour + "===时1" + minute + "===分" + second + "====秒" + timer);
            } else {
                Log.v("registerCountDownTimer ", hour + "===时" + minute + "===分" + second + "====秒" + timer);
                time_tv.setText("倒计时(h:m:s)格式：" + hour + "：" + minute + ":" + second + "   ,S(格式)：" + remainTime);
            }
        }
    }

    @Override
    public void finish() {
        if (timer != null) {
            Log.v("registerCountDownTimer", timer + "销毁时间");
            CountdownManager.getInstance().unRegisterCountDownTimer(timer);
            timer = null;
        }
        super.finish();
    }
}

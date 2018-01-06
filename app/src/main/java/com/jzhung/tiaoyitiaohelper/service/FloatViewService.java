package com.jzhung.tiaoyitiaohelper.service;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jzhung.tiaoyitiaohelper.R;
import com.jzhung.tiaoyitiaohelper.util.DensityUtil;
import com.jzhung.tiaoyitiaohelper.util.SPUtil;
import com.jzhung.tiaoyitiaohelper.util.ShellUtil;
import com.jzhung.tiaoyitiaohelper.view.GameView;

public class FloatViewService extends Service {
    private static final String TAG = "FloatViewService";
    private static final String SP_MOVE_SPEED = "MOVE_SPEED";
    private String shellTmp = "input touchscreen swipe %d %d %d %d %d";
    private WindowManager mWindowManager;
    private RelativeLayout mGameLayout;
    private int mMoveSpeed;//速度
    private double mMoveLength;//需要移动的距离
    private GameView mGameTouchView;

    private int mScreenWidth;
    private int mScreenHeight;
    private int mGameLayoutMargin;
    private int mTouchPointLeftMargin;

    private Point mTouchStartPoint;
    private Point mTouchEndPoint;
    private TextView mInfoTv;

    public FloatViewService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        initViews();
        mMoveSpeed = (int) SPUtil.get(getApplicationContext(), SP_MOVE_SPEED, 70);
        updateSpeedText();

        // 触摸点
        int margin = DensityUtil.dip2px(getApplicationContext(), 50);
//        int touchX = mScreenWidth - margin;
//        int touchY = margin;
        mTouchPointLeftMargin = DensityUtil.dip2px(getApplicationContext(), 50);
        mTouchStartPoint = new Point(mTouchPointLeftMargin, margin);
        mTouchEndPoint = new Point(mTouchPointLeftMargin + 100, margin);
    }

    private void updateSpeedText() {
        mInfoTv.setText("" + mMoveSpeed);
    }

    private void initViews() {
        mGameLayout = (RelativeLayout) LayoutInflater.from(getApplicationContext()).inflate(R.layout.game_layout, null);
        mGameLayoutMargin = DensityUtil.dip2px(getApplicationContext(), 150);
        mScreenWidth = mWindowManager.getDefaultDisplay().getWidth();
        mScreenHeight = mWindowManager.getDefaultDisplay().getHeight();
        mWindowManager.addView(mGameLayout, getParams());
        mGameTouchView = mGameLayout.findViewById(R.id.gameView);
        mGameLayout.findViewById(R.id.exitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWindowManager.removeView(mGameLayout);
                SPUtil.put(getApplicationContext(), SP_MOVE_SPEED, mMoveSpeed);
                stopSelf();
            }
        });

        mGameLayout.findViewById(R.id.clearBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mGameTouchView.clear();
            }
        });

        mGameLayout.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!mGameTouchView.ready()){
                    Toast.makeText(FloatViewService.this, "需要点击小人底部和下一个物体中间两个点", Toast.LENGTH_SHORT).show();
                    return;
                }

                mMoveLength = mGameTouchView.getLength();
                long time = (long) (mMoveLength * 100 / mMoveSpeed);
                Log.i(TAG, "执行触摸 时长：" + time + " 移动距离：" + mMoveLength);

                mGameTouchView.clear();

                //adb input touchscreen swipe x1 y1 x2 y2 100

                String cmd = String.format(shellTmp, mTouchStartPoint.x, mTouchStartPoint.y, mTouchEndPoint.x, mTouchEndPoint.y, time);
                //String cmd = "input touchscreen swipe 200 1700 800 1700 " + time;
                Log.i(TAG, "cmd: " + cmd);
                ShellUtil.execShellCmd(cmd);
            }
        });

        mGameLayout.findViewById(R.id.powerMinBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMoveSpeed += 1;
                updateSpeedText();
            }
        });

        mGameLayout.findViewById(R.id.powerPlusBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mMoveSpeed <= 1) {

                } else {
                    mMoveSpeed -= 1;
                    updateSpeedText();
                }
            }
        });

        mInfoTv = mGameLayout.findViewById(R.id.infoTv);
    }

    private WindowManager.LayoutParams getParams() {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        // 类型
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_ALERT;
        // WindowManager.LayoutParams.TYPE_SYSTEM_ALERT
        // 设置flag
        int flags = WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM
                | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        // 如果设置了WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE，弹出的View收不到Back键的事件
        params.flags = flags;
        // 不设置这个弹出框的透明遮罩显示为黑色
        params.format = PixelFormat.TRANSLUCENT;
        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // 设置 FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按
        // 不设置这个flag的话，home页的划屏会有问题
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        //params.verticalMargin = floatViewmargin;
        params.height = mScreenHeight - (mGameLayoutMargin * 2);
        params.gravity = Gravity.CENTER;
        return params;
    }

    @Override
    public void onDestroy() {
        if(mWindowManager != null){
            mWindowManager.removeView(mGameLayout);
        }
        super.onDestroy();
    }
}

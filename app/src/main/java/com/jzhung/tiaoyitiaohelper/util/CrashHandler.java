package com.jzhung.tiaoyitiaohelper.util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 全局未捕获异常处理器
 * 
 * @author jzhung
 *
 */
public class CrashHandler implements UncaughtExceptionHandler {
	private static final String TAG = "CrashHandler";
	private UncaughtExceptionHandler mDefaultHandler;
	private static CrashHandler INSTANCE = new CrashHandler();
    private static SimpleDateFormat sdf;
    public static final String sdfPattern = "yyyy-MM-dd_HH-mm-ss-ms";
    private Context context;

	public static String MYEXCEPTION_PATH_SDCARD_DIR = Environment.getExternalStorageDirectory().getAbsolutePath() + "/JLog/";// 日志文件在sdcard中的路径

	private CrashHandler() {
	}

	public static CrashHandler getInstance() {
        sdf = new SimpleDateFormat(sdfPattern);
		return INSTANCE;
	}

	public void init(Context context) {
        this.context = context;
		mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
		Thread.setDefaultUncaughtExceptionHandler(this);
	}

	@Override
	public void uncaughtException(Thread thread, Throwable ex) {
		if (!handleException(ex) && mDefaultHandler != null) {
			mDefaultHandler.uncaughtException(thread, ex);
		} else {
			ex.printStackTrace();
		}
	}

	private boolean handleException(final Throwable ex) {
		if (ex == null) {
			return true;
		}

        doHandler(ex);

//		new Thread() {
//			@Override
//			public void run() {
//				Looper.prepare();
//                doHandler(ex);
//				Looper.loop();
//			}
//
//		}.start();
		return true;
	}


    public void doHandler(Throwable ex){
        String fileName = "JCrash-" + sdf.format(new Date((System.currentTimeMillis()))) + ".log";
        if (!new File(MYEXCEPTION_PATH_SDCARD_DIR).exists()) {
            new File(MYEXCEPTION_PATH_SDCARD_DIR).mkdirs();
        }
        File file = new File(MYEXCEPTION_PATH_SDCARD_DIR, fileName);
        try {

            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            printWriter.close();
            FileOutputStream fos = new FileOutputStream(file, true);
            String msg = new String(writer.toString().getBytes());
			Log.e(TAG, "异常: ", ex);
			fos.write(msg.getBytes());
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            //发送错误日志邮件
            //new MailSender().sendEmail(context);
			Toast.makeText(context, "错误日志保存在：" + MYEXCEPTION_PATH_SDCARD_DIR, Toast.LENGTH_LONG).show();
        }
    }

}

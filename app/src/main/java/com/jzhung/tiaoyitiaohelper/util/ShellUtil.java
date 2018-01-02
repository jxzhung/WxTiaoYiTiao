package com.jzhung.tiaoyitiaohelper.util;

import android.util.Log;

import java.io.DataOutputStream;
import java.io.OutputStream;
import java.util.List;

/**
 * Shell工具类，需要ROOT SU权限
 * Created by jzhung on 2017/12/31.
 */

public class ShellUtil {
    private static final String TAG = "ShellUtil";
    /**
     * 执行shell命令
     *
     * @param cmd
     */
    public static void execShellCmd(String cmd) {

        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream outputStream = process.getOutputStream();
            DataOutputStream dataOutputStream = new DataOutputStream(
                    outputStream);
            dataOutputStream.writeBytes(cmd);
            dataOutputStream.flush();
            dataOutputStream.close();
            outputStream.close();
            Log.i(TAG, "execShellCmd: " + cmd);
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static void execShellCmd(List<String> cmdList){
        try {
            // 申请获取root权限，这一步很重要，不然会没有作用
            Process process = Runtime.getRuntime().exec("su");
            // 获取输出流
            OutputStream out = process.getOutputStream();
            DataOutputStream dos = new DataOutputStream(
                    out);
            for (String cmd : cmdList) {
                dos.writeBytes(cmd);
                dos.writeBytes("\n");
            }
            dos.flush();
            dos.close();
            out.close();
        } catch (Throwable t) {
            t.printStackTrace();
        }
    }
}

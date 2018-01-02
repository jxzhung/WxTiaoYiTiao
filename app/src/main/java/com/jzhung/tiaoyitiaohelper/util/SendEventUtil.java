package com.jzhung.tiaoyitiaohelper.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * getevent输出的是16进制，sendevent使用的是10进制，注意转换。
 * http://blog.csdn.net/fxdaniel/article/details/45846333
 *
 * 如在屏幕的x坐标为40，y坐标为210的点上touch一下，命令如下

 adb shell sendevent /dev/input/event0 3 0 40
 adb shell sendevent /dev/input/event0 3 1 210

 adb shell sendevent /dev/input/event0 1 330 1 //touch
 adb shell sendevent /dev/input/event0 0 0 0       //it must have

 adb shell sendevent /dev/input/event0 1 330 0 //untouch
 adb shell sendevent /dev/input/event0 0 0 0 //it must have
 * Created by jzhung on 2018/1/1.
 */

public class SendEventUtil {
    private static final String preCmdTemp = "sendevent /dev/input/event%s";

    public static void sendTouch(String input, int x, int y, long wait){
        List<String> cmds = new ArrayList<>();
        String preCmd = String.format(preCmdTemp, input);

        //定位
        cmds.add(preCmd + " 3 0 " + x);
        cmds.add(preCmd + " 3 1 " + y);

        //按下
        cmds.add(preCmd + " 1 330 1");
        cmds.add(preCmd + " 0 0 0");

        ShellUtil.execShellCmd(cmds);

        try {
            TimeUnit.MILLISECONDS.sleep(wait
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cmds.clear();
        //抬起
        cmds.add(preCmd + " 1 330 0");
        cmds.add(preCmd + " 0 0 0");
        ShellUtil.execShellCmd(cmds);
    }
}

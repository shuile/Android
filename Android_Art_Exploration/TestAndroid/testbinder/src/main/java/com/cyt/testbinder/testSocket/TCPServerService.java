package com.cyt.testbinder.testSocket;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.cyt.sdk_base.utils.LogUtil;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

public class TCPServerService extends Service {

    private static final String TAG = "TCPServerService";

    private boolean mIsServiceDestroyed = false;
    private String[] mDefinedMessage = new String[]{
            "你好啊，哈哈",
            "请问 你教什么名字呀？",
            "今天北京天气不错啊，shy",
            "你知道吗？我可是可以喝多个人同时聊天的哦",
            "给你讲个笑话吧，据说爱笑的人运气不会太差，不知道真假。"
    };

    @Override
    public void onCreate() {
        new Thread(new TcpServer()).start();
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mIsServiceDestroyed = true;
        super.onDestroy();
    }

    private void responseClient(Socket client) throws IOException {
        // 用于接收客户端消息
        BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        // 用于向客户端发送消息
        PrintWriter out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(client.getOutputStream())));
        out.println("欢迎来到聊天室！");
        while (!mIsServiceDestroyed) {
            String str = in.readLine();
            LogUtil.d(TAG, "msg from client: " + str);
            if (str == null) {
                // 客户端断开连接
                break;
            }
            int i = new Random().nextInt(mDefinedMessage.length);
            String msg = mDefinedMessage[i];
            out.println(msg);
            LogUtil.d(TAG, "send: " + msg);
        }
        LogUtil.d(TAG, "client quit.");
        // 关闭流
        out.close();
        in.close();
        client.close();
    }

    private class TcpServer implements Runnable {

        @SuppressWarnings("resource")
        @Override
        public void run() {
            ServerSocket serverSocket = null;
            try {
                // 监听本地8688端口
                serverSocket  = new ServerSocket(8688);
            } catch (IOException e) {
                LogUtil.e(TAG, "establish  tcp server failed, port: 8688");
                e.printStackTrace();
                return;
            }

            while (!mIsServiceDestroyed) {
                try {
                    // 接收客户端请求
                    final Socket client = serverSocket.accept();
                    LogUtil.d("accept");
                    new Thread() {
                        @Override
                        public void run() {
                            try {
                                responseClient(client);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

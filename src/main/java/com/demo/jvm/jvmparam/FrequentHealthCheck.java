package com.demo.jvm.jvmparam;

import com.demo.jvm.juc.utils.HashedWheelTimerUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.concurrent.TimeUnit;

@Slf4j
public class FrequentHealthCheck {
    public static void main(String[] args) {
        String ip = "pre.acceptor.mysirui.com";
        int port = 2103;
        SocketAddress address = new InetSocketAddress(ip,port);
        HashedWheelTimer timer = HashedWheelTimerUtil.instance().getTimer();
        for (int i=0;i<3;i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            final int index = i;
            timer.newTimeout(new TimerTask() {
                @Override
                public void run(Timeout timeout) throws Exception {
                    log.info("创建第{}次连接",index+1);
                    Socket socket = new Socket();
                    socket.connect(address);
                    socket.close();
                }
            },1000, TimeUnit.MILLISECONDS);
        }
        timer.stop();

    }
}

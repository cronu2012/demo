package com.rabbitmq.demo.utils;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.InetAddress;


@Slf4j
public class LoggingInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String host = request.url().host();

        long pingTime = pingHost(host);
        log.info("Ping to [{}] took: {} ms", host, pingTime);

        return chain.proceed(request);
    }

    private long pingHost(String host) {
        try {
            InetAddress inet = InetAddress.getByName(host);
            long start = System.currentTimeMillis();
            boolean reachable = inet.isReachable(3000); // timeout 3秒
            long end = System.currentTimeMillis();
            return reachable ? (end - start) : -1;
        } catch (IOException e) {
            return -1;
        }
    }
}
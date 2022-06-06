package com.gwang.socket.bio.server.handler;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 参考文档：
 * https://www.fallrain.cn/?p=1484
 * https://www.cnblogs.com/UUUz/p/11837632.html
 * https://juejin.cn/post/6844903939079421966（解释Content-Length）
 * https://blog.csdn.net/weixin_46535927/article/details/118975864（socket详解）
 *
 * HTTP响应头如下：
 *
 * HTTP/1.1 200 /r/n      http 协议 http状态码
 * Content-Type: text/html;charset=UTF-8 /r/n     类型
 * Content-Length: 10/r/n    数据长度
 * Date: Sun, 22 Aug 2021 13:20:36 GMT/r/n   时间
 * Keep-Alive: timeout=60rn  长连接时间
 * Connection: keep-alive/r/n 长连接，如果想保持长链接，服务端响应的时候要返回告诉客户端，不只要客户端带这个参数
 * /r/n
 *
 * HTTP响应头如下：
 * 具体的返回内容，长度要写在上面响应头里的Content-Length里
 *
 */

public class SocketHttpHandler implements Runnable {
    private final static Logger log = LoggerFactory.getLogger(SocketHandler.class);
    private final static String RESPONSE_DEFAULT_BODY = "Hello World";
    //	private final static String RESPONSE_HEAD = "HTTP/1.0 200 OK\r\nContent-type: text/plain\r\nConnection: keep-alive\r\n";
    //HTTP/1.1想保持长链接不用加Connection: keep-alive，默认就是keep-alive
    private final static String RESPONSE_HEAD = "HTTP/1.1 200 OK\r\nContent-type: text/plain\r\n";
    private final static String RESPONSE_LENGTH = "Content-Length: ";
    private final static String RESPONSE_END = "\r\n";

    final Socket socket;

    public SocketHttpHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            handleRequest(socket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }

    private void handleRequest(Socket socket) throws IOException {
        // Read the input stream, and return “200 OK”
        BufferedReader in = null;
        BufferedWriter out = null;
        int lineNum = 0;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

            String requestBody = null;
            String responseBody = RESPONSE_DEFAULT_BODY;
            while (true) {
                requestBody = in.readLine();
                lineNum++;
                if (StringUtils.isBlank(requestBody)) {
                    /**
                     * 以下判断只对http协议有效：
                     * BufferedReader在读取http协议的数据时，通过在结尾处传输"\r\n\r\n"来表述本次数据传输结束。
                     * 此时readLine()方法读取的是""或者null。
                     * 当client端（例如浏览器）断开socket链接时，readLine()方法读取的依然是""或者null
                     * 那么怎么区分是数据传输结束还是链接断开呢？因为当数据传出结束的时候要开始响应，而链接断开以后是要关闭链接和数据流
                     *
                     * 目前的想到的方法：当readLine()读取到""或者null时，判断是不是第一行读取，第一行读取表示链接断开。
                     *
                     * 如果用InputStreamReader读取数据的话需要自己写一个1024的bytes数据来缓冲数据。这时候判断链接断开返回数据为0。
                     * 判断数据传输完毕返回数据不为0，通过判断读取到的bytes数据是否是"\r\n\r\n"
                     *
                     */

                    if (lineNum == 1) {
                        //第一行读到的数据是""或者null表示链接断开
                        break;
                    }else {
                        //读取到""空字符串或是null标示读取内容完毕
                        log.info("---------------------------- request end --------------------------");
                        StringBuilder responseBuilder = new StringBuilder();

                        //响应头
                        responseBuilder.append(RESPONSE_HEAD).append(RESPONSE_LENGTH).append(responseBody.length())
                                .append(RESPONSE_END).append(RESPONSE_END).append(responseBody);
                        out.write(responseBuilder.toString());
                        out.flush();
                        log.info("---------------------------- response end --------------------------");
                        lineNum = 0;
                        continue;
                    }
                }
                if (lineNum < 3){
                    log.info(requestBody);
                }
                if (lineNum == 1){
                    String[] strings = requestBody.split(" ");
                    if (strings.length > 2){
                        responseBody = strings[1];
                    } else {
                        responseBody = RESPONSE_DEFAULT_BODY;
                    }
                }
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
            if (socket != null) {
                socket.close();
            }
        }
    }
}

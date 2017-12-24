package com.merpyzf.xmshare;

import com.merpyzf.httpcoreserver.constant.Constant;

import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.URLDecoder;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void TestURLDecoder() {


        String url = "http://172.28.67.84:8888/storage/emulated/legacy/%E7%8C%8E%E8%B1%B9%E6%B8%85%E7%90%86%E5%A4%A7%E5%B8%88";

        try {
            // 将Base64编码解码成utf-8编码
            String encodeUrl = URLDecoder.decode(url, Constant.ENCODING);
            System.out.println(encodeUrl);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testDot() {

        String fileName = "andro.id";

        //找不到就是返回-1
        int i = fileName.lastIndexOf(".");

        String substring = fileName.substring(i + 1);
        System.out.println(substring);
        System.out.println(i);


    }


    @Test
    public void testStr() {

        String str = "helnjnjnjnjnjnjnjnj211\0";

        int indexOf = str.indexOf('\0');

        CharSequence sequence = str.subSequence(0, indexOf);
        String s = sequence.toString();

        System.out.println(s);


    }

    @Test
    public void testSocketServer() throws IOException {

        ServerSocket serverSocket =null;

        try {
            serverSocket = new ServerSocket();
            serverSocket.setReuseAddress(true);
            serverSocket.bind(new InetSocketAddress(8888));


            while (true){

                System.out.println("阻塞中...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("连接设备:"+clientSocket.getInetAddress().getHostAddress());



            }




        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            serverSocket.close();
        }


    }

    @Test
    public void testSocketClient(){


        try {
            Socket socket = new Socket();
            socket.setReuseAddress(true);
            socket.bind(new InetSocketAddress(8888));



        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
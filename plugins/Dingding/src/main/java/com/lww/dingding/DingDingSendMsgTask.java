package com.lww.dingding;

import com.google.gson.Gson;

import org.apache.commons.codec.binary.Base64;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.internal.impldep.com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.inject.Inject;
import javax.net.ssl.HttpsURLConnection;

import groovy.json.JsonOutput;

public class DingDingSendMsgTask extends DefaultTask {
    DingDingEntity mInfo;
    private static String  mUploadResponseInfo;
   @Inject
    public DingDingSendMsgTask(DingDingEntity dingDingEntity) {
        this.mInfo = dingDingEntity;
        setGroup("DingDing");
    }

    public static void setUploadResponse(String output) {
        mUploadResponseInfo=output;
    }


    @TaskAction
    public void sendMsd() {
        String secret = mInfo.getSecret();
        String token = mInfo.getToken();
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;


        String   url = "https://oapi.dingtalk.com/robot/send?access_token=" +token+ "&timestamp="
                + timestamp + "&sign=" + getSign(stringToSign, secret);



        PGYResonse  pgyResonse =JsonUtil.parseJsonToBean(mUploadResponseInfo,PGYResonse.class);
        String buildShortcutUrl = pgyResonse.getData().getBuildShortcutUrl();

        DingDingEntity.LinkBean linkBean = new DingDingEntity.LinkBean();
        linkBean.setText(mInfo.getContent());
        linkBean.setTitle(mInfo.getTitle());
        linkBean.setPicUrl("");
//        linkBean.setMessageUrl(JsonUtil.getFieldValue(mUploadResponseInfo,"buildShortcutUrl"));
//        linkBean.setMessageUrl("https://www.pgyer.com/WsRT");
        linkBean.setMessageUrl("https://www.pgyer.com/"+pgyResonse.getData().getBuildShortcutUrl());
        linkBean.setMessageUrl( pgyResonse.getData().getBuildQRCodeURL());

        mInfo.setLink(linkBean);

         sendPostByMap(url, JsonUtil.parseMapToJson(mInfo));


    }




    public String getSign(String stringToSign, String secret) {
        String sign = null;
        Mac mac = null;
        try {
            mac = Mac.getInstance("HmacSHA256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            mac.init(new SecretKeySpec(secret.getBytes("UTF-8"), "HmacSHA256"));
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        byte[] signData = new byte[0];
        try {
            signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        try {
            sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    public String sendPostByMap(String url, String requestinfo) {
        Map<String, String> headParam = new HashMap();
        headParam.put("Content-type", "application/json;charset=UTF-8");
        return sendPost(url, requestinfo, headParam);
    }



    public String sendPost(String url, String requestinfo, Map<String, String> headParam) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性 请求头
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Fiddler");
            if (headParam != null) {
                for (Map.Entry<String, String> entry : headParam.entrySet()) {
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(requestinfo);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            System.out.println("发送 POST fanhui！" + result);
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！" + e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }








}


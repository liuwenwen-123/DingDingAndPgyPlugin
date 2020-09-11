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
    public static String  msg;
   @Inject
    public DingDingSendMsgTask(DingDingEntity dingDingEntity) {
        this.mInfo = dingDingEntity;
        System.out.println("  i  am   DingDingSendMsgTask");
        setGroup("DingDing");
    }

    public static void setInfo(String aaa) {
        msg=aaa;
    }

    @TaskAction
    public void sendMsd() {

        String secret = mInfo.getSecret();
        String url = null;

        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + secret;

        url = "https://oapi.dingtalk.com/robot/send?access_token=a71d894cf1571c514853b399b3c148f309fc25e8eb416fc6367d15f4782e6537" + "&timestamp=" + timestamp + "&sign=" + getSign(stringToSign, secret);
        ;

        JSONObject jsonObject = new JSONObject(mInfo.getInfo());
        String content = jsonObject.getString("content");

        DingDingEntity.TextBean textBean = new DingDingEntity.TextBean();

        textBean.setContent(content);
        mInfo.setText(textBean);



        System.out.println("aaa contex  " + JsonUtil.parseMapToJson(mInfo));

        Map<String, Object> json = new HashMap();
        Map<String, Object> text = new HashMap();
        json.put("msgtype", "text");
        text.put("content", "我是内容");
        json.put("text", text);
        // 发送post请求
//        sendPostByMap(url, JsonOutput.toJson(mInfo));

//        sendPostByMap(url, JsonUtil.parseMapToJson(mInfo));
        Map<String, String> params=new HashMap<>();
        params.put("_api_key","7936ccab122753254294d9d6b181e987");
        File file = new File(mInfo.getFilepath());

//       System.out.println("fiel  "+mInfo.getFile().getAbsolutePath());
        String  uploadinfo = uploadFile("https://www.pgyer.com/apiv2/app/upload", params,file
                );
        System.out.println("uploadinfo  "+uploadinfo);

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

    public String sendPostByMap(String url, Map<String, Object> mapParam) {
        Map<String, String> headParam = new HashMap();
        headParam.put("Content-type", "application/json;charset=UTF-8");
        return sendPost(url, mapParam, headParam);
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


    public String sendPost(String url, Map<String, Object> param, Map<String, String> headParam) {
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
            out.print(JsonOutput.toJson(param));
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
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



    public static String uploadFile(String url, Map<String, String> params, File file) {
        // 换行，或者说是回车
        //final String newLine = "\r\n";
        final String newLine = "\r\n";
        // 固定的前缀
        final String preFix = "--";
        //final String preFix = "";
        // 分界线，就是上面提到的boundary，可以是任意字符串，建议写长一点，这里简单的写了一个#
        final String bounDary = "----WebKitFormBoundaryCXRtmcVNK0H70msG";
        //final String bounDary = "";
        //请求返回内容
        String output = "";

        try {
            //统一资源定位符
            URL uploadFileUrl = new URL(url);
            //打开http链接类
            HttpURLConnection httpURLConnection = (HttpURLConnection) uploadFileUrl.openConnection();
            //设置是否向httpURLConnection输出
            httpURLConnection.setDoOutput(true);
            //设置请求方法默认为get
            httpURLConnection.setRequestMethod("POST");
            //Post请求不能使用缓存
            httpURLConnection.setUseCaches(false);
            //设置token
//            httpURLConnection.setRequestProperty("authorization", (String) GlobalContext.getSessionAttribute("token"));
            //为web端请求
//            httpURLConnection.setRequestProperty("os", "web");
            //从新设置请求内容类型
            httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
            httpURLConnection.setRequestProperty("Accept", "*/*");
            httpURLConnection.setRequestProperty("Accept-Encoding", "gzip, deflate, br");
            httpURLConnection.setRequestProperty("Cache-Control", "no-cache");
            //application/json;charset=UTF-8 application/x-www-form-urlencoded
            httpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + bounDary);
//            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded');

            httpURLConnection.setRequestProperty("User-Agent", "(Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.84 Safari/537.36)");


            DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());

            //设置DataOutputStream设置DataOutputStream数据输出流
            //OutputStream outputStream = httpURLConnection.getOutputStream();

            //上传普通文本文件
            if (params.size() != 0 && params != null) {
                for (Map.Entry<String, String> entry : params.entrySet()) {
                    //获取参数名称和值
                    String key = entry.getKey();
                    String value = params.get(key);
                    //向请求中写分割线
                    dos.writeBytes(preFix + bounDary + newLine);
                    //向请求拼接参数
                    //String parm = key + "=" + URLEncoder.encode(value,"utf-8") +"\r\n" ;
                    dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + key + "\"" + newLine);
                    //向请求中拼接空格
                    dos.writeBytes(newLine);
                    //写入值
                    dos.writeBytes(URLEncoder.encode(value, "utf-8"));
                    //dos.writeBytes(value);
                    //向请求中拼接空格
                    dos.writeBytes(newLine);
                }
            }

            //上传文件
            if (file != null && !params.isEmpty()) {
                //向请求中写分割线
                //把file装换成byte
                 File del = new File(file.toURI());
//                System.out.println(del);
                InputStream inputStream = new FileInputStream(del);
                byte[] bytes= input2byte(inputStream);
                String filePrams = "file";
                String fileName = file.getName();
                //向请求中加入分隔符号
                dos.write((preFix + bounDary + newLine).getBytes());
                //将byte写入
                dos.writeBytes("Content-Disposition: form-data; " + "name=\"" + URLEncoder.encode(filePrams, "utf-8") + "\"" + "; filename=\"" + URLEncoder.encode(fileName, "utf-8") + "\"" + newLine);
                dos.writeBytes(newLine);
                dos.write(bytes);
                //向请求中拼接空格
                dos.writeBytes(newLine);
            }
            dos.writeBytes(preFix + bounDary + preFix + newLine);
            //请求完成后关闭流
            //得到相应码
            dos.flush();
            //判断请求没有成功
            PGYUploadTask.setData("6666");
            if (httpURLConnection.getResponseCode() != 200) {
                System.out.println(url + "   请求异常，错误代码为：  " + httpURLConnection.getResponseCode());
//                logger.error(url + "   请求异常，错误代码为：  " + httpURLConnection.getResponseCode());
                return "{result:'fail',response:'errorCode:" + httpURLConnection.getResponseCode() + "'}";
            }
            //判断请求成功
            if (httpURLConnection.getResponseCode() == 200) {
                //将服务器的数据转化返回到客户端
                InputStream inputStream = httpURLConnection.getInputStream();
                byte[] bytes = new byte[0];
                bytes = new byte[inputStream.available()];
                inputStream.read(bytes);
                output = new String(bytes);
                inputStream.close();
            }
            dos.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(url + "   请求异常，错误信息为：  " + e.getMessage());
//            logger.error(url + "   请求异常，错误信息为：  " + e.getMessage());
            return "{result:'fail',response:'" + e.getMessage() + "'}";
        }
        return output;
    }

    /**
     * 将输入流转化成字节流
     * @param inStream
     * @return
     * @throws IOException
     */
    public static final byte[] input2byte(InputStream inStream) throws IOException {
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        while ((rc = inStream.read(buff, 0, 100)) > 0) {
            swapStream.write(buff, 0, rc);
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    public  void up() throws Exception{
        //本地图片
        java.io.File file = new java.io.File("/Users/jikukalun/Pictures/id1.jpg");
        FileInputStream fileInputStream = new FileInputStream(file);
        //对接外部接口
        String urlString = "************";

        URL url = new URL(urlString);
        HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
        // 设置是否向httpUrlConnection输出，因为这个是post请求，参数要放在
        // http正文内，因此需要设为true, 默认情况下是false;
        con.setDoOutput(true);
        // 设置是否从httpUrlConnection读入，默认情况下是true;
        con.setDoInput(true);
        // 设定请求的方法为"POST"，默认是GET
        con.setRequestMethod("POST");
        // Post 请求不能使用缓存
        con.setUseCaches(false);
        // 设定传送的内容类型是可序列化的java对象
        // (如果不设此项,在传送序列化对象时,当WEB服务默认的不是这种类型时可能抛java.io.EOFException)
//        con.setRequestProperty("Content-type", "application/x-java-serialized-object");
        OutputStream out = con.getOutputStream();

        //读取本地图片文件流
        FileInputStream inputStream = new FileInputStream(file);
        byte[] data = new byte[2048];
        int len = 0;
        int sum = 0;
        while ((len = inputStream.read(data)) != -1) {
            //将读取到的本地文件流读取到HttpsURLConnection,进行上传
            out.write(data, 0, len);
            sum = len + sum;
        }

        System.out.println("上传图片大小为:" + sum);

        out.flush();
        inputStream.close();
        out.close();

        int code = con.getResponseCode();  //获取post请求返回状态
        System.out.println("code=" + code + " url=" + url);
        if (code == 200) {
            InputStream inputStream2 = con.getInputStream();
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            while ((len = inputStream2.read(data)) != -1) {
                bos.write(data, 0, len);
            }
            inputStream2.close();
            String content = bos.toString();
            bos.close();
            System.out.println("result =" + content);
            //将返回的json格式的字符串转化为json对象
           /* JSONObject json = JSONObject.parseObject(content);
            try {
                System.out.println("name=" + json.getString("name") + ", people=" + json.getString("people") + ", sex=" + json.getString("sex")
                        + ", id_number=" + json.getString("id_number") + ", type=" + json.getString("type") + ", address=" + json.getString("address")
                        + ", birthday=" + json.getString("birthday"));
            } catch (JSONException e) {
                e.printStackTrace();
            }*/
        }
        //断开HttpsURLConnection连接
        con.disconnect();
    }

}


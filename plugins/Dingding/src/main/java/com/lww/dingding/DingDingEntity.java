package com.lww.dingding;

import java.io.File;
import java.util.List;

/**
 *  发送文本
 */
public class DingDingEntity {
    /**
     * msgtype : text
     * text : {"content":"我就是我, 是不一样的烟火@156xxxx8827"}
     * at : {"atMobiles":["156xxxx8827","189xxxx8325"],"isAtAll":false}
     */

    private String msgtype;
    private TextBean text;
    private String  info;
    private String secret;
    private File file;
    private  String   filepath;

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    private AtBean at;

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }

    public TextBean getText() {
        return text;
    }

    public void setText(TextBean text) {
        this.text = text;
    }

    public AtBean getAt() {
        return at;
    }

    public void setAt(AtBean at) {
        this.at = at;
    }

    public static class TextBean {
        /**
         * content : 我就是我, 是不一样的烟火@156xxxx8827
         */

        private String content;

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }

    public static class AtBean {
        /**
         * atMobiles : ["156xxxx8827","189xxxx8325"]
         * isAtAll : false
         */

        private boolean isAtAll;
        private List<String> atMobiles;

        public boolean isIsAtAll() {
            return isAtAll;
        }

        public void setIsAtAll(boolean isAtAll) {
            this.isAtAll = isAtAll;
        }

        public List<String> getAtMobiles() {
            return atMobiles;
        }

        public void setAtMobiles(List<String> atMobiles) {
            this.atMobiles = atMobiles;
        }
    }





}

package com.lww.dingding;

public class PGYResonse {
   private      int code;
    private String message;
    private  DataInfo data ;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public DataInfo getData() {
        return data;
    }

    public void setData(DataInfo data) {
        this.data = data;
    }

    public class  DataInfo{
        private   String  buildShortcutUrl;
        private   String   buildQRCodeURL;

        public String getBuildQRCodeURL() {
            return buildQRCodeURL;
        }

        public void setBuildQRCodeURL(String buildQRCodeURL) {
            this.buildQRCodeURL = buildQRCodeURL;
        }

        public String getBuildShortcutUrl() {
            return buildShortcutUrl;
        }

        public void setBuildShortcutUrl(String buildShortcutUrl) {
            this.buildShortcutUrl = buildShortcutUrl;
        }
    }
}

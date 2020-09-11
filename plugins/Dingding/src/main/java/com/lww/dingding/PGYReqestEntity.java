package com.lww.dingding;

import java.io.File;

/**
 *  蒲公英 请求 参数
 */
public class PGYReqestEntity {
    private String filePaht;
    private String _api_key;  //(必填) API Key
    private File file;  //需要上传的ipa或者apk文件
    private String buildInstallType;  // 应用安装方式，值为(1,2,3，默认为1 公开安装)。1：公开安装，2：密码安装，3：邀请安装
    private String buildPassword; //  设置App安装密码，密码为空时默认公开安装
    private String buildUpdateDescription;  // (选填) 版本更新描述，请传空字符串，或不传。
    private String buildInstallDate;  //(选填)是否设置安装有效期，值为：1 设置有效时间， 2 长期有效，如果不填写不修改上一次的设置
    private String buildInstallStartDate;  //(选填)安装有效期开始时间，字符串型，如：2018-01-01
    private String buildInstallEndDate;// 	(选填)安装有效期结束时间，字符串型，如：2018-12-31
    private String buildChannelShortcut; //(选填)所需更新的指定渠道的下载短链接，只可指定一个渠道，字符串型，如：abcd

    public String getFilePaht() {
        return filePaht;
    }

    public void setFilePaht(String filePaht) {
        this.filePaht = filePaht;
    }

    public String get_api_key() {
        return _api_key;
    }

    public void set_api_key(String _api_key) {
        this._api_key = _api_key;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public String getBuildInstallType() {
        return buildInstallType;
    }

    public void setBuildInstallType(String buildInstallType) {
        this.buildInstallType = buildInstallType;
    }

    public String getBuildPassword() {
        return buildPassword;
    }

    public void setBuildPassword(String buildPassword) {
        this.buildPassword = buildPassword;
    }

    public String getBuildUpdateDescription() {
        return buildUpdateDescription;
    }

    public void setBuildUpdateDescription(String buildUpdateDescription) {
        this.buildUpdateDescription = buildUpdateDescription;
    }

    public String getBuildInstallDate() {
        return buildInstallDate;
    }

    public void setBuildInstallDate(String buildInstallDate) {
        this.buildInstallDate = buildInstallDate;
    }

    public String getBuildInstallStartDate() {
        return buildInstallStartDate;
    }

    public void setBuildInstallStartDate(String buildInstallStartDate) {
        this.buildInstallStartDate = buildInstallStartDate;
    }

    public String getBuildInstallEndDate() {
        return buildInstallEndDate;
    }

    public void setBuildInstallEndDate(String buildInstallEndDate) {
        this.buildInstallEndDate = buildInstallEndDate;
    }

    public String getBuildChannelShortcut() {
        return buildChannelShortcut;
    }

    public void setBuildChannelShortcut(String buildChannelShortcut) {
        this.buildChannelShortcut = buildChannelShortcut;
    }
}

package com.jc.update;

/**
 * uptInfo实体类,这个实体类对应于服务器端的xml文件,当程序检测是否有新版本时,
 * 首先从服务器下载xml文件后,会将这个xml文件内的信息解析到这个实体类中
 * Created by bsty on 11/17/15.
 */
public class UpdateInfo {
    private String version; //版本号
    private String url; //url路径
    private String description; //版本更新说明


    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

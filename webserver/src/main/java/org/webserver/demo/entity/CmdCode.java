package org.webserver.demo.entity;

public enum CmdCode {
    /**
     * 获取列表
     */
    LIST,
    DOWNLOAD,
    UPLOAD,
    /**
     * 无效
     */
    INVAILD;

    /*Runnable task;
    CmdCode(Runnable task){
        this.task = task;
    }*/

    /**
     * 根据文本转换指令
     * @param text 文本
     * @return 指令
     */
    public static CmdCode transform(String text){
        if(text == null ||text.trim().length() == 0) return CmdCode.INVAILD;
        if(text.contains("list")){
            return CmdCode.LIST;
        }else if (text.contains("download")){
            return CmdCode.DOWNLOAD;
        }else if(text.contains("upload")){
            return CmdCode.UPLOAD;
        }
        return CmdCode.INVAILD;
    }
}

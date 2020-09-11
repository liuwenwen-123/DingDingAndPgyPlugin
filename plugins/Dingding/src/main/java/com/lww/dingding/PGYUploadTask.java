package com.lww.dingding;

import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;

import javax.inject.Inject;

public class PGYUploadTask extends DefaultTask {
    private static String infos;
    PGYReqestEntity mInfo;


    /*@Inject
    public PGYUploadTask() {

    }*/
    @Inject
    public PGYUploadTask(PGYReqestEntity mInfo) {
        this.mInfo = mInfo;
        setGroup("uploadapk");
        System.out.println("  i  am   PGYUploadTask");
    }

    public  static void setData(String s) {
         infos = s;
        System.out.println("  i  am   PGYUploadTask  ====  "+s);

    }
/* @Inject
    public PGYUploadTask(PGYReqestEntity pgyReqestEntity) {
        this.mInfo=pgyReqestEntity;
        System.out.println("  i  am   PGYUploadTask");
        setGroup("uploadPGY");
    }*/

    @TaskAction
    public void upload() {
//        DingDingSendMsgTask.setInfo("aaa");
        System.out.println("  i  am   PGYUploadTask   aaa   "+infos);
    }


}

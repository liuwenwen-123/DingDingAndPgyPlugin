package com.lww.dingding;


import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class DingDingPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
//        dingdingText  这个是在  app 的 build.gradle 中使用
        final DingDingEntity dingdingText = project.getExtensions().create("dingdingText1", DingDingEntity.class);

        final PGYReqestEntity pGYReqestEntity = project.getExtensions().create("pgy", PGYReqestEntity.class);
        project.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {
                System.out.println(dingdingText.getMsgtype());
                System.out.println(dingdingText.getInfo());
                System.out.println(dingdingText.getSecret());
                System.out.println(dingdingText.getFilepath());
                System.out.println("aaa " + pGYReqestEntity.get_api_key());

                DingDingSendMsgTask sendTxt = project.getTasks().create("sendTxt", DingDingSendMsgTask.class, dingdingText);

                PGYUploadTask upapk71 = project.getTasks().create("upapk7", PGYUploadTask.class, pGYReqestEntity);
                upapk71.dependsOn(sendTxt);  // dependsOn表示在自己之前先执行这个方法

// 51536  1300*/

//                AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
//         得到一个集合  默认获取  debug  和release
               /* appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {

                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
//                                需要加固的  文件
                                File outputFile = baseVariantOutput.getOutputFile();
                                String name = baseVariantOutput.getName();
                                dingdingText.setFile(outputFile);
                                project.getTasks().create("sendTxt1",DingDingSendMsgTask.class,dingdingText);
                            }
                        });
                    }
                });*/
            }


        });

    }
}

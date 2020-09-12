package com.lww.dingding;


import com.android.build.gradle.AndroidConfig;
import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.file.FileTree;
import org.gradle.api.logging.Logger;

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

                DingDingSendMsgTask sendTxt = project.getTasks().create("sendTxt",
              DingDingSendMsgTask.class, dingdingText);

                PGYUploadTask upapk71 = project.getTasks().create("upapk7", PGYUploadTask.class,
                pGYReqestEntity);
                sendTxt.dependsOn(upapk71);  // dependsOn表示在自己之前先执行这个方法

// 51536  1300*/

                // 获取 Android 配置信息（包括了应用和库）
//                AndroidConfig androidConfig = project.getExtensions().getByType(AndroidConfig.class);

                // 获取 Android Java 源文件
               /* androidConfig.getSourceSets().getByName("main", androidSourceSet -> {
                    FileTree javaSourceFiles = androidSourceSet.getJava().getSourceFiles();


                });*/




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
                                String outputType = baseVariantOutput.getOutputType();
                                System.out.println(outputFile + "----" + name + "------" + outputType);


                                pGYReqestEntity.setFilePaht(outputFile.getAbsolutePath());

    DingDingSendMsgTask sendTxt = project.getTasks().create("sendTxt",
              DingDingSendMsgTask.class, dingdingText);

                PGYUploadTask upapk71 = project.getTasks().create("upapk7", PGYUploadTask.class,
                pGYReqestEntity);
                sendTxt.dependsOn(upapk71);  // dependsOn表示在自己之前先执行这个方法

                            }
                        });
                    }
                });*/



               /* AppExtension appExtension = project.getExtensions().getByType(AppExtension.class);
//         得到一个集合  默认获取  debug  和release
                appExtension.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {

                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
//                                需要加固的  文件
                                File outputFile = baseVariantOutput.getOutputFile();
                                String name = baseVariantOutput.getName();
                               System.out.println(" ===  "+name);
                          if(name.equals("debug")){


                                DingDingSendMsgTask sendTxt = project.getTasks().create(name+"sendTxt",
                                        DingDingSendMsgTask.class, dingdingText);

                                pGYReqestEntity.setFilePaht(outputFile.getAbsolutePath());
                                PGYUploadTask upapk71 = project.getTasks().create(name +"upapk7", PGYUploadTask.class,
                                        pGYReqestEntity);
                                sendTxt.dependsOn(upapk71);
                          }
                            }
                        });
                    }
                });
*/

            }


        });

    }
}

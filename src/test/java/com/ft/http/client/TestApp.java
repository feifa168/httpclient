package com.ft.http.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ft.http.v3.App;
import com.ft.http.v3.HttpClient;
import com.ft.http.v3.config.AssetsScanResultMix;
import com.ft.http.v3.config.AssetsScanResultMixWithError;
import com.ft.http.v3.config.TaskScanConfig;
import com.ft.http.v3.scan.NewScan;
import com.ft.http.v3.scan.NewScanReturn;
import com.ft.http.v3.task.NewTask;
import com.ft.http.v3.task.NewTaskReturn;
import com.ft.http.v3.vulnerabilities.SingleSolutions;
import com.ft.http.v3.weakpassword.CrackScanReturn;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestApp {

    private App app = new App();

    NewTask task = null;
    NewScan scan;
    NewTaskReturn newTaskReturn = null;
    NewScanReturn newScanReturn = null;

    List<AssetsScanResultMix> scanReslutMix = new ArrayList<>();
    AssetsScanResultMixWithError scanReslutMixWithError = new AssetsScanResultMixWithError();
    Map<String, App.CrackScanResultInfo> crackResultInfos = new HashMap<>();
    TaskScanConfig taskScanConfig = null;

    public TestApp() {
    }

    @Before
    public void before() {

        String configFile = "assetsscan.xml";
        do {
            try {
                if (!app.parseRunConfig()) {
                    scanReslutMixWithError.setMessage("运行配置无效");
                    break;
                }
            } catch (IOException e) {
                scanReslutMixWithError.setMessage("读取运行配置文件失败，原因是"+e.getMessage());
                break;
            }

            // 读取配置文件
            try {
                taskScanConfig = app.buildTaskConfig(configFile);
            } catch (IOException e) {
                scanReslutMixWithError.setMessage("读取配置文件失败，原因是"+e.getMessage());
                break;
            }

            // 设置工具类型
            scanReslutMixWithError.setToolcategory(taskScanConfig.getToolcategory());
            // 设置下发的taskcode
            String code = taskScanConfig.getTaskcode();
            if (code == null) {
                scanReslutMixWithError.setMessage("没有配置taskcode");
                break;
            }
            scanReslutMixWithError.setTaskcode(code);

            // 构造任务
            try {
                task = app.buildNewTask(taskScanConfig);
            } catch (JsonProcessingException e) {
                scanReslutMixWithError.setMessage("创建任务失败，原因是"+e.getMessage());
                break;
            }

            if (0 == task.getEngineId()) {
                scanReslutMixWithError.setMessage("没有配置引擎ID");
                break;
            }
            if ((null==task.getScanTemplateId()) || ("".equals(task.getScanTemplateId()))) {
                scanReslutMixWithError.setMessage("没有配置模板ID");
                break;
            }
            NewTask.Scan sc = task.getScan();
            if ((null == sc)
                    || (null == sc.getAssets())
                    || (null == sc.getAssets().getIncludedTargets())
                    || (null == sc.getAssets().getIncludedTargets().getAddresses())
                    || (0 == sc.getAssets().getIncludedTargets().getAddresses().size())
            ) {
                scanReslutMixWithError.setMessage("没有配置扫描地址");
                break;
            }
        } while (false);
    }

    @Test
    public void testStartTask() {
        do{
            // 创建任务
            try {
                newTaskReturn = app.startTask(task);

                if (null == newTaskReturn) {
                    scanReslutMixWithError.setMessage("创建任务失败,返回无效任务");
                    break;
                }
            } catch (Exception e) {
                scanReslutMixWithError.setMessage("创建任务失败,"+e.getMessage());
                break;
            }

            int taskId = newTaskReturn.getId();
            if (0 == taskId) {
                scanReslutMixWithError.setMessage("创建任务失败,"+newTaskReturn.getError());
                break;
            }

            // 设置任务ID
            scanReslutMixWithError.setTaskid(String.valueOf(taskId));
        } while (false);
    }

    @Test
    public void testVulnerabilitiesDetail() {
        // /api/v3/vulnerabilities/{id}    generic-icmp-timestamp
        String vulnerabId = "generic-tcp-timestamp";
        try {
            app.queryVulnerabilitiesDetail(vulnerabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testVulnerabilitieSuggest() {
        // 漏洞修补建议
        // api/v3/vulnerabilities/{id}/solutions
        String vulnerabId = "generic-tcp-timestamp";
        try {
            app.queryVulnerabilitiesSolutions(vulnerabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingleSolutionsNotFound() {
        // 单个解决方案
        // /api/v3/solutions/{id}
        // "resources" : [ "generic-tcp-timestamp-disable-cisco", "generic-tcp-timestamp-disable-freebsd", "generic-tcp-timestamp-disable-linux", "generic-tcp-timestamp-disable-openbsd", "generic-tcp-timestamp-disable-windows", "generic-tcp-timestamp-disable-windows-postvista" ]

        String vulnerabId = "generic-tcp-timestamp";
        try {
            app.querySingleSolutions(vulnerabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingleSolutionsNormal() {
        // 单个解决方案
        // /api/v3/solutions/{id}
        // "resources" : [ "generic-tcp-timestamp-disable-cisco", "generic-tcp-timestamp-disable-freebsd", "generic-tcp-timestamp-disable-linux", "generic-tcp-timestamp-disable-openbsd", "generic-tcp-timestamp-disable-windows", "generic-tcp-timestamp-disable-windows-postvista" ]

        String vulnerabId = "generic-tcp-timestamp-disable-cisco";
        try {
            app.querySingleSolutions(vulnerabId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testSingleSolutionsAll() {
        // 单个解决方案
        // /api/v3/solutions/{id}
        // "resources" : [ "generic-tcp-timestamp-disable-cisco", "generic-tcp-timestamp-disable-freebsd", "generic-tcp-timestamp-disable-linux", "generic-tcp-timestamp-disable-openbsd", "generic-tcp-timestamp-disable-windows", "generic-tcp-timestamp-disable-windows-postvista" ]

        List<String> vulnerabIds = Stream.of(
                    "generic-tcp-timestamp-disable-cisco",
                    "generic-tcp-timestamp-disable-freebsd",
                    "generic-tcp-timestamp-disable-linux",
                    "generic-tcp-timestamp-disable-openbsd",
                    "generic-tcp-timestamp-disable-windows",
                    "generic-tcp-timestamp-disable-windows-postvista"
        ).collect(Collectors.toList());

        List<SingleSolutions> ss = new ArrayList<>();
        for (String vulnerabId : vulnerabIds) {
            try {
                ss.add(app.querySingleSolutions(vulnerabId));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void testVulnerabilitiesAssets() {
        // 关联设备
        // api/v3/vulnerabilities/{id}/assets
    }

    @Test
    public void testScanTemplate() {
        String scanTemplate = "discovery";
        try {
            app.queryScanTemplate(scanTemplate);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAllVul() {
        FileChannel fc = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String name = "vulnerabilities_"+formatter.format(new Date())+".json";

            //fc = FileChannel.open(Paths.get(name), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            RandomAccessFile raf = new RandomAccessFile(name, "rw");
            fc = raf.getChannel();
            //fc.write(ByteBuffer.wrap("tom".getBytes()));
            app.queryAllVulnerabilities(fc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != fc) {
                try {
                    fc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Test
    public void testOfflineUpdate() {
        try {
            String updateFile = "update_1.4.1.dat";// test.dat
            app.offlineUpdate(updateFile);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Test
    public void testQueryEngine() {
        FileChannel fc = null;
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String name = "engine_"+formatter.format(new Date())+".json";

            //fc = FileChannel.open(Paths.get(name), StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
            RandomAccessFile raf = new RandomAccessFile(name, "rw");
            fc = raf.getChannel();
            app.queryScanEngine(fc);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

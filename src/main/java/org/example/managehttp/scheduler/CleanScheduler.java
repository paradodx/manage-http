package org.example.managehttp.scheduler;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;

@Component
public class CleanScheduler {

    private final static String filePrefix = "src/main/resources/proto/";
    private final static long MAX_FILE_AGE_HOURS = 1; // 文件最大保留时间
    
    @Scheduled(fixedRate = 3600000)
    public void cleanScheduled(){
        File directory = new File(filePrefix);
        if (directory.exists() && directory.isDirectory()) {
            File[] files = directory.listFiles();
            if (files != null) {
                Arrays.stream(files)
                        .filter(File::isFile)
                        .filter(this::isFileOld)
                        .forEach(this::deleteFile);
            }
        }
    }

    private boolean isFileOld(File file) {
        long lastModified = file.lastModified();
        Instant fileInstant = Instant.ofEpochMilli(lastModified);
        return fileInstant.isBefore(Instant.now().minus(MAX_FILE_AGE_HOURS, ChronoUnit.HOURS));
    }

    private void deleteFile(File file) {
        if (!file.delete()) {
            System.err.println("Failed to delete file: " + file.getPath());
        }
    }
}

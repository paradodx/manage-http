package org.example.managehttp.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@Data
@AllArgsConstructor
public class ProtocTool {

    private String out;

    private String source;

    private String filePath;
    
    private String getCommand() {
        return String.format("protoc --descriptor_set_out=%s --proto_path=%s %s", this.out, this.source, this.filePath);
    }
    /**
     * compile proto file use protoc
     */
    public void compile() {
        Runtime runtime = Runtime.getRuntime();
        try {
            Process process = runtime.exec(getCommand());
            int exitCode = process.waitFor();
            if (exitCode == 0) {
                log.info("[ProtocTool] compile() success and exit normal");
            } else {
                log.error("[ProtocTool] compile() failure, exit code is {}", exitCode);
                throw new RuntimeException(String.format("protoc编译%s失败", this.filePath));
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
package kr.or.connect.controller.api;

import io.swagger.annotations.ApiOperation;
import kr.or.connect.dto.file.FileInfo;
import kr.or.connect.service.file.FileInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;

@RestController
@RequestMapping("/api")
public class FileController {
    @Autowired
    FileInfoService fileInfoService;

    private final String DOWNLOAD_DIR_PATH = "C:/tmp/";

    @ApiOperation(value = "이미지 다운로드하기")
    @GetMapping("/files/{fileId}")
    public void downloadFile(@PathVariable(name = "fileId", required = true)int fileId, HttpServletResponse response){
        FileInfo fileInfo = (FileInfo) fileInfoService.selectFileInfoByFileId(fileId, FileInfo.class);

        if(fileInfo != null){
            String saveFileName = fileInfo.getSaveFileName();
            String contentType = fileInfo.getContentType();

            File downloadFile = new File(DOWNLOAD_DIR_PATH + saveFileName);
            String contentLength = Long.toString(downloadFile.length());

            response.setHeader("Content-Disposition", "attachment; filename=\"" + saveFileName + "\";");
            response.setHeader("Content-Transfer-Encoding", "binary");
            response.setHeader("Content-Type", contentType);
            response.setHeader("Content-Length", contentLength);
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            try(FileInputStream fis = new FileInputStream(downloadFile);
                OutputStream out = response.getOutputStream()){

                int readCount = 0;
                byte[] buffer = new byte[1024];

                while((readCount = fis.read(buffer)) != -1){
                    out.write(buffer, 0, readCount);
                }
            }catch(Exception e){
                e.printStackTrace();
                throw new RuntimeException("fail to download file");
            }
        }
    }
}

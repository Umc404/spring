package com.ezen.spring.handler;

import com.ezen.spring.domain.FileVO;
import com.ezen.spring.repository.FileMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import java.io.File;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Component
@EnableScheduling
public class FileSweeper {
    private final FileMapper fileMapper;
    private final String BASE_PATH = "D:\\umc\\_myProject\\_java\\_fileUpload\\";


    public void fileSweeper() {
        log.info(">>>> FileSweeper Running Start. : {}", LocalDateTime.now());

        List<FileVO> dbList = fileMapper.selectListAllFile();

        List<String> currFiles = new ArrayList<>();
        for(FileVO fvo : dbList) {
            String filePath = fvo.getSaveDir() + File.separator + fvo.getUuid();
            String fileName = fvo.getFileName();
            currFiles.add(BASE_PATH + filePath + "_" + fileName);

            if(fvo.getFileType() > 0) {
                currFiles.add(BASE_PATH + filePath + "_th_" + fileName);
            }

            log.info(">> currFiles > {}",currFiles);

            LocalDate now = LocalDate.now();
            String today = now.toString();
            today = today.replace("-", File.separator);

            File dir = Paths.get(BASE_PATH+today).toFile();

            // listFiles() : 경로 안 모든 파일을 배열로 리턴
            File[] allFileObject = dir.listFiles();
            log.info(">> all file Object > {}", allFileObject.toString());

            for(File file : allFileObject) {
                String storedFileName = file.toPath().toString();
                if(!currFiles.contains(storedFileName)) {
                    file.delete();
                    log.info(">> delete files > {}", storedFileName);
                }
            }
        }
        log.info(">>>> FileSweeper Running End. : {}", LocalDateTime.now());
    }
}

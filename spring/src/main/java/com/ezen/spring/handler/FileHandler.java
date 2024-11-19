package com.ezen.spring.handler;

import com.ezen.spring.domain.FileVO;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.tika.Tika;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
public class FileHandler {

    private String UP_DIR = "D:\\umc\\_myProject\\_java\\_fileUpload\\";

    public List<FileVO> upLoadFiles(MultipartFile[] files) {
      List<FileVO> flist = new ArrayList<>();
        LocalDate date = LocalDate.now();
        // 2024-11-15 => 2024\\11\\15
        String today = date.toString().replace("-", File.separator);

        // D:umc\_myProject\_java\_fileUpload\2024\11\15
        File folders = new File(UP_DIR, today);

        // mkdir : 폴더 1개 / mkdirs : 폴더 여러개
        if(!folders.exists()) {
            folders.mkdirs();
        }

        // FileVO 생성
        for(MultipartFile file : files) {

            // file => name / size
            FileVO fvo = new FileVO();
            fvo.setSaveDir(today);
            fvo.setFileSize(file.getSize());

            // file.name => 경로를 포함하는 경우도 있음. /test/test.txt
            String originalFileName = file.getOriginalFilename();   // getOriginalFilename에 경로가 있을 수 있음
            String onlyFileName = originalFileName
                    .substring(originalFileName.lastIndexOf(File.separator)+1);
            fvo.setFileName(onlyFileName);

            UUID uuid = UUID.randomUUID();
            String uuidStr = uuid.toString();
            fvo.setUuid(uuidStr);

            // fvo 설정 마무리
            // 디스크에 저장
            String fileName = uuidStr+"_"+onlyFileName;
            File storeFile = new File(folders, fileName);

            // 저장
            try {
                file.transferTo(storeFile);
                // 파일 타입 체크 : 그림파일만 썸네일 생성
                if(isImageFile(storeFile)) {
                    fvo.setFileType(1);
                    File thumbnail = new File(folders, uuidStr+"_th_"+onlyFileName);
                    Thumbnails.of(storeFile).size(100,100).toFile(thumbnail);
                }
            } catch (Exception e) {
                log.info("파일 설정 오류");
                e.printStackTrace();
            }

            flist.add(fvo);
        }
         return flist;
    }

    private boolean isImageFile(File file) throws IOException {
        String mimeType = new Tika().detect(file);
        return mimeType.startsWith("image");
    }
}

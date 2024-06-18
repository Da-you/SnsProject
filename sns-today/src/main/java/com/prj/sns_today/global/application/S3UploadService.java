package com.prj.sns_today.global.application;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
public class S3UploadService {

  @Value("${cloud.aws.s3.bucket}")
  private String bucket;
  @Autowired
  @Qualifier("amazonS3Client") // 스프링 컨텍스 빈 충돌로 인하여 특정 빈 주입
  private AmazonS3Client s3client;

  private static final String ARTICLE_DIR = "article/";

  public List<String> uploadArticleImage(MultipartFile[] files)
      throws IOException {
    List<File> uploadFiles = convert(files).orElseThrow(
        () -> new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR));

    return uploadFiles.stream().map(file -> upload(file, ARTICLE_DIR)).collect(Collectors.toList());
  }

  // s3에 파일 업로드
  private String upload(File uploadFile, String dirName) {
    String fileName = dirName + uploadFile.getName();
    String uploadImageUrl = putS3(uploadFile, fileName);
    removeNewFile(uploadFile);
    log.info("file upload success :", uploadImageUrl);
    return uploadImageUrl;
  }

  // s3로 업로드
  private String putS3(File uploadFile, String fileName) {
    s3client.putObject(new PutObjectRequest(bucket, fileName, uploadFile).withCannedAcl(
        CannedAccessControlList.PublicRead));
    return s3client.getUrl(bucket, fileName).toString();
  }

  // 로컬에 저장된 이미지 지우기
  private void removeNewFile(File targetFile) {
    if (targetFile.delete()) {
      log.info("File delete success");
      return;
    }
    log.info("File delete fail");
  }

  // 로컬에 파일 업로드
  private Optional<List<File>> convert(MultipartFile[] files) throws IOException {

    List<File> fileList = new ArrayList<>();
    // 이미지 파일이 여러개 일시 반복문을 통해 fileList 담는다.
    for (MultipartFile file : files) {
      File convertFile = new File(
          System.getProperty("user.dir") + "/" + file.getOriginalFilename());
      if (convertFile.createNewFile()) { // 바로 위에서 지정한 경로에 file 생성, 경로가 잘못되면 생성 불가
        try (FileOutputStream fos = new FileOutputStream(
            convertFile)) { // FileOutputStream 데이터를 파일에 바이트 스트림으로 저장하기 위함
          fos.write(file.getBytes());
        } catch (Exception e) {
          log.error("file local upload fail");
          throw new ApplicationException(ErrorCode.INTERNAL_SERVER_ERROR);
        }

      }
      fileList.add(convertFile);
    }
    return Optional.of(fileList);

  }
}

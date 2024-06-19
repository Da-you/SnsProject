package com.prj.sns_today.global.utils;

import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.util.Arrays;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public class FileNameUtil {

  // Todo : 파일 확장자 확인및 유효성 검사

  public static boolean validImageFileExtension(String fileName) {
    int lastOfIndex = fileName.lastIndexOf(".");
    if (lastOfIndex == -1) {
      throw new ApplicationException(ErrorCode.IMAGE_FILE_EXCEPTION);
    }

    String extension = fileName.substring(lastOfIndex + 1).toLowerCase();
    List<String> allowedExtension = Arrays.asList("jpg", "jpeg", "png", "gif");

    if (!allowedExtension.contains(extension)) {
      throw new ApplicationException(ErrorCode.IMAGE_FILE_EXCEPTION);
    }
    return true;
  }
}

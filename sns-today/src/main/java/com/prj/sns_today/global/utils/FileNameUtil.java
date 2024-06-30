package com.prj.sns_today.global.utils;

import com.prj.sns_today.global.exception.ApplicationException;
import com.prj.sns_today.global.exception.ErrorCode;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileNameUtil {

  // Todo : 파일 확장자 확인및 유효성 검사
  public static boolean validImageFileExtension(String fileName) {
    int lastOfIndex = fileName.lastIndexOf("."); // 문자열에서 마지막에서 '.' 을 찾는다.
    if (lastOfIndex == -1) { // image.jpg 라면 lastOfIndex == 5 이며 만약에 '.' 을 찾지 못한다면 -1을 반환한다.
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

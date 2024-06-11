package com.prj.sns_today.global.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

// 제네릭을 이용한 공통 응답
@Getter
@AllArgsConstructor
public class ApiResponse<T> {

  /**
   * api의 실행 결과는 성공하여 응답이 내려올 경우도 있고 실패하여 내려오지 않을 경우도 있다. 실패시에는 그에 해당하는 에러 코드를 내려줘야 하는데 응답이 각각 다를경우
   * 프론트에서 파싱하기가 어렵다. 획일화된 응답 형태를 만들어 응답 형태의 필요에 따라서 응답을 넣어주면 협업에 도움이 된다
   */
  private String resultCode; // 결과에 대한 응답 코드
  private T result; // 결과의 응답 타입이 모두 다를수 있기에 제네릭 타입으로 설정

  // 에러의 경우 result가 없기에 void로 설정
  public static ApiResponse<Void> error(String errorCode) {
    return new ApiResponse<>(errorCode, null);
  }

  public static <T> ApiResponse<T> success(T result) {
    return new ApiResponse<>("Success", result);
  }

  public String toStream() {
    if (result == null) {
      return "{" +
          "\"resultCode\":" + "\"" + resultCode + "\"," +
          "\"result\":" + null + "}";
    }
    return "{" +
        "\"resultCode\":" + "\"" + resultCode + "\"," +
        "\"result\":" + "\"" + result + "\"" + "}";
  }
}

package kr.co.zeroPie.oauth2;

import jakarta.servlet.http.HttpServletRequest;

public interface OAuth2MemberInfo {

     //공급자 ex) google, facebook
    String getName(); //사용자 이름 ex) 홍길동
    String getEmail();

}

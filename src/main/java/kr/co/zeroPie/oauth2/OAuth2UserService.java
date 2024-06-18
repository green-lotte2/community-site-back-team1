package kr.co.zeroPie.oauth2;

import jakarta.servlet.http.HttpServletRequest;

import kr.co.zeroPie.entity.Stf;
import kr.co.zeroPie.repository.StfRepository;
import kr.co.zeroPie.security.MyUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class OAuth2UserService extends DefaultOAuth2UserService {

    private final StfRepository userRepository;
    private final HttpServletRequest request;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        log.info("loadUser...1");
        //String accessToken = userRequest.getAccessToken().getTokenValue();
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        log.info("loadUser...2 : " + registrationId);

        OAuth2User oauth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oauth2User.getAttributes();
        OAuth2MemberInfo userInfo = null;

        if (registrationId.equals("google")) {
            userInfo = new GoogleInfo(attributes);
        } else if (registrationId.equals("kakao")) {
            userInfo = new KakaoInfo(attributes);
        }

        log.info("loadUser...3 : " + userInfo);

        // 사용자 확인 및 회원가입 처리
        String email = userInfo.getEmail();
        String uid = email.substring(0, email.lastIndexOf("@"));
        String name = userInfo.getName();

        // 소셜 로그인시 이메일 중복되는 오류 수정
        Optional<Stf> optUser = userRepository.findByStfEmail(email);
        Stf user=null;

        if(optUser.isEmpty()){
            user=Stf.builder()
                    .stfNo(uid)
                    .stfEmail(email)
                    .stfName(name)
                    .build();

            userRepository.save(user);
        }else{
            user=optUser.get();
        }
        return MyUserDetails.builder()
                .stf(user)
                .build();
    }
}

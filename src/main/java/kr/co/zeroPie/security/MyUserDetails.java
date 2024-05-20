package kr.co.zeroPie.security;

import kr.co.lotteon.entity.Seller;
import kr.co.lotteon.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@Slf4j
@Getter
@Setter
@ToString
@Builder
public class MyUserDetails implements UserDetails, OAuth2User {
    // User Entity
    private User user;
    private Seller seller;


    @Override
    public Map<String, Object> getAttributes() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }

    // getAuthorities -> 권한 목록
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 계정이 갖는 권한 목록
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority(user.getUserRole()));
        log.info("authorities : " + authorities.toString());
        return authorities;
    }

    @Override
    public String getPassword() {
        log.info("getPassword : " + user.getUserPw());
        return user.getUserPw();
    }

    @Override
    public String getUsername() {
        return user.getUserId();
    }

    @Override
    public boolean isAccountNonExpired() {
        // 계정 만료 여부(true : 만료 안됨 / false : 만료)
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        // 계정 잠김 여부 (true : 잠김 안됨 / false : 잠김)
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        // 비밀번호 만료 여부 (true : 만료 안됨 / false : 만료)
        return true;
    }

    @Override
    public boolean isEnabled() {
        // 계정 활성화 여부 (true : 활성화 / false : 비활성화)
        return true;
    }

}

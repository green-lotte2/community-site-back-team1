package kr.co.zeroPie.security;

import kr.co.lotteon.entity.User;
import kr.co.lotteon.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@AllArgsConstructor
@Service
public class SecurityUserService implements UserDetailsService {
    // 주입
    private final UserRepository userRepository;

    // 인증 수행
    @Override
    public UserDetails loadUserByUsername(String user_name) throws UsernameNotFoundException {
        log.info("SecurityUserService : "+user_name);
        Optional<User> result = userRepository.findById(user_name);

        UserDetails userDetails = null;

        if(!result.isEmpty()){
            // 해당하는 사용자가 존재하면 인증 객체 생성
            User user = result.get();
            log.info("SecurityUserService user: "+user);
            userDetails = MyUserDetails.builder().user(user).build();
            log.info(userDetails.toString());

        }
        // Security ContextHolder에 저장
        return userDetails;
    }


}

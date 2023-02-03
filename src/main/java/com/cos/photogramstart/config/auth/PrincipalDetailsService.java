package com.cos.photogramstart.config.auth;


import com.cos.photogramstart.domain.user.User;
import com.cos.photogramstart.domain.user.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrincipalDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;


    /**
     * 패스워드는 알아서 check
     * username 의 정보가 DB에 있는지 확인 하고 있다면 유저 정보를 return 하여 세션을 만듬
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userEntity = usersRepository.findByUsername(username);

        if (userEntity == null) {
            return null;
        } else {
            return new PrincipalDetails(userEntity); // 리턴이 되면 실제 세션을 가지고 있음.
        }
    }
}

package com.cos.photogramstart.config.auth;

import com.cos.photogramstart.domain.user.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
@Data
public class PrincipalDetails implements UserDetails {

    private static final long serialVersionUID = 1L;

    private User user;

    public PrincipalDetails(User user) {
        this.user = user;
    }

    /**
     * 권한 : 한개가 아닐수 있기에 컬렉션으로 정의 되어 있다
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collector = new ArrayList<>();
        collector.add(()-> user.getRole());
        return collector;
    }

    @Override
    public String getPassword() {

        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() { // 계정만료 여부
        return true;
    } // 계정 만료 여부 t : 만료안됨 f : 만료

    @Override
    public boolean isAccountNonLocked() { // 계정 Lock 여부
        return true;
    } // 계정 lock t : 잠기지 않음 f : 잠김

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    } // 비밀번호만료 t : 만료 안됨 f : 만료

    @Override
    public boolean isEnabled() {
        return true;
    } // 계정이 사용 가능한 계정인지 t : 만료 안됨 f : 만료
}

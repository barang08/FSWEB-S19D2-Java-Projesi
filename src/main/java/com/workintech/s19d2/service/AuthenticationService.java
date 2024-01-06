package com.workintech.s19d2.service;

import com.workintech.s19d2.dao.MemberRepository;
import com.workintech.s19d2.dao.RoleRepository;
import com.workintech.s19d2.entity.Member;
import com.workintech.s19d2.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.PasswordAuthentication;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class AuthenticationService {
    private final MemberRepository memberRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public Member register(String email,String password){
        Optional<Member> byEmail= memberRepository.findByEmail(email);
        if(byEmail.isPresent()){
            throw new RuntimeException("email is not valid"+email);
        }

        String encodedPassword=passwordEncoder.encode(password);
     Role userRole = roleRepository.findByAuthority("USER").get();
        List<Role> roleList = new ArrayList<>();
        roleList.add(userRole);

        Member member = new Member();
        member.setPassword(encodedPassword);
        member.setEmail(email);
        member.setAuthorities(roleList);
        return memberRepository.save(member);
    }
}

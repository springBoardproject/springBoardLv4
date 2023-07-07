package com.sparta.board.service;

import com.sparta.board.dto.LoginRequestDto;
import com.sparta.board.dto.SignupRequestDto;
import com.sparta.board.dto.StatusCodesResponseDto;
import com.sparta.board.entity.User;
import com.sparta.board.entity.UserRoleEnum;
import com.sparta.board.jwt.JwtUtil;
import com.sparta.board.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public StatusCodesResponseDto signup(SignupRequestDto requestDto) {
        String username = requestDto.getUsername();
        String password = passwordEncoder.encode(requestDto.getPassword());

        // 회원 중복 확인
        Optional<User> checkUsername = userRepository.findByUsername(username);
        if (checkUsername.isPresent()) {
            throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
        }

        // 사용자 ROLE 확인
        UserRoleEnum role = UserRoleEnum.USER;
        if (requestDto.isAdmin()) {
            if (!jwtu.equals(requestDto.getAdminToken())) {
                throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
            }
            role = UserRoleEnum.ADMIN;
        }

        // 사용자 등록
        User user = new User(username, password, role);
        userRepository.save(user);

        // return
        StatusCodesResponseDto responseDto = new StatusCodesResponseDto(HttpStatus.CREATED.value(), "회원가입 성공");
        return responseDto;
    }

    public StatusCodesResponseDto login(LoginRequestDto requestDto, HttpServletResponse jwtResponse) {
        String username = requestDto.getUsername();
        String password = requestDto.getPassword();

        // 사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(() -> //Optional<T>에 orElseThrow 메서드는 결과값이 T로 나온다 (User)
                new IllegalArgumentException("등록된 사용자가 없습니다."));
        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호 불일치");
        }

        // Jwt 토큰 생성, response에 넣기
        String token = jwtUtil.createToken(user.getUsername());
        // Jwt 쿠키 저장
        jwtUtil.addJwtToCookie(token, jwtResponse);

        StatusCodesResponseDto responseDto = new StatusCodesResponseDto(HttpStatus.OK.value(), "로그인 성공");

        return responseDto;
    }
}

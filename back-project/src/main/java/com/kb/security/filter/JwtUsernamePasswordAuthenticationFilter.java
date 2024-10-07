package com.kb.security.filter;


import com.kb.kakao.KaKaoLoginService;
import com.kb.member.dto.KakaoLoginDTO;
import com.kb.member.dto.LoginDTO;
import com.kb.member.dto.Member;
import com.kb.member.service.MemberService;
import com.kb.security.handler.LoginFailureHandler;
import com.kb.security.handler.LoginSuccessHandler;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    @Setter(onMethod = @__({@Autowired}))
    private MemberService service;

    @Setter(onMethod = @__({@Autowired}))
    private KaKaoLoginService kaKaoLoginService;

    @Setter(onMethod = @__({@Autowired}))
    private UserDetailsService userDetailsService;


    // 스프링 생성자 주입을 통해 전달
    public JwtUsernamePasswordAuthenticationFilter(
            AuthenticationManager authenticationManager,
            LoginSuccessHandler loginSuccessHandler,
            LoginFailureHandler loginFailureHandler ) {
        super(authenticationManager);

        setFilterProcessesUrl("/api/auth/login");		          // POST 로그인 요청 url
        setAuthenticationSuccessHandler(loginSuccessHandler);	// 로그인 성공 핸들러 등록
        setAuthenticationFailureHandler(loginFailureHandler);  // 로그인 실패 핸들러 등록
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        // 카카오 로그인인지 확인하기 위한 파라미터 검사
        String kakaoCode = request.getParameter("code");

        if (kakaoCode != null && !kakaoCode.isEmpty()) {
            // 1. 카카오 로그인 처리
            return handleKakaoLogin(request, kakaoCode);
        } else {
            // 2. 자체 로그인 처리
            return handleDefaultLogin(request);
        }
    }

    // 카카오 로그인 처리를 위한 메서드
    private Authentication handleKakaoLogin(HttpServletRequest request, String kakaoCode) {
        String enrollUrl = "http://localhost:8081/auth/kakaologin";
        String token = kaKaoLoginService.getToken(kakaoCode, enrollUrl);
        Map<String, Object> map = kaKaoLoginService.getUserInfo(token);
        String kakaoId = (String) map.get("id");
        Member member = service.getMemberByKakaoId(kakaoId);

        if (member == null) {
            throw new AuthenticationException("카카오 로그인 실패: 사용자 정보가 없습니다.") {};
        }

        // 사용자 정보를 바탕으로 UsernamePasswordAuthenticationToken 생성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(member.getId(), null);

        // AuthenticationManager를 통해 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);
    }

    // 자체 로그인 처리를 위한 메서드
    private Authentication handleDefaultLogin(HttpServletRequest request) {
        // 요청 BODY의 JSON에서 id, password 추출하여 LoginDTO 생성
        LoginDTO login = LoginDTO.of(request);

        // 인증 토큰(UsernamePasswordAuthenticationToken) 구성
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(login.getId(), login.getPassword());

        // AuthenticationManager를 통해 인증 요청
        return getAuthenticationManager().authenticate(authenticationToken);
    }



}

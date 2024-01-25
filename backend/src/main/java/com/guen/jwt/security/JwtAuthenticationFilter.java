package com.guen.jwt.security;

import com.guen.jwt.exception.ExpiredRefreshJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Order(0)
@RequiredArgsConstructor
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenProvider tokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException{
        try {
                if(request.getRequestURI().equals("/api/sign-up") || request.getRequestURI()=="/api/sign-in"){
                    filterChain.doFilter(request, response);
                }
                String accessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);
                User user = parseUserSpecification(accessToken);
                AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, accessToken, user.getAuthorities());
                authenticated.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticated);
        }
        catch (ExpiredJwtException e) {
            try{
                reissueAccessToken(request, response, e);
            }catch (ExpiredJwtException eee){
                logger.info("액세스 토큰 만료");
                //throw new ExpiredJwtException(null,null,"액세스 토큰 만료");
                request.setAttribute("exception", new ExpiredJwtException(null,null,"액세스 토큰 만료"));
            }catch (ExpiredRefreshJwtException ee){
                logger.info("리프레쉬 토큰에 문제가 있음");
                //throw new ExpiredRefreshJwtException("리프레쉬 토큰 만료");
                request.setAttribute("exception", new ExpiredRefreshJwtException("리프레쉬 토큰 만료"));
            } catch (Exception ex) {
                logger.info(">>>>>>>>>>>>>>>>>>>>");
                //throw new RuntimeException(ex);
                request.setAttribute("exception", new RuntimeException(ex));
            }
        }
        catch (Exception e) {
            logger.info("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
            request.setAttribute("exception", e);
        }

        filterChain.doFilter(request, response);
    }

    // header 중 authorization의 값을 찾아서 Bearer로 시작하는지 확인
    // access-token을 추출한다. 없으면 null 반환
    private String parseBearerToken(HttpServletRequest request, String headerName) {
        return Optional.ofNullable(request.getHeader(headerName))
                .filter(token -> token.substring(0, 7).equalsIgnoreCase("Bearer "))
                .map(token -> token.substring(7))
                .orElse(null);
    }

    //access-token에서 subject를 추출
    //추출한 정보를 바탕으로 spring security User객체를 반환한다.
    private User parseUserSpecification(String token) {
        String[] split = Optional.ofNullable(token)
                .filter(subject -> subject.length() >= 10)
                .map(tokenProvider::validateTokenAndGetSubject)
                .orElse("anonymous:anonymous")
                .split(":");

        return new User(split[0], "", List.of(new SimpleGrantedAuthority(split[1])));
    }

    private void reissueAccessToken(HttpServletRequest request, HttpServletResponse response, Exception exception) throws ExpiredJwtException,ExpiredRefreshJwtException,Exception{
        try {
            String refreshToken = parseBearerToken(request, "Refresh-Token");
            if (refreshToken == null) {
                logger.info("xxxxxxxxxxxxxxx");
                throw new ExpiredJwtException(null,null,"액세스 토큰 만료");
            }
            String oldAccessToken = parseBearerToken(request, HttpHeaders.AUTHORIZATION);
            tokenProvider.validateRefreshToken(refreshToken, oldAccessToken);
            String newAccessToken = tokenProvider.recreateAccessToken(oldAccessToken);
            User user = parseUserSpecification(newAccessToken);
            AbstractAuthenticationToken authenticated = UsernamePasswordAuthenticationToken.authenticated(user, newAccessToken, user.getAuthorities());
            authenticated.setDetails(new WebAuthenticationDetails(request));
            SecurityContextHolder.getContext().setAuthentication(authenticated);

            response.setHeader("newToken", newAccessToken);
        }
        catch (ExpiredJwtException e){
            //logger.info("액세스 토큰 만료");
            throw new ExpiredJwtException(null,null,"액세스 토큰 만료");
        }catch (ExpiredRefreshJwtException ee){
            //logger.info("리프레쉬 토큰에 문제가 있음");
            throw new ExpiredRefreshJwtException("리프레쉬 토큰 만료");
        }
        catch (Exception e) {
            throw new Exception("");
            //request.setAttribute("exception", e);
        }
    }
}

package com.prj.sns_today.global.filter;


import com.prj.sns_today.domain.users.application.UserService;
import com.prj.sns_today.domain.users.domain.User;
import com.prj.sns_today.global.utils.JwtTokenUtils;
import java.io.IOException;
import java.util.List;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.filter.OncePerRequestFilter;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

  private final String key;
  private final UserService userService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    // get header
    final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
    if (header == null || !header.startsWith("Bearer ")) {
      log.error("Error occurs while getting header. header is null or invalid");
      filterChain.doFilter(request, response);
      return;
    }
    try {
      final String token = header.split(" ")[1].trim();
      // Todo : check token is valid

      if (JwtTokenUtils.isExpired(token, key)) {
        log.error("token is expired");
        filterChain.doFilter(request, response);
        return;
      }


      // Todo : get username from token
      String username = JwtTokenUtils.getUsername(token, key);

      // Todo : check the user is valid
      User user = userService.loadByUsername(username);

      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          user, null, List.of(new SimpleGrantedAuthority(user.getRole().toString()))
      );
      authenticationToken.setDetails(new WebAuthenticationDetails(request));
      SecurityContextHolder.getContext().setAuthentication(authenticationToken);
    } catch (RuntimeException e) {
      log.error("Error occurs while validating. {}", e.toString());
      filterChain.doFilter(request, response);
      return;
    }
    filterChain.doFilter(request, response);
  }
}

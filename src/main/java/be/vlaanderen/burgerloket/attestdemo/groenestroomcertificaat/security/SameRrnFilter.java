package be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.security;

import be.vlaanderen.burgerloket.attestdemo.groenestroomcertificaat.exception.AccessDeniedException;
import lombok.extern.apachecommons.CommonsLog;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Pattern;

@CommonsLog
@Component
public class SameRrnFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()
                && !(authentication instanceof AnonymousAuthenticationToken)
                && authentication.getPrincipal() instanceof Jwt) {
            Jwt jwt = (Jwt) authentication.getPrincipal();
            String rrn = jwt.getClaim("rrn");
            checkIfUserAsksInfoAboutHimself(getInszFromPath(request), rrn);
        }
        filterChain.doFilter(request, response);
    }

    private String getInszFromPath(HttpServletRequest request) {
        String url = request.getServletPath();
        String insz = stripInsz(url);
        if (StringUtils.isEmpty(insz)) {
            url = request.getPathInfo();
            insz = stripInsz(url);
        }
        return insz;
    }

    private void checkIfUserAsksInfoAboutHimself(String insz, String rrnInClaim) {
        if (!StringUtils.isEmpty(insz)) {
            if (!insz.equals(rrnInClaim)) {
                final String message = String.format("Profiel met insz '%s' vraagt gegevens op voor persoon met insz '%s'", rrnInClaim, insz);
                log.error(message);
                throw new AccessDeniedException(message);
            }
        }
    }

    private String stripInsz(String path) {
        if (path != null) {
            return Arrays.stream(path.split("/"))
                    .filter(s -> Pattern.compile("[\\d]{11}$").asPredicate().test(s))
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }
}

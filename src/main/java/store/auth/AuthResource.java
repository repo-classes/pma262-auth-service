package store.auth;

import java.time.Duration;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import store.account.AccountOut;

@RestController
public class AuthResource implements AuthController {

    @Autowired
    private AuthService authService;

    @Override
    public ResponseEntity<Void> create(RegisterIn in) {
        final Register register = AuthParser.to(in);
        authService.create(register);
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> login(LoginIn in) {
        final String token = authService.login(in.email(), in.password());
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.SET_COOKIE,
                buildTokenCookie(AuthController.AUTH_COOKIE_TOKEN, token, authService.getDuration()).toString()
            )
            .build();
    }

    @Override
    public ResponseEntity<AccountOut> me(String idAccount) {
        return ResponseEntity.ok(authService.me(idAccount));
    }

    @Override
    public ResponseEntity<Void> logout() {
        return ResponseEntity
            .ok()
            .header(
                HttpHeaders.SET_COOKIE,
                buildTokenCookie(
                    AuthController.AUTH_COOKIE_TOKEN, 
                    null, 
                    0l
                ).toString()
            )
            .build();
    }

    @Override
    public ResponseEntity<Void> healhCheck() {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Map<String, String>> solveToken(TokenOut map) {
        return ResponseEntity.ok(authService.solve(map.token()));
    }

    private ResponseCookie buildTokenCookie(String alias, String content, Long duration) {
        return ResponseCookie.from(alias, content)
            .httpOnly(authService.getHttpOnly())
            .sameSite("None")
            .secure(true)
            .path("/")
            .maxAge(Duration.ofMillis(duration))
            .build();
    }

}

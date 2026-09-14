package store.auth;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import store.account.AccountController;
import store.account.AccountIn;
import store.account.AccountOut;

@Service
public class AuthService {

    @Autowired
    private AccountController accountController;

    @Autowired
    private JwtService jwtService;

    @Value("${store.jwt.duration}")
    private Long duration;

    @Value("${store.jwt.httpOnly}")
    private Boolean httpOnly;

    public void create(Register register) {

        accountController.create(
            AccountIn.builder()
                .name(register.name())
                .email(register.email())
                .password(register.password())
                .build()   
        );

    }

    public String login(String email, String password) {
        final AccountOut account = accountController.login(
            AccountIn.builder().email(email).password(password).build()
        ).getBody();
        return jwtService.generate(account, getDuration());
    }

    public long getDuration() {
        return duration;
    }

    public boolean getHttpOnly() {
        return httpOnly;
    }

    public Map<String, String> solve(final String jwt) {
        return Map.of(
            "idAccount", jwtService.getId(jwt)
        );
    }

    public AccountOut me(String idAccount) {
        return accountController.findById(idAccount).getBody();
    }

}

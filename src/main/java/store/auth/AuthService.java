package store.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import store.account.AccountController;
import store.account.AccountIn;

@Service
public class AuthService {

    @Autowired
    private AccountController accountController;

    public void create(Register register) {

        accountController.create(
            AccountIn.builder()
                .name(register.name())
                .email(register.email())
                .password(register.password())
                .build()   
        );

    }

}

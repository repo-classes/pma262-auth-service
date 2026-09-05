package store.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

@Controller
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
    public ResponseEntity<Void> login() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'login'");
    }

    @Override
    public ResponseEntity<Void> me() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'me'");
    }

    @Override
    public ResponseEntity<Void> logout() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'logout'");
    }

    @Override
    public ResponseEntity<Void> healhCheck() {
        return ResponseEntity.ok().build();
    }
    
}

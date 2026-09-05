package store.auth;

public final class AuthParser {

    public static Register to(RegisterIn in) {
        return in == null ? null :
            Register.builder()
                .name(in.name())
                .email(in.email())
                .password(in.password())
                .build();
    }
    
}

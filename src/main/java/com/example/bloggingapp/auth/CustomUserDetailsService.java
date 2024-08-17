package com.example.bloggingapp.auth;

import com.example.bloggingapp.entites.User;
import com.example.bloggingapp.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepo userRepo;

    public CustomUserDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException(String.format("Username %s not found", username)));

        CustomUserDetails customUserDetails = Mapper.toCustomUserDetails(user);

        try {
            new AccountStatusUserDetailsChecker().check(customUserDetails);
        } catch (AccountStatusException e) {
            log.error("Could not authenticate user", e);
            throw new RuntimeException(e.getMessage());
        }

        return customUserDetails;
    }
}








}
//
//    private final UserRepo userRepo;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        //loading user from database by username
//        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "email :" + username, 0));
//        return user;
//    }
//}

///here jwt Security is not correct.it doesn't work.
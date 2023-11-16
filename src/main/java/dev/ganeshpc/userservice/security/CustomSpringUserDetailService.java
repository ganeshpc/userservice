package dev.ganeshpc.userservice.security;

import java.util.Optional;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dev.ganeshpc.userservice.models.User;
import dev.ganeshpc.userservice.repositories.UserRepository;

@Service
public class CustomSpringUserDetailService implements UserDetailsService {

    private UserRepository userRepository;

    public CustomSpringUserDetailService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findByEmailId(username);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("User with emailId does not exist: " + username);
        }

        User user = userOptional.get();

        return new CustomSpringUserDetails(user);
    }
    
}

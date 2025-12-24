package com.paras.tradeflow.serviceimpl;

import com.paras.tradeflow.dto.UserRegisterRequest;
import com.paras.tradeflow.dto.UserResponse;
import com.paras.tradeflow.entity.User;
import com.paras.tradeflow.repository.UserRepository;
import com.paras.tradeflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");
        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        User savedUser = userRepository.save(user);

        return new UserResponse(savedUser.getId(),savedUser.getName(),savedUser.getEmail());


    }
}

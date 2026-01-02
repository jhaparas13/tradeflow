package com.paras.tradeflow.serviceimpl;
import com.paras.tradeflow.dto.AuthResponse;
import com.paras.tradeflow.dto.UserLoginRequest;
import com.paras.tradeflow.dto.UserRegisterRequest;
import com.paras.tradeflow.dto.UserResponse;
import com.paras.tradeflow.entity.Role;
import com.paras.tradeflow.entity.User;
import com.paras.tradeflow.repository.UserRepository;
import com.paras.tradeflow.security.jwt.JwtService;
import com.paras.tradeflow.service.RefreshTokenService;
import com.paras.tradeflow.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final RefreshTokenService refreshTokenService;


    @Override
    public UserResponse register(UserRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail()))
            throw new RuntimeException("Email already exists");

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.CUSTOMER);
        userRepository.save(user);

        return new UserResponse(user.getId(),user.getName(),user.getEmail());


    }

    @Override
    public AuthResponse login(UserLoginRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid Email"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid Password");

        String accessToken = jwtService.generateToken(user);
        String refreshToken = refreshTokenService.create(user).getToken();
        return new AuthResponse(accessToken,refreshToken);
    }
}

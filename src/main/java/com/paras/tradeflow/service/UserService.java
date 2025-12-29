package com.paras.tradeflow.service;

import com.paras.tradeflow.dto.AuthResponse;
import com.paras.tradeflow.dto.UserLoginRequest;
import com.paras.tradeflow.dto.UserRegisterRequest;
import com.paras.tradeflow.dto.UserResponse;

public interface UserService {
   UserResponse register(UserRegisterRequest request);
   AuthResponse login (UserLoginRequest request);
}

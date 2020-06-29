package com.baizhi.travels.service;

import com.baizhi.travels.entity.User;

public interface UserService {



    User login(User user);

    void register(User user);
}

package com.example.useraddresses.dao;

import com.example.useraddresses.domain.User;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.jpa.domain.Specification;

// TODO: 14.02.2022 fill
public interface UserSpecification {
    Specification<User> getByFilter(final UserQueryFilter filter);
}

package com.example.useraddresses.dao;

import com.example.useraddresses.domain.UserProfile;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.jpa.domain.Specification;

/**
 * Specification for searching users by filter.
 *
 * @author Alexandr Yefremov
 * @see UserQueryFilter
 */
public interface UserSpecification {
    Specification<UserProfile> getByFilter(final UserQueryFilter filter);
}

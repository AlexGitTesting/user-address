package com.example.useraddresses.dao;

import com.example.useraddresses.domain.*;
import com.example.useraddresses.dto.UserQueryFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.util.StringUtils.hasText;

/**
 * Implementation of {@link UserSpecification}.
 *
 * @author Alexandr Yefremov
 */
@Component
public class UserSpecificationImpl implements UserSpecification {
    @Override
    public Specification<UserProfile> getByFilter(UserQueryFilter filter) {
        return ((root, query, cb) -> {
            final List<Predicate> predicates = new ArrayList<>();
            applyOrder(root, query, cb, filter.isSortingAscending());
            addPredicateIfExists(predicates, filter.getFirstname().orElse(""), root.get(UserProfile_.firstname), cb);
            addPredicateIfExists(predicates, filter.getLastname().orElse(""), root.get(UserProfile_.lastname), cb);
            addPredicateIfExists(predicates, filter.getPatronymic().orElse(""), root.get(UserProfile_.patronymic), cb); // TODO: 14.02.2022 clean
//            final Join<Address, Country> countryJoin;
//            if (!query.getResultType().equals(Long.class)) {
//                final Fetch<UserProfile, Address> addresses = root.fetch(UserProfile_.addresses);
//                final Fetch<Address, Country> country = addresses.fetch(Address_.country);
//                //noinspection unchecked
//                addressJoin = (Join<UserProfile, Address>) addresses;
//                //noinspection unchecked
//                countryJoin = (Join<Address, Country>) country;
//            } else {
//                addressJoin = root.join(UserProfile_.addresses, JoinType.INNER);
//                countryJoin = addressJoin.join(Address_.country);
//            }
//            addPredicateIfExists(predicates, filter.getCity().orElse(""), addressJoin.get(Address_.city), cb);
//            filter.getCountryId().ifPresent(countryId -> predicates.add(cb.equal(countryJoin.get(Country_.id), countryId)));
            if (filter.getCity().isPresent()) {
                final Join<UserProfile, Address> addressJoin = root.join(UserProfile_.addresses, JoinType.INNER);
                addPredicateIfExists(predicates, filter.getCity().orElse(""), addressJoin.get(Address_.city), cb);
            }
            if (filter.getCountryId().isPresent()) {
                final Join<Address, Country> countryJoin = root.join(UserProfile_.addresses, JoinType.INNER).join(Address_.country);
                predicates.add(cb.equal(countryJoin.get(Country_.id), filter.getCountryId()));
            }
            return predicates.isEmpty()
                    ? null
                    : cb.and(predicates.toArray(Predicate[]::new));
        });
    }

    private void addPredicateIfExists(final List<Predicate> predicates, final String field, Path<String> path, final CriteriaBuilder cb) {
        if (hasText(field)) predicates.add(cb.like(cb.lower(path), field.strip().toLowerCase() + "%"));
    }

    private void applyOrder(Root<UserProfile> root, CriteriaQuery<?> query, CriteriaBuilder cb, boolean isSortingAscending) {
        if (isSortingAscending) {
            query.orderBy(
                    cb.asc(root.get(UserProfile_.firstname)),
                    cb.asc(root.get(UserProfile_.lastname)),
                    cb.asc(root.get(UserProfile_.patronymic))
            );
        } else {
            query.orderBy(
                    cb.desc(root.get(UserProfile_.firstname)),
                    cb.desc(root.get(UserProfile_.lastname)),
                    cb.desc(root.get(UserProfile_.patronymic)));
        }

    }
}

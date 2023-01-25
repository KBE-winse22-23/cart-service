package com.onlineshop.cart.core.domain.service.interfaces;


import com.onlineshop.cart.core.domain.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}

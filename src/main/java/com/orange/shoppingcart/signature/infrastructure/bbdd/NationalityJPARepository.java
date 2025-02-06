package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.infrastructure.bbdd.model.Nationality;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NationalityJPARepository extends  JpaRepository<Nationality, String> {

    Optional<Nationality> findByNationality(String nationality);
}

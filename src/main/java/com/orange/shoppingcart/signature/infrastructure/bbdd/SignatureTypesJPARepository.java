package com.orange.shoppingcart.signature.infrastructure.bbdd;

import com.orange.shoppingcart.signature.infrastructure.bbdd.model.SignatureEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SignatureTypesJPARepository extends JpaRepository<SignatureEntity, Long> {

    @Query("SELECT r FROM SignatureEntity r " +
            "WHERE r.nationality = :nationality " +
            "AND (r.commercialAct = 'TODOS' OR r.commercialAct IN :commercialAct) " +
            "AND (r.documentType = 'TODOS' OR r.documentType = :documentType) " +
            "AND (r.segment = 'TODOS' OR r.segment = :segment)")
    List<SignatureEntity> findByFilters(
            @Param("nationality") String nationality,
            @Param("commercialAct") List<String> commercialAct,
            @Param("documentType") String documentType,
            @Param("segment") String segment);
}

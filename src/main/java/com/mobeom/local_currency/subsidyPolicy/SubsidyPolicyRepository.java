package com.mobeom.local_currency.subsidyPolicy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubsidyPolicyRepository extends JpaRepository<SubsidyPolicy,Long>,ISubsidyPolicyRepository {
}

package com.mobeom.local_currency.subsidyPolicy;


import com.mobeom.local_currency.proxy.Box;
import com.mobeom.local_currency.proxy.JpaService;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Component
interface SubsidyPolicyService extends JpaService<SubsidyPolicy> {
}

@Service
public class SubsidyPolicyServiceImpl implements SubsidyPolicyService{
    private final SubsidyPolicyRepository repository;
    private final Box<List<SubsidyPolicy>> subsidyPolicies;

    public SubsidyPolicyServiceImpl(SubsidyPolicyRepository repository, Box<List<SubsidyPolicy>> subsidyPolicies) {
        this.repository = repository;
        this.subsidyPolicies = subsidyPolicies;
    }

    @Override
    public Optional<SubsidyPolicy> findById(String id) {
        return repository.findById(Long.parseLong(id));
    }

    @Override
    public Iterable<SubsidyPolicy> findAll() {
        return repository.findAll();
    }

    @Override
    public int count() {
        return (int)repository.count();
    }

    @Override
    public void delete(String id) {
        repository.delete(findById(id).orElse(new SubsidyPolicy()));
    }

    @Override
    public boolean exists(String id) {
        return repository.existsById(Long.parseLong(id));
    }
}

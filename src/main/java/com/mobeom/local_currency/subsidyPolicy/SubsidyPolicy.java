package com.mobeom.local_currency.subsidyPolicy;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@Table(name="subsidypolicy")
public class SubsidyPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subsidy_policy_id", nullable = false) private Long subsidyPolicyId;
    @Column(name = "policy_name", nullable = false) private String policyName;
    @Column(name = "condi_age", nullable = false) private int condiAge;
    @Column(name = "condi_resd_duration", nullable = false) private int condiResdDuration;
    @Column(name = "condi_children_age", nullable = false) private int condiChildrenAge;
    @Column(name = "policy_desc", nullable = false) private String policyDesc;
    @Column(name = "policy_url", nullable = false) private String policyUrl;

    public SubsidyPolicy(){}
    @Builder
    public SubsidyPolicy(
            String policyName,
            int condiAge,
            int condiResdDuration,
            int condiChildrenAge,
            String policyDesc,
            String policyUrl
    ){
        this.policyName = policyName;
        this.condiAge = condiAge;
        this.condiResdDuration = condiResdDuration;
        this.condiChildrenAge = condiChildrenAge;
        this.policyDesc = policyDesc;
        this.policyUrl = policyUrl;
    }


}

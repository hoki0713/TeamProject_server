package com.mobeom.local_currency.subsidyPolicy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="subsidypolicy")
public class SubsidyPolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subsidy_policy_id", nullable = false) private Long subsidyPolicyId;
    @Column(name = "condition_age", nullable = false) private int conditionAge;
    @Column(name = "condition_income", nullable = false) private int conditionIncome;
    @Column(name = "condition_dependents", nullable = false) private int conditionDependents;

}

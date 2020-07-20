package com.mobeom.local_currency.subsidyPolicy;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QSubsidyPolicy is a Querydsl query type for SubsidyPolicy
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QSubsidyPolicy extends EntityPathBase<SubsidyPolicy> {

    private static final long serialVersionUID = -264111423L;

    public static final QSubsidyPolicy subsidyPolicy = new QSubsidyPolicy("subsidyPolicy");

    public final NumberPath<Integer> conditionAge = createNumber("conditionAge", Integer.class);

    public final NumberPath<Integer> conditionDependents = createNumber("conditionDependents", Integer.class);

    public final NumberPath<Integer> conditionIncome = createNumber("conditionIncome", Integer.class);

    public final NumberPath<Long> subsidyPolicyId = createNumber("subsidyPolicyId", Long.class);

    public QSubsidyPolicy(String variable) {
        super(SubsidyPolicy.class, forVariable(variable));
    }

    public QSubsidyPolicy(Path<? extends SubsidyPolicy> path) {
        super(path.getType(), path.getMetadata());
    }

    public QSubsidyPolicy(PathMetadata metadata) {
        super(SubsidyPolicy.class, metadata);
    }

}


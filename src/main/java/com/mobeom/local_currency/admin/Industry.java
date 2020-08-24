package com.mobeom.local_currency.admin;


import com.mobeom.local_currency.post.Post;
import com.mobeom.local_currency.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@NoArgsConstructor
@Entity
@Table(name="industry",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"industry_code"})})
public class Industry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="industry_id")
    private Long industryId;

    @Column(name="industry_code", nullable = false)
    private int industryCode;

    @Column(name="main_code", nullable = false)
    private String mainCode;

    @Column(name="store_type", nullable = false)
    private String storeType;

    @Column(name="industry_image_url", nullable = false)
    private String industryImageUrl;

}

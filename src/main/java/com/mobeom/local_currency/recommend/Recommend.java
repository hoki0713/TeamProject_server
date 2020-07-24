package com.mobeom.local_currency.recommend;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@Table(name="recommend")
public class Recommend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommend_id", nullable = false) private Long recommendId;
    @Column(name = "recommend_type", nullable = false) private String recommendType;
    @Column(name = "recommend_tag", nullable = false) private String recommendTag;

}

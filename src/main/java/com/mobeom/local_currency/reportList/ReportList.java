package com.mobeom.local_currency.reportList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mobeom.local_currency.store.Store;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Getter @Setter @ToString
@Entity @Table(name = "report_list")
public class ReportList {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @OneToOne
    @JoinColumn(name="store_id")
    private Store store;

    @Column(name = "reported_count")
    private int reportedCount;


}

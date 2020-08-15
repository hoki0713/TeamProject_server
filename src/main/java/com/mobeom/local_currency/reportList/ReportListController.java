package com.mobeom.local_currency.reportList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/reports")
public class ReportListController {
    private final ReportListService reportListService;

    public ReportListController(ReportListService reportListService) {
        this.reportListService = reportListService;
    }

    @PostMapping("/{storeId}")
    public ResponseEntity<ReportList> createReport(@PathVariable String storeId) {
        ReportList newReport = reportListService.createReport(Long.parseLong(storeId));
        return ResponseEntity.ok(newReport);
    }
}

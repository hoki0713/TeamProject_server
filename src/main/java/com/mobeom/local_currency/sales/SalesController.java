package com.mobeom.local_currency.sales;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/sales")
public class SalesController {
    @Autowired
    SalesService salesService;

    @GetMapping(value = "/purchase-history/{userId}")
    public ResponseEntity<Map<Long,RequestedPurchaseHistoryVO>> getSalesHistory(@PathVariable String userId) {
        Optional<Map<Long, RequestedPurchaseHistoryVO>> historyVOMap = salesService.getHistoryByUserId(Long.parseLong(userId));
        if(historyVOMap.isPresent()) {
            return ResponseEntity.ok(historyVOMap.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

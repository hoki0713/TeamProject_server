package com.mobeom.local_currency.favorites;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping("/favorites")
public class FavoritesController {

    private final FavoritesService favoritesService;

    public FavoritesController(FavoritesService favoritesService) {
        this.favoritesService = favoritesService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Map<Long, FavoritesVO>> getFavoritesList(@PathVariable String userId) {
        Optional<Map<Long, FavoritesVO>> favorites = favoritesService.getFavoritesList(Long.parseLong(userId));
        if(favorites.isPresent()) {
            return ResponseEntity.ok(favorites.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}

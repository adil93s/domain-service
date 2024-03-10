package com.example.domainservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import com.example.domainservice.model.Annonce;

@RestController
public class AnnonceController {

    private final WebClient webClient;

    public AnnonceController(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
    }

    @GetMapping("/annonces")
    public Flux<Annonce> getAllAnnonces() {
        return this.webClient.get().uri("/annonces")
                .retrieve()
                .bodyToFlux(Annonce.class);
    }

    @GetMapping("/annonces/{id}")
    public Mono<Annonce> getAnnonceById(@PathVariable Long id) {
        return this.webClient.get().uri("/annonces/{id}", id)
                .retrieve()
                .bodyToMono(Annonce.class);
    }

    @PostMapping("/annonces")
    public Mono<Annonce> createAnnonce(@RequestBody Annonce annonce) {
        return this.webClient.post().uri("/annonces")
                .body(Mono.just(annonce), Annonce.class)
                .retrieve()
                .bodyToMono(Annonce.class);
    }

    @PutMapping("/annonces/{id}")
    public Mono<Annonce> updateAnnonce(@PathVariable Long id, @RequestBody Annonce annonceDetails) {
        return this.webClient.put().uri("/annonces/{id}", id)
                .body(Mono.just(annonceDetails), Annonce.class)
                .retrieve()
                .bodyToMono(Annonce.class);
    }

    @DeleteMapping("/annonces/{id}")
    public Mono<ResponseEntity<Void>> deleteAnnonce(@PathVariable Long id) {
        return this.webClient.delete().uri("/annonces/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .map(r -> ResponseEntity.ok().<Void>build());
    }
}

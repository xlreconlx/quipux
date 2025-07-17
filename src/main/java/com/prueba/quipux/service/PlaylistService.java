/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.quipux.service;

import com.prueba.quipux.entity.Playlist;
import com.prueba.quipux.repository.PlaylistRepository;
import jakarta.transaction.Transactional;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 *
 * @author ander
 */
@Service
public class PlaylistService {
    private final PlaylistRepository playlistRepository;

    public PlaylistService(PlaylistRepository playlistRepository) {
        this.playlistRepository = playlistRepository;
    }

    public ResponseEntity<?> crearPlaylist(Playlist playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().isBlank()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El nombre de la lista no puede ser nulo ni vacío");
            return ResponseEntity.badRequest().body(error);
        }
        
        String encodedNombre = URLEncoder.encode(playlist.getNombre(), StandardCharsets.UTF_8);
        return ResponseEntity
                .created(URI.create("/lists/" + encodedNombre))
                .body(playlistRepository.save(playlist));
    }

    public List<Playlist> obtenerTodas() {
        return playlistRepository.findAll();
    }

    public Playlist obtenerPorNombre(String nombre) {
        return playlistRepository.findByNombre(nombre);
    }

    @Transactional
    public ResponseEntity<?> eliminarPorNombre(String nombre) {
        if (!playlistRepository.existsByNombre(nombre)) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Lista no encontrada");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        playlistRepository.deleteByNombre(nombre);
        return ResponseEntity.noContent().build();
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.quipux.controller;

import com.prueba.quipux.entity.Playlist;
import com.prueba.quipux.service.PlaylistService;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ander
 */
@RestController
@RequestMapping("/lists")
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @PostMapping
    public ResponseEntity<?> crearLista(@RequestBody Playlist playlist) {
        return playlistService.crearPlaylist(playlist);
    }

    @GetMapping
    public ResponseEntity<List<Playlist>> obtenerTodas() {
        return ResponseEntity.ok(playlistService.obtenerTodas());
    }

    @GetMapping("/{nombre}")
    public ResponseEntity<Playlist> obtenerPorNombre(@PathVariable String nombre) {
        Playlist playlist = playlistService.obtenerPorNombre(nombre);
        if (playlist == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(playlist);
    }

    @DeleteMapping("/{nombre}")
    public ResponseEntity<?> eliminar(@PathVariable String nombre) {
        return playlistService.eliminarPorNombre(nombre);
    }
}

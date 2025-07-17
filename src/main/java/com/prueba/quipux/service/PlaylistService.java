/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.quipux.service;

import com.prueba.quipux.entity.Playlist;
import com.prueba.quipux.repository.PlaylistRepository;
import java.util.List;
import org.springframework.http.HttpStatus;
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

    // Crear nueva lista
    public Playlist crearPlaylist(Playlist playlist) {
        if (playlist.getNombre() == null || playlist.getNombre().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El nombre no puede estar vacío");
        }

        if (playlistRepository.existsByNombre(playlist.getNombre())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ya existe una lista con ese nombre");
        }

        return playlistRepository.save(playlist);
    }

    public List<Playlist> obtenerTodas() {
        return playlistRepository.findAll();
    }

    public Playlist obtenerPorNombre(String nombre) {
        return playlistRepository.findByNombre(nombre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada"));
    }

    public void eliminarPorNombre(String nombre) {
        if (!playlistRepository.existsByNombre(nombre)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Lista no encontrada");
        }

        playlistRepository.deleteByNombre(nombre);
    }
}

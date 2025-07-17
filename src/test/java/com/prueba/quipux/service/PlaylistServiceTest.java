
package com.prueba.quipux.service;

import com.prueba.quipux.entity.Playlist;
import com.prueba.quipux.repository.PlaylistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlaylistServiceTest {

    @Mock
    private PlaylistRepository playlistRepository;

    @InjectMocks
    private PlaylistService playlistService;

    private Playlist playlist;

    @BeforeEach
    void setUp() {
        playlist = new Playlist();
        playlist.setId(1L);
        playlist.setNombre("Mi Playlist de Rock");
        playlist.setDescripcion("Las mejores canciones de rock.");
    }

    @Test
    void testCrearPlaylist_Exito() {
        when(playlistRepository.save(any(Playlist.class))).thenReturn(playlist);

        ResponseEntity<?> response = playlistService.crearPlaylist(playlist);
        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(playlist, response.getBody());
        verify(playlistRepository, times(1)).save(playlist);
    }

    @Test
    void testCrearPlaylist_NombreNulo() {
        Playlist playlistConNombreNulo = new Playlist();
        playlistConNombreNulo.setNombre(null);

        ResponseEntity<?> response = playlistService.crearPlaylist(playlistConNombreNulo);

        assertNotNull(response);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("El nombre de la lista no puede ser nulo ni vacío", ((Map<?, ?>) response.getBody()).get("error"));
        verify(playlistRepository, never()).save(any(Playlist.class));
    }

    @Test
    void testObtenerTodas() {
        when(playlistRepository.findAll()).thenReturn(Collections.singletonList(playlist));

        List<Playlist> playlists = playlistService.obtenerTodas();

        assertNotNull(playlists);
        assertEquals(1, playlists.size());
        assertEquals(playlist, playlists.get(0));
        verify(playlistRepository, times(1)).findAll();
    }

    @Test
    void testObtenerPorNombre_Encontrada() {
        when(playlistRepository.findByNombre("Mi Playlist de Rock")).thenReturn(playlist);

        Playlist encontrada = playlistService.obtenerPorNombre("Mi Playlist de Rock");

        assertNotNull(encontrada);
        assertEquals("Mi Playlist de Rock", encontrada.getNombre());
        verify(playlistRepository, times(1)).findByNombre("Mi Playlist de Rock");
    }

    @Test
    void testEliminarPorNombre_Exito() {
        when(playlistRepository.existsByNombre("Mi Playlist de Rock")).thenReturn(true);
        doNothing().when(playlistRepository).deleteByNombre("Mi Playlist de Rock");

        ResponseEntity<?> response = playlistService.eliminarPorNombre("Mi Playlist de Rock");

        assertNotNull(response);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(playlistRepository, times(1)).existsByNombre("Mi Playlist de Rock");
        verify(playlistRepository, times(1)).deleteByNombre("Mi Playlist de Rock");
    }

    @Test
    void testEliminarPorNombre_NoEncontrada() {
        when(playlistRepository.existsByNombre("Playlist Inexistente")).thenReturn(false);

        ResponseEntity<?> response = playlistService.eliminarPorNombre("Playlist Inexistente");

        assertNotNull(response);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("Lista no encontrada", ((Map<?, ?>) response.getBody()).get("error"));
        verify(playlistRepository, times(1)).existsByNombre("Playlist Inexistente");
        verify(playlistRepository, never()).deleteByNombre(anyString());
    }
}

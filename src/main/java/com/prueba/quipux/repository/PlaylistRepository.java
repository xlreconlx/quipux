/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.prueba.quipux.repository;

import com.prueba.quipux.entity.Playlist;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ander
 */
@Repository
public interface PlaylistRepository extends JpaRepository<Playlist, Long>{
    Playlist findByNombre(String nombre);

    boolean existsByNombre(String nombre);

    void deleteByNombre(String nombre);
}

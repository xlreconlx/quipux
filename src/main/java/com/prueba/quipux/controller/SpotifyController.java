/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.prueba.quipux.controller;

import com.prueba.quipux.service.SpotifyService;
import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author ander
 */
@RestController
@RequestMapping("/spotify")
public class SpotifyController {

    @Autowired
    private SpotifyService spotifyService;

    @GetMapping("/generos")
    public ResponseEntity<List<String>> getGenres() {
        //el api de spotify para obtener los generos ya no funciona
        //marcado como obsoleto en su pagina
        //https://developer.spotify.com/documentation/web-api/reference/get-recommendation-genres
        //List<String> genres = spotifyService.obtenerGeneros();
        List<String> generos = Arrays.asList(
            "acoustic", "afrobeat", "alt-rock", "alternative", "ambient",
            "anime", "black-metal", "bluegrass", "blues", "bossanova",
            "brazil", "breakbeat", "british", "cantopop", "chicago-house",
            "children", "chill", "classical", "club", "comedy", "country",
            "dance", "dancehall", "death-metal", "deep-house", "detroit-techno",
            "disco", "drum-and-bass", "dub", "dubstep", "edm", "electro",
            "electronic", "emo", "folk", "forro", "french", "funk", "garage",
            "german", "gospel", "goth", "grindcore", "groove", "grunge",
            "guitar", "happy", "hard-rock", "hardcore", "hardstyle", "heavy-metal",
            "hip-hop", "holidays", "honky-tonk", "house", "idm", "indian",
            "indie", "indie-pop", "industrial", "iranian", "j-dance", "j-idol",
            "j-pop", "j-rock", "jazz", "k-pop", "kids", "latin", "latino",
            "malay", "mandopop", "metal", "metalcore", "minimal-techno",
            "movies", "mpb", "new-age", "new-release", "opera", "pagode",
            "party", "philippines-opm", "piano", "pop", "pop-film", "post-dubstep",
            "power-pop", "progressive-house", "psych-rock", "punk", "punk-rock",
            "r-n-b", "rainy-day", "reggae", "reggaeton", "road-trip", "rock",
            "rock-n-roll", "rockabilly", "romance", "sad", "salsa", "samba",
            "sertanejo", "show-tunes", "singer-songwriter", "ska", "sleep",
            "songwriter", "soul", "soundtracks", "spanish", "study", "summer",
            "swedish", "synth-pop", "tango", "techno", "trance", "trip-hop",
            "turkish", "work-out", "world-music"
        );

        return ResponseEntity.ok(generos);
    }
}

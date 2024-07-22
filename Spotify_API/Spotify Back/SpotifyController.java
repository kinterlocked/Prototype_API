package com.ssadolda.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ssadolda.config.SpotifyConfig;
import com.ssadolda.mapper.Mapper;
import com.ssadolda.model.dto.SearchResponseDto;
import com.ssadolda.service.SpotifyService;

import lombok.RequiredArgsConstructor;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET , RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequiredArgsConstructor
@RequestMapping("/spotify")
public class SpotifyController {
	
	private SpotifyService spotifyService;
	
	   @Autowired
	    public SpotifyController(SpotifyService spotifyService) {
	        this.spotifyService = spotifyService;
	    }
	
    @GetMapping("/{keyword}")
    public List<SearchResponseDto> searchTracks(@PathVariable String keyword) {
        return spotifyService.search(keyword);
    }
	
	
	}
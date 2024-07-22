package com.ssadolda.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.neovisionaries.i18n.CountryCode;
import com.ssadolda.config.SpotifyConfig;
import com.ssadolda.mapper.Mapper;
import com.ssadolda.model.dto.SearchResponseDto;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.ArtistSimplified;
import se.michaelthelin.spotify.model_objects.specification.Image;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.model_objects.specification.Recommendations;
import se.michaelthelin.spotify.model_objects.specification.Track;
import se.michaelthelin.spotify.requests.data.browse.GetRecommendationsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchTracksRequest;

@Service
public class SpotifyService {

    private final SpotifyApi spotifyApi;
    private final Mapper mapper;

    @Autowired
    public SpotifyService(Mapper mapper) {
        this.spotifyApi = new SpotifyApi.Builder()
                .setAccessToken(SpotifyConfig.createToken())
                .build();
        this.mapper = mapper;
    }
 
    public List<SearchResponseDto> search(String keyword) {
        List<SearchResponseDto> searchResponseDtoList = new ArrayList<>();

        try {
        	// 토큰 생성
            SpotifyApi spotifyApi = SpotifyConfig.getSpotifyApi();
            spotifyApi.setAccessToken(SpotifyConfig.createToken());
            
            // 검색 요청의 상세 설정 가능
            // 갯수제한 1~50 , 나라설정 (한국안됨)
            // 타입 album", "artist", "playlist", "track", "show", "episode", "audiobook"
            // 첫번째 리턴 index 설정 
            SearchTracksRequest searchTrackRequest = spotifyApi.searchTracks(keyword)
                    .limit(10)
                    .build();
            
            Paging<Track> searchResult = searchTrackRequest.execute();
            System.out.println(searchResult);
            
            Track[] tracks = searchResult.getItems();
            
            for (Track track : tracks) {
                String title = track.getName();

                AlbumSimplified album = track.getAlbum();
                ArtistSimplified[] artists = album.getArtists();
                String artistName = artists[0].getName();
                Image[] images = album.getImages();
                String imageUrl = (images.length > 0) ? images[0].getUrl() : "NO_IMAGE";
                String albumName = album.getName();
                
                // 빌더로 트랙에 담긴값들 리스트에 넣음
                searchResponseDtoList.add(mapper.toSearchDto(artistName, title, albumName, imageUrl));
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
       
        return searchResponseDtoList;
    }
}

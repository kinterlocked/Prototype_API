package com.ssadolda.config;

import org.springframework.stereotype.Component;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
@Component
public class SpotifyConfig {
	private static final String CLIENT_ID = "15a653e050274e70b778bdbb170a75f6";
	private static final String CLIENT_SECRET = "dd023711072c4bfe88d381c41c0ab569";
	private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
													.setClientId(CLIENT_ID)
													.setClientSecret(CLIENT_SECRET)
													.build();
	
	public static String createToken() {
		ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
		try {
			final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
			spotifyApi.setAccessToken(clientCredentials.getAccessToken());
			System.out.println("토큰 생성" + clientCredentials);
			return spotifyApi.getAccessToken();
		}catch (Exception e) {
			System.out.println("Error : "+e.getMessage());
			return "errror";
		}
	}
	  public static SpotifyApi getSpotifyApi() {
	        return spotifyApi;
	    }
	
	
	
	
}

<template>
  <div>
    <h1>Spotify Integration</h1>

    <button @click="fetchToken">Fetch Access Token</button>
    <pre>{{ token }}</pre>
    <button @click="getTopTrack">Get TopTrack</button>
    <pre>{{ topTrack }}</pre>
    <button @click="getRecommendation">Get Recommendation</button>
    <pre>{{ recommendation }}</pre>
  </div>
</template>

<script setup>
//1.라이브러리 추가
// npm install spotify-web-api-node --save
import Spotify from 'spotify-web-api-node'
import axios from 'axios';
import { onMounted, ref } from 'vue';

// 2. Spotify API 클라이언트를 설정
// https://developer.spotify.com/
// 위 링크에서 create App 해서 web api 사용하고 settings에서 clientId, clientSecret가져오기
const spotify = new Spotify({
  clientId: '클라이언트id 작성',
  clientSecret: '클라이언트 secret 작성',
  redirectUri: 'http://localhost:5173/' //리다이렉트 경로 작성
})

const token = ref(null)
const recommendation = ref(null)
const topTrack = ref(null)
const selectedArtist = ref([])

//3. access token 발급받기 
const fetchToken = async () => {
  const data = new URLSearchParams()
  data.append('grant_type', 'client_credentials')
  data.append('client_id', '클라이언트id 작성')
  data.append('client_secret', '클라이언트 secret 작성')

  try {
    const response = await axios.post('https://accounts.spotify.com/api/token', data, {
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    })
    token.value = response.data.access_token
    //4. 모든 호출에 사용할 수 있도록 설정
    spotify.setAccessToken(response.data.access_token)
  } catch (error) {
    console.error('Error fetching access token:', error)
  }
}


// 아티스트 id로 해당 아티스트의 top track 가져오기 -> 프론트에서 개수 자르기
const getTopTrack = async ()=>{
  try {
        // Get an artist's top tracks - Taylor Swift의 Top Track 가져오기
        const response = await spotify.getArtistTopTracks('06HL4z0CvFAxyc27GXpf02', 'US')
        topTrack.value = response.body
        console.log(response)
      } catch (error) {
        console.error('Error fetching data from Spotify:', error)
      }
}

//seed_artists에 시드 아티스트 id 넣고 추천 트랙 가져오기
const getRecommendation = async () =>{
  try {
        // Get Recommendations Based on Seeds
        //뉴진스, 에스파 시드 아티스트로 넣고 추천 트랙 받아오기
        const response = await spotify.getRecommendations({
          min_energy: 0.4,
          seed_artists: ['6HvZYsbFfjnjFrWF950C9d', '6YVMFz59CuY7ngCxTxjpxE'],
          min_popularity: 50,
          limit:5
        })
        recommendation.value = response.body
        console.log(response)
      } catch (error) {
        console.error('Error fetching data from Spotify:', error)
      }
}

onMounted(fetchToken)

</script>



<script>
export default {
  data() {
    return {
      data: null,
      recommendation: null,
    }
  },
  methods: {
    async getSpotifyData() {
      try {
        // this.$spotify를 사용하여 전역 속성에 접근합니다.
        // Get an artist's top tracks - Taylor Swift의 Top Track 가져오기
        // const response = await this.$spotify.getArtistTopTracks('06HL4z0CvFAxyc27GXpf02', 'US')
        const response = await spotify.getArtistTopTracks('06HL4z0CvFAxyc27GXpf02', 'US')
        this.data = response.body
        console.log(response)
      } catch (error) {
        console.error('Error fetching data from Spotify:', error)
      }
    },
    async getRecommendation() {
      try {
        // Get Recommendations Based on Seeds
        //뉴진스, 에스파 시드 아티스트로 넣고 추천 트랙 받아오기
        const response = await spotify.getRecommendations({
          min_energy: 0.4,
          seed_artists: ['6HvZYsbFfjnjFrWF950C9d', '6YVMFz59CuY7ngCxTxjpxE'],
          min_popularity: 50
        })
        this.recommendation = response.body
        console.log(response)
      } catch (error) {
        console.error('Error fetching data from Spotify:', error)
      }
    },
  }
}
</script>

<style lang="scss" scoped></style>
<template>
  <h2>TMAP API!</h2>
  <div id="map_div"></div>
  <div>
    <label for="route" class="label"></label><br />
    <div id="routeCSS">
      <div id="startCSS">
        <label for="startPoint"><h4>출발지</h4></label><br />
        <input
          type="text"
          class="text"
          id="startPoint"
          placeholder="검색어를 입력해주세요"
          v-model="startPoint"
        />
        <button class="pathsearchbutton" @click="searchStartPoint">검색</button>
      </div>
     
      <div id="endCSS">
        <label for="endPoint"><h4>도착지</h4></label><br />
        <input
          type="text"
          class="text"
          id="endPoint"
          placeholder="검색어를 입력해주세요"
          v-model="endPoint"
        />

        <button class="pathsearchbutton" @click="searchEndPoint">검색</button>
        <!-- <div>{{ endLon }} {{ endLat }}</div> -->
      </div>
      <div>
        <input type="hidden" id="startlon" v-model="startLon" />
        <input type="hidden" id="startlat" v-model="startLat" />
        <input type="hidden" id="endlon" v-model="endLon" />
        <input type="hidden" id="endlat" v-model="endLat" />
        <button class="pathresultbutton" @click="displayRoute">경로 표시 </button>
      </div>
    </div>
    <h5  style="display: inline-block;"> 거리 : {{ totalDistance }}km <br> 예상 도착 시간 : {{ Math.floor((totalDistance /6)*60) }} 분</h5> 

  </div>
</template>

<script setup>

import { onMounted, ref,watch,reactive } from "vue";


let map;
const startPoint = ref("");
const endPoint = ref("");
const startLon = ref("");
const startLat = ref("");
const endLon = ref("");
const endLat = ref("");
let startMarker = null;
let endMarker = null;
let routeLayer = null;

const initMap = () => {
  if (window.Tmapv2) {
    console.log("Tmapv2가 이미 로드되었습니다.");
    initializeMap();
  } else {
    console.log("Tmapv2를 로드합니다.");
    const script = document.createElement("script");
    script.src = `https://apis.openapi.sk.com/tmap/jsv2?version=1&appKey=${
      import.meta.env.VITE_TMAP_API_KEY
    }`;
    script.onload = initializeMap;
    document.head.appendChild(script);
  }
};

const initializeMap = () => {
  const center = new Tmapv2.LatLng(37.566481622437934, 126.98502302169841);
  const mapOptions = {
    center: center,
    width: "100%",
    height: "300px",
    httpsMode: true,
    zoom: 15,
  };
  map = new Tmapv2.Map("map_div", mapOptions);
};
onMounted(initMap);

// 좌표 마커 띄우는 작업 시작
// 2. 키워드 검색 함수
const searchLocation = (keyword, type) => {
  const API_KEY = import.meta.env.VITE_TMAP_API_KEY;
  const URL = `https://apis.openapi.sk.com/tmap/pois?version=1&format=json&callback=result&searchKeyword=${encodeURIComponent(
    keyword
  )}&appKey=${API_KEY}`;

  fetch(URL)
    .then((response) => response.json())
    .then((data) => {
      if (data.searchPoiInfo.pois.poi.length > 0) {
        const firstResult = data.searchPoiInfo.pois.poi[0];
        const coordinates = {
          lat: firstResult.frontLat,
          lon: firstResult.frontLon,
        };

        if (type === "start") {
          startLat.value = coordinates.lat;
          startLon.value = coordinates.lon;
          addMarker(coordinates, "start");
        } else {
          endLat.value = coordinates.lat;
          endLon.value = coordinates.lon;
          addMarker(coordinates, "end");
        }
      } else {
        alert("검색 결과가 없습니다.");
      }
    })
    .catch((error) => console.error(error));
};

// 3. 마커 추가 함수

const addMarker = (coordinates, type) => {
  const position = new Tmapv2.LatLng(coordinates.lat, coordinates.lon);

  if (type === "start") {
    if (startMarker) {
      startMarker.setMap(null);
    }
    startMarker = new Tmapv2.Marker({
      position: position,
      map: map,
      icon: "https://tmapapi.tmapmobility.com/upload/tmap/marker/pin_r_m_a.png", // 새로운 출발지 마커 아이콘
      iconSize: new Tmapv2.Size(32, 32), // 아이콘 크기 조절
    });
    map.setCenter(position);
  } else {
    if (endMarker) {
      endMarker.setMap(null);
    }
    endMarker = new Tmapv2.Marker({
      position: position,
      map: map,
      icon: "https://tmapapi.tmapmobility.com/upload/tmap/marker/pin_b_m_b.png", // 새로운 도착지 마커 아이콘
      iconSize: new Tmapv2.Size(32, 32), // 아이콘 크기 조절
    });
    map.setCenter(position);
  }
};

// 출발지 검색 함수
const searchStartPoint = () => {
  searchLocation(startPoint.value, "start");
};

// 도착지 검색 함수
const searchEndPoint = () => {
  searchLocation(endPoint.value, "end");
};
const totalDistance = ref(0);
// 4. 경로지 선정
const displayRoute = () => {
  if (!startLat.value || !startLon.value || !endLat.value || !endLon.value) {
    alert("출발지와 도착지를 모두 설정해주세요.");
    return;
  }

  const API_KEY = import.meta.env.VITE_TMAP_API_KEY;
  const URL = `https://apis.openapi.sk.com/tmap/routes/pedestrian?version=1&appKey=${API_KEY}`;

  const headers = new Headers({
    "Content-Type": "application/json",
  });

  const body = JSON.stringify({
    startX: startLon.value,
    startY: startLat.value,
    endX: endLon.value,
    endY: endLat.value,
    reqCoordType: "WGS84GEO",
    resCoordType: "WGS84GEO",
    startName: "출발지",
    endName: "도착지",
  });
  console.log("fetch이전");
  console.log(body);
  fetch(URL, { method: "POST", headers: headers, body: body })
    .then((response) => response.json())
    .then((data) => {
      if (routeLayer) {
        routeLayer.setMap(null);
      }
      const routePoints = data.features
        .map((feature) => {
          const { geometry } = feature;
          if (geometry.type === "LineString") {
            return geometry.coordinates.map(
              (coord) => new Tmapv2.LatLng(coord[1], coord[0])
            );
          }
        })
        .flat();
      console.log("routePoints");
      console.log(routePoints);

      // 경로상 일부 좌표가 undefined 이면 빼고 그리기
      const filteredRoutePoints = routePoints.filter(
        (point) => point !== undefined
      );
      console.log("filteredRoutePoints");
      console.log(filteredRoutePoints);
      routeLayer = new Tmapv2.Polyline({
        path: filteredRoutePoints,
        strokeColor: "#FF0000",
        strokeWeight: 6,
        map: map,
      });

      totalDistance.value =
        calculateTotalDistanceOfSegments(filteredRoutePoints).toFixed(3);
      console.log("Total distance:", totalDistance, "km");

      const bounds = new Tmapv2.LatLngBounds();
      filteredRoutePoints.forEach((point) => bounds.extend(point));
      map.fitBounds(bounds);
    })
    .catch((error) => console.error(error));
};

// 경로의 km수를 확인해주는 함수
// 모든 세그먼트의 길이를 계산하는 함수
function calculateTotalDistanceOfSegments(points) {
  let totalDistance = 0;

  for (let i = 0; i < points.length - 1; i++) {
    const start = points[i];
    const end = points[i + 1];

    const segmentDistance = calculateDistance(start, end);
    console.log(i + "번째 " + segmentDistance);
    totalDistance += segmentDistance;
  }

  return totalDistance;
}

// 두 지점 간의 거리를 계산하는 함수 (Haversine 공식 활용)
function calculateDistance(start, end) {
  const earthRadius = 6371; // 지구 반지름 (단위: km)

  const lat1 = start._lat;
  const lon1 = start._lng;
  const lat2 = end._lat;
  const lon2 = end._lng;

  const dLat = toRadians(lat2 - lat1);
  const dLon = toRadians(lon2 - lon1);

  const a =
    Math.sin(dLat / 2) * Math.sin(dLat / 2) +
    Math.cos(toRadians(lat1)) *
      Math.cos(toRadians(lat2)) *
      Math.sin(dLon / 2) *
      Math.sin(dLon / 2);

  const c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
  const distance = earthRadius * c;

  return distance;
}

// 각도를 라디안으로 변환하는 함수
function toRadians(degrees) {
  return (degrees * Math.PI) / 180;
}
</script>

<style>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&family=Noto+Serif+KR:wght@200..900&display=swap');
*{
  font-family: "Noto Sans KR", sans-serif;
}

/* 지도 스타일 */
#map_div {
  width: 100%;
  height: 500px;
  border-radius: 0px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}

/* 경로 설정 스타일 */
#routeCSS {
  margin: 20px 0;
  display:flex;
}

/* 출발지, 목적지 입력란 스타일 */
#startCSS,
#endCSS {
 
  width: 30%;
}

/* 텍스트 입력란 스타일 */
.text {
  width: 80%;
  padding: 10px;
  margin-right: 20px;
  border: 1px solid #ccc;
  border-radius: 5px;
  font-size: 16px;
  margin-bottom: 10px;
}

/* 버튼 스타일 */

.pathsearchbutton{
width : 120px;
height:40px;
margin-bottom: 20px;
background-color: #512da8;
border:none;
color:white;
box-shadow: 0 4px 16px rgba(0,79,255,0.3);
font-size: 16px;
font-weight: bold;
border-radius: 20px;
transition: 0.5s;
}
.pathsearchbutton:focus{
  outline:0;
}
.pathsearchbutton:hover{
  background-color:rgba(192, 175, 212, 0.895) ;
  cursor:pointer;
  box-shadow: 0 2px 4pxrgba(0,79,255,0.6) ;
}


.pathresultbutton {
  background-color: #512da8;
  color: white;
  padding: 10px 20px;
  border: none;
  cursor: pointer;
  transition: background-color 0.5s ease;
  position: relative;
  top: 37.2px;
  box-shadow: 0 4px 16px rgba(0,79,255,0.3);
font-size: 16px;
font-weight: bold;
border-radius: 20px;
transition: 0.5s;
  
}

.pathresultbutton:hover {
  background-color: rgba(192, 175, 212, 0.895); /* Optional: Change color on hover */
}

.pathresultbutton:active {
  background: linear-gradient(to right, #512da8 50%, white 50%);
  background-size: 200% 100%;
  color: black rgba(116, 199, 200, 0.5); /* Optional: Change text color on click */
  transition: background 0.5s ease;
}







.pathresultbutton:active p {
  color: white; /* Optional: Change text color on click */
}




/* 입력란과 버튼을 감싸는 div 요소 */
.input-group {

}

/* 입력란과 버튼을 나란히 배치하는 스타일 */
.input-group .text,
.input-group .btn-primary {
  display: inline-block;
  vertical-align: middle;
}


</style>



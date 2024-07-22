// 네이버 검색광고 플랫폼에서 발급받은 Access Key, Secret Key, Customer ID 입력 

// https://ads.naver.com 

// 1-1. 광고시스템에 로그인.

// 1-2. [도구 -> API 사용 관리] 메뉴 클릭

// 1-3. [네이버 검색광고 API 서비스 신청] 버튼 클릭

// 1-4. API키 자동 발급


const accessLicense = "";
const secretKey = "";
const customerId = "";
 
//CORS우회 프록시 서버
const proxyURL = "https://cors-anywhere.herokuapp.com/";
 
// 네이버 키워드 검색 API를 이용하여 주어진 키워드에 대한 검색 결과 반환
async function fetchKeyword(keywords) {
  // API 호출에 필요한 인자들을 정의
  const method = "GET";
  const api_url = "/keywordstool";
  const timestamp = Date.now().toString();
 
  // HMAC-SHA256 암호화를 이용하여 서명(signature)을 생성
  const signature = await createSignature(secretKey, timestamp, method, api_url);
 
  // 생성한 서명과 함께 API를 호출하여 검색 결과 반환
  const response = await fetch(
    `${proxyURL}https://api.naver.com/keywordstool?hintKeywords=${encodeURIComponent(keywords)}&showDetail=1`,
    {
      headers: {
        "X-Timestamp": timestamp,
        "X-API-KEY": accessLicense,
        "X-CUSTOMER": customerId,
        "X-Signature": signature,
      },
    }
  );
 
  // 검색 결과를 출력하는 함수를 호출
  const data = await response.json();
  
  console.log(data)

  displayResults(data);

}
 
// HMAC-SHA256 암호화를 이용하여 서명(signature)을 생성
async function createSignature(secretKey, timestamp, method, api_url) {
  // TextEncoder를 이용하여 입력 데이터를 UTF-8 형식으로 인코딩
  const encoder = new TextEncoder();
  const data = encoder.encode(`${timestamp}.${method}.${api_url}`);
 
  // 암호화에 사용할 키를 생성
  const key = await crypto.subtle.importKey("raw", encoder.encode(secretKey), { name: "HMAC", hash: "SHA-256" }, false, ["sign"]);
 
  // 생성한 키와 입력 데이터를 이용하여 서명을 생성
  const signatureArrayBuffer = await crypto.subtle.sign("HMAC", key, data);
 
  // 서명 결과를 base64로 인코딩
  const signature = btoa(String.fromCharCode(...new Uint8Array(signatureArrayBuffer)));
  return signature;
}
 
// 검색 결과 출력
function displayResults(data) {
  // 결과를 표시할 HTML DOM
 const resultsContainer = document.getElementById("results");
 resultsContainer.innerHTML = '';
 let maxResults = 40;
 for (let i = 0; i < data.keywordList.length; i++) {
     const keyword = data.keywordList[i];
     if (i >= maxResults) break;
     // 새로운 행 생성
     const row = document.createElement("tr");
     // 연관 키워드
     const keywordCell = document.createElement("td");
     keywordCell.textContent = keyword.relKeyword;
     row.appendChild(keywordCell);
     // PC 월간 검색수
     const pcSearchesCell = document.createElement("td");
     pcSearchesCell.textContent = keyword.monthlyPcQcCnt;
     row.appendChild(pcSearchesCell);
     // 모바일 월간 검색수
     const mobileSearchesCell = document.createElement("td");
     mobileSearchesCell.textContent = keyword.monthlyMobileQcCnt;
     row.appendChild(mobileSearchesCell);
     // PC 월평균 클릭수
     const pcAvgClicksCell = document.createElement("td");
     pcAvgClicksCell.textContent = keyword.monthlyAvePcClkCnt;
     row.appendChild(pcAvgClicksCell);
     // 모바일 월평균 클릭수
     const mobileAvgClicksCell = document.createElement("td");
     mobileAvgClicksCell.textContent = keyword.monthlyAveMobileClkCnt;
     row.appendChild(mobileAvgClicksCell);
     // 경쟁 정도
     const competitionCell = document.createElement("td");
     competitionCell.textContent = keyword.compIdx;
     row.appendChild(competitionCell);
     // 결과 테이블에 행을 추가
     resultsContainer.appendChild(row);
 }
}

// 검색 버튼 클릭 시 이벤트 처리
document.getElementById("search-button").addEventListener("click", () => {
  const keyword = document.getElementById("keyword-input").value;
  if (keyword) {
    fetchKeyword(keyword);
  } else {
    alert("키워드를 입력하세요.");
  }
});

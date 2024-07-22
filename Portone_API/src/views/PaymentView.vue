<template>
  <div>
  </div>
</template>

<script>
export default {
  mounted() {
    // jQuery 로드
    // 초기 기본 세팅 
    const jqueryScript = document.createElement('script');
    jqueryScript.src = 'https://code.jquery.com/jquery-1.12.4.min.js';
    document.head.appendChild(jqueryScript);

    // iamport.payment.js 로드
    const iamportScript = document.createElement('script');
    iamportScript.src = 'https://cdn.iamport.kr/js/iamport.payment-1.2.0.js';
    document.head.appendChild(iamportScript);

    // iamport.payment.js 로드 후에 onPayment 메소드 실행
    iamportScript.onload = () => {
      this.onPayment();
    };
  },
  methods: {
    onPayment() {
      /* 1. 가맹점 식별하기 */
      // js에서 항상 해줘야함!!!
      // imp~~~ 이렇게 나와있는 번호 
      // 추가로 웹에서 imp밑에있는 키들은 백엔드 연동시 필요한 정보입니다.
      const { IMP } = window;
      IMP.init('키 입력');
      // 개별 테스트키 발급 후 위 입력
      
      /* 2. 결제 데이터 정의하기 */
      // DB랑 연결할 시 아래 정보들을 DB에서 가져와야합니다.
      const data = {
        pg: 'html5_inicis',                           
        pay_method: 'card',                           
        merchant_uid: `mid_${new Date().getTime()}`,   
        amount: 100,                                 
        name: '이름 설정하는곳',                  
        buyer_name: '홍길동',                           
        buyer_tel: '01012341234',                     
        buyer_email: 'example@example',               
        buyer_addr: '신사동 661-16',                    
        buyer_postcode: '06018',                      
      };
      
      /* 4. 결제 창 호출하기 */
      IMP.request_pay(data, this.callback);
    },
    callback(response) {
      /* 3. 콜백 함수 정의하기 */
      const {
        success,
        merchant_uid,
        error_msg,
        data,
      } = response;
      
      console.log(response)


      if (success) {
        alert('결제 성공');
      } else {
        alert(`결제 실패: ${error_msg}`);
      }
    },
  },
};
</script>

<style scoped>
</style>
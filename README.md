## 어디든 전기차 충전 어댑터

### 앱 개발자

+ 최준혁
+ 손영준

### 프로젝트 소개

+ 전기자동차 충전 어댑터를 220V 단자에 부착하기만 하면 언제 어디서나 쉽고 간편하게 전기자동차 충전소를 구축할 수 있으며, 전원 차단 기능을 탑재해 도전을 방지한다. 앱을 통해 소비전력 측정, 충전량 확인, 알람 등의 기능을 사용할 수 있습니다. NFC 결제시스템을 탑재하여 간편결제를 하실 수 있으며 전력사업자와 사용자 간의 공정한 거래가 가능합니다.

### 개발
+ Android Studio
+ Firebase

### 앱 화면 및 설명
+ 로그인과 회원가입의 화면입니다. 파이어베이스를 사용하여 개발하였습니다.
![2](https://user-images.githubusercontent.com/84082544/147317200-d8bbae69-8c60-4500-aeec-35429707fa4b.PNG)

## 
+ 메인화면, 메뉴, 포인트 충전, 결제화면 입니다.
+ 메인화면에서 지도는 구글api를 사용하여 개발하였습니다.
+ 메뉴는 Navigation drawer를 통해 개발하였습니다.
+ 포인트 충전화면은 다이얼로그를 통해 개발하였습니다.
+ 결제화면은 부트페이api를 통해 개발하였습니다.
![3](https://user-images.githubusercontent.com/84082544/147317281-570d69ff-1999-4aa2-a304-203c2dd6d75e.PNG)

## 
+ 지도의 마커를 클릭하면 다이얼로그가 나타나게 개발하였습니다.
+ 다이얼로그에 충전하기를 누르면 다이얼로그 창이 닫히며 다른 다이얼로그가 나타나게 하였습니다.
+ waveview를 사용하여 차오르는 형태를 물결이 치는 형태처럼 표현하였습니다.
![4](https://user-images.githubusercontent.com/84082544/147317284-b90367cd-094f-4a68-b892-c8ef16b28c18.PNG)

## 
+ DatePicker를 통해 날짜를 지정할 수 있게 하였습니다.
+ 지정된 날짜에 있는 데이터를 가져와 리스트뷰로 나타내게 하였습니다.
+ 폰에 있는 NFC기능을 활성화 시켜 NFC 정보가 데이터베이스에 전송되게 하였습니다.
![5](https://user-images.githubusercontent.com/84082544/147317286-675b2217-6412-426e-a1aa-b5073d42a676.PNG)

## 
+ 데이터베이스에 데이터를 가져와 리스뷰로 보이게 하였습니다.
![9](https://user-images.githubusercontent.com/84082544/147341913-dd15f7be-38a7-4568-abc5-c5731504aeaf.PNG)
## 

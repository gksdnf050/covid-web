const searchFormElem = $(".search-form");   // 주소 검색 form elem
const searchInputElem = $('.search-form__search-input'); // 주소 검색 input 요소
const suggestionsElem = $(".search-form__suggestions"); // 추천 검색어 container
const clearButtonElem = $(".search-form__clear-button");    // 입력 내용 제거 요소
const sidebarContent = $(".expandable-sidebar__content");

const PAGE_MENU_LIMIT = 20;   // 한 페이지에 나타낼 수 있는 메뉴의 개수 제한
const INPUT_EVENT_MIN_INTERVAL = 10;   // input 태그의 이벤트 발생 최소 간격

const mapOptions = {
    center: new naver.maps.LatLng(37.5546788388674, 126.970606917394),  // 서울역 좌표
    zoom: 15,
    minZoom : 13
};

let map = new naver.maps.Map('map', mapOptions);
const markers = []; // 마커를 담는 리스트



function delay(ms) {return new Promise(resolve => setTimeout(resolve, ms));}

function loading(flag){ // 로딩창을 생성 또는 삭제하는 함수
    if(flag === true){
        /* 로딩창 추가 */
        $(".loading-bg").removeClass("none")
        $(".loader").removeClass("none")
    }else{
        /* 로딩창 삭제 */
        $(".loading-bg").addClass("none")
        $(".loader").addClass("none")
    }
}

async function initializer(mode){
    let restaurantLoaded = false;   // 안심식당 정보가 로드 되었는지를 나타내는 flag
    let hospitalLoaded = false; // 안심병원 정보가 로드 되었는지를 나타내는 flag

    $(`.sidebar__${mode}-mode`).addClass("active"); // mode에 해당하는 좌측 sidebar의 메뉴 활성화 
    $(".menu")[0].click();  // 감염현황의 첫번째 메뉴 선택
    
    loading(true)   // 로딩창 생성
    if(mode !== "hospital"){
        $.ajax({    // 안심식당 정보 요청
            type : "GET",
            url : `/api/relax-info/restaurant`,
            success : function (restaurantList) {
                for(let restaurant of restaurantList){
                    const x = restaurant.x;
                    const y = restaurant.y;

                    const nullableList = [  // null이 될 수 있는 property 배열
                        {title : "업종상세", value: restaurant.relaxGubunDetail},
                        {title : "안심식당 지정일", value : restaurant.relaxRstrntRegDt},
                        {title : "안심식당 취소일", value : restaurant.relaxRstrntCnclDt},
                        {title : "수정일", value : restaurant.relaxUpdateDt}];

                    let contentString = [
                        `<strong>사업자명 : </strong>${restaurant.relaxRstrntNm}<br>`,
                        `<strong>대표자명 : </strong>${restaurant.relaxRstrntRepresent}<br>`,
                        `<strong>시도코드 : </strong>${restaurant.relaxZipcode}<br>`,
                        `<strong>시도명 : </strong>${restaurant.relaxSiNm}<br>`,
                        `<strong>시군구명 : </strong>${restaurant.relaxSidoNm}<br>`,
                        `<strong>없종 : </strong>${restaurant.relaxGubun}<br>`,
                        `<strong>전화번호 : </strong>${restaurant.relaxRstrntTel}<br>`,
                        `<strong>비고 : </strong>${restaurant.relaxRstrntEtc}<br>`,
                        `<strong>선정여부 : </strong>${restaurant.relaxUseYn}<br>`,
                        `<strong>안심식당 seq : </strong>${restaurant.relaxSeq}<br>`,
                        `<strong>주소1 : </strong>${restaurant.relaxAdd1}<br>`,
                        `<strong>주소2 : </strong>${restaurant.relaxAdd2}<br>`
                    ].join("");

                    for(let nullable of nullableList){
                        if(nullable.value !== null){    // 값이 null이 아니라면 infoWindow에 추가
                            contentString += `<strong>${nullable.title} : </strong>${nullable.value}<br>`;
                        }
                    }

                    contentString = `<div class = "info-window">` +
                                        `${contentString}` +
                                    `</div>`

                    createMaker(contentString, "restaurant.png", x, y)  // 각각의 안심식당을 표시하는 마커 생성
                }
                restaurantLoaded = true;    // 안심식당을 모두 로드했음을 나타내는 flag를 true로 설정
            },
            error : function(){
                restaurantLoaded = true;    // 안심식당 정보를 로드하는데 실패한 경우에도 로딩창이 제거되도록 true로 설정
                alert("안심식당 정보를 로드하는데 실패하였습니다.")
            }
        })
    }

    if(mode !== "restaurant"){
        $.ajax({    // 안심병원 정보 요청
            type : "get",
            url : "/api/relax-info/hospital",
            success : function(hospitalList){
                for(let hospital of hospitalList){
                    const x = hospital.x;
                    const y = hospital.y;

                    let contentString = [
                        `<strong>기관명 : </strong>${hospital.yadmNm}<br>`,
                        `<strong>시도명 : </strong>${hospital.sidoNm}</br>`,
                        `<strong>시군구명 : </strong>${hospital.sgguNm}</br>`,
                        `<strong>전화번호 : </strong>${hospital.telno}</br>`,
                        `<strong>운영가능일자 : </strong>${hospital.adtFrDd}</br>`,
                        `<strong>구분코드 : </strong>${hospital.spclAdmTyCd} <img src="/icon/question.png" alt = "물음표" title = "A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관"/></br> `,
                    ].join("");

                    if(hospital.hospTyTpCd !== null) // 선정유형이 null이 아닌 경우 infoWindow에 추가
                        contentString += `<strong>선정유형 : </strong>${hospital.hospTyTpCd} <img src="/icon/question.png" alt = "물음표" title = "국민안심병원 선정유형(A: 호흡기 전용 외래 진료소 분리 운영/B: 유형A+선별진료소, 호흡기병동 등 입원실까지 운영)"/></br>`;

                    contentString = `<div class = "info-window">` +
                                        `${contentString}` +
                                    `</div>`

                    createMaker(contentString, "hospital.png", x, y)    // 각각의 안심병원을 표시하는 마커 생성
                }

                hospitalLoaded = true   // 안심병원을 모두 로드했음을 나타내는 flag를 true로 설정
            }, error : function(){
                hospitalLoaded = true;   // 안심병원 정보를 로드하는데 실패한 경우에도 로딩창이 제거되도록 true로 설정
                alert("안심병원 정보를 로드하는데 실패하였습니다.")
            }
        })
    }

    let allLoaded = false;  // 모드에 따른 정보가 모두 로드되었는지를 나타내는 flag

    while(!allLoaded){  // 안심병원 또는 식당 정보가 모두 로드 될 때까지 while문 수행
        await delay(500);   // 500ms 대기

        switch(mode){
            case "hospital" : if(hospitalLoaded === true) allLoaded = true; break;
            case "restaurant" : if(restaurantLoaded === true) allLoaded = true; break;
            case "all" : if(hospitalLoaded === true && restaurantLoaded === true) allLoaded = true; break;
        }
    }

    if(allLoaded) {   // 모두 로드 된 경우
        updateMakers(map, markers); // 마커 update (marker를 생성할 때 모두 맵에서 지웠기 때문에 보이는 지도 영역의 marker들은 보이도록 updateMarkers를 호출해야함.)
        loading(false); // 로딩창 제거
    }
}

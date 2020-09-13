const searchFormElem = $(".search-form");   // 주소 검색 form elem
const searchInputElem = $('.search-form__search-input'); // 주소 검색 input 요소
const suggestionsElem = $(".search-form__suggestions"); // 추천 검색어 container
const clearButtonElem = $(".search-form__clear-button");    // 입력 내용 제거 요소
const mapElem = $("#map");  //  네이버 맵

const INPUT_EVENT_MIN_INTERVAL = 10;   // input 태그의 이벤트 발생 최소 간격
let lastKeyEvent = 0;   // input 태그의 가장 최근에 발생한 이벤트의 timestamp

let selectedSuggestionIndex = 0;    // 선택된 추천 검색어의 index

const mapOptions = {
    center: new naver.maps.LatLng(37.5546788388674, 126.970606917394),  // 서울역 좌표
    zoom: 15,
    minZoom : 13
};

function delay(ms) {return new Promise(resolve => setTimeout(resolve, ms));}


let map = new naver.maps.Map('map', mapOptions);
const markers = []; // 마커를 담는 리스트

naver.maps.Event.addListener(map, 'idle', function () {
    updateMakers(map, markers);
})

function updateMakers(map, makers) {
    let mapBounds = map.getBounds();
    let position;

    for(let marker of makers){
        position = marker.getPosition();

        if(mapBounds.hasLatLng(position)){
            showMarker(map, marker);
        }else{
            hideMarker(map, marker);
        }
    }
}

function showMarker(map, marker) {
    if(marker.getMap()) return;
    marker.setMap(map);
}

function hideMarker(map, marker) {
    if (!marker.getMap()) return;
    marker.setMap(null);
}

function createMaker(contentString, iconFileName, x, y){
    const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(y, x),
        map: map,
        icon : {
            url : `/icon/${iconFileName}`,
        }
    });

    marker.setMap(null);

    markers.push(marker);

    const infowindow = new naver.maps.InfoWindow({
        content: contentString
    });

    naver.maps.Event.addListener(marker, "click", function(e) {
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            infowindow.open(map, marker);
            $("div.info-window").parents('div').parents('div').css("border-radius", "10px");    // info-window css 추가
        }
    });
}

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

    $(`.sidebar__${mode}-mode`).addClass("active");

    loading(true)
    if(mode !== "hospital"){
        $.ajax({
            type : "GET",
            url : `/api/restaurant`,
            success : function (restaurantList) {
                for(let restaurant of restaurantList){
                    const x = restaurant.x;
                    const y = restaurant.y;

                    const nullableList = [
                        {title : "업종상세", value: restaurant.category_detail},
                        {title : "안심식당 지정일", value : restaurant.reg_date},
                        {title : "안심식당 취소일", value : restaurant.cancel_date},
                        {title : "수정일", value : restaurant.update_date}];

                    let contentString = [
                        `<strong>사업자명 : </strong>${restaurant.restaurant}<br>`,
                        `<strong>대표자명 : </strong>${restaurant.representative}<br>`,
                        `<strong>시도코드 : </strong>${restaurant.zipcode}<br>`,
                        `<strong>시도명 : </strong>${restaurant.sido}<br>`,
                        `<strong>시군구명 : </strong>${restaurant.sggu}<br>`,
                        `<strong>없종 : </strong>${restaurant.category}<br>`,
                        `<strong>전화번호 : </strong>${restaurant.tel}<br>`,
                        `<strong>비고 : </strong>${restaurant.etc}<br>`,
                        `<strong>선정여부 : </strong>${restaurant.selected}<br>`,
                        `<strong>안심식당 seq : </strong>${restaurant.seq}<br>`,
                        `<strong>주소1 : </strong>${restaurant.add1}<br>`,
                        `<strong>주소2 : </strong>${restaurant.add2}<br>`
                    ].join("");

                    for(let nullable of nullableList){
                        if(nullable.value !== null){
                            contentString += `<strong>${nullable.title} : </strong>${nullable.value}<br>`;
                        }
                    }

                    contentString = `<div class = "info-window">` +
                                        `${contentString}` +
                                    `</div>`

                    createMaker(contentString, "restaurant.png", x, y)
                }
                restaurantLoaded = true;
            },
            error : function(){
                alert("안심식당 정보를 로드하는데 실패하였습니다.")
            }
        })
    }

    if(mode !== "restaurant"){
        $.ajax({
            type : "get",
            url : "/api/hospital",
            success : function(hospitalList){
                for(let hospital of hospitalList){
                    const x = hospital.x;
                    const y = hospital.y;

                    let contentString = [
                        `<strong>기관명 : </strong>${hospital.hospital}<br>`,
                        `<strong>시도명 : </strong>${hospital.sido}</br>`,
                        `<strong>시군구명 : </strong>${hospital.sggu}</br>`,
                        `<strong>전화번호 : </strong>${hospital.tel}</br>`,
                        `<strong>운영가능일자 : </strong>${hospital.operableDate}</br>`,
                        `<strong>구분코드 : </strong>${hospital.typeCode} <i class="far fa-question-circle" title = "A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관"></i></br> `,
                    ].join("");

                    if(hospital.selectionType !== null)
                        contentString += `<strong>선정유형 : </strong>${hospital.selectionType} <i class="far fa-question-circle" title = "국민안심병원 선정유형(A: 호흡기 전용 외래 진료소 분리 운영/B: 유형A+선별진료소, 호흡기병동 등 입원실까지 운영)"></i></br>`;

                    contentString = `<div class = "info-window">` +
                                        `${contentString}` +
                                    `</div>`

                    createMaker(contentString, "hospital.png", x, y)
                }

                hospitalLoaded = true
            }, error : function(){
                alert("안심병원 정보를 로드하는데 실패하였습니다.")
            }
        })
    }

    let allLoaded = false;  // 모드에 따른 정보가 모두 로드되었는지를 나타내는 flag

    while(!allLoaded){  // 로드 될 때까지 while문 수행
        await delay(500);   // 500ms 대기

        switch(mode){
            case "hospital" : if(hospitalLoaded === true) allLoaded = true; break;
            case "restaurant" : if(restaurantLoaded === true) allLoaded = true; break;
            case "all" : if(hospitalLoaded === true && restaurantLoaded === true) allLoaded = true; break;
        }
    }

    if(allLoaded) {   // 모두 로드 된 경우
        updateMakers(map, markers); // update (marker를 생성할 때 모두 맵에서 지웠기 때문에 보이는 지도 영역의 marker들은 보이도록 updateMarkers를 호출해야함.)
        loading(false);
    }
}

function removeSuggestions(){   // 모든 추천 검색어 삭제
    const suggestionElem = $(".search-form__suggestion");
    searchFormElem.css( "padding-bottom", "0px"); // 추천 검색어와 form 태그의 간격을 제거

    suggestionElem.remove(); // 기존 검색어 추천 모두 삭제
}

mapElem.on("click", () => { // 맵 클릭 시
    searchInputElem.blur(); // searchInput의 focus 제거 (blur 하지 않으면 맵을 클릭해도 searchInputElem의 focus가 제거되지 않아 추천 검색어가 사라지지 않음.)
})

clearButtonElem.on("click", () => {
    searchInputElem.val("");    // input 태그에 입력된 값 지움.
    removeSuggestions();    // 추천 검색어 모두 삭제
})

searchFormElem.submit(function(event) {
    event.preventDefault(); // 기본 리스너 동작 제거

    let selectElem = $(".selected").eq(0) // 추천 검색어들 중 선택된 첫번째 검색어
    const suggestionElem = $(".search-form__suggestion");   // 추천 검색어 모두 선택

    if(selectElem.length === 0){    // 선택된 검색어가 없는 경우
        selectElem = suggestionElem.eq(0);  // 추천 검색어들 중 첫번째 추천 검색어 선택
    }

    const selectedValue = selectElem.children(".suggestion__title").text(); // 선택한 추천 검색어의 title
    const xCoordinateELem = selectElem.children("input[name = 'x']")
    const yCoordinateELem = selectElem.children("input[name = 'y']")

    const x = xCoordinateELem.val();    // x 좌표
    const y = yCoordinateELem.val();    // y 좌표
    console.log(x, y)
    map.setCenter(new naver.maps.LatLng(y, x))  // map 이동

    searchInputElem.val(selectedValue);    // input 태그에 입력된 값을 선택한 추천 검색어의 title로 수정.
    searchInputElem.blur(); // input 태그의 focus를 제거해서 추천 검색어가 표시되지 않도록 함.

    removeSuggestions();    // 추천 검색어 모두 삭제
});

function blurEventListener(event){
    removeSuggestions();    // 추천 검색어 모두 삭제
    $(this).parent().removeClass('focus');  // form 태그의 focus 클래스를 제거하여 border 제거
}

searchInputElem.focus(function(){
    $(this).parent().addClass('focus'); // form 태그에 focus 클래스를 추가하여 border 추가
}).blur(blurEventListener)

function arrowAndEnterKeyPressHandler(event){   // 눌린 방향키에 따라 해당 방향의 추천 검색어를 선택 표시하고, 눌린키가 위 아래 방향키 또는 Enter키 인 경우 true를 반환함.
    const suggestionElem = $(".search-form__suggestion");   // 추천 검색어 select
    const suggestionNum = suggestionElem.length;    // 현재 추천 검색어의 개수

    const code = event.originalEvent.code;  // 눌린 키의 code

    suggestionElem.removeClass("selected"); // 기존 선택된 추천 검색어들의 선택을 모두 해제

    if(suggestionElem.length >0){   // 추천 검색어가 있는 경우
        if(code === "ArrowUp"){    // 위 방향키를 누른 경우
            selectedSuggestionIndex = (selectedSuggestionIndex - 1) % suggestionNum;    // index를 현재 추천 검색어 위의 추천 검색어의 index로 수정
            suggestionElem.eq(selectedSuggestionIndex-1).addClass("selected");  // 헤당 추천 검색어를 선택 표시
        }else if(code === "ArrowDown"){    // 아래 방향키를 누른 경우
            selectedSuggestionIndex = (selectedSuggestionIndex + 1) % suggestionNum;    // index를 현재 추천 검색어 아래의 추천 검색어의 index로 수정
            suggestionElem.eq(selectedSuggestionIndex-1).addClass("selected");  // 헤당 추천 검색어를 선택 표시
        }
    }

    if(code === "ArrowDown" || code === "ArrowUp" || code === "Enter"){
        return true;
    }

    return false;
}

searchInputElem.on("propertyChange keyup paste focus", function(event){  // 입력 받거나 focus가 주어진 경우
    // 한글을 빠르게 입력하거나, 한글을 입력한 후 방향키나 엔터를 눌렀을 때 keyup 이벤트가 추가적으로 발생하는 문제가 생겨서, INPUT_EVENT_MIN_INTERVAL 이후 발생하는 이벤트만 처리함.
    const curTimeStamp = new Date().getTime();  // 현재 이벤트 발생 시간

    if(lastKeyEvent + INPUT_EVENT_MIN_INTERVAL > curTimeStamp){    // (가장 최근에 발생한 이벤트의 시간 + 제한 시간) 이내에 이벤트가 발생한 경우에는, 처리하지 않음.
        lastKeyEvent = new Date().getTime();    // 가장 최근에 발생한 이벤트의 timestamp 갱신
        return;
    }

    lastKeyEvent = new Date().getTime();    // 가장 최근에 발생한 이벤트의 timestamp 갱신

    const isArrowOrEnter = arrowAndEnterKeyPressHandler(event);

    if(isArrowOrEnter === true) // 눌린 키가 위 아래 방향키 또는 Enter키 인 경우 이벤트 리스너 종료.
        return;

    const target = event.target;    // event가 발생한 대상
    const query = target.value; // input 태그에 입력된 검색어

    if(query !== ""){    // 검색어가 있을 경우에만 서버에 추천 검색어 요청
        const url = `/api/instantSearch?query=${encodeURIComponent(query)}`;

        $.ajax({
            url : url,
            type : "GET",
            success : function(response){
                const jsonResponse = JSON.parse(response);  // response를 json 형식으로 변환
                const suggestions = jsonResponse.documents;

                removeSuggestions();    // 추천 검색어 모두 삭제
                selectedSuggestionIndex = 0;    // 선택된 추천 검색어의 index

                if(suggestions !== undefined && suggestions.length > 0){
                    searchFormElem.css( "padding-bottom", "10px"); // 추천 검색어가 있으면 padding-bottom을 주어서 둥근 테두리가 보이도록 하고, 없으면 제거함.

                    for(let suggestion of suggestions){ // 추천 검색어 container에 각각의 추천 검색어를 append
                        suggestionsElem.append(`<div class = "search-form__suggestion">
                                                           <i class="fas fa-map-marker-alt suggestion_marker"></i>
                                                           <span class = "suggestion__title">${suggestion.place_name}</span>
                                                           <span class = "suggestion__category">${suggestion.category_group_name}</span><br>
                                                           <span class = "suggestion__road_address">${suggestion.road_address_name}</span>
                                                           <input type="hidden" name = "x" value = "${suggestion.x}">
                                                           <input type="hidden" name = "y" value = "${suggestion.y}">
                                                       </div>`)
                    }
                    const suggestionElems = document.querySelectorAll('.search-form__suggestion')   // 모든 추천 검색어 선택

                    for(let suggestionElem of suggestionElems){ // 각각의 추천 검색어 요소에 대해 click, mouseenter, mouseleave 이벤트 핸들러를 등록한다.
                        suggestionElem.addEventListener("mouseenter", (event) => {  // jquery의 on 메서드를 사용했을 때, mouseenter, mouseleave 이벤트가 자식 요소에도 발생하는 문제가 생겨서, document를 사용하여 이벤트 핸들러를 등록함.
                            suggestionElem.classList.add("selected")    // 해당 추천 검색어에 마우스가 올라오면 해당 요소에 selected class를 추가해서 선택 표시를 해준다.
                            searchInputElem.off("blur") // 추천 검색어를 클릭했을 때, searchInputElem의 blur 이벤트 핸들러가 동작하고, 추천 검색어가 모두 제거 되어, 추천 검색어의 click 이벤트 핸들러가 동작하지 않는 문제가 발생하여 추천 검색어에 마우스가 올라왔을 때에는 , searchInputElem의 blur 이벤트 핸들러를 잠시 제거한다.
                        })

                        suggestionElem.addEventListener("mouseleave", (event) => {
                            suggestionElem.classList.remove("selected") // 해당 추천 검색어에서 마우스가 벗어나면 해당 요소에 selected class를 제거해서 선택 해제 표시를 해준다.
                            searchInputElem.on("blur", blurEventListener)   // 해당 추천 검색어에서 마우스가 벗어나면, 제거했던 searchInputElem의 blur 이벤트 핸들러를 다시 등록한다.
                        })

                        suggestionElem.addEventListener("click", ()=>{  // 추천 검색어를 클릭한 경우, submit 하여 해당 위치로 이동한다.
                            searchFormElem.submit();
                        })
                    }
                }else{
                    removeSuggestions();    // 추천 검색어 모두 삭제
                }
            },
            error : function(req, status, error){
                removeSuggestions();    // 추천 검색어 모두 삭제
                alert("검색 실패!")
            }
        })
    }else{  // 입력된 검색어가 없는 경우
        removeSuggestions();    // 추천 검색어 모두 삭제
    }
})
const searchFormElem = $(".search-form");   // 주소 검색 form elem
const searchInputElem = $('.search-form__search-input'); // 주소 검색 input 요소
const suggestionsElem = $(".search-form__suggestions"); // 추천 검색어 container
const clearButtonElem = $(".search-form__clear-button");    // 입력 내용 제거 요소
const mapElem = $("#map");  //  네이버 맵

const INPUT_EVENT_MIN_INTERVAL = 10;   // input 태그의 이벤트 발생 최소 간격
let lastKeyEvent = 0;   // input 태그의 가장 최근에 발생한 이벤트의 timestamp

let selectedSuggestionIndex = 0;    // 선택된 추천 검색어의 index

const mapOptions = {
    center: new naver.maps.LatLng(37.3595704, 127.105399),
    zoom: 15
};

let map = new naver.maps.Map('map', mapOptions);

function filterAgencyName(agencyName){
    const filterElementList = ["(의)", "재단", "법인", "대학교", "부속", "교도소", " "]
    for(let filterElement of filterElementList){
        agencyName = agencyName.split(filterElement).slice(-1)[0] // 가장 마지막 token
    }

    return agencyName;
}

function initializer(mode){
    /*if(mode != "hospital"){
        const requestSize = 1000;
        let startIndex = 1;
        let endIndex = 1000;

        let curRestaurantCnt = 0;

        $.ajax({
            type : "GET",
            url : `/api/restaurant?start=${startIndex}&end=${endIndex}`,
            success : function (response) {
                const jsonResponse = JSON.parse(response);  // response를 json 형식으로 변환
                console.log(jsonResponse)
            }
        })
    }*/
    if(mode != "restaurant"){
        $.ajax({
            type : "get",
            url : "/api/hospital",
            success : function(response){
                const jsonResponse = JSON.parse(response);  // response를 json 형식으로 변환
                const items = jsonResponse.response.body.items.item;

                for(let item of items){
                    agencyName = filterAgencyName(item.yadmNm);
                    const query = `${item.sidoNm} ${item.sgguNm} ${agencyName}`
                    $.ajax({
                        type : "GET",
                        url : `/api/addressToPoint?query=${query}`,
                        success : function(response){
                            try{
                                const jsonResponse = JSON.parse(response);  // response를 json 형식으로 변환
                                const x = jsonResponse.documents[0].x;
                                const y = jsonResponse.documents[0].y;

                                const marker = new naver.maps.Marker({
                                    position: new naver.maps.LatLng(y, x),
                                    map: map
                                });

                                let contentString = [
                                    `<div class = 'hospital-info'>`,
                                    `<strong>시도명 : </strong>${item.sidoNm}</br>`,
                                    `<strong>시군구명 : </strong>${item.sgguNm}</br>`,
                                    `<strong>전화번호 : </strong>${item.telno}</br>`,
                                    `<strong>운영가능일자 : </strong>${item.adtFrDd}</br>`,
                                    `<strong>구분코드 : </strong>${item.spclAdmTyCd} <i class="far fa-question-circle" title = "A0: 국민안심병원/97: 코로나검사 실시기관/99: 코로나 선별진료소 운영기관"></i></br> `,
                                    `</div>`,
                                ].join("");

                                if(item.hospTyTpCd !== undefined)
                                    contentString += `<strong>선정유형 : </strong>${item.hospTyTpCd} <i class="far fa-question-circle" title = "국민안심병원 선정유형(A: 호흡기 전용 외래 진료소 분리 운영/B: 유형A+선별진료소, 호흡기병동 등 입원실까지 운영)"></i></br>`;

                                const infowindow = new naver.maps.InfoWindow({
                                    content: contentString
                                });

                                naver.maps.Event.addListener(marker, "click", function(e) {
                                    if (infowindow.getMap()) {
                                        infowindow.close();
                                    } else {
                                        infowindow.open(map, marker);
                                    }
                                });
                            }catch(err){
                                console.log(`${item.sidoNm} ${item.sgguNm} ${item.yadmNm}`)
                            }
                        },
                        error : function(){
                            alert("주소 변환 실패!")
                        }
                    })
                }
            }, error : function(){
                alert("정보를 로드하는데 실패하였습니다.")
            }
        })
    }
};

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

    if(query != ""){    // 검색어가 있을 경우에만 서버에 추천 검색어 요청
        const url = `/api/instantSearch?query=${query}`;

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
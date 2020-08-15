<%--
  Created by IntelliJ IDEA.
  User: rlfal
  Date: 2020-08-13
  Time: 오후 6:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page isELIgnored="true" %>
<html>
    <head>
        <title>맵</title>
        <style>
            body {
                font-size: 13px;
                font-family: 'Roboto', sans-serif;
                overflow: hidden;
            }

            .focus{
                /* focus를 얻으면 테두리 추가 */
                border: 1px solid #0475f4;
            }

            .selected{
                background: #eef7ff;
            }

            .covid-map{
                /* 화면 전체를 맵으로 */
                width:100%;
                height:100%;
            }

            .search-form {
                /* instance search를 맵 위에 그리기 위한 속성 */
                z-index:1;
                position:absolute;  /**/
                top:50px;
                left:50px;

                width: 350px;
                border-radius: 10px;    /* 테두리가 동그란 모양 */
                box-shadow: 0 6px 8px rgba(0, 0, 0, 0.15);  /* 테두리 그림자 효과 */
                background: #fff;   /* 배경을 하얀색으로 (배경을 지정해주지 않으면 투명하게 나타남)*/
            }

            .search-form__search-input {
                margin-left: 45px;
                margin-top: 10px;
                margin-bottom: 10px;    /* margin을 주어서 form의 크기를 늘려줌 */

                color: #5a6674;

                width: 80%;
                height: 20px;
                border: none;   /* input 태그 테두리 제거 */
                appearance: none;
                outline: none;
            }

            .search-form__suggestions{
                width: 100%;
                font-size: 12px;
            }

            .search-form__suggestion{
                padding : 12px 5px 5px 5px; /* 추천 검색어 사이의 간격 */
            }

            .suggestion__category{
                float:right /* 오른쪽 끝에 붙어 있게 함. */
            }

            .suggestion__title{
                margin-left: 3px;   /* 마커 아이콘과의 간격 */
            }

            .suggestion__road_address{
                margin-left: 26px;   /* form 태그와 주소의 간격 */
            }
            .suggestion_marker{
                font-size: 15px;
                margin-left: 10px;
            }

            .search-button {
                position: absolute;
                top: 10px;
                left: 15px;

                height: 20px;
                width: 20px;

                padding: 0;
                margin: 0;
                border: none;
                background: none;
                outline: none !important;
                cursor: pointer;
            }

            .search-button svg {
                width: 20px;
                height: 20px;
                fill: #5a6674;
            }

            .search-form__clear-button {
                position: absolute !important;
                top: 12px !important;
                right: 18px;
                height: 14px;
                margin: auto;
                font-size: 18px;
                cursor: pointer;
                color: #ccc;
            }

            /* clears the 'X' from Internet Explorer */
            .search-form__search-input::-ms-clear,
            .search-form__search-input::-ms-reveal {
                display: none;
                width: 0;
                height: 0;
            }

            /* clears the 'X' from Chrome */
            .search-form__search-input::-webkit-search-decoration,
            .search-form__search-input::-webkit-search-cancel-button,
            .search-form__search-input::-webkit-search-results-button,
            .search-form__search-input::-webkit-search-results-decoration {
                display: none;
            }
        </style>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" rel="stylesheet">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
    </head>

    <body>
        <form class="search-form">
            <button type="submit" class="search-button">    <%-- submit 버튼 --%>
                <svg class="submit-button">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#search"></use>
                </svg>
            </button>
            <input type = "search" value="" placeholder="장소, 주소 검색" class="search-form__search-input">  <%-- 주소 검색 창 --%>
            <span class = "search-form__clear-button glyphicon glyphicon-remove-circle"></span> <%-- 입력 내용 삭제 버튼 --%>
            <div class = "search-form__suggestions"></div>  <%-- 추천 검색어 container --%>
        </form>

        <svg xmlns="http://www.w3.org/2000/svg" width="0" height="0" display="none">
            <symbol id="search" viewBox="0 0 32 32">
                <path d="M 19.5 3 C 14.26514 3 10 7.2651394 10 12.5 C 10 14.749977 10.810825 16.807458 12.125 18.4375 L 3.28125 27.28125 L 4.71875 28.71875 L 13.5625 19.875 C 15.192542 21.189175 17.250023 22 19.5 22 C 24.73486 22 29 17.73486 29 12.5 C 29 7.2651394 24.73486 3 19.5 3 z M 19.5 5 C 23.65398 5 27 8.3460198 27 12.5 C 27 16.65398 23.65398 20 19.5 20 C 15.34602 20 12 16.65398 12 12.5 C 12 8.3460198 15.34602 5 19.5 5 z" />
            </symbol>
        </svg>

        <div id="map" class = "covid-map"></div>

        <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i"></script>  <%-- 네이버 맵 --%>

        <script>
            const searchFormElem = $(".search-form");   <%-- 주소 검색 form elem --%>
            const searchInputElem = $('.search-form__search-input'); <%-- 주소 검색 input 요소 --%>
            const suggestionsElem = $(".search-form__suggestions"); <%-- 추천 검색어 container --%>
            const clearButtonElem = $(".search-form__clear-button");    <%-- 입력 내용 제거 요소 --%>
            const mapElem = $("#map");  <%-- 네이버 맵 --%>

            const INPUT_EVENT_MIN_INTERVAL = 10;   // input 태그의 이벤트 발생 최소 간격
            let lastKeyEvent = 0;   // input 태그의 가장 최근에 발생한 이벤트의 timestamp

            let selectedSuggestionIndex = 0;    // 선택된 추천 검색어의 index

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
                event.preventDefault();
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
                    const url = `/search?query=${query}`;

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

                                    suggestionElem.addEventListener("mouseenter", (event) => {
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
            var mapOptions = {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 15
            };

            var map = new naver.maps.Map('map', mapOptions);
        </script>
    </body>
</html>

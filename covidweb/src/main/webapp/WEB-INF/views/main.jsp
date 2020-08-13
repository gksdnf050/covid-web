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

            .covid-map{
                /* 화면 전체를 맵으로 */
                width:100%;
                height:100%;
            }

            .focus{
                /* focus를 얻으면 테두리 추가 */
                border: 1px solid #0475f4;
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
                margin: 0;  /* margin-bottom 제거함 */
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

            .suggestion__address{
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
        </style>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
        <link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    </head>

    <body>
        <form class="search-form">
            <input type="search" value="" placeholder="장소, 주소 입력" class="search-form__search-input">
            <button type="submit" class="search-button">
                <svg class="submit-button">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#search"></use>
                </svg>
            </button>
            <div class = "search-form__suggestions"></div>
        </form>

        <svg xmlns="http://www.w3.org/2000/svg" width="0" height="0" display="none">
            <symbol id="search" viewBox="0 0 32 32">
                <path d="M 19.5 3 C 14.26514 3 10 7.2651394 10 12.5 C 10 14.749977 10.810825 16.807458 12.125 18.4375 L 3.28125 27.28125 L 4.71875 28.71875 L 13.5625 19.875 C 15.192542 21.189175 17.250023 22 19.5 22 C 24.73486 22 29 17.73486 29 12.5 C 29 7.2651394 24.73486 3 19.5 3 z M 19.5 5 C 23.65398 5 27 8.3460198 27 12.5 C 27 16.65398 23.65398 20 19.5 20 C 15.34602 20 12 16.65398 12 12.5 C 12 8.3460198 15.34602 5 19.5 5 z" />
            </symbol>
        </svg>

        <div id="map" class = "covid-map"></div>

        <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

        <script>
            $('.search-form__search-input').focus(function(){
                $(this).parent().addClass('focus');
            }).blur(function(){
                $(".search-form__suggestion").remove(); // 기존 검색어 추천 삭제
                $(this).parent().removeClass('focus');
            })

            $('.search-form__search-input').on("input focus", function(event){  // 입력 받거나 focus가 주어진 경우
                const target = event.target;
                const query = target.value;

                $(".search-form__suggestion").remove(); // 기존 검색어 추천 삭제

                if(query != ""){    // 검색어가 있을 경우에만 서버에 추천 검색어 요청
                    const url = `/search?query=${query}`;

                    const suggestionElem = $(".search-form__suggestions");  // 검색어 추천

                    $.ajax({
                        url : url,
                        type : "GET",
                        success : function(response){
                            const jsonResponse = JSON.parse(response);
                            const suggestions = jsonResponse.items;


                            if(suggestions.length > 0){
                                for(let suggestion of suggestions){
                                    suggestionElem.append(`<div class = "search-form__suggestion">
                                                               <i class="fas fa-map-marker-alt suggestion_marker"></i>
                                                               <span class = "suggestion__title">${suggestion.title}</span>
                                                               <span class = "suggestion__category">${suggestion.category}</span><br>
                                                               <span class = "suggestion__address">${suggestion.address}</span>
                                                               <input type="hidden" value = "${suggestion.mapx},${suggestion.mapy}">
                                                           </div>`)
                                }
                            }
                        },
                        error : function(req, status, error){
                            suggestionElem.empty(); // 자식 모두 제거
                            alert("검색 실패!")
                        }
                    })
                }
            })
            var mapOptions = {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 10
            };

            var map = new naver.maps.Map('map', mapOptions);
        </script>
    </body>
</html>

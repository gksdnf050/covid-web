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
            .navbar{
                height: 7%;
                width: 100%;
                display: flex;
                align-items: center;
            }

            .covid-map{
                width:100%;
                height:93%;
            }
            .search-form{
                margin: 0;
            }
            .instance-search{
                display: inline-block;
            }

        </style>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <nav class = "navbar shadow">
            <select name="search-selector" class = "navbar__search-selector">
                <option value="All">전체</option>
                <option value="hospital">병원</option>
                <option value="restaurant">식당</option>
            </select>

            <form class = "search-form">
                <div class = "instance-search">
                    <input type="text" class = "instance-search__search-input">
                </div>
            </form>
        </nav>

        <div id="map" class = "covid-map"></div>
        <script  src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i"></script>
        <script>
            $('.instance-search__search-input').on("input", function(event){
                const target = event.target;
                const query = target.value;
                const url = `/search?query=${query}`;

                $.ajax({
                    url : url,
                    type : "GET",
                    success : function(response){
                        const jsonResponse = JSON.parse(response);
                        $(".instance-search").append("<div>a</div>")
                        console.log(jsonResponse.items)
                    },
                    error : function(req, status, error){
                        alert("검색 실패!")
                    }
                })
            })
            var mapOptions = {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 10
            };

            var map = new naver.maps.Map('map', mapOptions);
        </script>
    </body>
</html>

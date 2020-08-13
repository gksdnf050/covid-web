<%--
  Created by IntelliJ IDEA.
  User: rlfal
  Date: 2020-08-13
  Time: 오후 6:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

        </style>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    </head>
    <body>
        <nav class = "navbar shadow">
            <form class = "search-form" action="">
                <select name="search-selector">
                    <option value="All">전체</option>
                    <option value="hospital">병원</option>
                    <option value="restaurant">식당</option>
                </select>

                <input type="text">
                <input type="submit" value="검색">
            </form>
        </nav>

        <div id="map" class = "covid-map"></div>

        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i"></script>
        <script>
            var mapOptions = {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoom: 10
            };

            var map = new naver.maps.Map('map', mapOptions);
        </script>
    </body>
</html>

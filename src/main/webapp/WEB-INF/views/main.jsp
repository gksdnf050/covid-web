<%--
  Created by IntelliJ IDEA.
  User: rlfal
  Date: 2020-08-13
  Time: 오후 6:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>covidWeb</title>
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.8.2/css/all.min.css" rel="stylesheet">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">

        <link rel="stylesheet" href="/css/main/main.css">
        <link rel="stylesheet" href="/css/main/loadging.css">
        <link rel="stylesheet" href="/css/main/infectionInfo.css">
        <link rel="stylesheet" href="/css/main/table/main.css">
        <link rel="stylesheet" href="/css/main/table/util.css">
        <link rel="stylesheet" href="/css/main/table/perfect-scrollbar.css">
    </head>

    <body>
        <nav class="expandable-sidebar shadow-middle order-last">   <!-- https://colorlib.com/wp/bootstrap-sidebar/ -->
            <div class="custom-menu">
                <button type="button" class="expandable-sidebar__sidebar-collapse shadow-strict btn btn-primary">
                </button>
            </div>

            <h3 class = "expandable-sidebar__logo">감염현황</h3>
            <nav class ="menu-bar covid-menu-bar">
                <ul class="menu-list covid-menu-list">
                    <li class = "menu covid-menu domestic">국내</li>
                    <li class ="menu covid-menu city">시도별</li>
                    <li class ="menu covid-menu genAndAge">성별/연령별</li>
                    <li class ="menu covid-menu country">국가별</li>
                </ul>
            </nav>

            <div class = "expandable-sidebar__content m-t-15">
                <div class="wrap-table">
                    <div class="table ver1">
                        <div class="table-body">
                            <table>
                                <tbody>

                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>

        </nav>

        <nav class="sidebar shadow-strict">   <!-- https://freefrontend.com/css-menu/ -->
            <ul>
                <li class="sidebar__all-mode">
                    <a class ="sidebar__link" href="/main?mode=all">
                        <i class="fa fa-font menu__icon" style="font-size:23px;"></i>
                    </a>
                    <span class="tooltip">전체</span>
                </li>
                <li class="sidebar__hospital-mode">
                    <a class = "sidebar__link" href="/main?mode=hospital">
                        <i class="fa fa-hospital-o menu__icon" style="font-size:23px;"></i>
                    </a>
                    <span class="tooltip">안심병원</span>
                </li>
                <li class = "sidebar__restaurant-mode">
                    <a class = "sidebar__link" href="/main?mode=restaurant">
                        <i class="material-icons menu__icon" style="font-size:23px">restaurant</i>
                    </a>
                    <span class="tooltip">안심식당</span>
                </li>
                <li class = "logout">
                    <a class = "sidebar__link" href="/logout">
                        <i class="fa fa-sign-out menu__icon" style="font-size:23px"></i>
                    </a>
                    <span class="tooltip">로그아웃</span>
                </li>
            </ul>
        </nav>

        <form class="search-form shadow-lenient">
            <button type="submit" class="search-button">    <%-- submit 버튼 --%>
                <svg class="submit-button">
                    <use xmlns:xlink="http://www.w3.org/1999/xlink" xlink:href="#search"></use>
                </svg>
            </button>
            <input type = "search" value="" placeholder="장소, 주소 검색" class="search-form__search-input"/>  <%-- 주소 검색 창 --%>
            <span class = "search-form__clear-button glyphicon glyphicon-remove-circle"></span> <%-- 입력 내용 삭제 버튼 --%>
            <div class = "search-form__suggestions"></div>  <%-- 추천 검색어 container --%>
        </form>

        <svg xmlns="http://www.w3.org/2000/svg" width="0" height="0" display="none">
            <symbol id="search" viewBox="0 0 32 32">
                <path d="M 19.5 3 C 14.26514 3 10 7.2651394 10 12.5 C 10 14.749977 10.810825 16.807458 12.125 18.4375 L 3.28125 27.28125 L 4.71875 28.71875 L 13.5625 19.875 C 15.192542 21.189175 17.250023 22 19.5 22 C 24.73486 22 29 17.73486 29 12.5 C 29 7.2651394 24.73486 3 19.5 3 z M 19.5 5 C 23.65398 5 27 8.3460198 27 12.5 C 27 16.65398 23.65398 20 19.5 20 C 15.34602 20 12 16.65398 12 12.5 C 12 8.3460198 15.34602 5 19.5 5 z" />
            </symbol>
        </svg>

        <div id="map" class = "covid-map"></div>

        <!-- copyright -->
        <div class = "copyright">
            <p><strong>아이콘 : </strong> <a href="http://www.flaticon.com">Flaticon</a></p>
            <p><strong>사이드바 : </strong> <a href="http://www.Colorlib.com">Colorlib</a></p>
            <p><strong>지도 : </strong><a href="https://www.ncloud.com/">Ncloud</a></p>
            <p><strong>지역 검색 : </strong><a href="https://developers.kakao.com/">Kakao</a></p>
        </div>

        <div class="loading-bg dark-bg dark-bg--lenient none"></div>

        <div class="loader none">   <!-- https://freefrontend.com/css-loaders/,  https://freefrontend.com/css-spinners/, https://codepen.io/havardob/pen/VjjJey/ -->
        </div>

        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i"></script>  <%-- 네이버 맵 --%>
        <script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=h76lgnlg6i&submodules=geocoder"></script>
        <script src="/js/main/table/perfect-scrollbar.min.js"></script>

        <script src="/js/main/main.js"></script>
        <script src="/js/main/infectionInfo.js"></script>
        <script src="/js/main/map.js"></script>
        <script src="/js/main/localSearch.js"></script>

        <script>
            $(document).ready(() => {
                const mode = "<%=request.getAttribute("mode")%>";
                initializer(mode);
            })
        </script>
    </body>
</html>

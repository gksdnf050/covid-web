const mapElem = $("#map");  //  네이버 맵

naver.maps.Event.addListener(map, 'idle', function () {
    updateMakers(map, markers);
})

function updateMakers(map, makers) {    // 현재 보이는 영역의 마커만 표시
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

function createMaker(contentString, iconFileName, x, y){    // 마커의 내용, 아이콘 파일명, x, y 좌표를 인자로 전달받아 마커를 생성
    const marker = new naver.maps.Marker({
        position: new naver.maps.LatLng(y, x),
        map: map,
        icon : {
            url : `/icon/${iconFileName}`,
        }
    });

    marker.setMap(null);    // 마커를 표시하지 않음. (안심 식당/병원 마커를 지도에 모두 표시하면 버벅거리는 현상 때문에, 생설할 땐 모두 표시하지 않고, idle 이벤트가 발생할 때만 update 해주어, 지도의 보이는 영역의 마커만 표시해줌)

    markers.push(marker);

    const infowindow = new naver.maps.InfoWindow({
        content: contentString
    });

    naver.maps.Event.addListener(marker, "click", function() {
        if (infowindow.getMap()) {
            infowindow.close();
        } else {
            infowindow.open(map, marker);
            $("div.info-window").parents('div').parents('div').css("border-radius", "10px");    // info-window css 추가
        }
    });
}

mapElem.on("click", () => { // 맵 클릭 시
    searchInputElem.blur(); // searchInput의 focus 제거 (blur 하지 않으면 맵을 클릭해도 searchInputElem의 focus가 제거되지 않아 추천 검색어가 사라지지 않음.)
})
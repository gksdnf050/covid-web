const upIcon = "<i class='fas fa-arrow-up up' style='font-size:15px'></i>";
const downIcon = "<i class='fas fa-arrow-down down' style='font-size:15px'></i>";
const tbodyElement = $("tbody");

$(document).on("click", ".page-menu", pageMenuOnClickHandler)   // 상세 메뉴 하단의 페이지 메뉴를 클릭한 경우 

$(document).on("click", ".covid-detail-menu", detailMenuOnClickHandler) // 상세메뉴를 클릭한 경우 

$(document).on("click", ".menu", menuOnClinkHandler)    // 이후에 ".menu"에 해당 하는 element가 생겨도 이벤트가 하도록 동적 바인딩을 위해 $(document).on("event name", "selector", handler) 형식 사용

function menuOnClinkHandler() {    // 감염현황의 메뉴바 클릭 이벤트  // TODO : 람다식으로 전달할 경우와 정의된 함수를 전달하는 경우 this가 차이남 왜 차이 나는지 확인
    if($(this).hasClass("covid-detail-menu"))   // 상세 메뉴를 클릭한 경우
        $(".covid-detail-menu").removeClass("active");  // 모든 상세 메뉴의 active 클래스 제거
    else if ($(this).hasClass("covid-menu"))    // 감염현황의 카테고리를 클릭한 경우
        $(".covid-menu").removeClass("active");
    else if($(this).hasClass("page-menu"))  // 상세 메뉴 하단의 페이지 메뉴를 클릭한 경우
        $(".page-menu").removeClass("active");

    $(this).addClass("active"); // 클릭한 메뉴는 활성화
}

function pageMenuOnClickHandler(){  // 상세 메뉴 하단의 페이지 메뉴를 클릭한 경우
    const menuType = $(this).data("menu-type"); // 감염현황의 카테고리 (국내, 시도별, 성별/연령별, 국가별)
    const paramName = $(this).data("menu-param");   // 해당 카테고리에 대한 정보를 요청할 때 필요한 query parameter의 name
    const pageNo = $(this).text();  // 페이지 번호

    createCovidDetailMenuByUrl(menuType, paramName, pageNo, PAGE_MENU_LIMIT);   // 메뉴 클릭시 해당 페이지의 상세 메뉴를 생성
}

function detailMenuOnClickHandler(){    // 상세 메뉴 클릭시
    const paramName = $(this).data("param");    // 해당 카테고리의 상세메뉴에 대한 정보를 요청할 때 필요한 query parameter의 name
    const paramValue = $(this).text()   // query parameter의 값
    const menuType = $(this).data("type");   // 감염현황의 카테고리 (국내, 시도별, 성별/연령별, 국가별)
    const uri = `/api/infection-info/${menuType}?${paramName}=${paramValue}`    // 해당 카테고리의 상세메뉴에 대한 정보를 요청할 url

    let propertyDictionary, diffExclude, rateProperty;

    if(menuType === "city"){    // 시도별 카테고리에 대한 상세메뉴인 경우
        propertyDictionary = {    // 각 프로퍼티의 의미를 나타내는 객체
            stdDay : "기준 일시",
            defCnt : "확진자 수",
            localOccCnt : "지역감염 수",
            overFlowCnt : "해외유입 수",
            isolIngCnt : "격리 중인 환자 수",
            deathCnt : "사망자 수",
            gubun : "시도명",
            incDec : "전일 대비 증감 수",
            isolClearCnt : "격리 해제 수",
            qurRate : "10만명당 발생률",
        }
        diffExclude = ["stdDay", "gubun"]; // 어제와 오늘의 데이터를 비교하지 않을 property
        rateProperty = ["qurRate"]; // 확률값(rateProperty는 업/다운 아이콘으로 증감을 표시하지만, 소수점 아래 값이 길어지므로, 어제와 오늘의 값 차이는 표시하지 않음
    }else if(menuType === "genAndAge"){ // 성별/연령별 카테고리에 대한 상세메뉴인 경우
        propertyDictionary = {
            gubun : "구분(성별, 연령별)",
            confCase : "확진자 수",
            confCaseRate : "확진률",
            death : "사망자 수",
            deathRate : "사망률",
            criticalRate : "치명률",
        }
        diffExclude = ["gubun"]
        rateProperty = ["confCaseRate", "deathRate", "criticalRate"];
    }else if(menuType === "country"){   // 국가별 카테고리에 대한 상세메뉴인 경우
        propertyDictionary = {
            stdDay: "기준일시",
            areaNm : "지역명",
            nationNm : "국가명",
            natDefCnt : "국가별 확진자 수",
            natDeathCnt :  "국가별 사망자 수",
            natDeathRate : "확진률 대비 사망률"
        }
        diffExclude = ["stdDay", "areaNm", "nationNm"]
        rateProperty = ["natDeathRate"];
    }

    requestCovidInfo(uri,propertyDictionary, diffExclude, rateProperty);    // 해당 카테고리의 상세메뉴에 대한 정보를 요청하여, 테이블에 표시
}

$(".domestic").on("click", function () {
    const url = "/api/infection-info/domestics";    // 국내 확진자 정보를 요청할 url

    const propertyDictionary = {    // 각 프로퍼티의 의미를 나타내는 객체
        stateDt : "기준일",
        stateTime : "기준시간",
        decideCnt : "확진자 수",
        clearCnt : "격리 해제 수",
        examCnt : "검사 진행 수",
        deathCnt : "사망자 수",
        careCnt : "치료중 환자 수",
        resutlNegCnt : "결과 음성 수",
        accExamCnt : "누적 검사 수",
        accExamCompCnt : "누적 검사 완료 수",
        accDefRate : "누적 확진율"
    }

    const diffExclude = ["stateDt", "stateTime"]; // 어제와 오늘의 데이터를 비교하지 않을 property
    const rateProperty = ["accDefRate"] // 확률값(rateProperty는 업/다운 아이콘으로 증감을 표시하지만, 소수점 아래 값이 길어지므로, 어제와 오늘의 값 차이는 표시하지 않음

    requestCovidInfo(url, propertyDictionary, diffExclude, rateProperty);   // 국내 확진자 정보를 테이블에 표시
})

function requestCovidInfo(url, propertyDictionary, diffExclude, rateProperty){
    $.ajax({
        type : "GET",
        url : url,
        success : function (response) {
            const today = response.today;   // 어제의 확진자 정보
            const yesterday = response.yesterday;   // 오늘의 확진자 정보

            tbodyElement.html("");
            $(".covid-detail-bar").remove();    // 상세 메뉴 모두 삭제함.
            $(".page-bar").remove();     // 상세 메뉴 하단의 페이지 메뉴를 모두 삭제

            for(let property in today){ // 각 프로퍼티를 순회
                let todayValue = today[property];   // 해당 property에 대한 오늘의 데이터
                let yesterdayValue = yesterday[property];   // 해당 property에 대한 어제의 데이터

                let diffAbs = "", icon = "";
                const diff = todayValue - yesterdayValue;   // 어제와 오늘 데이터의 차이

                if(!diffExclude.includes(property)) {   // 어제와 오늘의 데이터에 차이가 있고, 데이터를 비교할 property에 해당한다면
                    if(diff !== 0 && !rateProperty.includes(property))  // 차이가 0이 아니고, 확률 정보가 아닌 경우 diffAbs를 화면에 표시
                        diffAbs = Math.abs(diff);   // 어제와 오늘의 데이터 차이의 절대값

                    if(diff > 0) icon = upIcon; else if(diff < 0) icon = downIcon; else icon = "";    // 오늘 데이터의 값이 더 큰지, 작은지, 오늘과 어제의 차이가 없는지에 따라 icon을 결정
                }

                tbodyElement.append([   // 정보를 테이블에 추가
                    `<tr class="row100 body covidInfo">`,
                        `<td class="cell100 column1">${propertyDictionary[property]}</td>`,
                        `<td class="cell100 column2">${todayValue} ${icon} ${diffAbs}</td>`,
                    `</tr>`].join("")
                )
            }
        },
        error : function () {
            console.log("fail to request covid info")
        }
    })
}

function createCovidDetailMenuByUrl(menuType, menuUrlParam, pageNo, numOfRows){ // 감연현황 카테고리에 대한 상세메뉴를 생성하는 함수
    const url = `/api/infection-info/${menuType}/categories?pageNo=${[pageNo]}&numOfRows=${numOfRows}`; // 생성할 상세메뉴 정보를 요청할 url

    $.ajax({
        type : "GET",
        url : url,
        success : function (response) {
            const totalCount = response.totalCount; // 상세 메뉴의 전체 개수
            const pageCount = Math.ceil(totalCount / PAGE_MENU_LIMIT);  // 상세메뉴 하단에 생성할 페이지 메뉴의 개수
            const items = response.items;  
            
            let listCnt = 0;    // 생성한 menu-list의 개수
            let cellCnt = 4;    // 한 줄에 들어갈 메뉴의 개수

            $(".covidInfo").remove();   // covid 정보를 표시하는 테이블의 tr 태그 모두 지움
            $(".covid-detail-bar").remove();    // 상세 메뉴들을 모두 삭제함.
            $(".page-bar").remove();    // 상세메뉴 하단의 페이지 메뉴를 모두 삭제함.

            sidebarContent.append([ // 상세 메뉴를 포함시킬 bar 생성
                `<nav class ="menu-bar covid-detail-bar">`,
                `</nav>`].join(""));

            const menuBar = $(".covid-detail-bar"); // 상세 메뉴를 포함시킬 bar

            for(let i = 0; i < items.length; i++){
                if (i % cellCnt === 0){ // 하나의 menu-list 안에 cellCnt만큼의 메뉴를 추가한 경우
                    menuBar.append([    // 새로운 menu-list 생성
                        `<ul class="menu-list covid-detail-list">`,
                        `</ul>`].join(""))

                    listCnt++;
                }

                $(`.covid-detail-list:nth-child(${listCnt})`).append(`<li class ="menu covid-detail-menu" data-type="${menuType}" data-param="${menuUrlParam}">${items[i]}</li>`);  // 가장 마지막 menu-list에 상세 메뉴 추가
            }

            if(pageCount > 1){  // 상세메뉴 하단에 생성할 페이지 메뉴의 개수가 1보다 큰 경우
                sidebarContent.append([ // 페이지 메뉴를 추가할 page-bar 생성
                    `<nav class ="menu-bar page-bar">`,
                        `<ul class="menu-list page-list">`,
                        `</ul>`,
                    `</nav>`].join(""));

                const pageList = $(".page-list");

                for(let i = 0; i < pageCount; i++){
                    pageList.append(`<li class ="menu page-menu" data-menu-type="${menuType}" data-menu-param="${menuUrlParam}">${i + 1}</li>`) // pageCount만큼의 페이지 메뉴 생성
                }

                $(".page-menu").removeClass("active");  // 기존 페이지 메뉴의 active 클래스 제거
                $(`.page-menu:nth-child(${pageNo})`).addClass("active");    // pageNo에 해당하는 페이지 메뉴는 활성화
            }
        },
        error : function () {
            console.log("covid detail error")
        }
    })
}

$(".city").on("click", function () {    // 감염현황의 카테고리를 선택한 경우
    const pageNo = 1;
    createCovidDetailMenuByUrl("city", "name", pageNo, PAGE_MENU_LIMIT)
})

$(".genAndAge").on("click", function () {
    const pageNo = 1;
    createCovidDetailMenuByUrl("genAndAge", "genOrAge", pageNo, PAGE_MENU_LIMIT)
})

$(".country").on("click", function () {
    const pageNo = 1;
    createCovidDetailMenuByUrl("country", "name", pageNo, PAGE_MENU_LIMIT)
});

(function($) {  // expandable sidebar의 js
    "use strict";

    $('.expandable-sidebar__sidebar-collapse').on('click', function () {
        $('.expandable-sidebar').toggleClass('active');
    });

})(jQuery);
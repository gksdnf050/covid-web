<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2020-09-06
  Time: 오후 1:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>회원가입</title>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet"/>

        <link rel="stylesheet" type="text/css" href="/fonts/font-awesome.min.css">
        <link rel="stylesheet" type="text/css" href="/css/login/util.css">
        <link rel="stylesheet" type="text/css" href="/css/login/main.css">

    </head>
    <body>
        <div class="limiter">
            <div class="login-container">
                <div class="login-wrap p-l-60 p-r-60 p-t-55 p-b-40">
                    <form class="login-form validate-form flex-sb flex-w" method="post" action = "/signup">
                        <span class="login-form__title p-b-32">회원가입</span>

                        <div class="input-wrap validate-input m-b-15" data-validate="이름을 입력해주세요">
                            <input class="input-wrap__input" type="text" name="name" placeholder="이름">
                            <span class="input-wrap__input-focus"></span>
                        </div>

                        <div class="input-wrap validate-input m-b-15" data-validate="이메일을 입력해주세요">
                            <input class="input-wrap__input" type="text" name="email" placeholder="이메일">
                            <span class="input-wrap__input-focus"></span>
                        </div>

                        <div class="input-wrap validate-input m-b-15" data-validate="비밀번호를 입력해주세요">
                            <span class="btn-show-pass">
                                <i class="fa fa-eye"></i>
                            </span>
                            <input class="input-wrap__input" type="password" name="password" placeholder="비밀번호">
                            <span class="input-wrap__input-focus"></span>
                        </div>

                        <span class = "login-form__fail-msg">${signupFailMsg}</span>

                        <button class="login-form__login-btn m-t-30">Sign up</button>
                    </form>
                </div>
            </div>
        </div>
        <div id="dropDownSelect1"></div>

        <script type="text/javascript" async="" src="https://www.google-analytics.com/analytics.js"></script>
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>

        <script src="/js/login/main.js" type="text/javascript"></script>

        <script type="text/javascript">
            window.dataLayer = window.dataLayer || [];

            function gtag() {
                dataLayer.push(arguments);
            }

            gtag('js', new Date());

            gtag('config', 'UA-23581568-13');
        </script>
    </body>
</html>

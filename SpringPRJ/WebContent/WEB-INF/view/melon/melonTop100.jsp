<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <script src="https://code.jquery.com/jquery-3.6.0.js"
		integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
		crossorigin="anonymous"></script>
    <script type="text/javascript">
        $(window).on("load", function () {
            //페이지 로딩 완료 후, 멜론 순위가져오기 함수 실행함
            getRank();
        });
        //멜론 순위가져오기
        function getRank() {
            //Ajax 호출
            $.ajax({
                url: "/melon/getRank.do",
                type: "post",
                dataType: "JSON",
                contentType: "application/json; charset=UTF-8",
                success: function (json) {
                    var melon_rank = "";
                    for (var i = 0; i < json.length; i++) {
                        melon_rank += (json[i].rank + "위 | ");
                        melon_rank += (json[i].song + " | ");
                        melon_rank += (json[i].singer + " | ");
                        melon_rank += (json[i].album + "<br>");
                    }
                    $('#melon_rank').html(melon_rank);
                }
            })
        }
    </script>
</head>
<body>
<h1>멜론 순위</h1>
<hr/>
<div id="melon_rank"></div>
<br/>
<hr/>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<style>
#chartdiv {
  width: 100%;
  height: 500px;
}
</style>
<script src="https://code.jquery.com/jquery-3.6.0.js"
		integrity="sha256-H+K7U5CnXl1h5ywQfKtSj8PCmoN9aaq30gDh27Xc0jk="
		crossorigin="anonymous"></script>
<!-- Resources -->
<script src="https://cdn.amcharts.com/lib/4/core.js"></script>
<script src="https://cdn.amcharts.com/lib/4/charts.js"></script>
<script src="https://cdn.amcharts.com/lib/4/plugins/wordCloud.js"></script>
<script src="https://cdn.amcharts.com/lib/4/themes/material.js"></script>
<script src="https://cdn.amcharts.com/lib/4/themes/animated.js"></script>
<meta charset="UTF-8">
<!-- Chart code -->
<script>

$(window).on("load", function () {
    //페이지 로딩 완료 후, 책제목 가져오기 함수 실행함
    getTitle();
});

//책제목 가져오기
function getTitle() {
    //Ajax 호출
    $.ajax({
        url: "/cloud/getTitle.do",
        type: "post",
        dataType: "JSON",
        contentType: "application/json; charset=UTF-8",
        success: function (json) {
            var title = "";
            for (var i = 0; i < json.length; i++) {
                title += (json[i].word);
                //console.log(title);
            }
            $('#title').html(title);
            
        }
    })
}
</script>
<title>Insert title here</title>
</head>
<body>
<div id="chartdiv"></div>
</body>

<script>
//let _text = document.querySelector('#title');
let _text = document.getElementById("title");
//console.log(document.querySelector('#title'));

am4core.ready(function() {
	// Themes begin
	am4core.useTheme(am4themes_material);
	am4core.useTheme(am4themes_animated);
	// Themes end

	var chart = am4core.create("chartdiv", am4plugins_wordCloud.WordCloud);
	var series = chart.series.push(new am4plugins_wordCloud.WordCloudSeries());

	series.accuracy = 4;
	series.step = 15;
	series.rotationThreshold = 0.7;
	series.maxCount = 200;
	series.minWordLength = 2;
	series.labels.template.margin(4,4,4,4);
	series.maxFontSize = am4core.percent(30);

	<!--series.text = "Though yet of Hamlet our dear brother's death The memory be green, and that it us befitted To bear our hearts in grief and our whole kingdom To be contracted in one brow of woe, Yet so far hath discretion fought with nature That we with wisest sorrow think on him, Together with remembrance of ourselves. Therefore our sometime sister, now our queen, The imperial jointress to this warlike state, Have we, as 'twere with a defeated joy,-- With an auspicious and a dropping eye, With mirth in funeral and with dirge in marriage, In equal scale weighing delight and dole,-- Taken to wife: nor have we herein barr'd Your better wisdoms, which have freely gone With this affair along. For all, our thanks. Now follows, that you know, young Fortinbras, Holding a weak supposal of our worth, Or thinking by our late dear brother's death Our state to be disjoint and out of frame, Colleagued with the dream of his advantage, He hath not fail'd to pester us with message, Importing the surrender of those lands Lost by his father, with all bonds of law, To our most valiant brother. So much for him. Now for ourself and for this time of meeting: Thus much the business is: we have here writ To Norway, uncle of young Fortinbras,-- Who, impotent and bed-rid, scarcely hears Of this his nephew's purpose,--to suppress His further gait herein; in that the levies, The lists and full proportions, are all made Out of his subject: and we here dispatch You, good Cornelius, and you, Voltimand, For bearers of this greeting to old Norway; Giving to you no further personal power To business with the king, more than the scope Of these delated articles allow. Farewell, and let your haste commend your duty. Tis sweet and commendable in your nature, Hamlet,To give these mourning duties to your father: But, you must know, your father lost a father; That father lost, lost his, and the survivor bound In filial obligation for some term To do obsequious sorrow: but to persever In obstinate condolement is a course Of impious stubbornness; 'tis unmanly grief; It shows a will most incorrect to heaven, A heart unfortified, a mind impatient, An understanding simple and unschool'd: For what we know must be and is as common As any the most vulgar thing to sense, Why should we in our peevish opposition Take it to heart? Fie! 'tis a fault to heaven, A fault against the dead, a fault to nature, To reason most absurd: whose common theme Is death of fathers, and who still hath cried, From the first corse till he that died to-day, 'This must be so.' We pray you, throw to earth This unprevailing woe, and think of us As of a father: for let the world take note, You are the most immediate to our throne; And with no less nobility of love Than that which dearest father bears his son, Do I impart toward you. For your intent In going back to school in Wittenberg, It is most retrograde to our desire: And we beseech you, bend you to remain Here, in the cheer and comfort of our eye, Our chiefest courtier, cousin, and our son.";--> 
	<!--series.text = "행정각부의 장은 국무위원 중에서 국무총리의 제청으로 대통령이 임명한다. 공무원인 근로자는 법률이 정하는 자에 한하여 단결권·단체교섭권 및 단체행동권을 가진다. 모든 국민은 소급입법에 의하여 참정권의 제한을 받거나 재산권을 박탈당하지 아니한다. 헌법재판소 재판관의 임기는 6년으로 하며, 법률이 정하는 바에 의하여 연임할 수 있다. 국가는 주택개발정책등을 통하여 모든 국민이 쾌적한 주거생활을 할 수 있도록 노력하여야 한다. 형사피의자 또는 형사피고인으로서 구금되었던 자가 법률이 정하는 불기소처분을 받거나 무죄판결을 받은 때에는 법률이 정하는 바에 의하여 국가에 정당한 보상을 청구할 수 있다.";-->
	console.log(_text)
	/* series.text = '"' + title + '"'; */
	series.text = "유미의 세포들 세트(10-13권)(전4권)위대한 서양미술사닥터앤닥터 육아일기. 2: 신생아 돌보기부자의 패턴(양장본 HardCover)팀 하포드의 세상을 바꾼 51가지 물건우리가 몰랐던 백년 건강 동의보감만만한 집밥 레시피 162이런 수학은 처음이야. 2문명. 1스티커 페인팅북: 가면헬로카봇 국기 카드 100인소의 법칙. 3(한정판)(만화)(디앤씨웹툰비즈)대슬이 밥상초등 질문의 힘또라이 질량 보존의 법칙에서 살아남기왜 리더인가(양장본 HardCover)암은 병이 아니다(개정증보판)궤도의 밖에서, 나의 룸메이트에게(문학동네 청소년 53)Go Go 카카오프렌즈. 20: 한국. 3문명. 2";
	console.log(series.text);
	series.colors = new am4core.ColorSet();
	series.colors.passOptions = {}; // makes it loop

	//series.labelsContainer.rotation = 45;
	series.angles = [0,-90];
	series.fontWeight = "700"

	setInterval(function () {
	  series.dataItems.getIndex(Math.round(Math.random() * (series.dataItems.length - 1))).setValue("value", Math.round(Math.random() * 10));
	 }, 10000)

	}); // end am4core.ready()
</script>
</html>
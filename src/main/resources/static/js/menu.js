//修改这个变量为实际控制器的地址，如../showGoods.do
var reqpath = "search.html"
/*ajax获得的json对象*/
var typelist = [{
	"id": "1",
	"parentId": "0",
	"name": "台式电脑"
}, {
	"id": "2",
	"parentId": "0",
	"name": "笔记本"
}, {
	"id": "3",
	"parentId": "3",
	"name": "电脑整机"
}, {
	"id": "4",
	"parentId": "3",
	"name": "电脑配件"
}, {
	"id": "29",
	"parentId": "20",
	"name": "游戏本"
}, {
	"id": "30",
	"parentId": "20",
	"name": "平板电脑"
}, {
	"id": "31",
	"parentId": "20",
	"name": "平板电脑配件"
}, {
	"id": "34",
	"parentId": "20",
	"name": "笔记本配件"
}, ]
//加载json数据的到一级分类的方法
function initMenu() {
	for (var i = 0; i < typelist.length; i++) {

		if (typelist[i].parentId == "0") {

			$(".index-menu").append($("<li data='" + typelist[i].id + "'>" + typelist[i].name + "</li>"))
		}
	}
}

window.addEventListener("load", function() {
	initMenu();
	//根据轮播图片的高，导航的高
	//获得轮播图高
	var lunh = $("#myCarousel").height();
	var lih = (lunh - 10) / 19;
	//确定导航高度
	$(".index-menu li").css("height", lih + "px")
	//确定按钮位置	
	var btnt = Math.floor($("#myCarousel").height() / 2 - 30);
	$(".left").css("margin-top", btnt + "px");
	$(".right").css("margin-top", btnt + "px");
	/*左侧分类一级菜单控制二级菜单显示和隐藏*/
	$(".index-menu").hover(function() {
		$("#showIndex").show();
	}, function() {
		$("#showIndex").hide();
	})
	/*左侧分类二级菜单控制三级菜单显示和隐藏*/
	$(".second-menu").hover(function() {
		$("#showSecond").show();
	}, function() {
		$("#showSecond").hide();
	})
	/*二级菜单自己控制显示和隐藏*/
	$("#showIndex").hover(function() {
		$("#showIndex").show();
	}, function() {
		$("#showIndex").hide();
	})
	/*三级菜单自己控制显示和隐藏*/
	$("#showSecond").hover(function() {
		$("#showIndex").show();
		$("#showSecond").show();
	}, function() {
		$("#showIndex").hide();
		$("#showSecond").hide();
	})
	/*一级分类项li鼠标移入移出*/
	var offTop = -100;
	var offLeft = 0;
	$(".index-menu li").hover(function() {
		$(".second-menu").empty();
		/*加载json数据*/
		for (var i = 0; i < typelist.length; i++) {
			if ($(this).attr("data") == typelist[i].parentId) {
				$(".second-menu").append($("<li class='second-menu-li' data='" + typelist[i].id +
					"' >" + typelist[i].name + "</li>"))
			}
		}
		offLeft = $(this).width() + $(this).offset().left;
		offTop = $(this).offset().top;
		$("#showIndex").css("top", offTop - 2 + "px")
		$("#showIndex").css("left", offLeft - 1 + "px")
		$(this).css("background-color", "#f5f5f5");
		$(this).css("color", "#4288c3");
	}, function() {
		$(this).css("background-color", "");
		$(this).css("color", "");
	})
	/*二级分类项li鼠标移入移出*/
	$(".second-menu-li").live("mouseover", function() {
		$(".third-menu").empty();
		/*加载数据*/
		for (var i = 0; i < typelist.length; i++) {
			if ($(this).attr("data") == typelist[i].parentId) {}
			//alert($(document).scrollTop() +":"+$(this).offset().top)
			var ot = $(document).scrollTop() == $(this).offset().top ? offTop : $(this).offset().top;
			var ol = $(this).width() + $(this).offset().left;
			$("#showSecond").css("top", ot - 2 + "px");
			$("#showSecond").css("left", ol + "px")
			$(this).css("background-color", "#4288c3");
			$(this).css("color", "#f5f5f5");
		}
		$(".second-menu-li").live("mouseout", function() {
			$(this).css("background-color", "");
			$(this).css("color", "");
		})
		/*三级分类项li鼠标移入移出*/
		$(".third-menu-li").live("mouseover", function() {
			$(this).css("background-color", "#dddddd");
			$(this).css("color", "#000000");
		})
		$(".third-menu-li").live("mouseout", function() {
			$(this).css("background-color", "");
			$(this).css("color", "");
		})
	})
})

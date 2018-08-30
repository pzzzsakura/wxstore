var app = {};
var aurl = ''
/**
 * 
 */

	
	//audio=document.createElement("audio");
//	app.url=function() {
//		//audio = new Audio('baybay.mp3');
//		audio = new Audio('js/baybay.mp3');
//		//audio.src="js/baybay.mp3";
//	}
//	app.play=function() {
//		audio.play();
//	}
//
//	app.pause=function() {
//		audio.pause();
//	}
app.ajaxFun = function(url,data,successFun,errorFun){
	//type = type || 'post'
	console.log('走了'+aurl+url);
	mui.ajax(aurl+url,{
		data:data,
		dataType:'json',//服务器返回json格式数据
		type:'post',//HTTP请求类型
		timeout:'10000',//超时时间设置为10秒；
		//headers:{'Content-Type':'application/json;charset=UTF-8'},
   // headers:{'Content-Type':' application/x-www-form-urlencoded;charset=UTF-8'},
		success:function(data){
			successFun(data)
		},
		error:function(xhr,e,errorThrown){
			errorFun(e)
		}
	});
}
app.ajaxJF = function(url,data,successFun,errorFun){
	//type = type || 'post'
	console.log('走了'+aurl+url);
	mui.ajax(aurl+url,{
		data:data,
		dataType:'json',//服务器返回json格式数据
		type:'POST',//HTTP请求类型
		timeout:'10000',//超时时间设置为10秒；
		headers:{'Content-Type':'application/json'},
		success:function(data){
			successFun(data)
		},
		error:function(xhr,e,errorThrown){
			errorFun(e)
		}
	});
}
//app.ajaxFun('12345',{a:1},'',function(data){},function(e){})
//判断是否为空
app.isempty=function (str){
		var reg = /^\s*$/g;
		if (reg.test(str)) {
			return true;
		} else{
			return false;
		}
	}
/**
 * js.siblings
 */
app.siblings = function (elem){
    var nodes=[]; //定义一个数组，用来存elem的兄弟元素 
    var previ=elem.previousSibling; 
    while(previ){ //先取o的哥哥们 判断有没有上一个哥哥元素，如果有则往下执行 previ表示previousSibling 
        if(previ.nodeType===1){ 
            nodes.push(previ); 
        } 
        previ=previ.previousSibling; //最后把上一个节点赋给previ 
    } 
    nodes.reverse(); //把顺序反转一下 这样元素的顺序就是按先后的了 
    var nexts=elem.nextSibling; //再取elem的弟弟 
    while(nexts){ //判断有没有下一个弟弟结点 nexts是nextSibling的意思 
        if(nexts.nodeType===1){ 
            nodes.push(nexts); 
        } 
        nexts=nexts.nextSibling; 
    } 
    return nodes; //最后按从老大到老小的顺序，把这一组元素返回 
}


//滚动条在Y轴上的滚动距离
app.scrollTopFun=function(){
　　var scrollTop = 0, bodyScrollTop = 0, documentScrollTop = 0;
　　if(document.body){
　　　　bodyScrollTop = document.body.scrollTop;
　　}
　　if(document.documentElement){
　　　　documentScrollTop = document.documentElement.scrollTop;
　　}
　　scrollTop = (bodyScrollTop - documentScrollTop > 0) ? bodyScrollTop : documentScrollTop;
　　return scrollTop;
}
//文档的总高度
app.scrollHeightFun=function(){
　　var scrollHeight = 0, bodyScrollHeight = 0, documentScrollHeight = 0;
　　if(document.body){
　　　　bodyScrollHeight = document.body.scrollHeight;
　　}
　　if(document.documentElement){
　　　　documentScrollHeight = document.documentElement.scrollHeight;
　　}
　　scrollHeight = (bodyScrollHeight - documentScrollHeight > 0) ? bodyScrollHeight : documentScrollHeight;
　　return scrollHeight;
}
//浏览器视口的高度
app.windowHeightFun=function(){
　　var windowHeight = 0;
　　if(document.compatMode == "CSS1Compat"){
　　　　windowHeight = document.documentElement.clientHeight;
　　}else{
　　　　windowHeight = document.body.clientHeight;
　　}
　　return windowHeight;
}
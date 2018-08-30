

mui.ready(function () {
	var sex=document.getElementById("sex");
	var peoplesum=document.getElementById("peoplesum");
	var wxhead=document.getElementById("wxhead");
	
//	wxhead.src="../img/pic1.png";
	app.ajaxFun('/mmns/welcome/getuserinitdata',{},function(data){
		if (data.success) {
			wxhead.setAttribute("src",data.headImg);
			peoplesum.innerHTML=data.rank;
			if (data.sex==1) {
				sex.innerHTML="英俊的骑士";
			} else{
				sex.innerHTML="美丽的公主";
			}
		
		} else{
			alert(data.errMsg);
		}
	},function(e){})
})
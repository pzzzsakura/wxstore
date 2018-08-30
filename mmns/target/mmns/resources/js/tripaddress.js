

mui.ready(function(){
	
	var proorderId=localStorage.getItem("proorderId");
	app.ajaxGet('mmns/mall/querytrack',{proorderId:proorderId},function(data){
		
	},function(e){})
})
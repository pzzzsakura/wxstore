mui.ready(function(){
	var segmentedControl = document.getElementById('segmentedControl');
	segmentedControl.className = 'mui-segmented-control mui-segmented-control-primary' ;
	
	var item1=document.getElementById("item1ul");//待支付
	var item2=document.getElementById("item2ul");//待发货
	var item3=document.getElementById("item3ul");//待收货
	var item4=document.getElementById("item4ul");//已完成
	var selectwho=document.getElementById("selectwho");
	showone();
	showtwo();
	showthree();
	showfour();
	mui('#selectwho').on('tap','a',function () {
		switch (this.innerHTML){
			case '待支付':
				showone();
				break;
			case '待发货':
				showtwo();
				break;
			case '待收货':
				showthree();
				break;
			case '已完成':
				showfour();
				break;
			
			default:
				break;
		}
	})
	
	//待支付订单列表项点击事件
mui("#item1ul").on("tap","li",function() {
	localStorage.setItem('proorderId',this.getAttribute('proorderId'))
	mui.openWindow("/mmns/mall/nopayorderdetail");
})

//待发货订单列表项点击事件
mui("#item1u2").on("tap","li",function() {
	localStorage.setItem('proorderId',this.getAttribute('proorderId'))
	mui.openWindow("/mmns/mall/orderdetail");
	
})
//待收货订单列表项点击事件
mui("#item1u3").on("tap","li",function() {
	localStorage.setItem('proorderId',this.getAttribute('proorderId'))
	mui.openWindow("/mmns/mall/orderdetail");
})
//已完成订单列表项点击事件
mui("#item1u4").on("tap","li",function() {
	localStorage.setItem('proorderId',this.getAttribute('proorderId'))
	mui.openWindow("/mmns/mall/orderdetail");
})
//设置去支付按钮点击事件
mui("#item1ul").on("tap",".tobuy",function() {
	var check=this;
	var btnArray = ['取消', '确定'];
				mui.confirm('将消费你'+this.parentNode.parentNode.parentNode.getAttribute("productprice")+'膜币', '支付', btnArray, function(e) {
					if (e.index == 1) {
						app.ajaxFun('/mmns/mall/gopay',{proorderId:this.parentNode.parentNode.parentNode.getAttribute('proorderId')},function(data){
							if (data.success) {
								
								check.parentNode.parentNode.parentNode.parentNode.removeChild(check.parentNode.parentNode.parentNode);
							}else{
								 mui.toast('支付失败',{ duration:'1000', type:'div' })
							}
						},function(e){})
					}else {
						mui.toast('支付取消',{ duration:'1000', type:'div' })
					}
				})
				return false;
})
//设置去删除订单按钮点击事件
mui("#item1ul").on("tap",".closeorder",function() {
	var check=this;
	var btnArray = ['否', '是'];
				mui.confirm('是否删除？', '删除订单', btnArray, function(e) {
					if (e.index == 1) {
						app.ajaxFun('/mmns/mall/cancelproductorder',{proorderId:check.parentNode.parentNode.parentNode.getAttribute('proorderId')},function(data){
							if(data.success){
								check.parentNode.parentNode.parentNode.parentNode.removeChild(check.parentNode.parentNode.parentNode);
							}else{
								 mui.toast('订单删除失败',{ duration:'1000', type:'div' }) 
							}
						},function(e){})
						
						
						alert(1)
					} else {
						mui.toast('取消了删除',{ duration:'1000', type:'div' });
					}
				})
				return false;
})

//设置去退货按钮点击事件
mui("#item2ul").on("tap",".backproduct",function() {
	var check=this;
	var btnArray = ['取消', '确定'];
				mui.confirm('确定退货？', '退货', btnArray, function(e) {
					if (e.index == 1) {
						app.ajaxFun('/mmns/mall/salesreturning',{proorderId:check.parentNode.parentNode.parentNode.getAttribute('proorderId')},function(data){
							if(data.success){
								
								check.innerHTML='等待处理';
							}else{
								mui.toast('请求出现异常，请重新请求',{ duration:'1000', type:'div' });
							}
						},function(e){})
						
					} else {
						mui.toast('取消了退货',{ duration:'1000', type:'div' });
					}
				})
				return false;
})
//设置去查物流按钮点击事件
mui("#item2ul").on("tap",".looktrip",function() {
	localStorage.setItem('proorderId',this.parentNode.parentNode.parentNode.getAttribute('proorderId'));
	mui.openWindow('/mmns/mall/tripaddress');
	return false;
})

//设置去查物流按钮点击事件
mui("#item3ul").on("tap",".looktrip",function() {
	localStorage.setItem('proorderId',this.parentNode.parentNode.parentNode.getAttribute('proorderId'));
	mui.openWindow('/mmns/mall/tripaddress');
	return false;
	
})
//设置去确认收货按钮点击事件
mui("#item3ul").on("tap",".sure",function() {
	var check=this;
	app.ajaxFun('/mmns/mall/sure',{proorderId:this.parentNode.parentNode.parentNode.getAttribute('proorderId')},function(data){
		if (data.success) {
			mui.toast('收货成功',{ duration:'1000', type:'div' });
			item3.removeChild(check.parentNode.parentNode.parentNode); 
		} else{
			mui.toast(data.errMsg,{ duration:'1000', type:'div' });
		}
	},function(e){})
	return false;
})


//设置去查物流按钮点击事件
mui("#item4ul").on("tap",".looktrip",function() {
	localStorage.setItem('proorderId',this.parentNode.parentNode.parentNode.getAttribute('proorderId'));
	mui.openWindow('/mmns/mall/tripaddress');
	return false;
})

	function showone(){
			//获取所有订单
	app.ajaxFun('/mmns/mall/getproductorderlist',{enableStatus:1},function(data){
	if (data.success) {
		//orderList
		for(var i=0;i<data.orderList.length;i++){
		var li=document.createElement("li");
		li.className="mui-table-view-cell mui-media";
		li.setAttribute("productprice",data.orderList[i].productOrder.totalMobi);//订单总价格
		li.setAttribute("proorderId",data.orderList[i].productOrder.proorderId);//订单总价格
		
		var html='<img src="'+'../img/263ec3f80f0246568a7d2f81aad97472.jpg'+'" class="mui-media-object mui-pull-left"/>'+
        		'<div class="mui-media-body">'+
        			'<div class="orderproductname">订单号 :<span class="ordercount">'+'D1234564879756'+'</span></div>'+
        			'<div>时间 :<span class="orderproducttime">'+"2017-08-23"+'</span></div>'+
        				'<div style="float: left;">'+
        				'价格 :<span style="line-height: 30px;" class="orderproductprice">'+'99'+'</span>'+
        				'</div>'+
        				'<div style="float: right;">'+
        					'<button style="height: 30px;border-radius: 15px;margin-right:3px" class="closeorder">删除订单</button>'+
        				'<button style="height: 30px;border-radius: 15px;color: #EE895E;border-color:#EE895E ;" class="tobuy">去支付</button>'+
        				'</div>'+
        		'</div>';
				li.innerHTML=html;
				item1.appendChild(li);
					        		
		}
	} else{
		
	}
		
	},function(e){
		
	})
	}
	
	function showtwo(){
			//获取所有订单
	app.ajaxFun('/mmns/mall/getproductorderlist',{enableStatus:2},function(data){
	if (data.success) {
		for(var i=0;i<orderList.length;i++){
		var li=document.createElement("li");
		li.className="mui-table-view-cell mui-media";
		li.setAttribute('proorderId',orderList[i].productOrder.proorderId);//订单Id
		var html='<img src="'+'../img/263ec3f80f0246568a7d2f81aad97472.jpg'+'" class="mui-media-object mui-pull-left"/>'+
					        		'<div class="mui-media-body">'+
					        			'<div class="orderproductname">订单号 :<span class="ordercount">'+'D1234564879756'+'</span></div>'+
					        			'<div>时间 :<span class="orderproducttime">'+"2017-08-23"+'</span></div>'+
					        				'<div style="float: left;">'+
					        				'价格 :<span style="line-height: 30px;" class="orderproductprice">'+'99'+'</span>'+
					        				'</div>'+
					        				'<div style="float: right;">'+
					        					'<button style="height: 30px;border-radius: 15px;margin-right:3px" class="backproduct">申请退货</button>'+
					        				'<button style="height: 30px;border-radius: 15px;color: #EE895E;border-color:#EE895E ;" class="looktrip">查物流</button>'+
					        				'</div>'+
					        		'</div>';
				li.innerHTML=html;
				item2.appendChild(li);
					        		
		}
	} else{
		
	}
		
	},function(e){
		
	})
	}
	
	function showthree(){
			//获取所有订单
	app.ajaxFun('/mmns/mall/getproductorderlist',{enableStatus:3},function(data){
	if (data.success) {
		//orderList
		for(var i=0;i<orderList.lengths;i++){
		var li=document.createElement("li");
		li.className="mui-table-view-cell mui-media";
		li.setAttribute('proorderId',orderList[i].productOrder.proorderId);//订单Id
		var html='<img src="'+'../img/263ec3f80f0246568a7d2f81aad97472.jpg'+'" class="mui-media-object mui-pull-left"/>'+
					        		'<div class="mui-media-body">'+
					        			'<div class="orderproductname">订单号 :<span class="ordercount">'+'D1234564879756'+'</span></div>'+
					        			'<div>时间 :<span class="orderproducttime">'+"2017-08-23"+'</span></div>'+
					        				'<div style="float: left;">'+
					        				'价格 :<span style="line-height: 30px;" class="orderproductprice">'+'99'+'</span>'+
					        				'</div>'+
					        				'<div style="float: right;">'+
					        					'<button style="height: 30px;border-radius: 15px;margin-right:3px" class="looktrip">查物流</button>'+
					        				'<button style="height: 30px;border-radius: 15px;color: #EE895E;border-color:#EE895E ;" class="sure">确认收货</button>'+
					        				'</div>'+
					        		'</div>';
				li.innerHTML=html;
				item3.appendChild(li);
					        		
		}
	} else{
		
	}
		
	},function(e){
		
	})
	}
	
	function showfour(){
			//获取所有订单
	app.ajaxFun('/mmns/mall/getproductorderlist',{enableStatus:4},function(data){
	if (data.success) {
		//orderList
		for(var i=0;i<8;i++){
		var li=document.createElement("li");
		li.className="mui-table-view-cell mui-media";
		li.setAttribute('proorderId',orderList[i].productOrder.proorderId);//订单Id
		var html='<img src="'+'../img/263ec3f80f0246568a7d2f81aad97472.jpg'+'" class="mui-media-object mui-pull-left"/>'+
        		'<div class="mui-media-body">'+
        			'<div class="orderproductname">订单号 :<span class="ordercount">'+'D1234564879756'+'</span></div>'+
        			'<div>时间 :<span class="orderproducttime">'+"2017-08-23"+'</span></div>'+
        				'<div style="float: left;">'+
        				'价格 :<span style="line-height: 30px;" class="orderproductprice">'+'99'+'</span>'+
        				'</div>'+
        				'<div style="float: right;">'+
        				'<button style="height: 30px;border-radius: 15px;color: #EE895E;border-color:#EE895E ;" class="looktrip">查物流</button>'+
        				'</div>'+
        		'</div>';
				li.innerHTML=html;
				item4.appendChild(li);
					        		
		}

	} else{
		
	}
		
	},function(e){
		
	})
	}
	
	function showfive(){
			//获取所有订单
	app.ajaxFun('/mmns/mall/getproductorderlist',{enableStatus:5},function(data){
	if (data.success) {
		//orderList
		for(var i=0;i<8;i++){
		var li=document.createElement("li");
		li.className="mui-table-view-cell mui-media";
		var html='<img src="'+'../img/263ec3f80f0246568a7d2f81aad97472.jpg'+'" class="mui-media-object mui-pull-left"/>'+
					        		'<div class="mui-media-body">'+
					        			'<div class="orderproductname">订单号 :<span class="ordercount">'+'D1234564879756'+'</span></div>'+
					        			'<div>时间 :<span class="orderproducttime">'+"2017-08-23"+'</span></div>'+
					        				'<div style="float: left;">'+
					        				'价格 :<span style="line-height: 30px;" class="orderproductprice">'+'99'+'</span>'+
					        				'</div>'+
					        				'<div style="float: right;">'+
					        					'<button style="height: 30px;border-radius: 15px;margin-right:3px" class="backproduct">申请退货</button>'+
					        				'<button style="height: 30px;border-radius: 15px;color: #EE895E;border-color:#EE895E ;" class="waitsend">查物流</button>'+
					        				'</div>'+
					        		'</div>';
				li.innerHTML=html;
				item2.appendChild(li);
					        		
		}
	} else{
		
	}
		
	},function(e){
		
	})
	}
	
	
		
		
		
	


})

mui.ready(function () {
	//获取切换地址按钮图标
	var excharge=document.getElementById("excharge");
	//获取显示总金额的控件
	var totalprice=document.getElementById("totalprice");
	//获取去结算按钮
	var topay=document.getElementById("topay");
	var lists=document.getElementById("lists");
	var proorderId=localStorage.getItem('proorderId');
	
	var name=document.getElementById('name');
	var phone=document.getElementById('phone');
	var address=document.getElementById('address');
	
	var sendnew=document.getElementById('sendnew');
	var sendcode=document.getElementById('sendcode');
	var sendcodetime=document.getElementById('sendcodetime');
	var sendprice=document.getElementById('sendprice');
	var sendscore=document.getElementById('sendscore');
	var looktrip=document.getElementById('looptrip');
  var addressId="";
	addressId=localStorage.getItem("addressId");
	show();
	//切换收货地址按钮点击事件
	excharge.onclick=function () {
		localStorage.setItem("excharge",1);
		mui.openWindow("/mmns/mall/myshippingaddress");
	}
	
	//订单项点击
	mui('#lists').on('tap','li',function () {
		localStorage.setItem('productId',this.getAttribute('productId'));
		mui.openWindow("/mmns/mall/productdetail");
	})
	//物流按钮
	looktrip.onclick=function () {
		localStorage.setItem('proorderId',proorderId);
		mui.openWindow("/mmns/mall/tripaddress");
	}
	//去支付按钮
	topay.onclick=function () {
				var btnArray = ['取消', '确定'];
				mui.confirm('是否确定？', '确定购买此商品么？', btnArray, function(e) {
					if (e.index == 1) {
						mui.toast('购买成功',{ duration:'1000', type:'div' });
					} else {
						mui.toast('购买失败',{ duration:'1000', type:'div' });
					}
				})
	}

  //将获得的long整型时间转换成Date类型
function datetimeFormat_2(longTypeDate){
 var datetimeType = "";
 var date = new Date();
 date.setTime(longTypeDate);
 datetimeType = date.getFullYear()+"-"+getMonth(date)+"-"+getDay(date)+"&nbsp;"+getHours(date)+":"+getMinutes(date)+":"+getSeconds(date);//yyyy-MM-dd 00:00:00格式日期
 return datetimeType;
}
//返回 01-12 的月份值
function getMonth(date){
 var month = "";
 month = date.getMonth() + 1; //getMonth()得到的月份是0-11
 if(month<10){
     month = "0" + month;
 }
 return month;
}
//返回01-30的日期
function getDay(date){
 var day = "";
 day = date.getDate();
 if(day<10){
     day = "0" + day;
 }
 return day;
}
//返回小时
function getHours(date){
 var hours = "";
 hours = date.getHours();
 if(hours<10){
     hours = "0" + hours;
 }
 return hours;
}
//返回分
function getMinutes(date){
 var minute = "";
 minute = date.getMinutes();
 if(minute<10){
     minute = "0" + minute;
 }
 return minute;
}
//返回秒
function getSeconds(date){
 var second = "";
 second = date.getSeconds();
 if(second<10){
     second = "0" + second;
 }
 return second;
}


  function show() {
		app.ajaxFun('/mmns/mall/getproductorder',{proorderId:proorderId,addressId:addressId},function(data){
			localStorage.removeItem("addressId");
		if (data.success) {
			totalprice.innerText=data.productOrder.totalMobi;
			name.innerHTML=data.address.addressName;
			phone.innerHTML=data.address.addressPhone;
			address.innerHTML=data.address.addressProvince+data.address.addressCity+data.address.addressArea+'&nbsp;'+data.address.addressRow+data.address.addressDetail;
			sendcode.innerHTML=data.productOrder.proorderNumber;
			sendcodetime.innerHTML=datetimeFormat_2(data.productOrder.createTime);
			sendprice.innerHTML=data.productOrder.expressFee;
			sendscore.innerHTML=data.productOrder.productIntegral;
			proorderId=data.productOrder.proorderId;
//			sendnew.innerHTML
//		data.productList.length
			alert(JSON.stringify(data))
		 for (var i=0;i<data.spNumList.length;i++) {
		 	var li = document.createElement("li");
			li.className="mui-table-view-cell";
			li.setAttribute("productId",data.spNumList[i][0].spNum.product.productId);

		 	var str="";
		 	for(var y=0;y<data.spNumList[i][0].propertyValueList.length;y++){
		 		str+=data.spNumList[i][0].propertyValueList[y].value+'&nbsp;&nbsp;';
		 	}
		 	alert(1)
		 	alert(JSON.stringify(data.spNumList[i][0].spNum.product.promotionMobi)+data.spNumList[i][0].spNum.product.productImg);
			if(data.spNumList[i][0].spNum.product.promotionMobi){
				var html ='<img src="'+data.spNumList[i][0].spNum.product.productImg+'" class="mui-media-object mui-pull-left"/>'+
		        		'<div style="margin-top: 10px;" class="mui-media-body">'+
		        			'<div style="width: 100%;height: 15px;">'+
		        			'<div class="productname">'+data.spNumList[i][0].spNum.product.productName+'</div>'+
		        			'<div class="productprice">￥<span>'+data.spNumList[i][0].spNum.product.promotionMobi+'</span></div>'+
		        			'</div>'+
		        			'<div style="width: 100%;">'+
		        				'<div style="float: left;width: 80%;line-height: 25px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;color: darkgray;">'+str+'</div>'+
		        				'<div style="float: right;width: 20%;" class="outproductprice">￥'+data.spNumList[i][0].spNum.product.normalMobi+'</div>'+
		        			'</div>'+
		        			'<div style="width: 100%;clear: both;">'+
		        			'<div style="color:darkgray;float: left;width: 80%;">'+
		        				'已售 <span>'+data.spNumList[i][0].spNum.product.productRepertory.saleNum+'</span>'+
		        			'</div>'+
		        			'<div style="width: 20%;float: right;">x'+data.spNumList[i][0].spNum.spNumNum+'</div>'+
		        			'</div>'+
		        		'</div>'
				
			}else{
				var html ='<img src="'+data.spNumList[i][0].spNum.product.productImg+'" class="mui-media-object mui-pull-left"/>'+
		        		'<div style="margin-top: 10px;" class="mui-media-body">'+
		        			'<div style="width: 100%;height: 15px;">'+
		        			'<div class="productname">'+data.spNumList[i][0].spNum.product.productName+'</div>'+
		        			'<div class="productprice">￥<span>'+data.spNumList[i][0].spNum.product.normalMobi+'</span></div>'+
		        			'</div>'+
		        			'<div style="width: 100%;">'+
		        				'<div style="float: left;width: 80%;line-height: 20px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;color: darkgray;">'+str+'</div>'+
		        				'<div style="float: right;width: 20%;visibility: hidden;" class="outproductprice">撒旦</div>'+
		        			'</div>'+
		        			'<div style="width: 100%;clear: both;">'+
		        			'<div style="color:darkgray;float: left;width: 80%;">'+
		        				'已售 <span>'+data.spNumList[i][0].spNum.product.productRepertory.saleNum+'</span>'+
		        			'</div>'+
		        			'<div style="width: 20%;float: right;">x'+data.spNumList[i][0].spNum.spNumNum+'</div>'+
		        			'</div>'+	
		        		'</div>'
			}

				li.innerHTML=html;
				lists.appendChild(li);
				
				 }
			} else{
				mui.toast(data.Msg,{ duration:'1000', type:'div' });
			}
		
		  
			},function(e){ 
			
			})
	}
		
		
})


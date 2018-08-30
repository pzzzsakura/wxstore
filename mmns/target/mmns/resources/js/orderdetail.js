
mui.ready(function () {
	
		var name=document.getElementById('name');
		var phone=document.getElementById('phone');
		var address=document.getElementById('address');
		
		var sendnew=document.getElementById('sendnew');
		var sendcode=document.getElementById('sendcode');
		var sendcodetime=document.getElementById('sendcodetime');
		var sendpay=document.getElementById('sendpay');
		var sendpaytime=document.getElementById('sendpaytime');
		var sendprice=document.getElementById('sendprice');
		var sendscore=document.getElementById('sendscore');
		var looktrip=document.getElementById('looptrip');
		var lists=document.getElementById("lists");
		var proorderId=localStorage.getItem('proorderId');
		show();
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
	
		function show(){
		
		app.ajaxFun('/mmns/mall/getproductorder',{pageIndex:pageIndex,pageSize:pageSize,priceNew:ppriceNew,lastTime:lastTime,productName:productName,procateId:procateId,isBoom:isBoom,isCombo:isCombo,isFree:isFree},function(data){
		if (data.success) {
			name.innerHTML=data.address.addressName;
			phone.innerHTML=data.address.addressPhone;
			address.innerHTML=data.address.addressDetail;
			sendcode.innerHTML=data.productOrder.proorderNumber;
			sendcodetime.innerHTML=data.productOrder.createTime;
			sendpay.innerHTML=data.productOrder.totalMobi;
			sendpaytime.innerHTML=data.productOrder.payTime;
			sendprice.innerHTML=data.productOrder.expressFee;
			sendscore.innerHTML=data.productOrder.productIntegral;
			proorderId=data.productOrder.proorderId;
		
		 for (var i=0;i<data.spNumList.length;i++) {
//					 
			var li = document.createElement("li");
			li.className="mui-table-view-cell";
			li.setAttribute("productId",data.spNumList[i].spNum.product.productId);
			var str="";
			var priceMobi;
		 	for(var y=0;y<data.spNumList[i].propertyValueList.length;y++){
		 		str+=data.spNumList[i].propertyValueList[y].propertyValue
		 	}
			//console.log(img);
			if(data.spNumList[i].spNum.product.promotionMobi){
				var html ='<img src="'+data.spNumList[i].spNum.product.productImg+'" class="mui-media-object mui-pull-left"/>'+
	        		'<div style="margin-top: 10px;" class="mui-media-body">'+
	        			'<div style="width: 100%;height: 15px;">'+
	        			'<div class="productname">'+data.spNumList[i].spNum.product.productName+'</div>'+
	        			'<div class="productprice">￥<span>'+data.spNumList[i].spNum.product.promotionMobi+'</span></div>'+
	        			'</div>'+
	        			'<div style="width: 100%;">'+
	        				'<div style="float: left;width: 80%;line-height: 25px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;color: darkgray;">'+str+'</div>'+
	        				'<div style="float: right;width: 20%;" class="outproductprice">￥'+data.spNumList[i].spNum.product.normalMobi+'</div>'+
	        			'</div>'+
	        			'<div style="width: 100%;clear: both;">'+
	        			'<div style="color:darkgray;float: left;width: 80%;">'+
	        				'已售 <span>'+data.spNumList[i].spNum.product.productRepertory.saleNum+'</span>'+
	        			'</div>'+
	        			'<div style="width: 20%;float: right;">x'+data.spNumList[i].spNum.spNumNum+'</div>'+
	        			'</div>'+	
	        		'</div>'

			}else{
				var html ='<img src="'+data.spNumList[i].spNum.product.productImg+'" class="mui-media-object mui-pull-left"/>'+
		        		'<div style="margin-top: 10px;" class="mui-media-body">'+
		        			'<div style="width: 100%;height: 15px;">'+
		        			'<div class="productname">'+data.spNumList[i].spNum.product.productName+'</div>'+
		        			'<div class="productprice">￥<span>'+data.spNumList[i].spNum.product.normalMobi+'</span></div>'+
		        			'</div>'+
		        			'<div style="width: 100%;">'+
		        				'<div style="float: left;width: 80%;line-height: 25px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;color: darkgray;">'+str+'</div>'+
		        				'<div style="float: right;width: 20%;" class="outproductprice"></div>'+
		        			'</div>'+
		        			'<div style="width: 100%;clear: both;">'+
		        			'<div style="color:darkgray;float: left;width: 80%;">'+
		        				'已售 <span>'+data.spNumList[i].spNum.product.productRepertory.saleNum+'</span>'+
		        			'</div>'+
		        			'<div style="width: 20%;float: right;">x'+data.spNumList[i].spNum.spNumNum+'</div>'+
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
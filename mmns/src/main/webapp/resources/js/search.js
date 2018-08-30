mui.ready(function(){
	var search=document.getElementById('search');
	var pageIndex=1;
	var content=document.getElementById('lists');
	var inputsearch=document.getElementById('searchinput');
	var productName;
//判断主页滑动是否到达底部
  mui.init({
    pullRefresh: {
      container: '#offCanvasContentScroll',
      up: {
        height:50,//可选.默认50.触发上拉加载拖动距离
        auto:true,//可选,默认false.自动上拉加载一次
        contentrefresh : "正在加载...",//可选，正在加载状态时，上拉加载控件上显示的标题内容
        contentnomore:'没有更多数据了',//可选，请求完毕若没有更多数据时显示的提醒内容；
        callback: pullupRefresh
      }
    }
  });


  /**
   * 上拉加载具体业务实现
   */
  function pullupRefresh() {
    setTimeout(function() {
      mui('#offCanvasContentScroll').pullRefresh().endPullupToRefresh((pageIndex>= count)); //参数为true代表没有更多数据了。
      showproduct();

    }, 1000);
  }
  if (mui.os.plus) {
    mui.plusReady(function() {
      setTimeout(function() {
        mui('#offCanvasContentScroll').pullRefresh().pullupLoading();
      }, 1000);

    });
  } else {
    mui.ready(function() {
      mui('#offCanvasContentScroll').pullRefresh().pullupLoading();
    });
  }
	search.onclick=function () {
		if (app.isempty(inputsearch.value)) {
			
		} else{
			productName=inputsearch.value.trim();
			showproduct();
		}
	}
	
	mui('#lists').on('tap','li',function(){
		localStorage.setItem('productId',this.getAttribute('productId'));
		mui.openWindow("/mmns/mall/productdetail");
	})
		//显示商品列表的方法
		function showproduct () {
		
		app.ajaxFun('/mmns/mall/getproductlistbycondition',{pageIndex:pageIndex++,pageSize:10,productName:encodeURI(productName)},function(data){
		if (data.success) {
		 for (var i=0;i<data.productList.length;i++) {
					 
			var li = document.createElement("li");
			li.className="mui-table-view-cell";
			li.setAttribute("productId",data.productList[i].productId);
//			li.setAttribute("isCombo",data.productList[i].isCombo);
//			li.setAttribute("productName",data.productList[i].productName);
//			li.setAttribute("currentNum",data.productList[i].productRepertory.currentNum);
//			li.setAttribute("productImg",data.productList[i].productImg);
			
			//console.log(img);
			//判断商品是否有折扣价
			if (data.productList[i].promotionMobi) {
//				li.setAttribute("price",data.productList[i].promotionMobi);
				var html ='<img src="'+data.productList[i].productImg+'" class="mui-media-object mui-pull-left"/>'+
		        		'<div style="" class="mui-media-body">'+
		        			'<div class="productname">'+data.productList[i].productName+'</div>'+
		        			'<div class="outproductprice">原价  <span class="lineprice">'+data.productList[i].normalMobi+' 膜币</span></div>'+
		        			'<div class="productprice">折扣价 '+data.productList[i].promotionMobi+' 膜币</div>'+
		        				'<div style="float: left;color:gray">'+
		        				'已售<span style="line-height: 30px;">'+data.productList[i].productRepertory.saleNum+'</span>'+
		        				'</div>'+
		        		'</div>'
			} else{
				li.setAttribute("price",data.productList[i].normalMobi);
				var html ='<img src="'+data.productList[i].productImg+'" class="mui-media-object mui-pull-left"/>'+
		        		'<div style="" class="mui-media-body">'+
		        			'<div class="productname">'+data.productList[i].productName+'</div>'+
		        			'<div class="productprice">价格  <span>'+data.productList[i].normalMobi+'</span>膜币</div>'+
		        				'<div style="float: left;color:gray">'+
		        				'已售<span style="line-height: 30px;">'+data.productList[i].productRepertory.saleNum+'</span>'+
		        				'</div>'+
		        				'<div style="float: right;">'+
		        				'<a class="iconfont icon-jiarugouwuche1" style="color: orchid;font-size: 35px;" class="addshippingcar"></a>'+
		        				'</div>'+
		        		'</div>'
			}
			

				li.innerHTML=html;
				content.appendChild(li);
				
				 }
			} else{
				mui.toast(data.errMsg,{ duration:'1000', type:'div' }); 
			}
		
		  
			},function(e){ 
			
			})
			}

})
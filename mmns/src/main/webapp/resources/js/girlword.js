mui.ready(function () {
	var search=document.getElementById('search');//搜索图标按钮
	var searchinput=document.getElementById('searchinput');//搜索输入框
	var looppic=document.getElementById('looppic');
	var girlone=document.getElementById('gridlawyero');
	var girltwo=document.getElementById('gridlawyert');
	//获取购物车图标按钮
	var shopcar=document.getElementById("shopcar");
	var moretao=document.getElementById('moretao');
	var morebao=document.getElementById('morebao');
	searchinput.blur();
	showlooppic();
	shopcar.onclick=function () {
		mui.openWindow("/mmns/mall/myshoppingcart");
	}
	
	morebao.onclick=function(){
		localStorage.setItem('isCombo',0);
		localStorage.setItem('isBoom',1);
		mui.openWindow("/mmns/mall/girltreasury");
		
	}
	
	moretao.onclick=function(){
		localStorage.setItem('isCombo',1);
		localStorage.setItem('isBoom',0);
		mui.openWindow("/mmns/mall/girltreasury");
	}
	searchinput.onclick=function () {
		mui.openWindow("/mmns/mall/search");
	}
	
	mui('.mui-content').on('tap','li',function () {
		localStorage.setItem("productId",this.getAttribute('productId'));
		mui.openWindow("/mmns/mall/productdetail");
	})
	
//	mui('.mui-content').on('tap','.img',function () {
//		localStorage.setItem('productId',this.parentNode.getAttribute('productId'));
//		mui.openWindow("productdetail.html");
//	})
	function showlooppic () {
	app.ajaxFun('/mmns/mall/getmainbannerlist',{},function(data){
		console.log(JSON.stringify(data));
		if (data.success) {
//		var looplist=['../img/one.jpg','../img/two.jpg','../img/three.jpg'];
		//data.mainBannerList.length
		for (var i=0;i<data.mainBannerList.length;i++) {
			var div=document.createElement("div");
			div.classList.add("mui-slider-item");
			div.setAttribute('productId',data.mainBannerList[i].bannerRedirectId);
			div.innerHTML='<img src="'+data.mainBannerList[i].bannerUrl+'" class="img"/>'
		  	looppic.appendChild(div);
		}
		var div=document.createElement("div");
			div.classList.add("mui-slider-item");
			//data.mainBannerList[0].bannerUrl
			div.innerHTML='<a href="#"><img src="'+data.mainBannerList[0].bannerUrl+'" class="img"/></a>';
			looppic.appendChild(div);
		var div=document.createElement("div");
			div.classList.add("mui-slider-item");
//			data.mainBannerList[data.mainBannerList.length-1].bannerUrl
			div.innerHTML='<a href="#"><img src="'+data.mainBannerList[data.mainBannerList.length-1].bannerUrl+'" class="img"/></a>';
			looppic.insertBefore(div,looppic.firstChild);
			loopingslide();
			} else{
				mui.toast(data.errMsg,{ duration:'1000', type:'div' }) 
			}
	},function(e){
		
	})
	
	app.ajaxFun('/mmns/mall/getproductlistbycondition',{pageIndex:1,pageSize:4,isBoom:1},function(data){
		console.log(JSON.stringify(data));
		if (data.success) {
			for (var i=0;i<4;i++) {
				var li=document.createElement('li');
				li.className='mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-3';
				li.setAttribute('isBoom',1);
				li.setAttribute('productId',data.productList[i].productId);
				var html='<a href="#">'+
		                   '<img src="'+data.productList[i].productImg+'" class="picture" style="float: left;"/></a>';
		                   
		                   li.innerHTML=html;
		                   girlone.appendChild(li);
			}
		} else{
			
		}
	},function(e){})
	
	app.ajaxFun('/mmns/mall/getproductlistbycondition',{pageIndex:1,pageSize:4,isCombo:1},function(data){
		if (data.success) {
			for (var i=0;i<4;i++) {
				var li=document.createElement('li');
				li.className='mui-table-view-cell mui-media mui-col-xs-6 mui-col-sm-3';
				li.setAttribute('isCombo',1);
				li.setAttribute('productId',data.productList[i].productId);
				var html='<a href="#">'+
		                   '<img src="'+data.productList[i].productImg+'" class="picture" style="float: left;"/></a>';
		                   
		                   li.innerHTML=html;
		                   girltwo.appendChild(li);
			}
		} else{
			
		}
	},function(e){})
	}

	//底部选项卡点击事件
	mui('#defaultTab').on('tap','a',function () {
		switch (this.children[0].innerHTML){
			case '女神宝库':
			localStorage.setItem("isCombo",null);
			localStorage.setItem("isBoom",null);
			mui.openWindow('/mmns/mall/girltreasury');
				break;
			case '我的':
			mui.openWindow('/mmns/mall/girlhouse');
				break;
			default:
				break;
		}
	})
	
	function loopingslide() {
		//获得slider插件对象 
		var gallery = mui('#pictures');
			gallery.slider({
		 	interval:5000//自动轮播周期，若为0则不自动播放，默认为0；
		});
	}
		
	
}) 
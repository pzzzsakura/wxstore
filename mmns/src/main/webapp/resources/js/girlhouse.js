
mui.ready(function(){
	//钱包图标按钮
	var mypay=document.getElementById("mypay");
	//订单图标按钮
	var dingdan=document.getElementById("dingdan");
	//购物车图标按钮
	var shopcar=document.getElementById('productcar');
	//收货地址图标按钮
	var address=document.getElementById("productaddress");
	
	var wxhead=document.getElementById('wxhead');//微信头像
	var wxname=document.getElementById('wxname');//微信名
	var wxsex=document.getElementById('wxsex');//微信性别
	var peoplesum=document.getElementById('peoplesum');//公众号第多少位公民
	var Iressage=document.getElementById('Iressage');//瑞莎龄
	var mobi=document.getElementById('mobi');//膜币
	var gameIntegra=document.getElementById('gameIntegra');//积分
	var mobimoney;
	var mobiscore;
	showmyself();
	//底部选项卡点击事件
	mui('#defaultTab').on('tap','a',function () {
		switch (this.children[0].innerHTML){
			case '女神宝库':
			mui.openWindow('/mmns/mall/girltreasury');
				break;
			case '女神世界':
			mui.openWindow('/mmns/mall/girlword');
				break;
			default:
				break;
		}
	})
	//设置上面图标按钮的点击事件
	mypay.onclick=function(){
		mui.openWindow("/mmns/mall/mypay");
		localStorage.setItem('mobimoney',mobimoney);
		localStorage.setItem('mobiscore',mobiscore);
	}
	dingdan.onclick=function(){
		mui.openWindow('/mmns/mall/myorders');
	}
	shopcar.onclick=function(){
		mui.openWindow("/mmns/mall/myshoppingcart");
	}
	address.onclick=function(){
		localStorage.removeItem("excharge");
		mui.openWindow("/mmns/mall/myshippingaddress");
	}
	
	function showmyself(){
		app.ajaxFun('/mmns/mall/getmyinfo',{},function(data){
			if (data.success) {
				wxhead.setAttribute('src',data.wechatAuth.wechatHeadimg);
				wxname.innerHTML=data.wechatAuth.wechatName;
				if (data.wechatAuth.wechatSex==1) {
					wxsex.innerHTML='骑士';
				} else{
					wxsex.innerHTML='公主';
				} 
				peoplesum.innerHTML=data.wechatAuth.num;
				Iressage.innerHTML=data.wechatAuth.personInfo.iage;
				gameIntegra.innerHTML=data.wechatAuth.personInfo.gameIntegral+data.wechatAuth.personInfo.shopIntegral;
				mobi.innerHTML=data.wechatAuth.personInfo.mobi;
				mobimoney=data.wechatAuth.personInfo.mobi;
				mobiscore=data.wechatAuth.personInfo.gameIntegral+data.wechatAuth.personInfo.shopIntegral;
			} else{
				
			}
		},function(e){})
	}
})

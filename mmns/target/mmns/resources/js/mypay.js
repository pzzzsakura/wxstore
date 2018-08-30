
mui.ready(function(){
	//膜币充值按钮
	var btnrecharge=document.getElementsByClassName('btnrecharge')[0];
	var mobimoney=localStorage.getItem('mobimoney');//膜币值
	var mobiscore=localStorage.getItem('mobiscore');//积分值
	var mobi=document.getElementsByClassName('paytwo')[0];//膜币显示控件
	var jifen=document.getElementsByClassName('paytwo')[1];//积分显示控件
	mobi.innerHTML=mobimoney;//膜币控件赋值
	jifen.innerHTML=mobiscore;//积分控件赋值
	
	var timestamp,nonceStr,signature,appid;
	//充值按钮点击事件
	btnrecharge.onclick=function () {
//		e.detail.gesture.preventDefault(); //修复iOS 8.x平台存在的bug，使用plus.nativeUI.prompt会造成输入法闪一下又没了
			var btnArray = ['取消', '确定'];
			mui.prompt('请输入你要充值的金额', '最低充值1元', '充值', btnArray, function(e) {
				if (e.index == 1) {
					var pattern= /^[1-9]\d*$/;
				        if (!(e.value.match(pattern))) {
							 mui.toast('充值金额格式错误',{ duration:'1000', type:'div' }) 
						} else{
						app.ajaxFun('mmns/mall/wechat/config',{url:window.location.pathname},function(data){
					        	timestamp=data.timestamp;
					     		nonceStr=data.nonceStr;
					     		signature=data.signature;
					     		appid=data.appId;
					        	payMoney();
					        },function(e){
					        	
					        })
  							function payMoney () {
//								//微信接口配置  
						        wx.config({  
						            debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。  
						            appId: appid, // 必填，公众号的唯一标识  
						            timestamp: timestamp, // 必填，生成签名的时间戳  
						            nonceStr: nonceStr, // 必填，生成签名的随机串  
						            signature: signature,// 必填，签名，见附录1  
						            jsApiList: ['checkJsApi',  
						                'chooseWXPay'  
						            ] // 必填，需要使用的JS接口列表，所有JS接口列表见附录2  
						        });  
						        
						    wx.ready(function () {  
								app.ajaxFun('mmns/mall/wechat/couponsConfirm',{payMoney:e.value},function(data){
								wx.chooseWXPay({
							 	timestamp: data.timestamp, // 支付签名时间戳，注意微信jssdk中的所有使用timestamp字段均为小写。但最新版的支付后台生成签名使用的timeStamp字段名需大写其中的S字符
							 	nonceStr: data.nonceStr, // 支付签名随机串，不长于 32 位
							 	package: 'prepay_id='+data.prepayId, // 统一支付接口返回的prepay_id参数值，提交格式如：prepay_id=***）
							 	signType: data.signType, // 签名方式，默认为'SHA1'，使用新版支付需传入'MD5'
							  	paySign: data.paySign, // 支付签名
							  	success: function (res) {
							      // 支付成功后的回调函数
								mui.toast('充值成功',{ duration:'1000', type:'div' }) ;
								app.ajaxGet('mmns/mall/getmyinfo',{},function(data){
									if (data.success) {
										mobi.innerHTML=data.wechatAuth.personInfo.mobi;
										jifen.innerHTML=data.wechatAuth.personInfo.gameIntegra;
									} else{
										
									}
								},function(e){})
							  	}
								});
						 
							  },function(e){})
						    });  
						        
						    wx.error(function (res) {  
						        if (typeof error == "function") {  
						                error();  
						            }  
						        });  

							
							}
						}
					} else {
						mui.toast('取消充值',{ duration:'1000', type:'div' }) 
					}
				})
	}
	
	

	
})
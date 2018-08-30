   mui.ready(function () {
		
			mui('.mui-scroll-wrapper').scroll({
				deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
			});
			var	pageIndex=1;//页数
			var	pageSize=10;//每页产品数量
			var	price;//价格由低向高排序
//			var	lastTime="up";//最新商品时间排序
			var	productName=null;//商品名称
			var	procateId=null;//商品种类id，可以为空
			var	isBoom=localStorage.getItem('isBoom');//1 爆品 0否 ，可以为空
			var	isCombo=localStorage.getItem('isCombo');//1套餐 0 否，可以为空
			var	isFree=null;//是否有赠品 1是 0否
			var productImg;
			
			var pplist;//参数组合判断
			var priceNew=null;//判断是点击价格还是更新
			var productId;//商品ID
			var checkeds;//判断用户选择各种样式都不为空是向后台请求返回商品价格
			var bottomcount;//商品加入购物车或购买时选择样式数量
			var comboId;//套餐Id
			var currentNum;//库存
			var shopcar=document.getElementById('shopcar');
			var bottomcurrentNum=document.getElementById('currentNum');
			var bottomprice=document.getElementById('price');
			var bottomImg=document.getElementById('bottomImg');
			var taocanall=document.getElementById("taocanall");
   			var offCanvasWrapper = mui('#offCanvasWrapper');
   			//主界面容器
			var offCanvasInner = offCanvasWrapper[0].querySelector('.mui-inner-wrap');
			 //菜单容器
			var offCanvasSide = document.getElementById("offCanvasSide");
			document.getElementById('list').addEventListener('tap', function() {
				offCanvasWrapper.offCanvas('show');
			});
			 //主界面和侧滑菜单界面均支持区域滚动；
			mui('#offCanvasSideScroll').scroll();
			mui('#offCanvasContentScroll').scroll();
			var search=document.getElementById('search');//搜索图标按钮
			var searchinput=document.getElementById('searchinput');//搜索输入框
			var content = document.getElementById("lists");//主体商品类表内容
			var popoverbottom=document.getElementById("popoverbottom");//底部商品属性选择区域，可滑动区
			var popover=document.getElementById("popover");//侧滑全部商品类别显示的区域
			var surebtn=document.getElementById("btn");//底部选择属性后的确定按钮
			var bottomall=document.getElementById("bottomall");//底部全部显示框
			var taocan=document.getElementById("taocan");//套餐显示div
//			var shownew=document.getElementById("shownew");
			var bottomfirst=document.getElementById("bottomfirst");
			var box=document.getElementById("box");//数量显示框
			var boxmax=document.getElementById("boxmax");//设置数量加减的最大值
			//显示已选择类型得参数名称
			var longstr=document.getElementById("longstr");
			var str='';//定义产品一级类型参数名称的集合
			var count = 2;//
			var childstr=[];//定义产品二级类型参数名称的集合
//			showproduct();//主内容显示方法，商品列表  （不用）
			showleftstyle();//侧滑区域，全部商品类别列表
//			localStorage.removeItem('isBoom');//（不用）
//			localStorage.removeItem('isCombo');//（不用）
			searchinput.blur();
			
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
			//取消左右滑动
			offCanvasInner.addEventListener('drag', function(event) {
			    event.stopPropagation();
			});
			
			shopcar.addEventListener('tap',function(){
				mui.openWindow("/mmns/mall/myshoppingcart");
			})
			//底部选项卡点击事件
			mui('#defaultTab').on('tap','a',function () {
				switch (this.children[0].innerHTML){
					case '女神世界':
					mui.openWindow('/mmns/mall/girlword');
						break;
					case '我的':
					mui.openWindow('/mmns/mall/girlhouse');
						break;
					default:
						break;
				}

			})


			//创建遮罩层
			var mask = mui.createMask(function () {
				bottomall.style.display="none";
			});
			
			
			searchinput.onclick=function () {
				mui.openWindow("/mmns/mall/search");
			}
			
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
	
			//选择参数确定按钮
			surebtn.addEventListener("tap",function () {
				//判断是否有参数
				if (bottomcount) {
					if (checkeds.length==bottomcount) {
						for (var i=0;i<checkeds.length;i++) {
//							str+=checkeds[y].parentNode.parentNode.getAttribute("Id")+""+checkeds[y].parentNode.parentNode.getAttribute("id")+"";
				   			childstr[i]=checkeds[i].innerHTML;//获取产品参数的按钮的类别名称(二级名称)
						}
						
						for (var i=0; i<pplist.length;i++) {
							if (pplist[i][str]) {
								bottomcurrentNum.innerHTML=pplist[i][str][0];
								bottomprice.innerHTML=pplist[i][str][1];
							} else{
								
							}
							
						}
            app.ajaxJF('/mmns/mall/addshoppingcart', {ppManageId:str,productId:productId,checkNum:box.value},
								function(data){
							//返回数据是否加入库成功  data.ppManage.ppmanageMobi/ppManageNum 前者价格后者库存
								if (data.success) {
								mui.toast('加入购物车成功',{ duration:'1000', type:'div' })
									mask.close();
									bottomall.style.display="none";
							} else{
                  mui.toast('加入购物车失败',{ duration:'1000', type:'div' })
							}
						},function(e){
						})
					}else{
						mui.toast('请选择完整属性',{ duration:'1000', type:'div' })
					}
				} else{
				app.ajaxJF('/mmns/mall/addshoppingcart',{ppManageId:str,productId:productId,checkNum:box.value},function(data){
							//返回数据是否加入库成功
							if (data.success) {
								mui.toast('加入购物车成功',{ duration:'1000', type:'div' });
                mask.close();
                bottomall.style.display="none";
							} else{
                mui.toast('加入购物车失败',{ duration:'1000', type:'div' })
							}
						},function(e){
							
						})
				}
			
			})



			mui('#popover').on('tap','button',function(){
				offCanvasWrapper.offCanvas('close');
				//当content下还存在子节点时 循环继续  
		   		 while(content.hasChildNodes()){  
			        content.removeChild(content.firstChild);  
			   	 } 
			   	pageIndex=1;
			   	isBoom=null;
			   	isCombo=null;
			   	isFree=null;
				procateId=this.parentNode.parentNode.getAttribute('procateId'+this.getAttribute("id"));
		   		pullupRefresh();
		   })
			//选择产品套餐点击事件
			mui('#taocan').on('tap','div',function(){
				comboId= this.getAttribute("comboId");
				localStorage.setItem('productId',comboId);
				mui.openWindow('/mmns/mall/productdetail');
			})
		
			mui('#popoverbottom').on('tap','.style',function(){
				longstr.innerHTML="";
				var check=this;
				
				if (this.classList.contains('stylechecked')) {
					bottomprice.innerHTML=price;
					check.classList.remove('stylechecked');
					//获取已选择产品参数的类型数
			   		checkeds=popoverbottom.querySelectorAll('.stylechecked');
					if (checkeds.length==0) {
						longstr.innerHTML="请选择商品类型";
					}else{
						for (var y=0;y<checkeds.length;y++) {
			   				longstr.innerHTML+=checkeds[y].innerHTML;
			   				
			   			}
					}
					
				}else{
					this.classList.add('stylechecked');
			   		var sib = app.siblings(check);
			   		for (var i=0;i<sib.length;i++) {
			   			sib[i].classList.remove('stylechecked');
			   		} 
			   		//获取已选择产品参数的类型数
			   		checkeds=popoverbottom.querySelectorAll('.stylechecked');
			   		//如果已选择类型数等于产品的参数类型数,获取所有已选择参数的信息传到后台请求产品价格
			   		if (checkeds.length==bottomcount) {
              bottomprice.innerHTML=price;
			   			str="";
			   			for (var y=0;y<bottomcount;y++) {
			   				str+=checkeds[y].parentNode.parentNode.getAttribute("parentId")+checkeds[y].parentNode.parentNode.getAttribute("childId"+checkeds[y].getAttribute("id"));
			   				childstr[y]=checkeds[y].innerHTML;//获取产品参数的按钮的类别名称(二级名称)
							longstr.innerHTML+=checkeds[y].innerHTML;
			   			}
						for (var i=0; i<pplist.length;i++) {
							if (pplist[i][str]) {
								bottomcurrentNum.innerHTML=pplist[i][str][0];
								bottomprice.innerHTML=pplist[i][str][1];
								boxmax.setAttribute("data-numbox-max",pplist[i][str][0]);
							} else{

							}
							
						}
							
					
					}else{
						bottomprice.innerHTML=price;
						for (var y=0;y<checkeds.length;y++) {
			   				longstr.innerHTML+=checkeds[y].innerHTML;
			   				
			   			}
					}
				} 
		   })
			
			mui('#showstyle').on('tap','td',function(){
		   		this.classList.add('tdchecked');
		   		var sib = app.siblings(this);
		   		for (var i=0;i<sib.length;i++) {
		   			sib[i].classList.remove('tdchecked');
		   		}
		   		//当content下还存在子节点时 循环继续  
		   		 while(content.hasChildNodes()){  
			        content.removeChild(content.firstChild);  
			   	 }  
		   		pageIndex=1;
		   		switch (this.innerHTML){
		   			case "默认":
		   			priceNew=null;
		   			pullupRefresh();
		   				break;
		   			case "价格":
		   			priceNew="price";
		   			pullupRefresh();
		   				break;
		   			case "最新":
		   			priceNew="new";
		   			pullupRefresh();
		   				break;
		   			default:
		   				break;
		   		}
		   })
			
			mui('#lists').on("tap",'.icon-jiarugouwuche1',function () {
        mui('#boxmax').numbox().setValue(1);
				//点击加入购物车获取商品属性的信息方法 showstyle(商品Id)
				boxmax.setAttribute("data-numbox-max",this.parentNode.parentNode.parentNode.getAttribute("currentNum"));
				productId=this.parentNode.parentNode.parentNode.getAttribute("productId");
				isCombo=this.parentNode.parentNode.parentNode.getAttribute("isCombo");
				productName=this.parentNode.parentNode.parentNode.getAttribute("productName");
				price=this.parentNode.parentNode.parentNode.getAttribute("price");
				productImg=this.parentNode.parentNode.previousSibling.getAttribute("src");
				currentNum=this.parentNode.parentNode.parentNode.getAttribute("currentNum");
				bottomcurrentNum.innerHTML=currentNum;
				bottomprice.innerText=price;
				bottomImg.setAttribute("src",productImg);
				if (isCombo==1) {
					alert(taocanall.innerHTML)
					taocanall.style.display="none";
					longstr.innerHTML=productName;
				} else{
					 while(popoverbottom.hasChildNodes()){  
			        popoverbottom.removeChild(popoverbottom.firstChild);  
			   	 }  
					showstyle();
				}
				
				//显示遮罩层
				mask.show();
				//显示底部选择商品属性的选择栏
				bottomall.style.display="block";
				//让点击反应传不上一层
				return false;
			})
			//商品列表项点击事件
			mui('#lists').on("tap",'li',function () {
				//跳商品详情页
				mui.openWindow("/mmns/mall/productdetail");
				localStorage.setItem("productId",this.getAttribute("productId"));
				localStorage.setItem("isComboo",this.getAttribute("isCombo"));
			})
			
		//显示商品列表的方法
		function showproduct () {
		
		app.ajaxFun('/mmns/mall/getproductlistbycondition',{pageIndex:pageIndex++,pageSize:pageSize,priceNew:priceNew,productName:encodeURI(productName),procateId:procateId,isBoom:isBoom,isCombo:isCombo,isFree:isFree},function(data){
		if (data.success) {
			if (data.count) {
				
				count=parseInt(data.count/10);
			} else{
				count=0;
			}
		 for (var i=0;i<data.productList.length;i++) {
//			var productId=data.productList[i].productId;
					 
			var li = document.createElement("li");
			li.className="mui-table-view-cell";
			li.setAttribute("productId",data.productList[i].productId);
			li.setAttribute("isCombo",data.productList[i].isCombo);
			li.setAttribute("productName",data.productList[i].productName);
			li.setAttribute("currentNum",data.productList[i].productRepertory.currentNum);
			li.setAttribute("productImg",data.productList[i].productImg);
//			alert(productImg)
			//console.log(img);
			//判断商品是否有折扣价
			if (data.productList[i].promotionMobi) {
				li.setAttribute("price",data.productList[i].promotionMobi);
				
				var html ='<img src="'+data.productList[i].productImg+'" class="mui-media-object mui-pull-left" style="margin-right: 20px;"/>'+
		        		'<div style="" class="mui-media-body">'+
		        			'<div class="productname">'+data.productList[i].productName+'</div>'+
		        			'<div class="outproductprice">原价  <span class="lineprice">'+data.productList[i].normalMobi+' 膜币</span></div>'+
		        			'<div class="productprice">折扣价 '+data.productList[i].promotionMobi+' 膜币</div>'+
		        				'<div style="float: left;color:gray">'+
		        				'已售<span style="line-height: 30px;">'+data.productList[i].productRepertory.saleNum+'</span>'+
		        				'</div>'+
		        				'<div style="float: right;">'+
		        				'<a class="iconfont icon-jiarugouwuche1" style="color: orchid;font-size: 35px;" class="addshippingcar"></a>'+
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
				 mui.toast(data.errMsg,{ duration:'1000', type:'div' }) 
			}
		
		  
			},function(e){ 
			
			})
			}

	function showstyle () {
	app.ajaxFun('/mmns/mall/getproductproperty',{productId:productId},function(data){
	pplist=data.ppManageList;
	bottomcount=data.propertyList.length;
	if (data.success) {
	if (data.comboList) {
		//data.comboList.length
		for (var a=0;a<data.comboList.length;a++) {
			var div=document.createElement("div");
			div.setAttribute("comboId",data.comboList[a].comboId);
			div.className="style";
			div.innerHTML=data.comboList[a].comboName;
			taocan.appendChild(div);
			
		}
	} else{
		taocanall.style.display='none';
	}
	
	if (data.propertyList) {
		for (var i=0;i<bottomcount;i++) {
		var div=document.createElement("div");
			var htmlchild='';
			div.setAttribute("parentId",data.propertyList[i][1]);
			//data.propertyList[i][2].length
			for (var x=0;x<data.propertyList[i][2].length;x++) {
				//data.propertyList[i].productProperties[x].propertyValue
				htmlchild+='<button class="style" id="'+x+'">'+data.propertyList[i][2][x][0]+'</button>';
				div.setAttribute("childId"+x,data.propertyList[i][2][x][1]);
			}
			
			var html='<h4 style="margin-top: 30px;">'+data.propertyList[i][0]+'</h4>'+
						'<div style="margin-top: 10px;">'+
						htmlchild+
					'</div>'
						
					div.innerHTML=html;

		
		
					popoverbottom.appendChild(div);
//					popoverbottom.insertBefore(div,bottomfirst);
		}
	}else{
		longstr.innerHTML=productName;
	}
	
	} else{
		
	}

	},function(e){
		
	})
	}
	
	function showleftstyle () {
	app.ajaxFun('/mmns/mall/getallproductcategorylist',{},function(data){
		if (data.success) {
//	data.productCategoryList.length
		for (var i=0;i<data.productCategoryList.length;i++) {
		var div=document.createElement("div");
		
				var htmlchild='';
				for (var x=0;x<data.productCategoryList[i].productChildCategory.length;x++) {
						
						htmlchild+='<button class="style" id="'+x+'">'+data.productCategoryList[i].productChildCategory[x].procateName+'</button>';
						div.setAttribute("procateId"+x,data.productCategoryList[i].productChildCategory[x].procateId);
					}
				var html='<h4 style="margin-top: 30px;">'+data.productCategoryList[i].productParentCategory.procateName+'</h1>'+
						'<div style="margin-top: 10px;">'+
							htmlchild+
						'</div>'
				
			
						
					div.innerHTML=html;
		
		
					
					popover.appendChild(div);
			}
		} else{
			alert(data.errMsg.toString());
		}
	
	},function(e){
		
	})
	}
   })

mui.ready(function () {
	//获取全选按钮
	var allselect= document.getElementById("allselect");
	//获取显示合计金额的控件
	var totalprice=document.getElementById('totalprice');
	//设置被选中商品数
	var blackcount=0;
	var delect=document.getElementById('delete');//删除图标
	var carlist=document.getElementById('carlist');//商品列表
	//获取去结算按钮控件
	var toorder=document.getElementById('tobeorder');//合计价格
	var price=0;//总价格
	var currentNum;//库存
	var carnum;//购物车显示商品列表数量(意思是有几条数据)
	//提交生成未支付订单的参数
	var ppManageId;
	var productId;
	var checkNum;
	var spNumId;
	var request=[];
	showproductcar();

  //获取全部选中按钮控件的集合
  var select;
	var checkbutton=[];
	//删除点击事件
	delect.onclick=function(){
		if (checkbutton.length!=0) {
			var str=[];
      mui.each(checkbutton,function(index,item){
        str[index]=item.parentNode.parentNode.parentNode.getAttribute('spNumId');
      })

      app.ajaxFun('/mmns/mall/removespnums',{"spNumIdList":JSON.stringify(str)},function(data){
				if (data.success) {
         	 mui.each(checkbutton,function(index,item){
            carlist.removeChild(item.parentNode.parentNode.parentNode);
          })
          mui.toast('删除成功',{ duration:'1000', type:'div' })
				} else{
          mui.toast('删除失败',{ duration:'1000', type:'div' })
        }
			},function(e){})

		}else{
			mui.toast('请选择要删除的项',{ duration:'1000', type:'div' })
		}
	}

	mui('#carlist').on('tap','li',function(){
		localStorage.setItem('productId',this.getAttribute('productId'));
		mui.openWindow("/mmns/mall/productdetail");
	})
	//提交订单按钮点击事件
	toorder.onclick=function () {
    if (checkbutton.length!=0) {
      var str = [];
      for (var i=0;i<checkbutton.length;i++){
        var duixiang = {};
        duixiang.spNumId = checkbutton[i].parentNode.parentNode.parentNode.getAttribute(
            'spNumId');
        duixiang.checkNum = mui(checkbutton[i].parentNode.parentNode.nextSibling.children[3]).numbox().getValue();
        duixiang.ppManageId = checkbutton[i].parentNode.parentNode.parentNode.getAttribute(
            'ppManageId');
        duixiang.productId = checkbutton[i].parentNode.parentNode.parentNode.getAttribute(
            'productId');
        str[i] = duixiang;
			}
      // mui.each(checkbutton, function (index, item) {
      //   duixiang.spNumId = item.parentNode.parentNode.parentNode.getAttribute(
      //       'spNumId');
      //   duixiang.checkNum = mui(item.parentNode.parentNode.nextSibling.children[3]).numbox().getValue();
      //   duixiang.ppManageId = item.parentNode.parentNode.parentNode.getAttribute(
      //       'ppManageId');
      //   duixiang.productId = item.parentNode.parentNode.parentNode.getAttribute(
      //       'productId');
      //   str[index] = duixiang;
      // })
      app.ajaxFun('/mmns/mall/clearing', {shoppingListStr: JSON.stringify(str)},
          function (data) {
            if (data.success) {
            	localStorage.setItem("proorderId",data.productOrder.proorderId) ;

                mui.openWindow("/mmns/mall/nopayorderdetail");


            } else {
              if (data.errCode == -1009) {
                mui.toast(data.errMsg, {duration: '1000', type: 'div'})
              }  else {
                mui.toast("系统错误", {duration: '1000', type: 'div'})
              }

            }
          }, function (e) {

          })
    }else{
      mui.toast('选项为空',{ duration:'1000', type:'div' })
		}
	}
  //遍历所有加减按钮为它们设置点击事件start
	mui('#carlist').on('tap','button',function(){
		var check=this;
    if (mui(check.parentNode).numbox().getValue()==0){
      mui(check.parentNode).numbox().setValue(1);
      return false;
    }
    // alert(check.getAttribute("id"))
		var next=(check.parentNode.parentNode).previousSibling;
		if (next.children[0].children[0].classList.contains('selectchecked')) {

			if (check.id==1) {
          price-=(next.parentNode.getAttribute('productprice'));

      } else{
        price+=parseInt(next.parentNode.getAttribute('productprice'));
			}
		}
		totalprice.innerHTML=price;
		return false;
	})


	//遍历所有选中按钮为它们设置点击事件start
	mui('#carlist').on('tap','.icon-xuanze',function(){
		//每个选中按钮点击事件

			var check=this;
			if (this.classList.contains('selectchecked')) {
				--blackcount;
				check.classList.remove('selectchecked');
				allselect.classList.remove('selectchecked');
				var next=(check.parentNode.parentNode).nextSibling;
				//可以用 mui(next.children[3]).numbox().getValue()试试
				price-=(mui(next.children[3]).numbox().getValue())*(next.parentNode.getAttribute('productprice'))
			} else{
        check.classList.add('selectchecked');
        if (++blackcount==carnum) {
          allselect.classList.add('selectchecked');
        }
        var next=check.parentNode.parentNode.nextSibling;
        price+=(mui(next.children[3]).numbox().getValue())*(next.parentNode.getAttribute('productprice'))
			}
		checkbutton=carlist.querySelectorAll('.selectchecked');
    totalprice.innerText=price;
    return false;
	})//遍历所有选中按钮为它们设置点击事件end

	//全选按钮点击事件start
	allselect.addEventListener("tap",function () {
		if (allselect.classList.contains('selectchecked')) {
			mui.each(select,function(index,item){
  			item.classList.remove('selectchecked');
			})
			blackcount=0;
		} else{
			// mui.each(select,function(index,item){
  			// item.classList.add('selectchecked');
			// })
			for (var i=0;i<select.length;i++){
				select[i].classList.add("selectchecked")
			}
			blackcount=carnum;
		}
    checkbutton=carlist.querySelectorAll('.selectchecked');
		price=0;
    for (var i=0;i<checkbutton.length;i++){
      	var next=(checkbutton[i].parentNode.parentNode).nextSibling;
				price+=(mui(next.children[3]).numbox().getValue())*(next.parentNode.getAttribute('productprice'));
    }
    totalprice.innerText=price;
	});//全选按钮点击事件end
	function showproductcar(){
		app.ajaxFun('/mmns/mall/getshoppingcart',{},function(data){
			//24
			carnum=data.spNumList.length;
			if (data.success) {
				var spprice;
				for (var i=0;i<data.spNumList.length;i++) {

					if(data.spNumList[i][0].spNum.product.promotionMobi){
						spprice=data.spNumList[i][0].spNum.product.promotionMobi;
					}else{
						spprice=data.spNumList[i][0].spNum.product.normalMobi;
					}
				var li=document.createElement('li');
				li.classList.add('mui-table-view-cell');
				li.setAttribute('productprice',spprice);
				li.setAttribute('spNumId',data.spNumList[i][0].spNum.spNumId);
				li.setAttribute('spNumNum',data.spNumList[i][0].spNum.spNumNum);
				li.setAttribute('ppManageId',data.spNumList[i][0].spNum.ppManageId);
				li.setAttribute('productId',data.spNumList[i][0].spNum.product.productId);

          var canstr="";
           for (var j=0;j<data.spNumList[i][0].propertyValueList.length;j++) {
             canstr+=data.spNumList[i][0].propertyValueList[j].value+"&nbsp;&nbsp;&nbsp;&nbsp;";
           }

          //currentNum=data.spNumList[i][0].product.productRepertory.currentNum;
          var html=
              '<div style="width: 50%;">'+
              '<div style="float: left;height: 100%;margin-right: 10px;">'+
              '<i class="iconfont icon-xuanze" style="line-height: 100px;font-size: 20px;"></i>'+
              '</div>'+
              '<img src="'+data.spNumList[i][0].spNum.product.productImg+'" style="line-height: 100px;display: block;float: left;"/>'+
              '</div>'+
							'<div style="float: right;width: 50%;margin-top: 10px;">'+
							'<div id="productname" style="margin-bottom: 5px;">'+data.spNumList[i][0].spNum.product.productName+'</div>'+
              '<div style="margin-bottom: 5px;">'+canstr+'</div>'+
							'<div style="float: left;line-height: 33px;">￥<span class="mobimoney">'+spprice+'</span></div>'+
             '<div class="mui-numbox" data-numbox-min=""  data-numbox-max=""  style="float: right">'+
              '<button class="mui-btn mui-btn-numbox-minus" type="button" id="1">-</button>'+
              '<input id="box" class="mui-input-numbox" type="number" value="'+data.spNumList[i][0].spNum.spNumNum+'"/>'+
              '<button class="mui-btn mui-btn-numbox-plus" type="button" id="0">+</button>'+
              '</div>'+
              '</div>';

          li.innerHTML=html;
          carlist.appendChild(li);
          mui('.mui-numbox').numbox();
        }
        select=document.querySelectorAll("i");
			} else{
			}
		},function(e){
      mui.toast('系统错误，请尝试重新进入',{ duration:'1000', type:'div' })
		})
	}
})
	
	

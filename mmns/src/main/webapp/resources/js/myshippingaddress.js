
window.onload=function () {
	mui.init({
		  gestureConfig:{
		   tap: true, //默认为true
//		   doubletap: true, //默认为false
		   longtap: true, //默认为false
//		   swipe: false, //默认为true
//		   drag: true, //默认为true
		   hold:true,//默认为false，不监听
		   release:true//默认为false，不监听
		  }
		});
	var changeaddress;
	//弹出框
	var popover=document.getElementById("popover");
	//获得所有地址list列表
	var addresses=document.getElementById("addresses");
	//添加地址按钮
	var addaddress=document.getElementById("addaddress");
	var excharge=localStorage.getItem("excharge");
	showaddresses();
	
	var mask=mui.createMask(function () {
		popover.style.display="none";
		document.body.style.overflow='scroll';
		document.documentElement.style.overflow='';
	});

if (excharge==1){
  mui('#addresses').on("tap","li",function () {
    localStorage.removeItem("excharge");
    localStorage.setItem("addressId",this.getAttribute("addressId"));
    mui.openWindow("nopayorderdetail.html");
  })

}



	mui('#addresses').on("longtap","li",function () {
		mask.show();
		popover.style.display="block"; 
		document.body.style.overflow='hidden';
		document.documentElement.style.overflow='hidden';
		changeaddress=this;
	})
	
		//收货地址集合所有项的点击事件
		mui('#popover').on("tap","li",function () {
			var thisaddress=this;
//			var moren=document.getElementsByClassName("moren")[0];
		switch (this.innerHTML){
			case '设为默认':
			app.ajaxFun('/mmns/mall/modifyisdefaultaddress',{addressId:changeaddress.getAttribute('addressId')},function(data){
				if(data.success){
					changeaddress.children[0].children[1].children[0].style.visibility="visible";
					changeaddress.classList.add('border');
			   		var sib = app.siblings(changeaddress);
			   		for (var i=0;i<sib.length;i++) {
			   			sib[i].children[0].children[1].children[0].style.visibility="hidden";
			   			sib[i].classList.remove('border');
			   		}	
				}else{
					mui.toast('设置失败',{ duration:'1000', type:'div' });
				}
				
			},function(e){})
			
			popover.style.display="none";
			mask.close();
				break;
			case '编辑':
			localStorage.setItem('isadd',0);
			localStorage.setItem('addressId',changeaddress.getAttribute('addressId'))
			mui.openWindow("/mmns/mall/addshippingaddress");
			popover.style.display="none";
			mask.close();
				break;
			case '删除':
			app.ajaxFun('/mmns/mall/removeaddress',{addressId:changeaddress.getAttribute('addressId')},function(data){
				if(data.success){
					addresses.removeChild(changeaddress);
					popover.style.display="none";
					mask.close();
				}else{
					alert('删除失败')
				}
				
			},function(e){})
			
				break;
			default:
				break;
		}
		document.body.style.overflow='scroll';
		document.documentElement.style.overflow='';
//		mui('.mui-popover').popover("hide");
	})
		
	addaddress.onclick=function () {
		localStorage.setItem('isadd',1)
		mui.openWindow("/mmns/mall/addshippingaddress");
	}
	function showaddresses () {
	app.ajaxFun('/mmns/mall/getaddresslist',{},function(data){
	if (data.success) {
		//data.addressList.length
    for (var i=0;i<data.addressList.length;i++) {
      var li=document.createElement("li");
      li.className="mui-table-view-cell";
      li.setAttribute('addressId',data.addressList[i].addressId);
      //data.addressList[i].isDefault==1
      if (data.addressList[i].isDefault==1) {
        var html='<div style="width: 100%;height: 30px;">'+
            '<span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width: 30%;display: block;float: left;font-size: 17px;">'+data.addressList[i].addressName+'</span>'+
            '<div style="float: right;width: 30%; class="moren">'+
            '<i class="iconfont icon-default" style="display: block;float: right;font-size: 40px;"></i>'+
            '</div>'+
            '<span style="display: block;float: right;width: 40%;text-align: center;">'+data.addressList[i].addressPhone+'</span>'+
            '</div>'+
            '<div><span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">'+data.addressList[i].addressProvince+data.addressList[i].addressCity+data.addressList[i].addressArea+'&nbsp;'+data.addressList[i].addressRow+data.addressList[i].addressDetail+'</span></div>';

        li.innerHTML=html;
        li.classList.add('border');
      } else{
        var html='<div style="width: 100%;height: 30px;">'+
            '<span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;width: 30%;display: block;float: left;font-size: 17px;">'+data.addressList[i].addressName+'</span>'+
            '<div style="float: right;width: 30%;">'+
            '<i class="iconfont icon-default" style="display: block;float: right;font-size: 40px;visibility: hidden;"></i>'+
            '</div>'+
            '<span style="display: block;float: right;width: 40%;text-align: center;">'+data.addressList[i].addressPhone+'</span>'+
            '</div>'+
            '<div><span style="overflow: hidden;white-space: nowrap;text-overflow: ellipsis;">'+data.addressList[i].addressProvince+data.addressList[i].addressCity+data.addressList[i].addressArea+'&nbsp;'+data.addressList[i].addressRow+data.addressList[i].addressDetail+'</span></div>';
        li.innerHTML=html;
      }
      addresses.appendChild(li);
    }

  } else{
		alert('网络异常');
	}
	
	},function(e){
		
	})
	}

}
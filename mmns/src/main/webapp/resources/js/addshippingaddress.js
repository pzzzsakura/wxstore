mui.ready(function(){
	
	//收货人姓名
	var name=document.getElementById("buyname");
	//收货人手机号
	var buyphone=document.getElementById('buyphone');
	//收货人地址省市区
	var cityResult3=document.getElementById("cityResult3");
	//地址的街道信息
	var buystreet=document.getElementById("buystreet");
	//收货地邮编
	var buyzipcode=document.getElementById("buyzipcode");
	//收货详细地址
	var buydetailaddress=document.getElementById("buydetailaddress");
//	var data={};
	var sheng,shi, qu;
	//保存按钮
	var conservation=document.getElementById("conservation");
	var moren=document.getElementById('moren');
	var isadd=localStorage.getItem('isadd');
	var addressId=localStorage.getItem('addressId');
	var isDefault=0;
	if(isadd==1){
		
	}else{
		showaddress();
	}
	//弹出地址选择框
	var cityPicker3 = new mui.PopPicker({
						layer: 3
					});
					cityPicker3.setData(cityData3);
					var showCityPickerButton = document.getElementById('showCityPicker3');
					// cityResult3 = document.getElementById('cityResult3');
					showCityPickerButton.addEventListener('tap', function(event) {
						cityPicker3.show(function(items) {sheng=(items[0] || {}).text;
						shi= (items[1] || {}).text;
						qu=(items[2] || {}).text;
              cityResult3.innerText =  (items[0] || {}).text + " " + (items[1] || {}).text + " " + (items[2] || {}).text;
							//返回 false 可以阻止选择框的关闭
							//return false;
						});
					}, false);
	
	conservation.addEventListener("tap",function () {

		if (name.value==""||isempty(name.value)||buyphone.value==""||isempty(buyphone.value)||buyzipcode.value==""||isempty(buyzipcode.value)||buystreet.value==""||isempty(buystreet)||cityResult3.innerHTML==""||isempty(cityResult3.innerHTML)||buydetailaddress.value==""||isempty(buydetailaddress.value)) {
			alert("填写完整，不能为空！");
		} else if(!phone(buyphone.value)){
			alert("输入手机号格式错误");
		}else if(!zipcode(buyzipcode.value)){
			alert("输入邮编格式错误");
		}else{
			if(isadd==1){
			app.ajaxJF('/mmns/mall/addaddress',{addressName:name.value,addressPhone:buyphone.value,addressProvince:sheng,addressCity:shi,addressArea:qu,addressRow:buystreet.value,postCode:buyzipcode.value,addressDetail:buydetailaddress.value,isDefault:isDefault},function(data){
				if (data.success) {
          mui.toast('地址添加成功',{ duration:'1000', type:'div' });
					mui.openWindow("/mmns/mall/myshippingaddress");
				} else{
					mui.toast('地址添加失败',{ duration:'1000', type:'div' });
				}
			},function(e){
			})
			}else{
        app.ajaxJF('/mmns/mall/modifyaddress',{addressId:addressId,addressName:name.value,addressPhone:buyphone.value,addressProvince:sheng,addressCity:shi,addressArea:qu,addressRow:buystreet.value,postCode:buyzipcode.value,addressDetail:buydetailaddress.value,isDefault:isDefault},function(data){
				if (data.success) {
          mui.toast('地址修改成功',{ duration:'1000', type:'div' });
					mui.openWindow("/mmns/mall/myshippingaddress");
				} else{
					mui.toast('地址修改失败',{ duration:'1000', type:'div' });
					
				}
			},function(e){
				
			})
		}
		}
	})

  moren.addEventListener("tap",function(){

    if (this.checked) {
      isDefault=0;
    } else{
      isDefault=1;
    }
  })

  function zipcode (str) {

        var re= /^[1-9][0-9]{5}$/;
        if(re.test(str)){
        	return true;
        }else{
          return false;
        }
	}
	function phone (str) {
          var myreg=/^[1][3,4,5,7,8][0-9]{9}$/;  
          if (!myreg.test(str)) {  
              return false;  
          } else {  
              return true;  
          }  
		}
	function isempty(str){
		var reg = /^\s*$/g;
		if (reg.test(str)) {
			return true;
		} else{
			return false;
		}
	}
	
	function showaddress () {
		app.ajaxFun('/mmns/mall/getcurrentaddress',{addressId:addressId},function(data){
			if (data.success) {
					name.value=data.address.addressName;
					buyphone.value=data.address.addressPhone;
          cityResult3.innerHTML=data.address.addressProvince+data.address.addressCity+data.address.addressArea;
					buystreet.value=data.address.addressRow;
					buyzipcode.value=data.address.postCode;
					buydetailaddress.value=data.address.addressDetail;
					
					if (data.address.isDefault==1) {
						moren.checked=true;
						isDefault=1;
					}
				} else{
					mui.toast('网络异常',{ duration:'1000', type:'div' });
					
				}
			},function(e){
				
			})
	}
})

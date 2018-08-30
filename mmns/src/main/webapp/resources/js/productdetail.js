window.onload = function () {
  mui('.mui-scroll-wrapper').scroll({
    deceleration: 0.0005 //flick 减速系数，系数越大，滚动速度越慢，滚动距离越小，默认值0.0006
  });

  //库存
  var currentNum;
  //获取弹出框选择样式
  var popover = document.getElementById("popover");
  //获取选择型号和数量的确定按钮
  var surebtn = document.getElementById("btn");
  var orginnav = document.getElementById('orginnav');
  var box = document.getElementById("box");
  //获取提交订单按钮
  var tobuy = document.getElementById("tobuy");
  //获取加入购物车按钮图标
  var addshoppingcar = document.getElementById("addshoppingcar");
  var taocan = document.getElementById("taocan");//套餐显示div
  var bottomfirst = document.getElementById("bottomfirst");//底部选择产品或套餐数量框
  var bottomall = document.getElementById("bottomall");//底部全部显示框
  var picture = document.getElementById("picture");//底部商品图片信息
  var currentNum = document.getElementById('currentNum');//底部商品库存量
  var productId = localStorage.getItem("productId");//获取产品Id
  var isCombo = localStorage.getItem("isComboo");//获取是否是套餐
  var taocanall = document.getElementById("taocanall");

  var title = document.getElementById("title");
  var pictures = document.getElementById("pictures");//主页轮播图
  var gardgirlprice = document.getElementById("gardgirlprice");//女神价
  var outproductprice = document.getElementById("outproductprice");//获得女神分
  var productName = document.getElementById("productname");//商品名称显示框
  var salenum = document.getElementById("salenum");
  var showpic = document.getElementById("showpic");//商品详情图
  var dots = document.getElementById('dots');//小圆点
  var checkeds;//底部选中状态的属性类型数量
  var productname;//获得的商品名称
  var istobuy;//设置参数确定按钮是否跳页  1跳 0不跳
  var longstr = document.getElementById("longstr");//显示已选择类型得参数名称
  var bottomprice = document.getElementById("price");//底部商品价格信息
  var bottomcurrentNum = document.getElementById("currentNum");
  var pplist;
  var str = "";//定义产品一级类型参数名称的集合
  var childstr = [];//定义产品二级类型参数名称的集合
  var bottomcount;//商品加入购物车或购买时选择样式数量
  detailproduct();
  //创建遮罩层
  var mask = mui.createMask(function () {
    bottomall.style.display = "none";
  });

//	dots.firstChild.classList.add('mui-active');
  var slider = mui("#slider");
  slider.slider({
    interval: 5000
  });

  //底部属性选择确定按钮
  surebtn.addEventListener("tap", function () {

    //判断是否有参数
    if (bottomcount) {
      if (checkeds.length == bottomcount) {
        for (var i = 0; i < checkeds.length; i++) {
          childstr[i] = checkeds[i].innerHTML;//获取产品参数的按钮的类别名称(二级名称)
        }

        for (var i = 0; i < pplist.length; i++) {
          if (pplist[i][str]) {
            bottomcurrentNum.innerHTML = pplist[i][str][0];
            bottomprice.innerHTML = pplist[i][str][1];
          } else {

          }

        }
        alert(istobuy)

        if (istobuy == 1) {
          app.ajaxFun('/mmns/mall/clearing', {
            propertyNameList: str,
            propertyValueList: childstr,
            productId: productId,
            selectCount: box.value
          }, function (data) {
            //返回数据库存是否够，然后是是否有默认地址。根据这些跳不跳页面
            if (data.success) {
              mui.openWindow("/mmns/mall/nopayorderdetail");
            } else {
              if (data.errCode == -1009) {
                mui.toast('库存不足', {duration: '1000', type: 'div'})
              } else {
                mui.openWindow("/mmns/mall/myshippingaddress");
              }
            }
          }, function (e) {

          })

        } else {
          app.ajaxJF('/mmns/mall/addshoppingcart',
              {ppManageId: str, productId: productId, checkNum: box.value},
              function (data) {
                //返回数据是否加入库成功 data.ppManage.ppmanageMobi/ppManageNum 前者价格后者库存
                if (data.success) {
                  mui.toast('加入购物车成功', {duration: '1000', type: 'div'})
                  mask.close();
                  bottomall.style.display = "none";
                } else {
                  mui.toast('加入购物车失败', {duration: '1000', type: 'div'})
                }
              }, function (e) {

              })

        }

      } else {
        mui.toast('请选择完整产品参数', {duration: '1000', type: 'div'})
      }
    } else {
      if (istobuy == 1) {
        app.ajaxFun('/mmns/mall/clearing', {
          propertyNameList: str,
          propertyValueList: childstr,
          productId: productId,
          selectCount: box.value
        }, function (data) {
          //返回数据库存是否够，然后是是否有默认地址。根据这些跳不跳页面
          if (data.success) {
            mui.openWindow("/mmns/mall/nopayorderdetail");
          } else {
            if (data.errCode == -1009) {
              mui.toast('库存不足', {duration: '1000', type: 'div'})
            } else {
              mui.openWindow("/mmns/mall/myshippingaddress");
            }
          }
        }, function (e) {

        })
      } else {

        app.ajaxJF('/mmns/mall/addshoppingcart',
            {ppManageId: ppManageId, productId: productId, checkNum: box.value},
            function (data) {
              //返回数据是否加入库成功 data.ppManage.ppmanageMobi/ppManageNum 前者价格后者库存
              if (data.success) {
                mui.toast('加入购物车成功', {duration: '1000', type: 'div'})
                mask.close();
                bottomall.style.display = "none";
              } else {
                mui.toast('加入购物车失败', {duration: '1000', type: 'div'})
              }
            }, function (e) {

            })
      }
    }

  })
  //选择数量的按钮(不用)
//		mui('#bottomfirst').on('tap','button',function(){
//			
//		})

  //提交订单按钮点击事件
  tobuy.addEventListener("tap", function () {
    istobuy = 1;
    //判断是否是套餐
    if (isCombo == 1) {
      taocanall.style.display = "none";
      longstr.innerHTML = productname;
    } else {
      while (popover.hasChildNodes()) {
        popover.removeChild(popover.firstChild);
      }
      showstyle();

    }
    mask.show();
    bottomall.style.display = "block";
  });
  //加入购物车按钮点击事件
  addshoppingcar.addEventListener("tap", function () {
    istobuy = 0;
    //判断是否是套餐
    if (isCombo == 1) {
      taocanall.style.display = "none";
      longstr.innerHTML = productname;
    } else {
//			taocanall.style.display="none";
      while (popover.hasChildNodes()) {
        popover.removeChild(popover.firstChild);
      }
      showstyle();
    }
    mask.show();
    bottomall.style.display = "block";
  })

  //选择产品套餐点击事件   //页面要重新加载
  mui('#taocan').on('tap', 'div', function () {
    localStorage.setItem("productId", this.getAttribute("comboId"));
    localStorage.setItem("isCombo", 1);
//		isCombo=1;
    document.location.reload();
  })

  mui('#popover').on('tap', '.style', function () {
    longstr.innerHTML = "";
    var check = this;

    if (this.classList.contains('stylechecked')) {
      bottomprice.innerHTML = gardgirlprice.innerHTML;
      check.classList.remove('stylechecked');
      //获取已选择产品参数的类型数
      checkeds = popover.querySelectorAll('.stylechecked');
      if (checkeds.length == 0) {
        longstr.innerHTML = "请选择商品类型";
      } else {
        for (var y = 0; y < checkeds.length; y++) {
          longstr.innerHTML += checkeds[y].innerHTML;

        }
      }

    } else {
      this.classList.add('stylechecked');
      var sib = app.siblings(check);
      for (var i = 0; i < sib.length; i++) {
        sib[i].classList.remove('stylechecked');
      }
      //获取已选择产品参数的类型数
      checkeds = popover.querySelectorAll('.stylechecked');
      //如果已选择类型数等于产品的参数类型数,获取所有已选择参数的信息传到后台请求产品价格
      if (checkeds.length == bottomcount) {
        str = '';
        bottomprice.innerHTML = gardgirlprice.innerHTML;
        for (var y = 0; y < bottomcount; y++) {
          str += checkeds[y].parentNode.parentNode.getAttribute("Id") + ""
              + checkeds[y].parentNode.parentNode.getAttribute("childId"
                  + checkeds[y].getAttribute("id"));
          childstr[y] = checkeds[y].innerHTML;//获取产品参数的按钮的类别名称(二级名称)
          longstr.innerHTML += checkeds[y].innerHTML;
        }
        for (var i = 0; i < pplist.length; i++) {
          if (pplist[i][str]) {
            bottomcurrentNum.innerHTML = pplist[i][str][0];
            bottomprice.innerHTML = pplist[i][str][1];
            boxmax.setAttribute("data-numbox-max", pplist[i][src][0]);
          } else {

          }

        }

      } else {
        // bottomprice.innerHTML=gardgirlprice.innerHTML;
        for (var y = 0; y < checkeds.length; y++) {
          longstr.innerHTML += checkeds[y].innerHTML;

        }
      }
    }
  })

  function showstyle() {
    app.ajaxFun('/mmns/mall/getproductproperty', {productId: productId},
        function (data) {
          pplist = data.ppManageList;
          bottomcount = data.propertyList.length;
          bottomcurrentNum.innerHTML = currentNum;
          bottomprice.innerHTML = gardgirlprice.innerHTML;
          if (data.success) {
            if (data.comboList) {
              //data.comboList.length
              for (var a = 0; a < data.comboList.length; a++) {
                var div = document.createElement("div");
                div.setAttribute("comboId", data.comboList[a].comboId);
                div.className = "style";
                div.innerHTML = data.comboList[a].comboName;
                taocan.appendChild(div);
              }
            } else {
              taocanall.style.display = 'none';
            }

            if (data.propertyList) {
              for (var i = 0; i < bottomcount; i++) {
                var div = document.createElement("div");
                var htmlchild = '';
                div.setAttribute('Id', data.propertyList[i][1]);
                //data.propertyList[i][2].length
                for (var x = 0; x < data.propertyList[i][2].length; x++) {
                  //data.propertyList[i].productProperties[x].propertyValue
                  htmlchild += '<button class="style" id="' + x + '">'
                      + data.propertyList[i][2][x][0] + '</button>';
                  div.setAttribute("childId" + x,
                      data.propertyList[i][2][x][1]);
                }

                var html = '<h4 style="margin-top: 30px;">'
                    + data.propertyList[i][0] + '</h4>' +
                    '<div style="margin-top: 10px;">' +
                    htmlchild +
                    '</div>'

                div.innerHTML = html;

                popover.appendChild(div);
//					popover.insertBefore(div,bottomfirst);
              }
            } else {
              longstr.innerHTML = productName;
            }

          } else {

          }

        }, function (e) {

        })
  }

  function detailproduct() {
    app.ajaxFun('/mmns/mall/getproductdetailinfo', {productId: productId},
        function (data) {

          if (data.success) {
            if (data.product.promotionMobi) {
              gardgirlprice.innerHTML = data.product.promotionMobi;
              outproductprice.innerHTML = data.product.normalMobi;
            } else {
              gardgirlprice.innerHTML = data.product.normalMobi;
            }
            title.innerHTML = data.product.productName;
            productName.innerHTML = data.product.productName;
            salenum.innerHTML = data.product.productRepertory.saleNum;
            currentNum = data.product.productRepertory.currentNum;
            productname = data.product.productName;//商品名
            //循环首轮播图显示
            var firstpic = document.getElementById('firstpic');
            //循环尾轮播图显示
            document.getElementById('lastpic').setAttribute("src",
                data.product.productBannerList[data.product.productBannerList.length
                - 1].probannerUrl);
            firstpic.setAttribute("src",
                data.product.productBannerList[0].probannerUrl);
            //轮播图加载
            for (var i = 0; i < data.product.productBannerList.length; i++) {
              var div = document.createElement("div");
              div.className = "mui-slider-item";
              div.innerHTML = '<a href="#"><img src="'
                  + data.product.productBannerList[i].probannerUrl
                  + '" class="img"/></a>';
              pictures.insertBefore(div, firstpic.parentNode);
              //轮播图小圆点
              var dotdiv = document.createElement('div');
              if (i == 0) {
                dotdiv.classList.add('mui-indicator', 'mui-active');
              } else {
                dotdiv.classList.add('mui-indicator');
              }
              dots.appendChild(dotdiv);
            }
            //详情图显示
            for (var j = 0; j < data.product.productImgList.length; j++) {
              var div = document.createElement("div");
              div.innerHTML = '<img src="'
                  + data.product.productImgList[j].proimgUrl
                  + '" style="width: 100%;display: block;"/>';
              showpic.appendChild(div);
            }
          } else {
            mui.toast(data.errMsg, {duration: '1000', type: 'div'})
          }

        }, function (e) {

        })
  }
}
<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.TourOrder" %>
<%@ page import="com.chengxusheji.po.Tour" %>
<%@ page import="com.chengxusheji.po.UserInfo" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<TourOrder> tourOrderList = (List<TourOrder>)request.getAttribute("tourOrderList");
    //获取所有的tourObj信息
    List<Tour> tourList = (List<Tour>)request.getAttribute("tourList");
    //获取所有的userObj信息
    List<UserInfo> userInfoList = (List<UserInfo>)request.getAttribute("userInfoList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    Tour tourObj = (Tour)request.getAttribute("tourObj");
    String startDate = (String)request.getAttribute("startDate"); //出发日期查询关键字
    String telephone = (String)request.getAttribute("telephone"); //联系电话查询关键字
    UserInfo userObj = (UserInfo)request.getAttribute("userObj");
    String orderTime = (String)request.getAttribute("orderTime"); //下单时间查询关键字
    String shzt = (String)request.getAttribute("shzt"); //审核状态查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>订单查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="row"> 
		<div class="col-md-9 wow fadeInDown" data-wow-duration="0.5s">
			<div>
				<!-- Nav tabs -->
				<ul class="nav nav-tabs" role="tablist">
			    	<li><a href="<%=basePath %>index.jsp">首页</a></li>
			    	<li role="presentation" class="active"><a href="#tourOrderListPanel" aria-controls="tourOrderListPanel" role="tab" data-toggle="tab">订单列表</a></li>
			    	<li role="presentation" ><a href="<%=basePath %>TourOrder/tourOrder_frontAdd.jsp" style="display:none;">添加订单</a></li>
				</ul>
			  	<!-- Tab panes -->
			  	<div class="tab-content">
				    <div role="tabpanel" class="tab-pane active" id="tourOrderListPanel">
				    		<div class="row">
				    			<div class="col-md-12 top5">
				    				<div class="table-responsive">
				    				<table class="table table-condensed table-hover">
				    					<tr class="success bold"><td>序号</td><td>旅游景点</td><td>出发日期</td><td>出行人数</td><td>总价格</td><td>联系电话</td><td>下单时间</td><td>审核状态</td><td>操作</td></tr>
				    					<% 
				    						/*计算起始序号*/
				    	            		int startIndex = (currentPage -1) * 5;
				    	            		/*遍历记录*/
				    	            		for(int i=0;i<tourOrderList.size();i++) {
					    	            		int currentIndex = startIndex + i + 1; //当前记录的序号
					    	            		TourOrder tourOrder = tourOrderList.get(i); //获取到订单对象
 										%>
 										<tr>
 											<td><%=currentIndex %></td>
 											<td><%=tourOrder.getTourObj().getTourName() %></td>
 											<td><%=tourOrder.getStartDate() %></td>
 											<td><%=tourOrder.getTotalPersonNum() %></td>
 											<td><%=tourOrder.getTotalPrice() %></td>
 											<td><%=tourOrder.getTelephone() %></td>
 											<td><%=tourOrder.getOrderTime() %></td>
 											<td><%=tourOrder.getShzt() %></td>
 											<td>
 												<a href="<%=basePath  %>TourOrder/<%=tourOrder.getOrderId() %>/frontshow"><i class="fa fa-info"></i>&nbsp;查看</a>&nbsp;
 												<a href="#" onclick="tourOrderEdit('<%=tourOrder.getOrderId() %>');" style="display:none;"><i class="fa fa-pencil fa-fw"></i>编辑</a>&nbsp;
 												<a href="#" onclick="tourOrderDelete('<%=tourOrder.getOrderId() %>');" style="display:none;"><i class="fa fa-trash-o fa-fw"></i>删除</a>
 											</td> 
 										</tr>
 										<%}%>
				    				</table>
				    				</div>
				    			</div>
				    		</div>

				    		<div class="row">
					            <div class="col-md-12">
						            <nav class="pull-left">
						                <ul class="pagination">
						                    <li><a href="#" onclick="GoToPage(<%=currentPage-1 %>,<%=totalPage %>);" aria-label="Previous"><span aria-hidden="true">&laquo;</span></a></li>
						                     <%
						                    	int startPage = currentPage - 5;
						                    	int endPage = currentPage + 5;
						                    	if(startPage < 1) startPage=1;
						                    	if(endPage > totalPage) endPage = totalPage;
						                    	for(int i=startPage;i<=endPage;i++) {
						                    %>
						                    <li class="<%= currentPage==i?"active":"" %>"><a href="#"  onclick="GoToPage(<%=i %>,<%=totalPage %>);"><%=i %></a></li>
						                    <%  } %> 
						                    <li><a href="#" onclick="GoToPage(<%=currentPage+1 %>,<%=totalPage %>);"><span aria-hidden="true">&raquo;</span></a></li>
						                </ul>
						            </nav>
						            <div class="pull-right" style="line-height:75px;" >共有<%=recordNumber %>条记录，当前第 <%=currentPage %>/<%=totalPage %> 页</div>
					            </div>
				            </div> 
				    </div>
				</div>
			</div>
		</div>
	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>订单查询</h1>
		</div>
		<form name="tourOrderQueryForm" id="tourOrderQueryForm" action="<%=basePath %>TourOrder/userFrontlist" class="mar_t15" method="post">
            <div class="form-group">
            	<label for="tourObj_tourId">旅游景点：</label>
                <select id="tourObj_tourId" name="tourObj.tourId" class="form-control">
                	<option value="0">不限制</option>
	 				<%
	 				for(Tour tourTemp:tourList) {
	 					String selected = "";
 					if(tourObj!=null && tourObj.getTourId()!=null && tourObj.getTourId().intValue()==tourTemp.getTourId().intValue())
 						selected = "selected";
	 				%>
 				 <option value="<%=tourTemp.getTourId() %>" <%=selected %>><%=tourTemp.getTourName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="startDate">出发日期:</label>
				<input type="text" id="startDate" name="startDate" class="form-control"  placeholder="请选择出发日期" value="<%=startDate %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="telephone">联系电话:</label>
				<input type="text" id="telephone" name="telephone" value="<%=telephone %>" class="form-control" placeholder="请输入联系电话">
			</div>
 
            <div class="form-group" style="display:none;">
            	<label for="userObj_user_name">订单用户：</label>
                <select id="userObj_user_name" name="userObj.user_name" class="form-control">
                	<option value="">不限制</option>
	 				<%
	 				for(UserInfo userInfoTemp:userInfoList) {
	 					String selected = "";
 					if(userObj!=null && userObj.getUser_name()!=null && userObj.getUser_name().equals(userInfoTemp.getUser_name()))
 						selected = "selected";
	 				%>
 				 <option value="<%=userInfoTemp.getUser_name() %>" <%=selected %>><%=userInfoTemp.getName() %></option>
	 				<%
	 				}
	 				%>
 			</select>
            </div>
			<div class="form-group">
				<label for="orderTime">下单时间:</label>
				<input type="text" id="orderTime" name="orderTime" class="form-control"  placeholder="请选择下单时间" value="<%=orderTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="shzt">审核状态:</label>
				<input type="text" id="shzt" name="shzt" value="<%=shzt %>" class="form-control" placeholder="请输入审核状态">
			</div>






            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
	</div> 
<div id="tourOrderEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;订单信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="tourOrderEditForm" id="tourOrderEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="tourOrder_orderId_edit" class="col-md-3 text-right">订单id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="tourOrder_orderId_edit" name="tourOrder.orderId" class="form-control" placeholder="请输入订单id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="tourOrder_tourObj_tourId_edit" class="col-md-3 text-right">旅游景点:</label>
		  	 <div class="col-md-9">
			    <select id="tourOrder_tourObj_tourId_edit" name="tourOrder.tourObj.tourId" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_startDate_edit" class="col-md-3 text-right">出发日期:</label>
		  	 <div class="col-md-9">
                <div class="input-group date tourOrder_startDate_edit col-md-12" data-link-field="tourOrder_startDate_edit"  data-link-format="yyyy-mm-dd">
                    <input class="form-control" id="tourOrder_startDate_edit" name="tourOrder.startDate" size="16" type="text" value="" placeholder="请选择出发日期" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_totalPersonNum_edit" class="col-md-3 text-right">出行人数:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tourOrder_totalPersonNum_edit" name="tourOrder.totalPersonNum" class="form-control" placeholder="请输入出行人数">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_totalPrice_edit" class="col-md-3 text-right">总价格:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tourOrder_totalPrice_edit" name="tourOrder.totalPrice" class="form-control" placeholder="请输入总价格">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_telephone_edit" class="col-md-3 text-right">联系电话:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tourOrder_telephone_edit" name="tourOrder.telephone" class="form-control" placeholder="请输入联系电话">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_orderMemo_edit" class="col-md-3 text-right">订单备注:</label>
		  	 <div class="col-md-9">
			    <textarea id="tourOrder_orderMemo_edit" name="tourOrder.orderMemo" rows="8" class="form-control" placeholder="请输入订单备注"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_userObj_user_name_edit" class="col-md-3 text-right">订单用户:</label>
		  	 <div class="col-md-9">
			    <select id="tourOrder_userObj_user_name_edit" name="tourOrder.userObj.user_name" class="form-control">
			    </select>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_orderTime_edit" class="col-md-3 text-right">下单时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date tourOrder_orderTime_edit col-md-12" data-link-field="tourOrder_orderTime_edit">
                    <input class="form-control" id="tourOrder_orderTime_edit" name="tourOrder.orderTime" size="16" type="text" value="" placeholder="请选择下单时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_shzt_edit" class="col-md-3 text-right">审核状态:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tourOrder_shzt_edit" name="tourOrder.shzt" class="form-control" placeholder="请输入审核状态">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tourOrder_shhf_edit" class="col-md-3 text-right">审核回复:</label>
		  	 <div class="col-md-9">
			    <textarea id="tourOrder_shhf_edit" name="tourOrder.shhf" rows="8" class="form-control" placeholder="请输入审核回复"></textarea>
			 </div>
		  </div>
		</form> 
	    <style>#tourOrderEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxTourOrderModify();">提交</button>
      </div>
    </div><!-- /.modal-content -->
  </div><!-- /.modal-dialog -->
</div><!-- /.modal -->
<jsp:include page="../footer.jsp"></jsp:include> 
<script src="<%=basePath %>plugins/jquery.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap.js"></script>
<script src="<%=basePath %>plugins/wow.min.js"></script>
<script src="<%=basePath %>plugins/bootstrap-datetimepicker.min.js"></script>
<script src="<%=basePath %>plugins/locales/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript" src="<%=basePath %>js/jsdate.js"></script>
<script>
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.tourOrderQueryForm.currentPage.value = currentPage;
    document.tourOrderQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.tourOrderQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.tourOrderQueryForm.currentPage.value = pageValue;
    documenttourOrderQueryForm.submit();
}

/*弹出修改订单界面并初始化数据*/
function tourOrderEdit(orderId) {
	$.ajax({
		url :  basePath + "TourOrder/" + orderId + "/update",
		type : "get",
		dataType: "json",
		success : function (tourOrder, response, status) {
			if (tourOrder) {
				$("#tourOrder_orderId_edit").val(tourOrder.orderId);
				$.ajax({
					url: basePath + "Tour/listAll",
					type: "get",
					success: function(tours,response,status) { 
						$("#tourOrder_tourObj_tourId_edit").empty();
						var html="";
		        		$(tours).each(function(i,tour){
		        			html += "<option value='" + tour.tourId + "'>" + tour.tourName + "</option>";
		        		});
		        		$("#tourOrder_tourObj_tourId_edit").html(html);
		        		$("#tourOrder_tourObj_tourId_edit").val(tourOrder.tourObjPri);
					}
				});
				$("#tourOrder_startDate_edit").val(tourOrder.startDate);
				$("#tourOrder_totalPersonNum_edit").val(tourOrder.totalPersonNum);
				$("#tourOrder_totalPrice_edit").val(tourOrder.totalPrice);
				$("#tourOrder_telephone_edit").val(tourOrder.telephone);
				$("#tourOrder_orderMemo_edit").val(tourOrder.orderMemo);
				$.ajax({
					url: basePath + "UserInfo/listAll",
					type: "get",
					success: function(userInfos,response,status) { 
						$("#tourOrder_userObj_user_name_edit").empty();
						var html="";
		        		$(userInfos).each(function(i,userInfo){
		        			html += "<option value='" + userInfo.user_name + "'>" + userInfo.name + "</option>";
		        		});
		        		$("#tourOrder_userObj_user_name_edit").html(html);
		        		$("#tourOrder_userObj_user_name_edit").val(tourOrder.userObjPri);
					}
				});
				$("#tourOrder_orderTime_edit").val(tourOrder.orderTime);
				$("#tourOrder_shzt_edit").val(tourOrder.shzt);
				$("#tourOrder_shhf_edit").val(tourOrder.shhf);
				$('#tourOrderEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除订单信息*/
function tourOrderDelete(orderId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "TourOrder/deletes",
			data : {
				orderIds : orderId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#tourOrderQueryForm").submit();
					//location.href= basePath + "TourOrder/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交订单信息表单给服务器端修改*/
function ajaxTourOrderModify() {
	$.ajax({
		url :  basePath + "TourOrder/" + $("#tourOrder_orderId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#tourOrderEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#tourOrderQueryForm").submit();
            }else{
                alert(obj.message);
            } 
		},
		processData: false,
		contentType: false,
	});
}

$(function(){
	/*小屏幕导航点击关闭菜单*/
    $('.navbar-collapse a').click(function(){
        $('.navbar-collapse').collapse('hide');
    });
    new WOW().init();

    /*出发日期组件*/
    $('.tourOrder_startDate_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd',
    	minView: 2,
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
    /*下单时间组件*/
    $('.tourOrder_orderTime_edit').datetimepicker({
    	language:  'zh-CN',  //语言
    	format: 'yyyy-mm-dd hh:ii:ss',
    	weekStart: 1,
    	todayBtn:  1,
    	autoclose: 1,
    	minuteStep: 1,
    	todayHighlight: 1,
    	startView: 2,
    	forceParse: 0
    });
})
</script>
</body>
</html>


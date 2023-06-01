<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.Tour" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<Tour> tourList = (List<Tour>)request.getAttribute("tourList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String tourName = (String)request.getAttribute("tourName"); //旅游景点名称查询关键字
    String startPlace = (String)request.getAttribute("startPlace"); //出发地查询关键字
    String endPlace = (String)request.getAttribute("endPlace"); //目的地查询关键字
    String tuijianFlag = (String)request.getAttribute("tuijianFlag"); //是否推荐查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>旅游景点查询</title>
<link href="<%=basePath %>plugins/bootstrap.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-dashen.css" rel="stylesheet">
<link href="<%=basePath %>plugins/font-awesome.css" rel="stylesheet">
<link href="<%=basePath %>plugins/animate.css" rel="stylesheet">
<link href="<%=basePath %>plugins/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">
</head>
<body style="margin-top:70px;">
<div class="container">
<jsp:include page="../header.jsp"></jsp:include>
	<div class="col-md-9 wow fadeInLeft">
		<ul class="breadcrumb">
  			<li><a href="<%=basePath %>index.jsp">首页</a></li>
  			<li><a href="<%=basePath %>Tour/frontlist">旅游景点信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>Tour/tour_frontAdd.jsp" style="display:none;">添加旅游景点</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<tourList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		Tour tour = tourList.get(i); //获取到旅游景点对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>Tour/<%=tour.getTourId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=tour.getTourPhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		旅游id:<%=tour.getTourId() %>
			     	</div>
			     	<div class="field">
	            		旅游景点名称:<%=tour.getTourName() %>
			     	</div>
			     	<div class="field">
	            		出发地:<%=tour.getStartPlace() %>
			     	</div>
			     	<div class="field">
	            		目的地:<%=tour.getEndPlace() %>
			     	</div>
			     	<div class="field">
	            		旅游价格:<%=tour.getTourPrice() %>
			     	</div>
			     	<div class="field">
	            		是否推荐:<%=tour.getTuijianFlag() %>
			     	</div>
			     	<div class="field">
	            		浏览次数:<%=tour.getHitNum() %>
			     	</div>
			     	<div class="field">
	            		发布时间:<%=tour.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>Tour/<%=tour.getTourId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="tourEdit('<%=tour.getTourId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="tourDelete('<%=tour.getTourId() %>');" style="display:none;">删除</a>
			     </div>
			</div>
			<%  } %>

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

	<div class="col-md-3 wow fadeInRight">
		<div class="page-header">
    		<h1>旅游景点查询</h1>
		</div>
		<form name="tourQueryForm" id="tourQueryForm" action="<%=basePath %>Tour/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="tourName">旅游景点名称:</label>
				<input type="text" id="tourName" name="tourName" value="<%=tourName %>" class="form-control" placeholder="请输入旅游景点名称">
			</div>
			<div class="form-group">
				<label for="startPlace">出发地:</label>
				<input type="text" id="startPlace" name="startPlace" value="<%=startPlace %>" class="form-control" placeholder="请输入出发地">
			</div>
			<div class="form-group">
				<label for="endPlace">目的地:</label>
				<input type="text" id="endPlace" name="endPlace" value="<%=endPlace %>" class="form-control" placeholder="请输入目的地">
			</div>
			<div class="form-group">
				<label for="tuijianFlag">是否推荐:</label>
				<input type="text" id="tuijianFlag" name="tuijianFlag" value="<%=tuijianFlag %>" class="form-control" placeholder="请输入是否推荐">
			</div>
			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="tourEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;旅游景点信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="tourEditForm" id="tourEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="tour_tourId_edit" class="col-md-3 text-right">旅游id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="tour_tourId_edit" name="tour.tourId" class="form-control" placeholder="请输入旅游id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="tour_tourName_edit" class="col-md-3 text-right">旅游景点名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_tourName_edit" name="tour.tourName" class="form-control" placeholder="请输入旅游景点名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_tourPhoto_edit" class="col-md-3 text-right">旅游图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="tour_tourPhotoImg" border="0px"/><br/>
			    <input type="hidden" id="tour_tourPhoto" name="tour.tourPhoto"/>
			    <input id="tourPhotoFile" name="tourPhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_startPlace_edit" class="col-md-3 text-right">出发地:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_startPlace_edit" name="tour.startPlace" class="form-control" placeholder="请输入出发地">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_endPlace_edit" class="col-md-3 text-right">目的地:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_endPlace_edit" name="tour.endPlace" class="form-control" placeholder="请输入目的地">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_tourPrice_edit" class="col-md-3 text-right">旅游价格:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_tourPrice_edit" name="tour.tourPrice" class="form-control" placeholder="请输入旅游价格">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_tourDesc_edit" class="col-md-3 text-right">旅游景点介绍:</label>
		  	 <div class="col-md-9">
			 	<textarea name="tour.tourDesc" id="tour_tourDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_tuijianFlag_edit" class="col-md-3 text-right">是否推荐:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_tuijianFlag_edit" name="tour.tuijianFlag" class="form-control" placeholder="请输入是否推荐">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_hitNum_edit" class="col-md-3 text-right">浏览次数:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="tour_hitNum_edit" name="tour.hitNum" class="form-control" placeholder="请输入浏览次数">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="tour_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date tour_addTime_edit col-md-12" data-link-field="tour_addTime_edit">
                    <input class="form-control" id="tour_addTime_edit" name="tour.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#tourEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxTourModify();">提交</button>
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
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.config.js"></script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/ueditor.all.min.js"> </script>
<script type="text/javascript" charset="utf-8" src="${pageContext.request.contextPath}/ueditor1_4_3/lang/zh-cn/zh-cn.js"></script>
<script>
//实例化编辑器
var tour_tourDesc_edit = UE.getEditor('tour_tourDesc_edit'); //旅游景点介绍编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.tourQueryForm.currentPage.value = currentPage;
    document.tourQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.tourQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.tourQueryForm.currentPage.value = pageValue;
    documenttourQueryForm.submit();
}

/*弹出修改旅游景点界面并初始化数据*/
function tourEdit(tourId) {
	$.ajax({
		url :  basePath + "Tour/" + tourId + "/update",
		type : "get",
		dataType: "json",
		success : function (tour, response, status) {
			if (tour) {
				$("#tour_tourId_edit").val(tour.tourId);
				$("#tour_tourName_edit").val(tour.tourName);
				$("#tour_tourPhoto").val(tour.tourPhoto);
				$("#tour_tourPhotoImg").attr("src", basePath +　tour.tourPhoto);
				$("#tour_startPlace_edit").val(tour.startPlace);
				$("#tour_endPlace_edit").val(tour.endPlace);
				$("#tour_tourPrice_edit").val(tour.tourPrice);
				tour_tourDesc_edit.setContent(tour.tourDesc, false);
				$("#tour_tuijianFlag_edit").val(tour.tuijianFlag);
				$("#tour_hitNum_edit").val(tour.hitNum);
				$("#tour_addTime_edit").val(tour.addTime);
				$('#tourEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除旅游景点信息*/
function tourDelete(tourId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "Tour/deletes",
			data : {
				tourIds : tourId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#tourQueryForm").submit();
					//location.href= basePath + "Tour/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交旅游景点信息表单给服务器端修改*/
function ajaxTourModify() {
	$.ajax({
		url :  basePath + "Tour/" + $("#tour_tourId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#tourEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#tourQueryForm").submit();
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

    /*发布时间组件*/
    $('.tour_addTime_edit').datetimepicker({
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


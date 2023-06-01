<%@ page language="java" import="java.util.*"  contentType="text/html;charset=UTF-8"%> 
<%@ page import="com.chengxusheji.po.DzLine" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    List<DzLine> dzLineList = (List<DzLine>)request.getAttribute("dzLineList");
    int currentPage =  (Integer)request.getAttribute("currentPage"); //当前页
    int totalPage =   (Integer)request.getAttribute("totalPage");  //一共多少页
    int recordNumber =   (Integer)request.getAttribute("recordNumber");  //一共多少记录
    String tuijianFlag = (String)request.getAttribute("tuijianFlag"); //是否推荐查询关键字
    String addTime = (String)request.getAttribute("addTime"); //发布时间查询关键字
    String lineName = (String)request.getAttribute("lineName"); //线路名称查询关键字
    String startPlace = (String)request.getAttribute("startPlace"); //出发地查询关键字
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1 , user-scalable=no">
<title>定制线路查询</title>
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
  			<li><a href="<%=basePath %>DzLine/frontlist">定制线路信息列表</a></li>
  			<li class="active">查询结果显示</li>
  			<a class="pull-right" href="<%=basePath %>DzLine/dzLine_frontAdd.jsp" style="display:none;">添加定制线路</a>
		</ul>
		<div class="row">
			<%
				/*计算起始序号*/
				int startIndex = (currentPage -1) * 5;
				/*遍历记录*/
				for(int i=0;i<dzLineList.size();i++) {
            		int currentIndex = startIndex + i + 1; //当前记录的序号
            		DzLine dzLine = dzLineList.get(i); //获取到定制线路对象
            		String clearLeft = "";
            		if(i%4 == 0) clearLeft = "style=\"clear:left;\"";
			%>
			<div class="col-md-3 bottom15" <%=clearLeft %>>
			  <a  href="<%=basePath  %>DzLine/<%=dzLine.getLineId() %>/frontshow"><img class="img-responsive" src="<%=basePath%><%=dzLine.getLinePhoto()%>" /></a>
			     <div class="showFields">
			     	<div class="field">
	            		线路id:<%=dzLine.getLineId() %>
			     	</div>
			     	<div class="field">
	            		线路名称:<%=dzLine.getLineName() %>
			     	</div>
			     	<div class="field">
	            		出发地:<%=dzLine.getStartPlace() %>
			     	</div>
			     	<div class="field">
	            		产品主题:<%=dzLine.getZhuti() %>
			     	</div>
			     	<div class="field">
	            		线路价格:<%=dzLine.getLinePrice() %>
			     	</div>
			     	<div class="field">
	            		是否推荐:<%=dzLine.getTuijianFlag() %>
			     	</div>
			     	<div class="field">
	            		点击率:<%=dzLine.getHitNum() %>
			     	</div>
			     	<div class="field">
	            		发布时间:<%=dzLine.getAddTime() %>
			     	</div>
			        <a class="btn btn-primary top5" href="<%=basePath %>DzLine/<%=dzLine.getLineId() %>/frontshow">详情</a>
			        <a class="btn btn-primary top5" onclick="dzLineEdit('<%=dzLine.getLineId() %>');" style="display:none;">修改</a>
			        <a class="btn btn-primary top5" onclick="dzLineDelete('<%=dzLine.getLineId() %>');" style="display:none;">删除</a>
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
    		<h1>定制线路查询</h1>
		</div>
		<form name="dzLineQueryForm" id="dzLineQueryForm" action="<%=basePath %>DzLine/frontlist" class="mar_t15" method="post">
			<div class="form-group">
				<label for="tuijianFlag">是否推荐:</label>
				<input type="text" id="tuijianFlag" name="tuijianFlag" value="<%=tuijianFlag %>" class="form-control" placeholder="请输入是否推荐">
			</div>
			<div class="form-group">
				<label for="addTime">发布时间:</label>
				<input type="text" id="addTime" name="addTime" class="form-control"  placeholder="请选择发布时间" value="<%=addTime %>" onclick="SelectDate(this,'yyyy-MM-dd')" />
			</div>
			<div class="form-group">
				<label for="lineName">线路名称:</label>
				<input type="text" id="lineName" name="lineName" value="<%=lineName %>" class="form-control" placeholder="请输入线路名称">
			</div>
			<div class="form-group">
				<label for="startPlace">出发地:</label>
				<input type="text" id="startPlace" name="startPlace" value="<%=startPlace %>" class="form-control" placeholder="请输入出发地">
			</div>
            <input type=hidden name=currentPage value="<%=currentPage %>" />
            <button type="submit" class="btn btn-primary">查询</button>
        </form>
	</div>

		</div>
</div>
<div id="dzLineEditDialog" class="modal fade" tabindex="-1" role="dialog">
  <div class="modal-dialog" style="width:900px;" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <h4 class="modal-title"><i class="fa fa-edit"></i>&nbsp;定制线路信息编辑</h4>
      </div>
      <div class="modal-body" style="height:450px; overflow: scroll;">
      	<form class="form-horizontal" name="dzLineEditForm" id="dzLineEditForm" enctype="multipart/form-data" method="post"  class="mar_t15">
		  <div class="form-group">
			 <label for="dzLine_lineId_edit" class="col-md-3 text-right">线路id:</label>
			 <div class="col-md-9"> 
			 	<input type="text" id="dzLine_lineId_edit" name="dzLine.lineId" class="form-control" placeholder="请输入线路id" readOnly>
			 </div>
		  </div> 
		  <div class="form-group">
		  	 <label for="dzLine_lineName_edit" class="col-md-3 text-right">线路名称:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_lineName_edit" name="dzLine.lineName" class="form-control" placeholder="请输入线路名称">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_linePhoto_edit" class="col-md-3 text-right">线路图片:</label>
		  	 <div class="col-md-9">
			    <img  class="img-responsive" id="dzLine_linePhotoImg" border="0px"/><br/>
			    <input type="hidden" id="dzLine_linePhoto" name="dzLine.linePhoto"/>
			    <input id="linePhotoFile" name="linePhotoFile" type="file" size="50" />
		  	 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_startPlace_edit" class="col-md-3 text-right">出发地:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_startPlace_edit" name="dzLine.startPlace" class="form-control" placeholder="请输入出发地">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_zhuti_edit" class="col-md-3 text-right">产品主题:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_zhuti_edit" name="dzLine.zhuti" class="form-control" placeholder="请输入产品主题">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_linePrice_edit" class="col-md-3 text-right">线路价格:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_linePrice_edit" name="dzLine.linePrice" class="form-control" placeholder="请输入线路价格">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_lineDesc_edit" class="col-md-3 text-right">线路描述:</label>
		  	 <div class="col-md-9">
			 	<textarea name="dzLine.lineDesc" id="dzLine_lineDesc_edit" style="width:100%;height:500px;"></textarea>
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_tuijianFlag_edit" class="col-md-3 text-right">是否推荐:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_tuijianFlag_edit" name="dzLine.tuijianFlag" class="form-control" placeholder="请输入是否推荐">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_hitNum_edit" class="col-md-3 text-right">点击率:</label>
		  	 <div class="col-md-9">
			    <input type="text" id="dzLine_hitNum_edit" name="dzLine.hitNum" class="form-control" placeholder="请输入点击率">
			 </div>
		  </div>
		  <div class="form-group">
		  	 <label for="dzLine_addTime_edit" class="col-md-3 text-right">发布时间:</label>
		  	 <div class="col-md-9">
                <div class="input-group date dzLine_addTime_edit col-md-12" data-link-field="dzLine_addTime_edit">
                    <input class="form-control" id="dzLine_addTime_edit" name="dzLine.addTime" size="16" type="text" value="" placeholder="请选择发布时间" readonly>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-remove"></span></span>
                    <span class="input-group-addon"><span class="glyphicon glyphicon-calendar"></span></span>
                </div>
		  	 </div>
		  </div>
		</form> 
	    <style>#dzLineEditForm .form-group {margin-bottom:5px;}  </style>
      </div>
      <div class="modal-footer"> 
      	<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
      	<button type="button" class="btn btn-primary" onclick="ajaxDzLineModify();">提交</button>
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
var dzLine_lineDesc_edit = UE.getEditor('dzLine_lineDesc_edit'); //线路描述编辑器
var basePath = "<%=basePath%>";
/*跳转到查询结果的某页*/
function GoToPage(currentPage,totalPage) {
    if(currentPage==0) return;
    if(currentPage>totalPage) return;
    document.dzLineQueryForm.currentPage.value = currentPage;
    document.dzLineQueryForm.submit();
}

/*可以直接跳转到某页*/
function changepage(totalPage)
{
    var pageValue=document.dzLineQueryForm.pageValue.value;
    if(pageValue>totalPage) {
        alert('你输入的页码超出了总页数!');
        return ;
    }
    document.dzLineQueryForm.currentPage.value = pageValue;
    documentdzLineQueryForm.submit();
}

/*弹出修改定制线路界面并初始化数据*/
function dzLineEdit(lineId) {
	$.ajax({
		url :  basePath + "DzLine/" + lineId + "/update",
		type : "get",
		dataType: "json",
		success : function (dzLine, response, status) {
			if (dzLine) {
				$("#dzLine_lineId_edit").val(dzLine.lineId);
				$("#dzLine_lineName_edit").val(dzLine.lineName);
				$("#dzLine_linePhoto").val(dzLine.linePhoto);
				$("#dzLine_linePhotoImg").attr("src", basePath +　dzLine.linePhoto);
				$("#dzLine_startPlace_edit").val(dzLine.startPlace);
				$("#dzLine_zhuti_edit").val(dzLine.zhuti);
				$("#dzLine_linePrice_edit").val(dzLine.linePrice);
				dzLine_lineDesc_edit.setContent(dzLine.lineDesc, false);
				$("#dzLine_tuijianFlag_edit").val(dzLine.tuijianFlag);
				$("#dzLine_hitNum_edit").val(dzLine.hitNum);
				$("#dzLine_addTime_edit").val(dzLine.addTime);
				$('#dzLineEditDialog').modal('show');
			} else {
				alert("获取信息失败！");
			}
		}
	});
}

/*删除定制线路信息*/
function dzLineDelete(lineId) {
	if(confirm("确认删除这个记录")) {
		$.ajax({
			type : "POST",
			url : basePath + "DzLine/deletes",
			data : {
				lineIds : lineId,
			},
			success : function (obj) {
				if (obj.success) {
					alert("删除成功");
					$("#dzLineQueryForm").submit();
					//location.href= basePath + "DzLine/frontlist";
				}
				else 
					alert(obj.message);
			},
		});
	}
}

/*ajax方式提交定制线路信息表单给服务器端修改*/
function ajaxDzLineModify() {
	$.ajax({
		url :  basePath + "DzLine/" + $("#dzLine_lineId_edit").val() + "/update",
		type : "post",
		dataType: "json",
		data: new FormData($("#dzLineEditForm")[0]),
		success : function (obj, response, status) {
            if(obj.success){
                alert("信息修改成功！");
                $("#dzLineQueryForm").submit();
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
    $('.dzLine_addTime_edit').datetimepicker({
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


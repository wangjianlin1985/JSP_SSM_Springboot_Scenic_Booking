package com.chengxusheji.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.chengxusheji.utils.ExportExcelUtil;
import com.chengxusheji.utils.UserException;
import com.chengxusheji.service.TourOrderService;
import com.chengxusheji.po.TourOrder;
import com.chengxusheji.service.TourService;
import com.chengxusheji.po.Tour;
import com.chengxusheji.service.UserInfoService;
import com.chengxusheji.po.UserInfo;

//TourOrder管理控制层
@Controller
@RequestMapping("/TourOrder")
public class TourOrderController extends BaseController {

    /*业务层对象*/
    @Resource TourOrderService tourOrderService;

    @Resource TourService tourService;
    @Resource UserInfoService userInfoService;
	@InitBinder("tourObj")
	public void initBindertourObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("tourObj.");
	}
	@InitBinder("userObj")
	public void initBinderuserObj(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("userObj.");
	}
	@InitBinder("tourOrder")
	public void initBinderTourOrder(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("tourOrder.");
	}
	/*跳转到添加TourOrder视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new TourOrder());
		/*查询所有的Tour信息*/
		List<Tour> tourList = tourService.queryAllTour();
		request.setAttribute("tourList", tourList);
		/*查询所有的UserInfo信息*/
		List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
		request.setAttribute("userInfoList", userInfoList);
		return "TourOrder_add";
	}

	/*客户端ajax方式提交添加订单信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated TourOrder tourOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
        tourOrderService.addTourOrder(tourOrder);
        message = "订单添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*客户端ajax方式提交添加订单信息*/
	@RequestMapping(value = "/userAdd", method = RequestMethod.POST)
	public void userAdd(TourOrder tourOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response,HttpSession session) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		
		float price = tourService.getTour(tourOrder.getTourObj().getTourId()).getTourPrice();
		tourOrder.setTotalPrice(tourOrder.getTotalPersonNum() * price);
		
		UserInfo userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		tourOrder.setUserObj(userObj);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tourOrder.setOrderTime(sdf.format(new java.util.Date()));
		
		tourOrder.setShzt("待审核");
		tourOrder.setShhf("--");
		
        tourOrderService.addTourOrder(tourOrder);
        message = "订单添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	
	
	/*ajax方式按照查询条件分页查询订单信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(@ModelAttribute("tourObj") Tour tourObj,String startDate,String telephone,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String shzt,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (startDate == null) startDate = "";
		if (telephone == null) telephone = "";
		if (orderTime == null) orderTime = "";
		if (shzt == null) shzt = "";
		if(rows != 0)tourOrderService.setRows(rows);
		List<TourOrder> tourOrderList = tourOrderService.queryTourOrder(tourObj, startDate, telephone, userObj, orderTime, shzt, page);
	    /*计算总的页数和总的记录数*/
	    tourOrderService.queryTotalPageAndRecordNumber(tourObj, startDate, telephone, userObj, orderTime, shzt);
	    /*获取到总的页码数目*/
	    int totalPage = tourOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = tourOrderService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(TourOrder tourOrder:tourOrderList) {
			JSONObject jsonTourOrder = tourOrder.getJsonObject();
			jsonArray.put(jsonTourOrder);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询订单信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<TourOrder> tourOrderList = tourOrderService.queryAllTourOrder();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(TourOrder tourOrder:tourOrderList) {
			JSONObject jsonTourOrder = new JSONObject();
			jsonTourOrder.accumulate("orderId", tourOrder.getOrderId());
			jsonArray.put(jsonTourOrder);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询订单信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(@ModelAttribute("tourObj") Tour tourObj,String startDate,String telephone,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String shzt,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (startDate == null) startDate = "";
		if (telephone == null) telephone = "";
		if (orderTime == null) orderTime = "";
		if (shzt == null) shzt = "";
		List<TourOrder> tourOrderList = tourOrderService.queryTourOrder(tourObj, startDate, telephone, userObj, orderTime, shzt, currentPage);
	    /*计算总的页数和总的记录数*/
	    tourOrderService.queryTotalPageAndRecordNumber(tourObj, startDate, telephone, userObj, orderTime, shzt);
	    /*获取到总的页码数目*/
	    int totalPage = tourOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = tourOrderService.getRecordNumber();
	    request.setAttribute("tourOrderList",  tourOrderList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("tourObj", tourObj);
	    request.setAttribute("startDate", startDate);
	    request.setAttribute("telephone", telephone);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("orderTime", orderTime);
	    request.setAttribute("shzt", shzt);
	    List<Tour> tourList = tourService.queryAllTour();
	    request.setAttribute("tourList", tourList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "TourOrder/tourOrder_frontquery_result"; 
	}

	
	/*前台按照查询条件分页查询订单信息*/
	@RequestMapping(value = { "/userFrontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String userFrontlist(@ModelAttribute("tourObj") Tour tourObj,String startDate,String telephone,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String shzt,Integer currentPage, Model model, HttpServletRequest request,HttpSession session) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (startDate == null) startDate = "";
		if (telephone == null) telephone = "";
		if (orderTime == null) orderTime = "";
		if (shzt == null) shzt = "";
		
		userObj = new UserInfo();
		userObj.setUser_name(session.getAttribute("user_name").toString());
		
		List<TourOrder> tourOrderList = tourOrderService.queryTourOrder(tourObj, startDate, telephone, userObj, orderTime, shzt, currentPage);
	    /*计算总的页数和总的记录数*/
	    tourOrderService.queryTotalPageAndRecordNumber(tourObj, startDate, telephone, userObj, orderTime, shzt);
	    /*获取到总的页码数目*/
	    int totalPage = tourOrderService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = tourOrderService.getRecordNumber();
	    request.setAttribute("tourOrderList",  tourOrderList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("tourObj", tourObj);
	    request.setAttribute("startDate", startDate);
	    request.setAttribute("telephone", telephone);
	    request.setAttribute("userObj", userObj);
	    request.setAttribute("orderTime", orderTime);
	    request.setAttribute("shzt", shzt);
	    List<Tour> tourList = tourService.queryAllTour();
	    request.setAttribute("tourList", tourList);
	    List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
	    request.setAttribute("userInfoList", userInfoList);
		return "TourOrder/tourOrder_userFrontquery_result"; 
	}
	
	
     /*前台查询TourOrder信息*/
	@RequestMapping(value="/{orderId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer orderId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键orderId获取TourOrder对象*/
        TourOrder tourOrder = tourOrderService.getTourOrder(orderId);

        List<Tour> tourList = tourService.queryAllTour();
        request.setAttribute("tourList", tourList);
        List<UserInfo> userInfoList = userInfoService.queryAllUserInfo();
        request.setAttribute("userInfoList", userInfoList);
        request.setAttribute("tourOrder",  tourOrder);
        return "TourOrder/tourOrder_frontshow";
	}

	/*ajax方式显示订单修改jsp视图页*/
	@RequestMapping(value="/{orderId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer orderId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键orderId获取TourOrder对象*/
        TourOrder tourOrder = tourOrderService.getTourOrder(orderId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonTourOrder = tourOrder.getJsonObject();
		out.println(jsonTourOrder.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新订单信息*/
	@RequestMapping(value = "/{orderId}/update", method = RequestMethod.POST)
	public void update(@Validated TourOrder tourOrder, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		try {
			tourOrderService.updateTourOrder(tourOrder);
			message = "订单更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "订单更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除订单信息*/
	@RequestMapping(value="/{orderId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer orderId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  tourOrderService.deleteTourOrder(orderId);
	            request.setAttribute("message", "订单删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "订单删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条订单记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String orderIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = tourOrderService.deleteTourOrders(orderIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出订单信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(@ModelAttribute("tourObj") Tour tourObj,String startDate,String telephone,@ModelAttribute("userObj") UserInfo userObj,String orderTime,String shzt, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(startDate == null) startDate = "";
        if(telephone == null) telephone = "";
        if(orderTime == null) orderTime = "";
        if(shzt == null) shzt = "";
        List<TourOrder> tourOrderList = tourOrderService.queryTourOrder(tourObj,startDate,telephone,userObj,orderTime,shzt);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "TourOrder信息记录"; 
        String[] headers = { "订单id","旅游景点","出发日期","出行人数","总价格","联系电话","订单用户","下单时间","审核状态"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<tourOrderList.size();i++) {
        	TourOrder tourOrder = tourOrderList.get(i); 
        	dataset.add(new String[]{tourOrder.getOrderId() + "",tourOrder.getTourObj().getTourName(),tourOrder.getStartDate(),tourOrder.getTotalPersonNum() + "",tourOrder.getTotalPrice() + "",tourOrder.getTelephone(),tourOrder.getUserObj().getName(),tourOrder.getOrderTime(),tourOrder.getShzt()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		OutputStream out = null;//创建一个输出流对象 
		try { 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TourOrder.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = request.getSession().getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,_title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
    }
}

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
import com.chengxusheji.service.TourService;
import com.chengxusheji.po.Tour;

//Tour管理控制层
@Controller
@RequestMapping("/Tour")
public class TourController extends BaseController {

    /*业务层对象*/
    @Resource TourService tourService;

	@InitBinder("tour")
	public void initBinderTour(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("tour.");
	}
	/*跳转到添加Tour视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new Tour());
		return "Tour_add";
	}

	/*客户端ajax方式提交添加旅游景点信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated Tour tour, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			tour.setTourPhoto(this.handlePhotoUpload(request, "tourPhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        tourService.addTour(tour);
        message = "旅游景点添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询旅游景点信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (tourName == null) tourName = "";
		if (startPlace == null) startPlace = "";
		if (endPlace == null) endPlace = "";
		if (tuijianFlag == null) tuijianFlag = "";
		if (addTime == null) addTime = "";
		if(rows != 0)tourService.setRows(rows);
		List<Tour> tourList = tourService.queryTour(tourName, startPlace, endPlace, tuijianFlag, addTime, page);
	    /*计算总的页数和总的记录数*/
	    tourService.queryTotalPageAndRecordNumber(tourName, startPlace, endPlace, tuijianFlag, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = tourService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = tourService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(Tour tour:tourList) {
			JSONObject jsonTour = tour.getJsonObject();
			jsonArray.put(jsonTour);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询旅游景点信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<Tour> tourList = tourService.queryAllTour();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(Tour tour:tourList) {
			JSONObject jsonTour = new JSONObject();
			jsonTour.accumulate("tourId", tour.getTourId());
			jsonTour.accumulate("tourName", tour.getTourName());
			jsonArray.put(jsonTour);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询旅游景点信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (tourName == null) tourName = "";
		if (startPlace == null) startPlace = "";
		if (endPlace == null) endPlace = "";
		if (tuijianFlag == null) tuijianFlag = "";
		if (addTime == null) addTime = "";
		List<Tour> tourList = tourService.queryTour(tourName, startPlace, endPlace, tuijianFlag, addTime, currentPage);
	    /*计算总的页数和总的记录数*/
	    tourService.queryTotalPageAndRecordNumber(tourName, startPlace, endPlace, tuijianFlag, addTime);
	    /*获取到总的页码数目*/
	    int totalPage = tourService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = tourService.getRecordNumber();
	    request.setAttribute("tourList",  tourList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("tourName", tourName);
	    request.setAttribute("startPlace", startPlace);
	    request.setAttribute("endPlace", endPlace);
	    request.setAttribute("tuijianFlag", tuijianFlag);
	    request.setAttribute("addTime", addTime);
		return "Tour/tour_frontquery_result"; 
	}

     /*前台查询Tour信息*/
	@RequestMapping(value="/{tourId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer tourId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键tourId获取Tour对象*/
        Tour tour = tourService.getTour(tourId);
        tour.setHitNum(tour.getHitNum() + 1);
        tourService.updateTour(tour);
        request.setAttribute("tour",  tour);
        return "Tour/tour_frontshow";
	}

	/*ajax方式显示旅游景点修改jsp视图页*/
	@RequestMapping(value="/{tourId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer tourId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键tourId获取Tour对象*/
        Tour tour = tourService.getTour(tourId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonTour = tour.getJsonObject();
		out.println(jsonTour.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新旅游景点信息*/
	@RequestMapping(value = "/{tourId}/update", method = RequestMethod.POST)
	public void update(@Validated Tour tour, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String tourPhotoFileName = this.handlePhotoUpload(request, "tourPhotoFile");
		if(!tourPhotoFileName.equals("upload/NoImage.jpg"))tour.setTourPhoto(tourPhotoFileName); 


		try {
			tourService.updateTour(tour);
			message = "旅游景点更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "旅游景点更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除旅游景点信息*/
	@RequestMapping(value="/{tourId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer tourId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  tourService.deleteTour(tourId);
	            request.setAttribute("message", "旅游景点删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "旅游景点删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条旅游景点记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String tourIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = tourService.deleteTours(tourIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出旅游景点信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(tourName == null) tourName = "";
        if(startPlace == null) startPlace = "";
        if(endPlace == null) endPlace = "";
        if(tuijianFlag == null) tuijianFlag = "";
        if(addTime == null) addTime = "";
        List<Tour> tourList = tourService.queryTour(tourName,startPlace,endPlace,tuijianFlag,addTime);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "Tour信息记录"; 
        String[] headers = { "旅游id","旅游景点名称","旅游图片","出发地","目的地","旅游价格","是否推荐","浏览次数","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<tourList.size();i++) {
        	Tour tour = tourList.get(i); 
        	dataset.add(new String[]{tour.getTourId() + "",tour.getTourName(),tour.getTourPhoto(),tour.getStartPlace(),tour.getEndPlace(),tour.getTourPrice() + "",tour.getTuijianFlag(),tour.getHitNum() + "",tour.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"Tour.xls");//filename是下载的xls的名，建议最好用英文 
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

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
import com.chengxusheji.service.DzLineService;
import com.chengxusheji.po.DzLine;

//DzLine管理控制层
@Controller
@RequestMapping("/DzLine")
public class DzLineController extends BaseController {

    /*业务层对象*/
    @Resource DzLineService dzLineService;

	@InitBinder("dzLine")
	public void initBinderDzLine(WebDataBinder binder) {
		binder.setFieldDefaultPrefix("dzLine.");
	}
	/*跳转到添加DzLine视图*/
	@RequestMapping(value = "/add", method = RequestMethod.GET)
	public String add(Model model,HttpServletRequest request) throws Exception {
		model.addAttribute(new DzLine());
		return "DzLine_add";
	}

	/*客户端ajax方式提交添加定制线路信息*/
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public void add(@Validated DzLine dzLine, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
		boolean success = false;
		if (br.hasErrors()) {
			message = "输入信息不符合要求！";
			writeJsonResponse(response, success, message);
			return ;
		}
		try {
			dzLine.setLinePhoto(this.handlePhotoUpload(request, "linePhotoFile"));
		} catch(UserException ex) {
			message = "图片格式不正确！";
			writeJsonResponse(response, success, message);
			return ;
		}
        dzLineService.addDzLine(dzLine);
        message = "定制线路添加成功!";
        success = true;
        writeJsonResponse(response, success, message);
	}
	/*ajax方式按照查询条件分页查询定制线路信息*/
	@RequestMapping(value = { "/list" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void list(String tuijianFlag,String addTime,String lineName,String startPlace,Integer page,Integer rows, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		if (page==null || page == 0) page = 1;
		if (tuijianFlag == null) tuijianFlag = "";
		if (addTime == null) addTime = "";
		if (lineName == null) lineName = "";
		if (startPlace == null) startPlace = "";
		if(rows != 0)dzLineService.setRows(rows);
		List<DzLine> dzLineList = dzLineService.queryDzLine(tuijianFlag, addTime, lineName, startPlace, page);
	    /*计算总的页数和总的记录数*/
	    dzLineService.queryTotalPageAndRecordNumber(tuijianFlag, addTime, lineName, startPlace);
	    /*获取到总的页码数目*/
	    int totalPage = dzLineService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dzLineService.getRecordNumber();
        response.setContentType("text/json;charset=UTF-8");
		PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象
		JSONObject jsonObj=new JSONObject();
		jsonObj.accumulate("total", recordNumber);
		JSONArray jsonArray = new JSONArray();
		for(DzLine dzLine:dzLineList) {
			JSONObject jsonDzLine = dzLine.getJsonObject();
			jsonArray.put(jsonDzLine);
		}
		jsonObj.accumulate("rows", jsonArray);
		out.println(jsonObj.toString());
		out.flush();
		out.close();
	}

	/*ajax方式按照查询条件分页查询定制线路信息*/
	@RequestMapping(value = { "/listAll" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void listAll(HttpServletResponse response) throws Exception {
		List<DzLine> dzLineList = dzLineService.queryAllDzLine();
        response.setContentType("text/json;charset=UTF-8"); 
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = new JSONArray();
		for(DzLine dzLine:dzLineList) {
			JSONObject jsonDzLine = new JSONObject();
			jsonDzLine.accumulate("lineId", dzLine.getLineId());
			jsonDzLine.accumulate("lineName", dzLine.getLineName());
			jsonArray.put(jsonDzLine);
		}
		out.println(jsonArray.toString());
		out.flush();
		out.close();
	}

	/*前台按照查询条件分页查询定制线路信息*/
	@RequestMapping(value = { "/frontlist" }, method = {RequestMethod.GET,RequestMethod.POST})
	public String frontlist(String tuijianFlag,String addTime,String lineName,String startPlace,Integer currentPage, Model model, HttpServletRequest request) throws Exception  {
		if (currentPage==null || currentPage == 0) currentPage = 1;
		if (tuijianFlag == null) tuijianFlag = "";
		if (addTime == null) addTime = "";
		if (lineName == null) lineName = "";
		if (startPlace == null) startPlace = "";
		List<DzLine> dzLineList = dzLineService.queryDzLine(tuijianFlag, addTime, lineName, startPlace, currentPage);
	    /*计算总的页数和总的记录数*/
	    dzLineService.queryTotalPageAndRecordNumber(tuijianFlag, addTime, lineName, startPlace);
	    /*获取到总的页码数目*/
	    int totalPage = dzLineService.getTotalPage();
	    /*当前查询条件下总记录数*/
	    int recordNumber = dzLineService.getRecordNumber();
	    request.setAttribute("dzLineList",  dzLineList);
	    request.setAttribute("totalPage", totalPage);
	    request.setAttribute("recordNumber", recordNumber);
	    request.setAttribute("currentPage", currentPage);
	    request.setAttribute("tuijianFlag", tuijianFlag);
	    request.setAttribute("addTime", addTime);
	    request.setAttribute("lineName", lineName);
	    request.setAttribute("startPlace", startPlace);
		return "DzLine/dzLine_frontquery_result"; 
	}

     /*前台查询DzLine信息*/
	@RequestMapping(value="/{lineId}/frontshow",method=RequestMethod.GET)
	public String frontshow(@PathVariable Integer lineId,Model model,HttpServletRequest request) throws Exception {
		/*根据主键lineId获取DzLine对象*/
        DzLine dzLine = dzLineService.getDzLine(lineId);
        dzLine.setHitNum(dzLine.getHitNum() + 1);
        dzLineService.updateDzLine(dzLine);
        
        request.setAttribute("dzLine",  dzLine);
        return "DzLine/dzLine_frontshow";
	}

	/*ajax方式显示定制线路修改jsp视图页*/
	@RequestMapping(value="/{lineId}/update",method=RequestMethod.GET)
	public void update(@PathVariable Integer lineId,Model model,HttpServletRequest request,HttpServletResponse response) throws Exception {
        /*根据主键lineId获取DzLine对象*/
        DzLine dzLine = dzLineService.getDzLine(lineId);

        response.setContentType("text/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
		//将要被返回到客户端的对象 
		JSONObject jsonDzLine = dzLine.getJsonObject();
		out.println(jsonDzLine.toString());
		out.flush();
		out.close();
	}

	/*ajax方式更新定制线路信息*/
	@RequestMapping(value = "/{lineId}/update", method = RequestMethod.POST)
	public void update(@Validated DzLine dzLine, BindingResult br,
			Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
		String message = "";
    	boolean success = false;
		if (br.hasErrors()) { 
			message = "输入的信息有错误！";
			writeJsonResponse(response, success, message);
			return;
		}
		String linePhotoFileName = this.handlePhotoUpload(request, "linePhotoFile");
		if(!linePhotoFileName.equals("upload/NoImage.jpg"))dzLine.setLinePhoto(linePhotoFileName); 


		try {
			dzLineService.updateDzLine(dzLine);
			message = "定制线路更新成功!";
			success = true;
			writeJsonResponse(response, success, message);
		} catch (Exception e) {
			e.printStackTrace();
			message = "定制线路更新失败!";
			writeJsonResponse(response, success, message); 
		}
	}
    /*删除定制线路信息*/
	@RequestMapping(value="/{lineId}/delete",method=RequestMethod.GET)
	public String delete(@PathVariable Integer lineId,HttpServletRequest request) throws UnsupportedEncodingException {
		  try {
			  dzLineService.deleteDzLine(lineId);
	            request.setAttribute("message", "定制线路删除成功!");
	            return "message";
	        } catch (Exception e) { 
	            e.printStackTrace();
	            request.setAttribute("error", "定制线路删除失败!");
				return "error";

	        }

	}

	/*ajax方式删除多条定制线路记录*/
	@RequestMapping(value="/deletes",method=RequestMethod.POST)
	public void delete(String lineIds,HttpServletRequest request,HttpServletResponse response) throws IOException, JSONException {
		String message = "";
    	boolean success = false;
        try { 
        	int count = dzLineService.deleteDzLines(lineIds);
        	success = true;
        	message = count + "条记录删除成功";
        	writeJsonResponse(response, success, message);
        } catch (Exception e) { 
            //e.printStackTrace();
            message = "有记录存在外键约束,删除失败";
            writeJsonResponse(response, success, message);
        }
	}

	/*按照查询条件导出定制线路信息到Excel*/
	@RequestMapping(value = { "/OutToExcel" }, method = {RequestMethod.GET,RequestMethod.POST})
	public void OutToExcel(String tuijianFlag,String addTime,String lineName,String startPlace, Model model, HttpServletRequest request,HttpServletResponse response) throws Exception {
        if(tuijianFlag == null) tuijianFlag = "";
        if(addTime == null) addTime = "";
        if(lineName == null) lineName = "";
        if(startPlace == null) startPlace = "";
        List<DzLine> dzLineList = dzLineService.queryDzLine(tuijianFlag,addTime,lineName,startPlace);
        ExportExcelUtil ex = new ExportExcelUtil();
        String _title = "DzLine信息记录"; 
        String[] headers = { "线路id","线路名称","线路图片","出发地","产品主题","线路价格","是否推荐","点击率","发布时间"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<dzLineList.size();i++) {
        	DzLine dzLine = dzLineList.get(i); 
        	dataset.add(new String[]{dzLine.getLineId() + "",dzLine.getLineName(),dzLine.getLinePhoto(),dzLine.getStartPlace(),dzLine.getZhuti(),dzLine.getLinePrice() + "",dzLine.getTuijianFlag(),dzLine.getHitNum() + "",dzLine.getAddTime()});
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
			response.setHeader("Content-disposition","attachment; filename="+"DzLine.xls");//filename是下载的xls的名，建议最好用英文 
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

package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Tour;

import com.chengxusheji.mapper.TourMapper;
@Service
public class TourService {

	@Resource TourMapper tourMapper;
    /*每页显示记录数目*/
    private int rows = 10;;
    public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}

    /*保存查询后总的页数*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*保存查询到的总记录数*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*添加旅游景点记录*/
    public void addTour(Tour tour) throws Exception {
    	tourMapper.addTour(tour);
    }

    /*按照查询条件分页查询旅游景点记录*/
    public ArrayList<Tour> queryTour(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!tourName.equals("")) where = where + " and t_tour.tourName like '%" + tourName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_tour.startPlace like '%" + startPlace + "%'";
    	if(!endPlace.equals("")) where = where + " and t_tour.endPlace like '%" + endPlace + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_tour.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_tour.addTime like '%" + addTime + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return tourMapper.queryTour(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<Tour> queryTour(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime) throws Exception  { 
     	String where = "where 1=1";
    	if(!tourName.equals("")) where = where + " and t_tour.tourName like '%" + tourName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_tour.startPlace like '%" + startPlace + "%'";
    	if(!endPlace.equals("")) where = where + " and t_tour.endPlace like '%" + endPlace + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_tour.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_tour.addTime like '%" + addTime + "%'";
    	return tourMapper.queryTourList(where);
    }

    /*查询所有旅游景点记录*/
    public ArrayList<Tour> queryAllTour()  throws Exception {
        return tourMapper.queryTourList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String tourName,String startPlace,String endPlace,String tuijianFlag,String addTime) throws Exception {
     	String where = "where 1=1";
    	if(!tourName.equals("")) where = where + " and t_tour.tourName like '%" + tourName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_tour.startPlace like '%" + startPlace + "%'";
    	if(!endPlace.equals("")) where = where + " and t_tour.endPlace like '%" + endPlace + "%'";
    	if(!tuijianFlag.equals("")) where = where + " and t_tour.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_tour.addTime like '%" + addTime + "%'";
        recordNumber = tourMapper.queryTourCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取旅游景点记录*/
    public Tour getTour(int tourId) throws Exception  {
        Tour tour = tourMapper.getTour(tourId);
        return tour;
    }

    /*更新旅游景点记录*/
    public void updateTour(Tour tour) throws Exception {
        tourMapper.updateTour(tour);
    }

    /*删除一条旅游景点记录*/
    public void deleteTour (int tourId) throws Exception {
        tourMapper.deleteTour(tourId);
    }

    /*删除多条旅游景点信息*/
    public int deleteTours (String tourIds) throws Exception {
    	String _tourIds[] = tourIds.split(",");
    	for(String _tourId: _tourIds) {
    		tourMapper.deleteTour(Integer.parseInt(_tourId));
    	}
    	return _tourIds.length;
    }
}

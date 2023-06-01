package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.Tour;

public interface TourMapper {
	/*添加旅游景点信息*/
	public void addTour(Tour tour) throws Exception;

	/*按照查询条件分页查询旅游景点记录*/
	public ArrayList<Tour> queryTour(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有旅游景点记录*/
	public ArrayList<Tour> queryTourList(@Param("where") String where) throws Exception;

	/*按照查询条件的旅游景点记录数*/
	public int queryTourCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条旅游景点记录*/
	public Tour getTour(int tourId) throws Exception;

	/*更新旅游景点记录*/
	public void updateTour(Tour tour) throws Exception;

	/*删除旅游景点记录*/
	public void deleteTour(int tourId) throws Exception;

}

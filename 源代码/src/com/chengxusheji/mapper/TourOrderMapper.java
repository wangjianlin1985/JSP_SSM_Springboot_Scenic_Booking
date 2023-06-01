package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.TourOrder;

public interface TourOrderMapper {
	/*添加订单信息*/
	public void addTourOrder(TourOrder tourOrder) throws Exception;

	/*按照查询条件分页查询订单记录*/
	public ArrayList<TourOrder> queryTourOrder(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有订单记录*/
	public ArrayList<TourOrder> queryTourOrderList(@Param("where") String where) throws Exception;

	/*按照查询条件的订单记录数*/
	public int queryTourOrderCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条订单记录*/
	public TourOrder getTourOrder(int orderId) throws Exception;

	/*更新订单记录*/
	public void updateTourOrder(TourOrder tourOrder) throws Exception;

	/*删除订单记录*/
	public void deleteTourOrder(int orderId) throws Exception;

}

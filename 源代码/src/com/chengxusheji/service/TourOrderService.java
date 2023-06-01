package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.Tour;
import com.chengxusheji.po.UserInfo;
import com.chengxusheji.po.TourOrder;

import com.chengxusheji.mapper.TourOrderMapper;
@Service
public class TourOrderService {

	@Resource TourOrderMapper tourOrderMapper;
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

    /*添加订单记录*/
    public void addTourOrder(TourOrder tourOrder) throws Exception {
    	tourOrderMapper.addTourOrder(tourOrder);
    }

    /*按照查询条件分页查询订单记录*/
    public ArrayList<TourOrder> queryTourOrder(Tour tourObj,String startDate,String telephone,UserInfo userObj,String orderTime,String shzt,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(null != tourObj && tourObj.getTourId()!= null && tourObj.getTourId()!= 0)  where += " and t_tourOrder.tourObj=" + tourObj.getTourId();
    	if(!startDate.equals("")) where = where + " and t_tourOrder.startDate like '%" + startDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_tourOrder.telephone like '%" + telephone + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null  && !userObj.getUser_name().equals(""))  where += " and t_tourOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_tourOrder.orderTime like '%" + orderTime + "%'";
    	if(!shzt.equals("")) where = where + " and t_tourOrder.shzt like '%" + shzt + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return tourOrderMapper.queryTourOrder(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<TourOrder> queryTourOrder(Tour tourObj,String startDate,String telephone,UserInfo userObj,String orderTime,String shzt) throws Exception  { 
     	String where = "where 1=1";
    	if(null != tourObj && tourObj.getTourId()!= null && tourObj.getTourId()!= 0)  where += " and t_tourOrder.tourObj=" + tourObj.getTourId();
    	if(!startDate.equals("")) where = where + " and t_tourOrder.startDate like '%" + startDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_tourOrder.telephone like '%" + telephone + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_tourOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_tourOrder.orderTime like '%" + orderTime + "%'";
    	if(!shzt.equals("")) where = where + " and t_tourOrder.shzt like '%" + shzt + "%'";
    	return tourOrderMapper.queryTourOrderList(where);
    }

    /*查询所有订单记录*/
    public ArrayList<TourOrder> queryAllTourOrder()  throws Exception {
        return tourOrderMapper.queryTourOrderList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(Tour tourObj,String startDate,String telephone,UserInfo userObj,String orderTime,String shzt) throws Exception {
     	String where = "where 1=1";
    	if(null != tourObj && tourObj.getTourId()!= null && tourObj.getTourId()!= 0)  where += " and t_tourOrder.tourObj=" + tourObj.getTourId();
    	if(!startDate.equals("")) where = where + " and t_tourOrder.startDate like '%" + startDate + "%'";
    	if(!telephone.equals("")) where = where + " and t_tourOrder.telephone like '%" + telephone + "%'";
    	if(null != userObj &&  userObj.getUser_name() != null && !userObj.getUser_name().equals(""))  where += " and t_tourOrder.userObj='" + userObj.getUser_name() + "'";
    	if(!orderTime.equals("")) where = where + " and t_tourOrder.orderTime like '%" + orderTime + "%'";
    	if(!shzt.equals("")) where = where + " and t_tourOrder.shzt like '%" + shzt + "%'";
        recordNumber = tourOrderMapper.queryTourOrderCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取订单记录*/
    public TourOrder getTourOrder(int orderId) throws Exception  {
        TourOrder tourOrder = tourOrderMapper.getTourOrder(orderId);
        return tourOrder;
    }

    /*更新订单记录*/
    public void updateTourOrder(TourOrder tourOrder) throws Exception {
        tourOrderMapper.updateTourOrder(tourOrder);
    }

    /*删除一条订单记录*/
    public void deleteTourOrder (int orderId) throws Exception {
        tourOrderMapper.deleteTourOrder(orderId);
    }

    /*删除多条订单信息*/
    public int deleteTourOrders (String orderIds) throws Exception {
    	String _orderIds[] = orderIds.split(",");
    	for(String _orderId: _orderIds) {
    		tourOrderMapper.deleteTourOrder(Integer.parseInt(_orderId));
    	}
    	return _orderIds.length;
    }
}

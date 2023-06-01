package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class TourOrder {
    /*订单id*/
    private Integer orderId;
    public Integer getOrderId(){
        return orderId;
    }
    public void setOrderId(Integer orderId){
        this.orderId = orderId;
    }

    /*旅游景点*/
    private Tour tourObj;
    public Tour getTourObj() {
        return tourObj;
    }
    public void setTourObj(Tour tourObj) {
        this.tourObj = tourObj;
    }

    /*出发日期*/
    @NotEmpty(message="出发日期不能为空")
    private String startDate;
    public String getStartDate() {
        return startDate;
    }
    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    /*出行人数*/
    @NotNull(message="必须输入出行人数")
    private Integer totalPersonNum;
    public Integer getTotalPersonNum() {
        return totalPersonNum;
    }
    public void setTotalPersonNum(Integer totalPersonNum) {
        this.totalPersonNum = totalPersonNum;
    }

    /*总价格*/
    @NotNull(message="必须输入总价格")
    private Float totalPrice;
    public Float getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    /*联系电话*/
    @NotEmpty(message="联系电话不能为空")
    private String telephone;
    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /*订单备注*/
    private String orderMemo;
    public String getOrderMemo() {
        return orderMemo;
    }
    public void setOrderMemo(String orderMemo) {
        this.orderMemo = orderMemo;
    }

    /*订单用户*/
    private UserInfo userObj;
    public UserInfo getUserObj() {
        return userObj;
    }
    public void setUserObj(UserInfo userObj) {
        this.userObj = userObj;
    }

    /*下单时间*/
    @NotEmpty(message="下单时间不能为空")
    private String orderTime;
    public String getOrderTime() {
        return orderTime;
    }
    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    /*审核状态*/
    @NotEmpty(message="审核状态不能为空")
    private String shzt;
    public String getShzt() {
        return shzt;
    }
    public void setShzt(String shzt) {
        this.shzt = shzt;
    }

    /*审核回复*/
    @NotEmpty(message="审核回复不能为空")
    private String shhf;
    public String getShhf() {
        return shhf;
    }
    public void setShhf(String shhf) {
        this.shhf = shhf;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonTourOrder=new JSONObject(); 
		jsonTourOrder.accumulate("orderId", this.getOrderId());
		jsonTourOrder.accumulate("tourObj", this.getTourObj().getTourName());
		jsonTourOrder.accumulate("tourObjPri", this.getTourObj().getTourId());
		jsonTourOrder.accumulate("startDate", this.getStartDate().length()>19?this.getStartDate().substring(0,19):this.getStartDate());
		jsonTourOrder.accumulate("totalPersonNum", this.getTotalPersonNum());
		jsonTourOrder.accumulate("totalPrice", this.getTotalPrice());
		jsonTourOrder.accumulate("telephone", this.getTelephone());
		jsonTourOrder.accumulate("orderMemo", this.getOrderMemo());
		jsonTourOrder.accumulate("userObj", this.getUserObj().getName());
		jsonTourOrder.accumulate("userObjPri", this.getUserObj().getUser_name());
		jsonTourOrder.accumulate("orderTime", this.getOrderTime().length()>19?this.getOrderTime().substring(0,19):this.getOrderTime());
		jsonTourOrder.accumulate("shzt", this.getShzt());
		jsonTourOrder.accumulate("shhf", this.getShhf());
		return jsonTourOrder;
    }}
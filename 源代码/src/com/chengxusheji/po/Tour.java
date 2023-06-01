package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class Tour {
    /*旅游id*/
    private Integer tourId;
    public Integer getTourId(){
        return tourId;
    }
    public void setTourId(Integer tourId){
        this.tourId = tourId;
    }

    /*旅游景点名称*/
    @NotEmpty(message="旅游景点名称不能为空")
    private String tourName;
    public String getTourName() {
        return tourName;
    }
    public void setTourName(String tourName) {
        this.tourName = tourName;
    }

    /*旅游图片*/
    private String tourPhoto;
    public String getTourPhoto() {
        return tourPhoto;
    }
    public void setTourPhoto(String tourPhoto) {
        this.tourPhoto = tourPhoto;
    }

    /*出发地*/
    @NotEmpty(message="出发地不能为空")
    private String startPlace;
    public String getStartPlace() {
        return startPlace;
    }
    public void setStartPlace(String startPlace) {
        this.startPlace = startPlace;
    }

    /*目的地*/
    @NotEmpty(message="目的地不能为空")
    private String endPlace;
    public String getEndPlace() {
        return endPlace;
    }
    public void setEndPlace(String endPlace) {
        this.endPlace = endPlace;
    }

    /*旅游价格*/
    @NotNull(message="必须输入旅游价格")
    private Float tourPrice;
    public Float getTourPrice() {
        return tourPrice;
    }
    public void setTourPrice(Float tourPrice) {
        this.tourPrice = tourPrice;
    }

    /*旅游景点介绍*/
    @NotEmpty(message="旅游景点介绍不能为空")
    private String tourDesc;
    public String getTourDesc() {
        return tourDesc;
    }
    public void setTourDesc(String tourDesc) {
        this.tourDesc = tourDesc;
    }

    /*是否推荐*/
    @NotEmpty(message="是否推荐不能为空")
    private String tuijianFlag;
    public String getTuijianFlag() {
        return tuijianFlag;
    }
    public void setTuijianFlag(String tuijianFlag) {
        this.tuijianFlag = tuijianFlag;
    }

    /*浏览次数*/
    @NotNull(message="必须输入浏览次数")
    private Integer hitNum;
    public Integer getHitNum() {
        return hitNum;
    }
    public void setHitNum(Integer hitNum) {
        this.hitNum = hitNum;
    }

    /*发布时间*/
    @NotEmpty(message="发布时间不能为空")
    private String addTime;
    public String getAddTime() {
        return addTime;
    }
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public JSONObject getJsonObject() throws JSONException {
    	JSONObject jsonTour=new JSONObject(); 
		jsonTour.accumulate("tourId", this.getTourId());
		jsonTour.accumulate("tourName", this.getTourName());
		jsonTour.accumulate("tourPhoto", this.getTourPhoto());
		jsonTour.accumulate("startPlace", this.getStartPlace());
		jsonTour.accumulate("endPlace", this.getEndPlace());
		jsonTour.accumulate("tourPrice", this.getTourPrice());
		jsonTour.accumulate("tourDesc", this.getTourDesc());
		jsonTour.accumulate("tuijianFlag", this.getTuijianFlag());
		jsonTour.accumulate("hitNum", this.getHitNum());
		jsonTour.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonTour;
    }}
package com.chengxusheji.po;

import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;
import org.json.JSONException;
import org.json.JSONObject;

public class DzLine {
    /*线路id*/
    private Integer lineId;
    public Integer getLineId(){
        return lineId;
    }
    public void setLineId(Integer lineId){
        this.lineId = lineId;
    }

    /*线路名称*/
    @NotEmpty(message="线路名称不能为空")
    private String lineName;
    public String getLineName() {
        return lineName;
    }
    public void setLineName(String lineName) {
        this.lineName = lineName;
    }

    /*线路图片*/
    private String linePhoto;
    public String getLinePhoto() {
        return linePhoto;
    }
    public void setLinePhoto(String linePhoto) {
        this.linePhoto = linePhoto;
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

    /*产品主题*/
    @NotEmpty(message="产品主题不能为空")
    private String zhuti;
    public String getZhuti() {
        return zhuti;
    }
    public void setZhuti(String zhuti) {
        this.zhuti = zhuti;
    }

    /*线路价格*/
    @NotNull(message="必须输入线路价格")
    private Float linePrice;
    public Float getLinePrice() {
        return linePrice;
    }
    public void setLinePrice(Float linePrice) {
        this.linePrice = linePrice;
    }

    /*线路描述*/
    @NotEmpty(message="线路描述不能为空")
    private String lineDesc;
    public String getLineDesc() {
        return lineDesc;
    }
    public void setLineDesc(String lineDesc) {
        this.lineDesc = lineDesc;
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

    /*点击率*/
    @NotNull(message="必须输入点击率")
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
    	JSONObject jsonDzLine=new JSONObject(); 
		jsonDzLine.accumulate("lineId", this.getLineId());
		jsonDzLine.accumulate("lineName", this.getLineName());
		jsonDzLine.accumulate("linePhoto", this.getLinePhoto());
		jsonDzLine.accumulate("startPlace", this.getStartPlace());
		jsonDzLine.accumulate("zhuti", this.getZhuti());
		jsonDzLine.accumulate("linePrice", this.getLinePrice());
		jsonDzLine.accumulate("lineDesc", this.getLineDesc());
		jsonDzLine.accumulate("tuijianFlag", this.getTuijianFlag());
		jsonDzLine.accumulate("hitNum", this.getHitNum());
		jsonDzLine.accumulate("addTime", this.getAddTime().length()>19?this.getAddTime().substring(0,19):this.getAddTime());
		return jsonDzLine;
    }}
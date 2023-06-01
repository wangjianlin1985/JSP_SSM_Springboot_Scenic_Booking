package com.chengxusheji.service;

import java.util.ArrayList;
import javax.annotation.Resource; 
import org.springframework.stereotype.Service;
import com.chengxusheji.po.DzLine;

import com.chengxusheji.mapper.DzLineMapper;
@Service
public class DzLineService {

	@Resource DzLineMapper dzLineMapper;
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

    /*添加定制线路记录*/
    public void addDzLine(DzLine dzLine) throws Exception {
    	dzLineMapper.addDzLine(dzLine);
    }

    /*按照查询条件分页查询定制线路记录*/
    public ArrayList<DzLine> queryDzLine(String tuijianFlag,String addTime,String lineName,String startPlace,int currentPage) throws Exception { 
     	String where = "where 1=1";
    	if(!tuijianFlag.equals("")) where = where + " and t_dzLine.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_dzLine.addTime like '%" + addTime + "%'";
    	if(!lineName.equals("")) where = where + " and t_dzLine.lineName like '%" + lineName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_dzLine.startPlace like '%" + startPlace + "%'";
    	int startIndex = (currentPage-1) * this.rows;
    	return dzLineMapper.queryDzLine(where, startIndex, this.rows);
    }

    /*按照查询条件查询所有记录*/
    public ArrayList<DzLine> queryDzLine(String tuijianFlag,String addTime,String lineName,String startPlace) throws Exception  { 
     	String where = "where 1=1";
    	if(!tuijianFlag.equals("")) where = where + " and t_dzLine.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_dzLine.addTime like '%" + addTime + "%'";
    	if(!lineName.equals("")) where = where + " and t_dzLine.lineName like '%" + lineName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_dzLine.startPlace like '%" + startPlace + "%'";
    	return dzLineMapper.queryDzLineList(where);
    }

    /*查询所有定制线路记录*/
    public ArrayList<DzLine> queryAllDzLine()  throws Exception {
        return dzLineMapper.queryDzLineList("where 1=1");
    }

    /*当前查询条件下计算总的页数和记录数*/
    public void queryTotalPageAndRecordNumber(String tuijianFlag,String addTime,String lineName,String startPlace) throws Exception {
     	String where = "where 1=1";
    	if(!tuijianFlag.equals("")) where = where + " and t_dzLine.tuijianFlag like '%" + tuijianFlag + "%'";
    	if(!addTime.equals("")) where = where + " and t_dzLine.addTime like '%" + addTime + "%'";
    	if(!lineName.equals("")) where = where + " and t_dzLine.lineName like '%" + lineName + "%'";
    	if(!startPlace.equals("")) where = where + " and t_dzLine.startPlace like '%" + startPlace + "%'";
        recordNumber = dzLineMapper.queryDzLineCount(where);
        int mod = recordNumber % this.rows;
        totalPage = recordNumber / this.rows;
        if(mod != 0) totalPage++;
    }

    /*根据主键获取定制线路记录*/
    public DzLine getDzLine(int lineId) throws Exception  {
        DzLine dzLine = dzLineMapper.getDzLine(lineId);
        return dzLine;
    }

    /*更新定制线路记录*/
    public void updateDzLine(DzLine dzLine) throws Exception {
        dzLineMapper.updateDzLine(dzLine);
    }

    /*删除一条定制线路记录*/
    public void deleteDzLine (int lineId) throws Exception {
        dzLineMapper.deleteDzLine(lineId);
    }

    /*删除多条定制线路信息*/
    public int deleteDzLines (String lineIds) throws Exception {
    	String _lineIds[] = lineIds.split(",");
    	for(String _lineId: _lineIds) {
    		dzLineMapper.deleteDzLine(Integer.parseInt(_lineId));
    	}
    	return _lineIds.length;
    }
}

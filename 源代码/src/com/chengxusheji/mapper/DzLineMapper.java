package com.chengxusheji.mapper;

import java.util.ArrayList;
import org.apache.ibatis.annotations.Param;
import com.chengxusheji.po.DzLine;

public interface DzLineMapper {
	/*添加定制线路信息*/
	public void addDzLine(DzLine dzLine) throws Exception;

	/*按照查询条件分页查询定制线路记录*/
	public ArrayList<DzLine> queryDzLine(@Param("where") String where,@Param("startIndex") int startIndex,@Param("pageSize") int pageSize) throws Exception;

	/*按照查询条件查询所有定制线路记录*/
	public ArrayList<DzLine> queryDzLineList(@Param("where") String where) throws Exception;

	/*按照查询条件的定制线路记录数*/
	public int queryDzLineCount(@Param("where") String where) throws Exception; 

	/*根据主键查询某条定制线路记录*/
	public DzLine getDzLine(int lineId) throws Exception;

	/*更新定制线路记录*/
	public void updateDzLine(DzLine dzLine) throws Exception;

	/*删除定制线路记录*/
	public void deleteDzLine(int lineId) throws Exception;

}

package com.wsddata.dao;

import java.util.List;
import java.util.Map;

public interface DataMapper {
	public List<Map> selectAll(String tableName,String order);
	public List<Map> selectWithPage(String tableName,int pageNumber,int currentPage,String order);
}

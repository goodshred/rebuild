/*
Copyright (c) REBUILD <https://getrebuild.com/> and its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.server.service;

import cn.devezhao.persist4j.Record;
import cn.devezhao.persist4j.engine.ID;
import com.rebuild.server.Application;

/**
 * 业务对象基础服务类定义
 * 
 * @author devezhao
 * @since 12/28/2018
 */
public interface ServiceSpec {
	
	/**
	 * 专供某个实体的
	 * 
	 * @return
	 * @see Application#getEntityService(int)
	 */
	int getEntityCode();
	
	/**
	 * 新建或更新
	 * 
	 * @param record
	 * @return
	 * 
	 * @see #create(Record)
	 * @see #update(Record)
	 */
	Record createOrUpdate(Record record);
	
	/**
	 * 新建
	 * 
	 * @param record
	 * @return
	 */
	Record create(Record record);
	
	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	Record update(Record record);
	
	/**
	 * 删除
	 * 
	 * @param recordId
	 * @return
	 */
	int delete(ID recordId);
}

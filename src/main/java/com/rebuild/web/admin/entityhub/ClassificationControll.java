/*
Copyright (c) REBUILD <https://getrebuild.com/> and its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.web.admin.entityhub;

import cn.devezhao.persist4j.Record;
import cn.devezhao.persist4j.engine.ID;
import com.rebuild.server.Application;
import com.rebuild.server.metadata.EntityHelper;
import com.rebuild.server.service.configuration.ClassificationService;
import com.rebuild.utils.JSONUtils;
import com.rebuild.web.BasePageControll;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 分类数据管理
 * 
 * @author devezhao zhaofang123@gmail.com
 * @since 2019/03/27
 */
@Controller
@RequestMapping("/admin/entityhub/")
public class ClassificationControll extends BasePageControll {
	
	@RequestMapping("classifications")
	public ModelAndView pageList() throws IOException {
		return createModelAndView("/admin/entityhub/classification-list.jsp");
	}
	
	@RequestMapping("classification/{id}")
	public ModelAndView pageData(@PathVariable String id,
                                 HttpServletResponse resp) throws IOException {
		Object[] data = Application.createQuery(
				"select name,openLevel from Classification where dataId = ?")
				.setParameter(1, ID.valueOf(id))
				.unique();
		if (data == null) {
			resp.sendError(404, "分类数据不存在");
			return null;
		}
		
		ModelAndView mv = createModelAndView("/admin/entityhub/classification-editor.jsp");
		mv.getModel().put("dataId", id);
		mv.getModel().put("name", data[0]);
		mv.getModel().put("openLevel", data[1]);
		return mv;
	}

	private static final String[] CN_NUMBER = new String[] { "一", "二", "三", "四" };

	@RequestMapping("classification/list")
	public void list(HttpServletResponse resp) throws IOException {
		Object[][] array = Application.createQuery(
				"select dataId,name,isDisabled,openLevel from Classification order by name")
				.array();
		for (Object[] o : array) {
			int level = (int) o[3];
			if (level >= 0 && level <= 3) {
				o[3] = CN_NUMBER[level];
			}
		}
		writeSuccess(resp, array);
	}
	
	@RequestMapping("classification/info")
	public void info(HttpServletRequest request, HttpServletResponse resp) throws IOException {
		ID dataId = getIdParameterNotNull(request, "id");
		Object[] data = Application.createQuery(
				"select name from Classification where dataId = ?")
				.setParameter(1, dataId)
				.unique();
		if (data == null) {
			writeFailure(resp, "分类数据不存在");
			return;
		}
		writeSuccess(resp, JSONUtils.toJSONObject("name", data[0]));
	}
	
	@RequestMapping("classification/save-data-item")
	public void saveDataItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ID user = getRequestUser(request);
		ID itemId = getIdParameter(request, "item_id");
		ID dataId = getIdParameter(request, "data_id");
		
		Record item;
		if (itemId != null) {
			item = EntityHelper.forUpdate(itemId, user);
		} else if (dataId != null) {
			ID parent = getIdParameter(request, "parent");
			int level = getIntParameter(request, "level", 0);
			
			item = EntityHelper.forNew(EntityHelper.ClassificationData, user);
			item.setID("dataId", dataId);
			if (parent != null) {
				item.setID("parent", parent);
			}
			item.setInt("level", level);
		} else {
			writeFailure(response, "无效参数");
			return;
		}
		
		String code = getParameter(request, "code");
		String name = getParameter(request, "name");
		String hide = getParameter(request, "hide");
		if (StringUtils.isNotBlank(code)) {
			item.setString("code", code);
		}
		if (StringUtils.isNotBlank(name)) {
			item.setString("name", name);
		}
		if (StringUtils.isNotBlank(hide)) {
			item.setBoolean("isHide", BooleanUtils.toBooleanObject(hide));
		}
		item = Application.getBean(ClassificationService.class).createOrUpdateItem(item);
		writeSuccess(response, item.getPrimary());
	}
	
	@RequestMapping("classification/delete-data-item")
	public void deleteDataItem(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ID itemId = getIdParameter(request, "item_id");
		Application.getBean(ClassificationService.class).deleteItem(itemId);
		writeSuccess(response);
	}
	
	@RequestMapping("classification/load-data-items")
	public void loadDataItems(HttpServletRequest request, HttpServletResponse response) throws IOException {
		ID dataId = getIdParameterNotNull(request, "data_id");
		ID parent = getIdParameter(request, "parent");
		
		Object[][] child;
		if (parent != null) {
			child = Application.createQuery(
					"select itemId,name,code,isHide from ClassificationData where dataId = ? and parent = ? order by code,name")
					.setParameter(1, dataId)
					.setParameter(2, parent)
					.array();
		} else if (dataId != null) {
			child = Application.createQuery(
					"select itemId,name,code,isHide from ClassificationData where dataId = ? and parent is null order by code,name")
					.setParameter(1, dataId)
					.array();
		} else {
			writeFailure(response, "无效参数");
			return;
		}
		writeSuccess(response, child);
	}
}

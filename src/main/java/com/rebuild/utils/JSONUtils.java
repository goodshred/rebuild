/*
rebuild - Building your system freely.
Copyright (C) 2018 devezhao <zhaofang123@gmail.com>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program. If not, see <https://www.gnu.org/licenses/>.
*/

package com.rebuild.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

/**
 * 
 * @author devezhao
 * @since 09/29/2018
 */
public class JSONUtils {

	/**
	 * @param key
	 * @param value
	 * @return
	 */
	public static JSON toJSONObject(String key, Object value) {
		return toJSONObject(new String[] { key }, new Object[] { value });
	}

	/**
	 * @param keys
	 * @param values
	 * @return
	 */
	public static JSON toJSONObject(String keys[], Object values[]) {
		Assert.isTrue(keys.length == values.length, "K/V 长度不匹配");
		Map<String, Object> map = new HashMap<>();
		for (int i = 0; i < keys.length; i++) {
			map.put(keys[i], values[i]);
		}
		return (JSON) JSON.toJSON(map);
	}
}

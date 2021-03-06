/*
Copyright (c) REBUILD <https://getrebuild.com/> and its owners. All rights reserved.

rebuild is dual-licensed under commercial and open source licenses (GPLv3).
See LICENSE and COMMERCIAL in the project root for license information.
*/

package com.rebuild.server.service.bizz;

import cn.devezhao.persist4j.engine.ID;
import com.rebuild.server.Application;
import com.rebuild.server.TestSupport;
import com.rebuild.server.service.bizz.privileges.Department;
import com.rebuild.server.service.bizz.privileges.User;
import org.junit.Test;

import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author devezhao
 * @since 01/04/2019
 */
public class UserStoreTest extends TestSupport {

	@Test
	public void testStore() {
		Application.getUserStore().getAllUsers();
		Application.getUserStore().getAllDepartments();
		Application.getUserStore().getAllRoles();
		Application.getUserStore().getAllTeams();
		
		Application.getUserStore().getUser(UserService.ADMIN_USER);
		Application.getUserStore().getDepartment(DepartmentService.ROOT_DEPT);
		Application.getUserStore().getRole(RoleService.ADMIN_ROLE);
		Application.getUserStore().getTeam(SIMPLE_TEAM);
	}
	
	@Test
	public void testRefresh() {
		Application.getUserStore().refreshUser(UserService.ADMIN_USER);
		Application.getUserStore().refreshDepartment(DepartmentService.ROOT_DEPT);
		Application.getUserStore().refreshRole(RoleService.ADMIN_ROLE);
		Application.getUserStore().refreshTeam(SIMPLE_TEAM);
	}
	
	@Test
	public void testExists() {
		assertTrue(Application.getUserStore().existsUser("admin"));
        assertFalse(Application.getUserStore().existsUser("not_exists"));
	}

	@Test
	public void testMemberToTeam() {
		Application.getSessionStore().set(UserService.SYSTEM_USER);

		ID teamUser = UserService.ADMIN_USER;
		try {
			Application.getBean(TeamService.class).createMembers(SIMPLE_TEAM, Collections.singletonList(teamUser));
			User user = Application.getUserStore().getUser(teamUser);
			System.out.println(user.getOwningTeams());

            assertFalse(user.getOwningTeams().isEmpty());
			assertTrue(Application.getUserStore().getTeam(SIMPLE_TEAM).isMember(teamUser));

			Application.getBean(TeamService.class).deleteMembers(SIMPLE_TEAM, Collections.singletonList(teamUser));
			System.out.println(user.getOwningTeams());

		} finally {
			Application.getSessionStore().clean();
		}
	}

	@Test
	public void existsAny() {
	    Application.getUserStore().existsAny(RoleService.ADMIN_ROLE);
    }

    @Test
    public void deptMethods() {
        Department simple = Application.getUserStore().getDepartment(SIMPLE_DEPT);
        System.out.println(simple.getAllChildren());
        System.out.println(simple.isChildren(DepartmentService.ROOT_DEPT));
    }
}

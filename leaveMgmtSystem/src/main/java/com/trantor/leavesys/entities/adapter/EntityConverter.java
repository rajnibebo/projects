/**
 * 
 */
package com.trantor.leavesys.entities.adapter;

import com.trantor.leavesys.entities.Leave;
import com.trantor.leavesys.entities.User;
import com.trantor.leavesys.entities.UserLeave;
import com.trantor.leavesys.entities.UserRole;
import com.trantor.leavesys.models.LeaveModel;
import com.trantor.leavesys.models.RoleModel;
import com.trantor.leavesys.models.UserLeaveModel;
import com.trantor.leavesys.models.UserModel;

import java.util.HashSet;
import java.util.Set;

import com.trantor.leavesys.business.IUserRole;

/**
 * @author rajni.ubhi
 *
 */
public class EntityConverter {
	
	public static UserConverter getUserConverter() {
		return new UserConverter();
	}
	
	public static LeaveConverter getLeaveConverter() {
		return new LeaveConverter();
	}
	
	public static UserLeaveConverter getUserLeaveConverter() {
		return new UserLeaveConverter();
	}
	
	public static RoleConverter getRoleConverter() {
		return new RoleConverter();
	}
	private static class UserConverter implements IEntityConverter<UserModel, User>{

		@Override
		public User convertModelToEntity(UserModel model) {
			// TODO Auto-generated method stub
			User user = new User();
			user.setCompanyName(model.getCompanyName());
			user.setUserName(model.getUserName());
			user.setAccountNonLocked(model.isAccountNonLocked());
			user.setCredentialNonExpired(model.isCredentialNonExpired());
			user.setEnabled(model.isEnabled());
			user.setPassword(model.getPassword());
			
			Set<IUserRole> setRoles = new HashSet<IUserRole>();
			for(IUserRole role : model.getUserRoles()) {
				IUserRole convertedRole = getRoleConverter().convertModelToEntity((RoleModel)role);
				setRoles.add(convertedRole);
			}
			user.setUserRoles(setRoles); // must be retrieved from entity converter
			return user;
		}

		@Override
		public UserModel convertEntityToModel(User entity) {
			// TODO Auto-generated method stub
			UserModel model = new UserModel();
			model.setUserId(entity.getUserId());
			model.setUserName(entity.getUserName());
			model.setCompanyName(entity.getCompanyName());
			model.setAccountNonLocked(entity.isAccountNonLocked());
			model.setCredentialNonExpired(entity.isCredentialNonExpired());
			model.setEnabled(entity.isEnabled());
			model.setPassword(entity.getPassword());
			
			Set<IUserRole> setRoles = new HashSet<IUserRole>();
			for(IUserRole role : entity.getUserRoles()) {
				IUserRole convertedRole = getRoleConverter().convertEntityToModel((UserRole) role);
				setRoles.add(convertedRole);
			}
			model.setUserRoles(setRoles);
			return model;
		}

		@Override
		public User convertModelToEntityUsingID(UserModel model) {
			// TODO Auto-generated method stub
			User user = convertModelToEntity(model);
			user.setUserId(model.getUserId());
			return user;
		}
		
	}
	
	private static class LeaveConverter implements IEntityConverter<LeaveModel, Leave>{

		@Override
		public Leave convertModelToEntity(LeaveModel model) {
			// TODO Auto-generated method stub
			Leave leave = new Leave();
			leave.setLeaveType(model.getLeaveType());
			return leave;
		}

		@Override
		public LeaveModel convertEntityToModel(Leave entity) {
			// TODO Auto-generated method stub
			LeaveModel leaveModel = new LeaveModel();
			leaveModel.setLeaveId(entity.getLeaveId());
			leaveModel.setLeaveType(entity.getLeaveType());
			return leaveModel;
		}

		@Override
		public Leave convertModelToEntityUsingID(LeaveModel model) {
			// TODO Auto-generated method stub
			Leave leave = convertModelToEntity(model);
			leave.setLeaveId(model.getLeaveId());
			return leave;
		}
		
	}
	
	private static class UserLeaveConverter implements IEntityConverter<UserLeaveModel, UserLeave> {

		@Override
		public UserLeave convertModelToEntity(UserLeaveModel model) {
			// TODO Auto-generated method stub
			UserLeave userLeave = new UserLeave();
			LeaveModel leaveModel = (LeaveModel) model.getLeave();
			userLeave.setLeave(EntityConverter.getLeaveConverter().convertModelToEntity(leaveModel));
			userLeave.setLeaveReason(model.getLeaveReason());
			userLeave.setLeaveStatus(model.getLeaveStatus());
			userLeave.setStartDate(model.getStartDate());
			userLeave.setEndDate(model.getEndDate());
			UserModel userModel = (UserModel) model.getUser();
			userLeave.setUser((EntityConverter.getUserConverter().convertModelToEntity(userModel)));
			return userLeave;
		}

		@Override
		public UserLeaveModel convertEntityToModel(UserLeave entity) {
			// TODO Auto-generated method stub
			UserLeaveModel userLeaveModel = new UserLeaveModel();
			userLeaveModel.setUserLeaveId(entity.getUserLeaveId());
			
			Leave leave = (Leave) entity.getLeave();
			userLeaveModel.setLeave(EntityConverter.getLeaveConverter().convertEntityToModel(leave));
			
			userLeaveModel.setLeaveReason(entity.getLeaveReason());
			userLeaveModel.setLeaveStatus(entity.getLeaveStatus());
			userLeaveModel.setStartDate(entity.getStartDate());
			userLeaveModel.setEndDate(entity.getEndDate());
			userLeaveModel.setUserLeaveId(entity.getUserLeaveId());
			
			User user = (User) entity.getUser();
			userLeaveModel.setUser(EntityConverter.getUserConverter().convertEntityToModel(user));
			return userLeaveModel;
		}

		@Override
		public UserLeave convertModelToEntityUsingID(UserLeaveModel model) {
			// TODO Auto-generated method stub
			UserLeave userLeave = convertModelToEntity(model);
			userLeave.setUserLeaveId(model.getUserLeaveId());
			return userLeave;
		}
		
	}
	
	private static class RoleConverter implements IEntityConverter<RoleModel, UserRole> {

		@Override
		public UserRole convertModelToEntity(RoleModel model) {
			// TODO Auto-generated method stub
			UserRole role = new UserRole();
			role.setUserRole(model.getUserRole());
			return role;
		}

		@Override
		public RoleModel convertEntityToModel(UserRole entity) {
			// TODO Auto-generated method stub
			RoleModel roleModel = new RoleModel();
			roleModel.setUserRoleId(entity.getUserRoleId());
			roleModel.setUserRole(entity.getUserRole());
			return roleModel;
		}

		@Override
		public UserRole convertModelToEntityUsingID(RoleModel model) {
			// TODO Auto-generated method stub
			UserRole role = convertModelToEntity(model);
			role.setUserRoleId(model.getUserRoleId());
			return role;
		}
		
	}
}

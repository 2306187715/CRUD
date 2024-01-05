const TokenKey = 'token';
const RoleKey = 'roles';
const UserIdKey = 'userId';
const RoleListKey = 'roleList';

export function getToken() {
  return localStorage.getItem(TokenKey);
}

export function setToken(token) {
  return localStorage.setItem(TokenKey, token);
}

export function removeToken() {
  return localStorage.removeItem(TokenKey);
}

export function getRole() {
  return localStorage.getItem(RoleKey);
}

export function setRole(role) {
  return localStorage.setItem(RoleKey, role);
}

export function removeRole() {
  return localStorage.removeItem(RoleKey);
}
export function getUserId() {
  return localStorage.getItem(UserIdKey);
}

export function setUserId(userId) {
  return localStorage.setItem(UserIdKey, userId);
}

export function removeUserId() {
  return localStorage.removeItem(UserIdKey);
}

export function getRoleList() {
  return localStorage.getItem(RoleListKey);
}

export function setRoleList(roleList) {
  return localStorage.setItem(RoleListKey, roleList);
}

export function removeRoleList() {
  return localStorage.removeItem(RoleListKey);
}

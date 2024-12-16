import Cookies from 'js-cookie'

const UsernameKey = 'username'
const TokenKey = 'sso_token'
const PermissionKey = 'permission_key'

export function getUsername() {
    return Cookies.get(UsernameKey)
}

export function setUsername(username) {
    return Cookies.set(UsernameKey, username)
}

export function getToken() {
    return Cookies.get(TokenKey)
}

export function setToken(token) {
    return Cookies.set(TokenKey, token)
}

export function removeToken() {
    return Cookies.remove(TokenKey)
}

export function getClientToken() {
    return 'Basic Y2xpZW50LXBhc3N3b3JkOmNsaWVudC1wYXNzd29yZA=='
}

export function getPermission() {
    return Cookies.get(PermissionKey)
}

export function setPermission(permission) {
    return Cookies.set(PermissionKey, permission)
}

export function removePermission() {
    return Cookies.remove(PermissionKey)
}
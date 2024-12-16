import request from '@/utils/request'
import qs from 'qs'

const grant_type = 'password'

export function login(data) {
  return request({
    baseURL: 'auth',
    url: '/oauth/token',
    method: 'post',
    data: qs.stringify({ grant_type: grant_type, username: data.username, password: data.password })
  })
}

export function getInfo(token) {
  return request({
    baseURL: 'auth',
    url: '/user/info',
    method: 'post'
  })
}

export function logout() {
  return request({
    baseURL: 'auth',
    url: '/user/info',
    method: 'post'
  })
}

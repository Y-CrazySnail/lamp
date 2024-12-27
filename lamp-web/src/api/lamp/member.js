import request from '@/utils/request'

export function login(data) {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-member/login',
    method: 'post',
    data
  })
}

export function signup(data) {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-member/signup',
    method: 'post',
    data
  })
}

export function get() {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-member/get',
    method: 'get'
  })
}

export function updatePassword(data) {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-member/updatePassword',
    method: 'post',
    data
  })
}

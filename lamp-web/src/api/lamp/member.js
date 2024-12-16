import request from '@/utils/request'

export function login(data) {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-member/login',
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

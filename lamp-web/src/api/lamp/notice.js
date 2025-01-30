import request from '@/utils/request'

export function list() {
  return request({
    baseURL: 'lamp',
    url: '/web/lamp-notice/list',
    method: 'get'
  })
}

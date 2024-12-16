import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-user/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-user/getById',
        method: 'get',
        params
    })
}

export function distributionUserList(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-user/distribution-user-list',
        method: 'get',
        params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-zero',
        url: '/manager-zero-user/update',
        method: 'put',
        data: params
    })
}
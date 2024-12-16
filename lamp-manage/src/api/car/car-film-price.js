import request from '@/utils/request'

// 列表
export function page(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-price/pages',
        method: 'get',
        params
    })
}

// 列表
export function levelList(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-price/level-list',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-price/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-price/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-price/delete',
        method: 'delete',
        data: params
    })
}
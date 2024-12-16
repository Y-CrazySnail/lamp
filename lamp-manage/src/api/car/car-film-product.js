import request from '@/utils/request'

// 列表
export function list(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-product/list',
        method: 'get',
        params
    })
}

// 列表
export function page(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-product/pages',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-product/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-product/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-product/delete',
        method: 'delete',
        data: params
    })
}
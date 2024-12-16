import request from '@/utils/request'

// 列表
export function list(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-tenant/list',
        method: 'get',
        params
    })
}

// 分页
export function pages(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-tenant/pages',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-tenant/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-tenant/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-tenant/delete',
        method: 'delete',
        data: params
    })
}
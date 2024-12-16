import request from '@/utils/request'

export function list(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-dictionary/list',
        method: 'get',
        params
    })
}

// 列表
export function pages(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-dictionary/pages',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-dictionary/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-dictionary/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-dictionary/delete',
        method: 'delete',
        data: params
    })
}
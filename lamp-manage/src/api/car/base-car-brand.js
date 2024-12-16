import request from '@/utils/request'

// 列表
export function pages(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-brand/pages',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-brand/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-brand/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/base-car-brand/delete',
        method: 'delete',
        data: params
    })
}
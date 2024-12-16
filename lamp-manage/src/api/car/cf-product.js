import request from '@/utils/request'

// 根据ID查询
export function getById(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/getById',
        method: 'get',
        params
    })
}

// 列表
export function list(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/list',
        method: 'get',
        params
    })
}

// 分页
export function page(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/page',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/cf-product/delete',
        method: 'delete',
        data: params
    })
}
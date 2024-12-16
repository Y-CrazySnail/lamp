import request from '@/utils/request'

// 列表
export function page(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-quality/pages',
        method: 'get',
        params
    })
}

// 新增
export function save(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-quality/save',
        method: 'post',
        data: params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-quality/update',
        method: 'put',
        data: params
    })
}

// 删除
export function remove(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-quality/delete',
        method: 'delete',
        data: params
    })
}
import request from '@/utils/request'

// 分页
export function pages(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-user/pages',
        method: 'get',
        params
    })
}

// 更新
export function edit(params) {
    return request({
        baseURL: 'saas-car-film',
        url: '/car-film-user/update',
        method: 'put',
        data: params
    })
}
import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/pages',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/list',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/package/delete',
        method: 'delete',
        data: params
    })
}

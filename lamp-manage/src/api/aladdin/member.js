import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/page',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/get',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/list',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-member/delete',
        method: 'delete',
        data: params
    })
}

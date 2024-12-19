import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-inbound/page',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-inbound/get',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-inbound/save',
        method: 'post',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/lamp-inbound/remove',
        method: 'delete',
        data: params
    })
}


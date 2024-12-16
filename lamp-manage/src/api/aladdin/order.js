import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/pages',
        method: 'get',
        params
    })
}

export function getById(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/list',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/save',
        method: 'post',
        data: params
    })
}

export function pay(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/pay',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'aladdin',
        url: '/manage/order/delete',
        method: 'delete',
        data: params
    })
}

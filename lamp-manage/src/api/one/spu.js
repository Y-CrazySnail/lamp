import request from '@/utils/request'

export function page(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/page',
        method: 'get',
        params
    })
}

export function get(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/getById',
        method: 'get',
        params
    })
}

export function all(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/getAll',
        method: 'get',
        params
    })
}

export function save(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/save',
        method: 'post',
        data: params
    })
}

export function update(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/update',
        method: 'put',
        data: params
    })
}

export function remove(params) {
    return request({
        baseURL: 'e-one',
        url: '/manage/spu/remove',
        method: 'delete',
        data: params
    })
}
import {list, pages, save, edit, remove} from '@/api/car/base-car-dictionary'

const state = {}

const mutations = {}

const actions = {
    list({commit, state}, params) {
        return new Promise((resolve, reject) => {
            list(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },

    pages({commit, state}, params) {
        return new Promise((resolve, reject) => {
            pages(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },

    save({commit, state}, params) {
        return new Promise((resolve, reject) => {
            save(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },

    edit({commit, state}, params) {
        return new Promise((resolve, reject) => {
            edit(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },

    remove({commit, state}, params) {
        return new Promise((resolve, reject) => {
            remove(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    }
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
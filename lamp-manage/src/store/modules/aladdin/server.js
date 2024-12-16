import { page, all, save, update, remove, getById } from '@/api/aladdin/server'

const state = {}

const mutations = {}

const actions = {
    page({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            page(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    all({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            getById(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    all({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    save({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            save(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    update({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            update(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    remove({ commit, state }, params) {
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
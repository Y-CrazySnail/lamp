import { page, getById, save, update, remove, refund } from '@/api/zero/order'

const state = {
    order: {}
}

const mutations = {
    setOrder: (state, data) => {
        state.order = data
    }
}

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
    getById({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            getById(params).then(response => {
                commit('setOrder', response)
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
    },
    refund({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            refund(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
import { page, save, edit, remove, levelList } from '@/api/car/car-film-price'

const state = {
    levelList: []
}

const mutations = {}

const actions = {
    levelList({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            levelList(params).then(response => {
                state.levelList = response
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },

    pages({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            page(params).then(response => {
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

    edit({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            edit(params).then(response => {
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
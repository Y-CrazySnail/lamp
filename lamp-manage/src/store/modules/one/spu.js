import { page, get, all, save, update, remove } from '@/api/one/spu'

const state = {
    spuList: []
}

const mutations = {
    setSpuList: (state, data) => {
        state.spuList = data
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
    get({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            get(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    all({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                commit('setSpuList', response)
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
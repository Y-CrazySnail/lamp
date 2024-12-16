import { page, all, save, update, remove } from '@/api/system/dictionary'

const state = {
    dictionaryList: []
}

const mutations = {
    setDictionaryList: (state, data) => {
        state.dictionaryList = data
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
    all({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                commit('setDictionaryList', response)
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
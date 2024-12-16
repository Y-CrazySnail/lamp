import { all, apple, updateUUID } from '@/api/lamp/service'

const state = {
    serviceList: [],
    service: {},
    apple: {
        "username": "",
        "password": ""
    }
}

const mutations = {
    setServiceList: (state, data) => {
        state.serviceList = data
    },
    setApple: (state, data) => {
        state.apple = data
    }
}

const actions = {
    list({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                commit('setServiceList', response)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    apple({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            apple(params).then(response => {
                commit('setApple', response)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    updateUUID({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            updateUUID(params).then(response => {
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
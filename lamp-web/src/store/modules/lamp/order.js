import { all, place, pay } from '@/api/lamp/order'

const state = {
    orderList: []
}

const mutations = {
    setOrderList: (state, data) => {
        state.orderList = data
    }
}

const actions = {
    list({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                commit('setOrderList', response)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    place({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            place(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    pay({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            pay(params).then(response => {
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
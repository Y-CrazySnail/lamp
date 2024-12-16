import { page, get, distributionUserList, update } from '@/api/zero/member'

const state = {
    distributionUserList: []
}

const mutations = {
    setDistributionUserList: (state, data) => {
        state.distributionUserList = data
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
            get(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    distributionUserList({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            distributionUserList(params).then(response => {
                commit('setDistributionUserList', response)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    update({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            console.log(params)
            update(params).then(response => {
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
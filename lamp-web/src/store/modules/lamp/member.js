import { login, get, updatePassword } from '@/api/lamp/member'

const state = {
    member: {}
}

const mutations = {
    setMember: (state, data) => {
        state.member = data
    }
}

const actions = {
    get({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            get(params).then(response => {
                commit('setMember', response)
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    login({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            login(params).then(response => {
                resolve(response)
            }).catch(error => {
                reject(error)
            })
        })
    },
    updatePassword({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            updatePassword(params).then(response => {
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
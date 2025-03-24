import { all, list } from '@/api/lamp/package'

const state = {
    packageList: [],
    spuList: []
}

const mutations = {
    setPackageList: (state, data) => {
        state.packageList = data
    },
    setSpuList: (state, data) => {
        state.spuList = data
    },
}

const actions = {
    list({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            list(params).then(response => {
                commit('setSpuList', response)
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

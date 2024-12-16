import { all } from '@/api/lamp/package'

const state = {
    packageList: []
}

const mutations = {
    setPackageList: (state, data) => {
        state.packageList = data
    }
}

const actions = {
    list({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            all(params).then(response => {
                commit('setPackageList', response)
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

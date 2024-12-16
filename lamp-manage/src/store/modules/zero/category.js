import { dict } from '@/api/zero/category'

const state = {
    
}

const mutations = {
    
}

const actions = {
    dict({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            dict(params).then(response => {
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
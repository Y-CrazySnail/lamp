import { pages, edit } from '@/api/car/car-film-user'

const state = {}

const mutations = {}

const actions = {
    pages({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            pages(params).then(response => {
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
}

export default {
    namespaced: true,
    state,
    mutations,
    actions
}
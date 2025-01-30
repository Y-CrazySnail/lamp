import { list } from '@/api/lamp/notice'

const state = {
    noticeList: []
}

const mutations = {
    setNoticeList: (state, data) => {
        state.noticeList = data
    }
}

const actions = {
    list({ commit, state }, params) {
        return new Promise((resolve, reject) => {
            list(params).then(response => {
                commit('setNoticeList', response)
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
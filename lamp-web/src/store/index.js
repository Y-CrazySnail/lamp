import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import auth from './modules/auth'
import member from './modules/lamp/member'
import service from './modules/lamp/service'
import packages from './modules/lamp/package'
import order from './modules/lamp/order'
import notice from './modules/lamp/notice'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        app,
        settings,
        auth,
        member,
        service,
        packages,
        order,
        notice
    },
    getters
})

export default store

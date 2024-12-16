import Vue from 'vue'

import 'normalize.css/normalize.css' // A modern alternative to CSS resets

import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import '@/assets/css/common.css'

import '@/styles/index.scss' // global css

import qs from 'qs'

import App from './App'
import store from './store'
import router from './router'

import VueClipboard from 'vue-clipboard2'
Vue.use(VueClipboard)

import UUID from "vue-uuid";
Vue.use(UUID);

import '@/icons' // icon
import '@/permission' // permission control

import Chat from 'vue-beautiful-chat'
Vue.use(Chat)

Vue.use(ElementUI)

Vue.prototype.$qs = qs

Vue.config.productionTip = false

new Vue({
    el: '#app',
    router,
    store,
    render: h => h(App)
})
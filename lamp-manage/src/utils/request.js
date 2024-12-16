import axios from 'axios'
import { MessageBox, Message } from 'element-ui'
import store from '@/store'
import router from './../router'
import { getToken, removeToken, removePermission } from '@/utils/auth'

const service = axios.create({
    baseURL: 'auth',
    timeout: 60000
})

service.interceptors.request.use(
    config => {
        if (config.baseURL === 'auth') {
            config.baseURL = process.env.VUE_APP_AUTH_API
            if (config.url === '/oauth/token') {
                config.headers['Authorization'] = `Basic Y2xpZW50LXBhc3N3b3JkOmNsaWVudC1wYXNzd29yZA==`
            } else if (store.getters.token) {
                config.headers['Authorization'] = `Bearer ` + getToken()
            }
        } else if (config.baseURL === 'e-zero') {
            config.baseURL = process.env.VUE_APP_ZERO_API
            config.headers['Authorization'] = `Bearer ` + getToken()
        } else if (config.baseURL === 'e-one') {
            config.baseURL = process.env.VUE_APP_ONE_API
            config.headers['Authorization'] = `Bearer ` + getToken()
        } else if (config.baseURL === "saas-car-film") {
            config.baseURL = process.env.VUE_APP_CAR_API
            config.headers['Authorization'] = `Bearer ` + getToken()
        } else if (config.baseURL === "aladdin") {
            config.baseURL = process.env.VUE_APP_ALADDIN_API
            config.headers['Authorization'] = `Bearer ` + getToken()
        }
        return config
    },
    error => {
        console.log(error)
        return Promise.reject(error)
    }
)

service.interceptors.response.use(
    response => {
        const res = response
        if (res.status !== 200) {
            Message({
                message: res.message || 'Error',
                type: 'error',
                duration: 5 * 1000
            })

            // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
            if (res.code === 50008 || res.code === 50012 || res.code === 50014) {
                // to re-login
                MessageBox.confirm('You have been logged out, you can cancel to stay on this page, or log in again', 'Confirm logout', {
                    confirmButtonText: 'Re-Login',
                    cancelButtonText: 'Cancel',
                    type: 'warning'
                }).then(() => {
                    store.dispatch('auth/resetToken').then(() => {
                        location.reload()
                    })
                })
            }
            return Promise.reject(new Error(res.message || 'Error'))
        } else {
            return res.data
        }
    },
    error => {
        if (String(error).includes("401")) {
            removeToken()
            router.push('/')
        }
        return Promise.reject(error)
    }
)

export default service
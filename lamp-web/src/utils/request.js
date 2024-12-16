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
        if (config.baseURL === 'lamp') {
            config.baseURL = process.env.VUE_APP_ALADDIN_API
            config.headers['token'] = getToken()
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
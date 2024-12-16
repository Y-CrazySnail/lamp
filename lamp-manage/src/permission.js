import router from './router'
import store from './store'
import { Message } from 'element-ui'
import NProgress from 'nprogress'
import 'nprogress/nprogress.css'
import { getToken, getPermission } from '@/utils/auth'
import getPageTitle from '@/utils/get-page-title'

NProgress.configure({ showSpinner: false })

const whiteList = ['/login']
const ignorePermission = ['/', '/dashboard', '/404']

router.beforeEach(async(to, from, next) => {
    document.title = getPageTitle(to.meta.title)
    NProgress.start()
    const token = getToken()
    const permission = getPermission() ? JSON.parse(getPermission()) : []
    if (token) {
        // 存在token
        if (to.path === '/login') {
            next({ path: '/' })
            NProgress.done()
        } else {
            if (!permission) {
                await store.dispatch('auth/getInfo')
            }
            if (permission.includes(to.path)) {
                next()
                NProgress.done()
            } else if (ignorePermission.includes(to.path)) {
                next()
                NProgress.done()
            } else {
                next(from.path)
                NProgress.done()
            }
        }
    } else {
        // 不存在token
        if (whiteList.indexOf(to.path) !== -1) {
            next()
            NProgress.done()
        } else {
            next(`/login?redirect=${to.path}`)
            NProgress.done()
        }
    }
})

router.afterEach(() => {
    NProgress.done()
})
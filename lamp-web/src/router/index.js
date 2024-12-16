import Vue from 'vue'
import Router from 'vue-router'
import { getPermission } from '@/utils/auth'

Vue.use(Router)

import Layout from '@/layout'
const permission = getPermission() ? JSON.parse(getPermission()) : []

export const constantRoutes = [
    {
        path: '/login',
        component: () => import('@/views/login/index'),
        hidden: true
    },
    {
        path: '/pay',
        component: Layout,
        children: [{
            path: 'index',
            name: 'Pay',
            component: () => import('@/views/order/pay'),
        }]
    },
    {
        path: '/404',
        component: () =>
            import('@/views/404'),
        hidden: true
    },
    {
        path: '/',
        component: Layout,
        redirect: '/service',
        children: [{
            path: 'service',
            name: 'Service',
            component: () => import('@/views/service/index'),
            meta: { title: '用户中心', icon: 'el-icon-view' }
        }]
    },
    {
        path: '/order',
        component: Layout,
        children: [{
            path: 'index',
            name: 'Order',
            component: () => import('@/views/order/index'),
            meta: { title: '订单中心', icon: 'el-icon-document-checked' }
        }]
    },
    {
        path: '/package',
        component: Layout,
        children: [{
            path: 'index',
            name: 'Package',
            component: () => import('@/views/package/index'),
            meta: { title: '会员商店', icon: 'el-icon-shopping-cart-full' }
        }]
    },
    {
        path: '/ios',
        component: Layout,
        meta: { title: 'IOS教程', icon: 'el-icon-guide' },
        children: [{
            path: 'index',
            name: 'guide',
            component: () => import('@/views/guide/ios'),
            meta: { title: 'IOS教程', icon: 'el-icon-guide' }
        }]
    },
    {
        path: '/windows',
        component: Layout,
        meta: { title: 'Windows教程', icon: 'el-icon-guide' },
        children: [{
            path: 'index',
            name: 'guide',
            component: () => import('@/views/guide/windows'),
            meta: { title: 'Windows教程', icon: 'el-icon-guide' }
        }]
    },
    {
        path: '/mac',
        component: Layout,
        meta: { title: 'Mac教程', icon: 'el-icon-guide' },
        children: [{
            path: 'index',
            name: 'Mac',
            component: () => import('@/views/guide/mac'),
            meta: { title: 'Mac教程', icon: 'el-icon-guide' }
        }]
    },
    {
        path: '/android',
        component: Layout,
        meta: { title: '安卓教程', icon: 'el-icon-guide' },
        children: [{
            path: 'index',
            name: 'guide',
            component: () => import('@/views/guide/android'),
            meta: { title: '安卓教程', icon: 'el-icon-guide' }
        }]
    },
    { path: '*', redirect: '/404', hidden: true }
]
const createRouter = () => new Router({
    mode: 'history',
    scrollBehavior: () => ({ y: 0 }),
    routes: constantRoutes
})

const router = createRouter()
export function resetRouter() {
    const newRouter = createRouter()
    router.matcher = newRouter.matcher
}
export default router
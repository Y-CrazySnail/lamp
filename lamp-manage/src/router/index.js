import Vue from 'vue'
import Router from 'vue-router'
import { getPermission } from '@/utils/auth'

Vue.use(Router)

import Layout from '@/layout'

const permission = getPermission() ? JSON.parse(getPermission()) : []
export const constantRoutes = [{
    path: '/login',
    component: () =>
        import('@/views/login/index'),
    hidden: true
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
    redirect: '/dashboard',
    children: [{
        path: 'dashboard',
        name: 'Dashboard',
        component: () =>
            import('@/views/dashboard/index'),
        meta: { title: '仪表盘', icon: 'dashboard' }
    }]
},
{
    path: '/system',
    component: Layout,
    redirect: '/system/user',
    name: 'System',
    meta: { title: '系统设置', icon: 'el-icon-s-tools' },
    hidden: !permission.find(e => e.includes('/system')),
    children: [{
        path: 'user',
        name: 'User',
        component: () =>
            import('@/views/system/user/index'),
        meta: { title: '用户', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/system/user")
    },
    {
        path: 'role',
        name: 'Role',
        component: () =>
            import('@/views/system/role/index'),
        meta: { title: '角色', icon: 'el-icon-s-grid' },
        hidden: !permission.includes("/system/role")
    },
    {
        path: 'permission',
        name: 'Permission',
        component: () =>
            import('@/views/system/permission/index'),
        meta: { title: '权限', icon: 'el-icon-menu' },
        hidden: !permission.includes("/system/permission")
    },
    {
        path: 'dictionary',
        name: 'Dictionary',
        component: () =>
            import('@/views/system/dictionary/index'),
        meta: { title: '字典', icon: 'el-icon-menu' },
        hidden: !permission.includes("/system/dictionary")
    }
    ]
},
{
    path: '/zero',
    component: Layout,
    redirect: '/zero/product',
    name: 'Zero',
    meta: { title: '祝家酒业', icon: 'el-icon-s-promotion' },
    hidden: !permission.find(e => e.includes('/zero')),
    children: [{
        path: 'product',
        name: 'Product',
        component: () =>
            import('@/views/zero/product/index'),
        meta: { title: '商品管理', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/zero/product")
    }, {
        path: 'order',
        name: 'Order',
        component: () =>
            import('@/views/zero/order/index'),
        meta: { title: '订单管理', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/zero/order")
    }, {
        path: 'member',
        name: 'Member',
        component: () =>
            import('@/views/zero/member/index'),
        meta: { title: '会员管理', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/zero/member")
    }]
},
{
    path: '/one',
    component: Layout,
    redirect: '/one/tenant',
    name: 'One',
    meta: { title: 'One', icon: 'el-icon-s-shop' },
    hidden: !permission.find(e => e.includes('/one')),
    children: [{
        path: 'tenant',
        name: 'Tenant',
        component: () =>
            import('@/views/one/tenant/index'),
        meta: { title: '客户管理', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/one/tenant")
    },
    {
        path: 'store',
        name: 'store',
        component: () =>
            import('@/views/one/store/index'),
        meta: { title: '店铺管理', icon: 'el-icon-s-shop' },
        hidden: !permission.includes("/one/store")
    },
    {
        path: 'category',
        name: 'category',
        component: () =>
            import('@/views/one/category/index'),
        meta: { title: '分类管理', icon: 'el-icon-menu' },
        hidden: !permission.includes("/one/category")
    },
    {
        path: 'spu',
        name: 'spu',
        component: () =>
            import('@/views/one/spu/index'),
        meta: { title: '产品管理', icon: 'el-icon-s-goods' },
        hidden: !permission.includes("/one/spu")
    },
    {
        path: 'sku',
        name: 'sku',
        component: () =>
            import('@/views/one/sku/index'),
        meta: { title: '库存管理', icon: 'el-icon-s-claim' },
        hidden: !permission.includes("/one/sku")
    },
    {
        path: 'user',
        name: 'user',
        component: () =>
            import('@/views/one/user/index'),
        meta: { title: '用户管理', icon: 'el-icon-user-solid' },
        hidden: !permission.includes("/one/user")
    },
    {
        path: 'order',
        name: 'order',
        component: () =>
            import('@/views/one/order/index'),
        meta: { title: '订单管理', icon: 'el-icon-s-order' },
        hidden: !permission.includes("/one/order")
    }]
},
{
    path: '/car-film',
    component: Layout,
    redirect: '/car-film/tenant',
    name: 'CarFilm',
    meta: { title: '车膜管理', icon: 'el-icon-star-on' },
    hidden: !permission.find(e => e.includes('/car-film')),
    children: [
        {
            path: 'brand',
            name: 'Brand',
            component: () =>
                import('@/views/car-film/brand/index'),
            meta: { title: '汽车品牌', icon: 'el-icon-s-help' },
            hidden: !permission.includes("/car-film/brand")
        },
        {
            path: 'model',
            name: 'Model',
            component: () =>
                import('@/views/car-film/model/index'),
            meta: { title: '汽车型号', icon: 'el-icon-s-data' },
            hidden: !permission.includes("/car-film/model")
        },
        {
            path: 'tenant',
            name: 'Tenant',
            component: () =>
                import('@/views/car-film/tenant/index'),
            meta: { title: '客户管理', icon: 'el-icon-s-management' },
            hidden: !permission.includes("/car-film/tenant")
        },
        {
            path: 'product',
            name: 'Product',
            component: () =>
                import('@/views/car-film/product/index'),
            meta: { title: '产品管理', icon: 'el-icon-s-ticket' },
            hidden: !permission.includes("/car-film/product")
        },
        {
            path: 'price',
            name: 'Price',
            component: () =>
                import('@/views/car-film/price/index'),
            meta: { title: '价格管理', icon: 'el-icon-price-tag' },
            hidden: !permission.includes("/car-film/price")
        },
        {
            path: 'quality',
            name: 'Quality',
            component: () =>
                import('@/views/car-film/quality/index'),
            meta: { title: '质保管理', icon: 'el-icon-s-check' },
            hidden: !permission.includes("/car-film/quality")
        },
        {
            path: 'user',
            name: 'User',
            component: () =>
                import('@/views/car-film/user/index'),
            meta: { title: '用户管理', icon: 'el-icon-s-ticket' },
            hidden: !permission.includes("/car-film/user")
        },
    ]
},
{
    path: '/aladdin',
    component: Layout,
    redirect: '/aladdin/member',
    name: 'Aladdin',
    meta: { title: '阿拉丁', icon: 'el-icon-star-on' },
    hidden: !permission.find(e => e.includes('/aladdin')),
    children: [
        {
            path: 'member',
            name: 'Member',
            component: () =>
                import('@/views/aladdin/member/index'),
            meta: { title: '会员', icon: 'el-icon-s-help' },
            hidden: !permission.includes("/aladdin/member")
        },
        {
            path: 'server',
            name: 'Server',
            component: () =>
                import('@/views/aladdin/server/index'),
            meta: { title: '服务器', icon: 'el-icon-s-help' },
            hidden: !permission.includes("/aladdin/server")
        },
        {
            path: 'order',
            name: 'Order',
            component: () =>
                import('@/views/aladdin/order/index'),
            meta: { title: '订单', icon: 'el-icon-s-data' },
            hidden: !permission.includes("/aladdin/order")
        },
        {
            path: 'package',
            name: 'Package',
            component: () =>
                import('@/views/aladdin/package/index'),
            meta: { title: '套餐', icon: 'el-icon-s-data' },
            hidden: !permission.includes("/aladdin/package")
        },
    ]
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
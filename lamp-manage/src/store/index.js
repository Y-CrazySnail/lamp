import Vue from 'vue'
import Vuex from 'vuex'
import getters from './getters'
import app from './modules/app'
import settings from './modules/settings'
import auth from './modules/auth'
import user from './modules/system/user'
import role from './modules/system/role'
import permission from './modules/system/permission'
import dictionary from './modules/system/dictionary'
import car_film_tenant from './modules/car/car-film-tenant'
import car_film_product from './modules/car/car-film-product'
import car_film_price from './modules/car/car-film-price'
import car_film_quality from './modules/car/car-film-quality'
import car_film_user from './modules/car/car-film-user'
import base_car_brand from './modules/car/base-car-brand'
import base_car_model from './modules/car/base-car-model'
import base_car_dictionary from './modules/car/base-car-dictionary'
import cf_tenant from './modules/car/cf-tenant'
import cf_product from './modules/car/cf-product'
import zero_product from './modules/zero/product'
import zero_order from './modules/zero/order'
import zero_category from './modules/zero/category'
import zero_member from './modules/zero/member'
import one_tenant from './modules/one/tenant'
import one_store from './modules/one/store'
import one_category from './modules/one/category'
import one_spu from './modules/one/spu'
import one_sku from './modules/one/sku'
import one_order from './modules/one/order'
import one_user from './modules/one/user'
import aladdin_member from './modules/aladdin/member'
import aladdin_service from './modules/aladdin/service'
import aladdin_server from './modules/aladdin/server'
import aladdin_package from './modules/aladdin/package'
import aladdin_order from './modules/aladdin/order'

Vue.use(Vuex)

const store = new Vuex.Store({
    modules: {
        app,
        settings,
        auth,
        user,
        role,
        permission,
        dictionary,
        base_car_brand,
        base_car_model,
        base_car_dictionary,
        cf_tenant,
        cf_product,
        car_film_tenant,
        car_film_product,
        car_film_price,
        car_film_quality,
        car_film_user,
        zero_product,
        zero_order,
        zero_category,
        zero_member,
        one_tenant,
        one_store,
        one_category,
        one_spu,
        one_sku,
        one_order,
        one_user,
        aladdin_member,
        aladdin_service,
        aladdin_server,
        aladdin_package,
        aladdin_order
    },
    getters
})

export default store

import Vue from 'vue'
import App from './App.vue'
import router from './router'
import axios from 'axios'
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
Vue.use(ElementUI);
import vuex from 'vuex'
Vue.use(vuex);
import store from './store'
import vuescroll from 'vuescroll';
Vue.use(vuescroll);

Vue.config.productionTip = false
Vue.prototype.$echarts = window.echarts

Vue.prototype.$http = axios

new Vue({
  render: h => h(App),
  router,
  store
}).$mount('#app')

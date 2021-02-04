/*!

=========================================================
* BootstrapVue Argon Dashboard - v1.0.0
=========================================================

* Product Page: https://www.creative-tim.com/product/bootstrap-vue-argon-dashboard
* Copyright 2020 Creative Tim (https://www.creative-tim.com)

* Coded by www.creative-tim.com

=========================================================

* The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

*/
import Vue from 'vue';
import DashboardPlugin from './plugins/dashboard-plugin';
import App from './App.vue';
import VueMindmap from 'vue-mindmap'
import 'vue-mindmap/dist/vue-mindmap.css'
import VueCarousel from 'vue-carousel'
import "./plugins/axios"
import moment from 'moment'
import VueMoment from "vue-momentjs";

// router setup
import router from './routes/router';

import store from './store'

import { mapGetters } from 'vuex'

// vue-flatpickr => 컴포넌트 날짜 설정기능
import VueFlatPickr from 'vue-flatpickr-component';
import "flatpickr/dist/flatpickr.css";
import 'flatpickr/dist/themes/material_blue.css';
Vue.use(VueFlatPickr)

// plugin setup
Vue.use(VueCarousel)
Vue.use(DashboardPlugin);
Vue.use(VueMindmap)
Vue.use(VueMoment, moment);

/* eslint-disable no-new */
new Vue({
  el: '#app',
  render: h => h(App),
  store,
  router
});
